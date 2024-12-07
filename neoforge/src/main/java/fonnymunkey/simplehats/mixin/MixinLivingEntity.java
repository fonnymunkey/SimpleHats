package fonnymunkey.simplehats.mixin;

import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(
            method = "getEquipmentSlotForItem",
            at = @At("TAIL"),
            cancellable = true
    )
    private void simplehats_getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if(stack.getItem() instanceof HatItem && SimpleHatsConfigAbstract.allowHatInHelmetSlot()) {
            cir.setReturnValue(EquipmentSlot.HEAD);
        }
    }
}