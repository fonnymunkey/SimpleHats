package fonnymunkey.simplehats.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.SimpleHatsCommon;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class MixinHumanoidArmorLayer {
    
    @Inject(
            method = "renderArmorPiece",
            at = @At("HEAD"),
            cancellable = true
    )
    public void simplehats_renderArmorPiece(PoseStack matrices, MultiBufferSource vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, HumanoidModel<LivingEntity> model, CallbackInfo ci) {
        if(entity instanceof Player && armorSlot.equals(EquipmentSlot.HEAD) && !SimpleHatsCommon.HAT_SLOT_COMPAT.getVisibleHat(entity).isEmpty()) {
            ci.cancel();
        }
    }
}
