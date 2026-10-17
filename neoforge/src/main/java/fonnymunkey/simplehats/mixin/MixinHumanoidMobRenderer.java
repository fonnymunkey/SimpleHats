package fonnymunkey.simplehats.mixin;

import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.client.hat.HatRenderer;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class MixinHumanoidMobRenderer {
    
    @Inject(
            method = "extractHumanoidRenderState",
            at = @At("TAIL")
    )
    private static void simplehats_hideHelmetOrSpawnParticles(LivingEntity entity, HumanoidRenderState state, float partialTick, ItemModelResolver itemModelResolver, CallbackInfo ci) {
        if(entity instanceof Player) {
            if(SimpleHatsCommon.HAT_SLOT_COMPAT.isHatVisible(entity)) {
                state.headEquipment = ItemStack.EMPTY;
            }
            else if(entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HatItem hatItem) {
                HatRenderer.spawnParticles(hatItem.getHatEntry().getHatParticleSettings(), entity);
            }
        }
    }
}
