package fonnymunkey.simplehats.client.hat;

import dev.emi.trinkets.api.TrinketsApi;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.RotationAxis;

public class HatLayer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
	
	public HatLayer(FeatureRendererContext<T,M> renderer) {
		super(renderer);
	}
	
	@Override
	public void render(MatrixStack poseStack, VertexConsumerProvider buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
		//Hacky fix for first person render mods also rendering player layers over the camera
		if(livingEntity == MinecraftClient.getInstance().cameraEntity && MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON && SimpleHats.config.client.forceFirstPersonNoRender) return;
		
		TrinketsApi.getTrinketComponent(livingEntity)
				   .ifPresent(component -> component
						   .forEach((slotReference, itemStack) -> {
							   if(!itemStack.isEmpty() && itemStack.getItem() instanceof HatItem) {
								   render(itemStack, poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, age, netHeadYaw, headPitch);
							   }
						   }));
	}
	
	private void render(ItemStack itemStack, MatrixStack poseStack, VertexConsumerProvider buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
		if(!livingEntity.isInvisible()) {
			poseStack.push();
			
			//Slightly scale up to fix some skin layer difference issues
			poseStack.scale(1.01F, 1.01F, 1.01F);
			poseStack.translate(0.0F, 0.0F - SimpleHats.config.client.hatYOffset, 0.0F);
			
			boolean flag = livingEntity instanceof VillagerEntity || livingEntity instanceof ZombieVillagerEntity;
			if(livingEntity.isBaby() && !(livingEntity instanceof VillagerEntity)) {
				poseStack.translate(0.0F, 0.03125F, 0.0F);
				poseStack.scale(0.7F, 0.7F, 0.7F);
				poseStack.translate(0.0F, 1.0F, 0.0F);
			}
			
			this.getContextModel().getHead().rotate(poseStack);
			translateToHead(poseStack, flag);
			MinecraftClient.getInstance().getEntityRenderDispatcher().getHeldItemRenderer().renderItem(livingEntity, itemStack, ModelTransformationMode.HEAD, false, poseStack, buffer, packedLight);
			
			poseStack.pop();
		}
		if(livingEntity instanceof PlayerEntity) {
			HatEntry.HatParticleSettings particleSettings = ((HatItem)itemStack.getItem()).getHatEntry().getHatParticleSettings();
			if(particleSettings.getUseParticles() && !MinecraftClient.getInstance().isPaused() && livingEntity.getRandom().nextFloat() < (livingEntity.isInvisible() ? particleSettings.getParticleFrequency()/2 : particleSettings.getParticleFrequency())) {
				double d0 = livingEntity.getRandom().nextGaussian() * 0.02D,
						d1 = livingEntity.getRandom().nextGaussian() * 0.02D,
						d2 = livingEntity.getRandom().nextGaussian() * 0.02D,
						y = switch(particleSettings.getParticleMovement()) {
							case TRAILING_HEAD -> livingEntity.getY()+1.75;
							case TRAILING_FEET -> livingEntity.getY()+0.25;
							case TRAILING_FULL -> livingEntity.getRandomBodyY();
						};
				ParticleType<?> particleType = particleSettings.getParticleType();
				if(particleType instanceof ParticleEffect particleEffect) {
					livingEntity.getWorld().addParticle(particleEffect, livingEntity.getX() + livingEntity.getRandom().nextFloat() - 0.5, y, livingEntity.getZ() + livingEntity.getRandom().nextFloat() - 0.5, d0, d1,d2);
				}
				else if(particleType == ParticleTypes.ENTITY_EFFECT) {
					livingEntity.getWorld().addParticle(EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, livingEntity.getRandom().nextFloat(), livingEntity.getRandom().nextFloat(), livingEntity.getRandom().nextFloat()), livingEntity.getX() + livingEntity.getRandom().nextFloat() - 0.5, y, livingEntity.getZ() + livingEntity.getRandom().nextFloat() - 0.5, d0, d1,d2);
				}
			}
		}
	}
	
	private static void translateToHead(MatrixStack pPoseStack, boolean pIsVillager) {
		pPoseStack.translate(0.0F, -0.25F, 0.0F);
		pPoseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
		pPoseStack.scale(0.625F, -0.625F, -0.625F);
		if(pIsVillager) {
			pPoseStack.translate(0.0F, 0.1875F, 0.0F);
		}
	}
}