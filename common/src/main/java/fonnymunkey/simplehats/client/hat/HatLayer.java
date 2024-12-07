package fonnymunkey.simplehats.client.hat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.util.HatEntry;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.EquipmentChecking;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class HatLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
	
	public HatLayer(RenderLayerParent<T,M> renderer) {
		super(renderer);
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
		//Hacky fix for first person render mods also rendering player layers over the camera
		if(livingEntity == Minecraft.getInstance().cameraEntity && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON && SimpleHatsConfigAbstract.forceFirstPersonNoRender()) return;
		
		Optional.ofNullable(AccessoriesCapability.get(livingEntity)).ifPresent(component -> {
			SlotEntryReference slotCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.COSMETICALLY_OVERRIDABLE);
			SlotEntryReference slotNonCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.ACCESSORIES_ONLY);
			boolean renderingCos = false;
			if(slotCos != null) {
				AccessoriesContainer container = slotCos.reference().slotContainer();
				if(container != null && container.shouldRender(slotCos.reference().slot())) {
					renderingCos = true;
					render(slotCos.stack(), poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, age, netHeadYaw, headPitch);
				}
			}
			if(slotNonCos != null && !renderingCos) {
				AccessoriesContainer container = slotNonCos.reference().slotContainer();
				if(container != null && container.shouldRender(slotNonCos.reference().slot())) {
					render(slotNonCos.stack(), poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, age, netHeadYaw, headPitch);
				}
			}
		});
	}
	
	private void render(ItemStack itemStack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
		if(!livingEntity.isInvisible()) {
			poseStack.pushPose();
			
			//Slightly scale up to fix some skin layer difference issues
			poseStack.scale(1.01F, 1.01F, 1.01F);
			poseStack.translate(0.0F, 0.0F - SimpleHatsConfigAbstract.hatYOffset(), 0.0F);
			
			boolean flag = livingEntity instanceof Villager || livingEntity instanceof ZombieVillager;
			if(livingEntity.isBaby() && !(livingEntity instanceof Villager)) {
				poseStack.translate(0.0F, 0.03125F, 0.0F);
				poseStack.scale(0.7F, 0.7F, 0.7F);
				poseStack.translate(0.0F, 1.0F, 0.0F);
			}
			
			this.getParentModel().getHead().translateAndRotate(poseStack);
			translateToHead(poseStack, flag);
			Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(livingEntity, itemStack, ItemDisplayContext.HEAD, false, poseStack, buffer, packedLight);
			
			poseStack.popPose();
		}
		if(livingEntity instanceof Player) {
			HatEntry.HatParticleSettings particleSettings = ((HatItem)itemStack.getItem()).getHatEntry().getHatParticleSettings();
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