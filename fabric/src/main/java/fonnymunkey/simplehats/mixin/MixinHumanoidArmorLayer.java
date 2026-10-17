package fonnymunkey.simplehats.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.platform.ClientServices;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Mixin(HumanoidArmorLayer.class)
public abstract class MixinHumanoidArmorLayer<S extends HumanoidRenderState> {
    
    @Inject(
            method = "renderArmorPiece",
            at = @At("HEAD"),
            cancellable = true
    )
    public void simplehats_renderArmorPiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, EquipmentSlot slot, int lightCoords, S state, CallbackInfo ci) {
        if (state instanceof AvatarRenderState && slot.equals(EquipmentSlot.HEAD)) {
            var hatRenderState = ClientServices.CLIENT_PLATFORM.getHatState(state);

            if (!hatRenderState.hatStack.isEmpty()) {
                ci.cancel();
            }
        }
    }
}
