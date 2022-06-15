package fonnymunkey.simplehats.mixin.core;

import dev.emi.trinkets.api.TrinketsApi;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public class MixinHumanoidArmorLayer {

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    public void simplehats_renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, CallbackInfo ci) {
        if(entity instanceof PlayerEntity && armorSlot.equals(EquipmentSlot.HEAD)) {
            TrinketsApi.getTrinketComponent(entity).ifPresent(component ->
                    component.forEach((slotReference, stack) -> {
                        if(stack.getItem() instanceof HatItem) ci.cancel();
                        })
                    );
        }
    }
}
