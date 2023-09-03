package fonnymunkey.simplehats.mixin.core;

import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.common.data.CuriosSlotManager;
import top.theillusivec4.curios.mixin.CuriosImplMixinHooks;

import java.util.Map;

@Mixin(CuriosImplMixinHooks.class)
public class MixinCuriosImplMixinHooks {

    @Inject(
            method = "getItemStackSlots(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Map;",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void simplehats_getItemStackSlots(ItemStack stack, CallbackInfoReturnable<Map<String, ISlotType>> cir) {
        if(stack.getItem() instanceof HatItem) {
            Map<String, ISlotType> returnMap = cir.getReturnValue();
            ISlotType slotType = CuriosSlotManager.INSTANCE.getSlots().get("head");
            if(slotType != null) returnMap.put("head", slotType);
            cir.setReturnValue(returnMap);
        }
    }

    @Inject(
            method = "getItemStackSlots(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/Map;",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void simplehats_getItemStackSlots(ItemStack stack, LivingEntity livingEntity, CallbackInfoReturnable<Map<String, ISlotType>> cir) {
        if(stack.getItem() instanceof HatItem) {
            Map<String, ISlotType> returnMap = cir.getReturnValue();
            ISlotType slotType = CuriosImplMixinHooks.getEntitySlots(livingEntity.getType()).get("head");
            if(slotType != null) returnMap.put("head", slotType);
            cir.setReturnValue(returnMap);
        }
    }
}