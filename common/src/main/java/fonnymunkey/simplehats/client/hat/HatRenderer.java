package fonnymunkey.simplehats.client.hat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.client.renderer.entity.state.ZombieVillagerRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HatRenderer {

	public static boolean shouldRender(LivingEntity entity) {
		//Hacky fix for first person render mods also rendering player layers over the camera
		return !(entity == Minecraft.getInstance().getCameraEntity() && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON && SimpleHatsConfigAbstract.forceFirstPersonNoRender());
	}

	public static ItemStackRenderState extractHat(ItemStack stack, LivingEntity entity) {
		ItemStackRenderState stackState = new ItemStackRenderState();
		Minecraft.getInstance().getItemModelResolver().updateForLiving(stackState, stack, ItemDisplayContext.HEAD, entity);

		if(entity instanceof Player && stack.getItem() instanceof HatItem hatItem) {
			spawnParticles(hatItem.getHatEntry().getHatParticleSettings(), entity);
		}
		return stackState;
	}

	public static void submitHat(ItemStackRenderState stackState, LivingEntityRenderState entityState, EntityModel<?> model, PoseStack poseStack, SubmitNodeCollector collector, int light) {
		if(stackState == null || stackState.isEmpty() || entityState.isInvisible || !(model instanceof HeadedModel headedModel)) return;

		poseStack.pushPose();

		//Slightly scale up to fix some skin layer difference issues
		poseStack.scale(1.01F, 1.01F, 1.01F);
		poseStack.translate(0.0F, 0.0F - SimpleHatsConfigAbstract.hatYOffset(), 0.0F);

		model.root().translateAndRotate(poseStack);
		headedModel.translateToHead(poseStack);
		translateToHead(poseStack, entityState instanceof VillagerRenderState || entityState instanceof ZombieVillagerRenderState);
		stackState.submit(poseStack, collector, light, OverlayTexture.NO_OVERLAY, entityState.outlineColor);

		poseStack.popPose();
	}

	public static void spawnParticles(HatEntry.HatParticleSettings particleSettings, LivingEntity livingEntity) {
		Minecraft mc = Minecraft.getInstance();
		if(livingEntity == mc.getCameraEntity() && !mc.gameRenderer.getMainCamera().isDetached()) return;
		if(particleSettings.getUseParticles() && !Minecraft.getInstance().isPaused() && livingEntity.getRandom().nextFloat() < (livingEntity.isInvisible() ? particleSettings.getParticleFrequency()/2 : particleSettings.getParticleFrequency())) {
			double d0 = livingEntity.getRandom().nextGaussian() * 0.02D,
					d1 = livingEntity.getRandom().nextGaussian() * 0.02D,
					d2 = livingEntity.getRandom().nextGaussian() * 0.02D,
					y = switch(particleSettings.getParticleMovement()) {
						case TRAILING_HEAD -> livingEntity.getY()+1.75;
						case TRAILING_FEET -> livingEntity.getY()+0.25;
						case TRAILING_FULL -> livingEntity.getRandomY();
					};
			ParticleType<?> particleType = particleSettings.getParticleType();
			if(particleType instanceof SimpleParticleType particleEffect) {
				livingEntity.level().addParticle(particleEffect, livingEntity.getX() + livingEntity.getRandom().nextFloat() - 0.5, y, livingEntity.getZ() + livingEntity.getRandom().nextFloat() - 0.5, d0, d1,d2);
			}
			else if(particleType == ParticleTypes.ENTITY_EFFECT) {
				livingEntity.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, livingEntity.getRandom().nextFloat(), livingEntity.getRandom().nextFloat(), livingEntity.getRandom().nextFloat()), livingEntity.getX() + livingEntity.getRandom().nextFloat() - 0.5, y, livingEntity.getZ() + livingEntity.getRandom().nextFloat() - 0.5, d0, d1, d2);
			}
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
