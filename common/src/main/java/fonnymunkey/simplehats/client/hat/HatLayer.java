package fonnymunkey.simplehats.client.hat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.platform.ClientServices;
import fonnymunkey.simplehats.platform.Services;
import fonnymunkey.simplehats.util.HatEntry;
import org.jspecify.annotations.NonNull;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.client.renderer.entity.state.ZombieVillagerRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HatLayer<S extends LivingEntityRenderState, M extends EntityModel<S> & HeadedModel> extends RenderLayer<S, M> {
	public HatLayer(RenderLayerParent<S, @NonNull M> renderer) {
		super(renderer);
	}

	public static void extractRenderState(LivingEntity entity, HatRenderState renderState) {
		renderState.itemStackRenderState.clear();
		renderState.hatStack = ItemStack.EMPTY;

		//Hacky fix for first person render mods also rendering player layers over the camera
		if (entity == Minecraft.getInstance().getCameraEntity() && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON && SimpleHatsConfigAbstract.forceFirstPersonNoRender())
			return;

		ItemStack cosmeticHatStack = Services.ACCESSORY.getFirstHatEquipped(entity);
		boolean renderingHat = false;
		ItemModelResolver resolver = Minecraft.getInstance().getItemModelResolver();

		if (cosmeticHatStack != null && cosmeticHatStack != ItemStack.EMPTY) {
			if (Services.ACCESSORY.shouldRenderHat(entity)) {
				renderingHat = true;
				resolver.updateForLiving(renderState.itemStackRenderState, cosmeticHatStack, ItemDisplayContext.HEAD, entity);
				renderState.hatStack = cosmeticHatStack;
			}
		}

		if (renderingHat) {
			HatEntry.HatParticleSettings particleSettings = ((HatItem) renderState.hatStack.getItem()).getHatEntry().getHatParticleSettings();
			if (particleSettings.getUseParticles() && !Minecraft.getInstance().isPaused() && entity.getRandom().nextFloat() < (entity.isInvisible() ? particleSettings.getParticleFrequency() / 2 : particleSettings.getParticleFrequency())) {
				ParticleType<?> particleType = particleSettings.getParticleType();
				ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

				double d0 = entity.getRandom().nextGaussian() * 0.02D,
					d1 = entity.getRandom().nextGaussian() * 0.02D,
					d2 = entity.getRandom().nextGaussian() * 0.02D,
					y = switch(particleSettings.getParticleMovement()) {
						case TRAILING_HEAD -> entity.getY()+1.75;
						case TRAILING_FEET -> entity.getY()+0.25;
						case TRAILING_FULL -> entity.getRandomY();
					};

				if(particleType instanceof SimpleParticleType particleEffect) {
					particleEngine.createParticle(particleEffect, entity.getX() + entity.getRandom().nextFloat() - 0.5, y, entity.getZ() + entity.getRandom().nextFloat() - 0.5, d0, d1, d2);
				} else if (particleType == ParticleTypes.ENTITY_EFFECT) {
					particleEngine.createParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, entity.getRandom().nextFloat(), entity.getRandom().nextFloat(), entity.getRandom().nextFloat()), entity.getX() + entity.getRandom().nextFloat() - 0.5, y, entity.getZ() + entity.getRandom().nextFloat() - 0.5, d0, d1, d2);
				}
			}
		}
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
		HatRenderState hatRenderState = ClientServices.CLIENT_PLATFORM.getHatState(state);
		if (hatRenderState.hatStack != null && !hatRenderState.hatStack.isEmpty()) {
			submitItem(poseStack, submitNodeCollector, lightCoords, state, hatRenderState.itemStackRenderState);
		}
	}
	
	private void submitItem(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, ItemStackRenderState itemStackRenderState) {
		if(!state.isInvisible) {
			poseStack.pushPose();
			
			//Slightly scale up to fix some skin layer difference issues
			poseStack.scale(1.01F, 1.01F, 1.01F);
			poseStack.translate(0.0F, 0.0F - SimpleHatsConfigAbstract.hatYOffset(), 0.0F);
			
			boolean flag = state instanceof VillagerRenderState || state instanceof ZombieVillagerRenderState;
			if(state.isBaby && !(state instanceof VillagerRenderState)) {
				poseStack.translate(0.0F, 0.03125F, 0.0F);
				poseStack.scale(0.7F, 0.7F, 0.7F);
				poseStack.translate(0.0F, 1.0F, 0.0F);
			}
			
			this.getParentModel().getHead().translateAndRotate(poseStack);
			translateToHead(poseStack, flag);

			itemStackRenderState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
			
			poseStack.popPose();
		}
	}
	
	private static void translateToHead(PoseStack pPoseStack, boolean pIsVillager) {
		pPoseStack.translate(0.0F, -0.25F, 0.0F);
		pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		pPoseStack.scale(0.625F, -0.625F, -0.625F);
		if(pIsVillager) {
			pPoseStack.translate(0.0F, 0.1875F, 0.0F);
		}
	}
}
