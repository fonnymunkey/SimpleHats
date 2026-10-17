package fonnymunkey.simplehats.mixin;

import fonnymunkey.simplehats.client.SimpleHatsClient;
import fonnymunkey.simplehats.client.hat.HatLayer;
import fonnymunkey.simplehats.client.hat.HatRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState> {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void simplehats_prepareRenderState(T entity, S state, float partialTicks, CallbackInfo ci) {
        HatRenderState hatRenderState = new HatRenderState();
        HatLayer.extractRenderState(entity, hatRenderState);
        state.setRenderData(SimpleHatsClient.HAT_RENDER_STATE_KEY, hatRenderState);
    }
}
