package fonnymunkey.simplehats.mixin.core;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "getPreferredEquipmentSlot", at = @At("TAIL"), cancellable = true)
    private void simplehats_getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if(stack.getItem() instanceof HatItem && SimpleHats.config.common.allowHatInHelmetSlot) {
            cir.setReturnValue(EquipmentSlot.HEAD);
        }
    }
}
