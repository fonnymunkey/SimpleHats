package fonnymunkey.simplehats.mixin.core;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(TrinketsApi.class)
public class MixinTrinketsApi {

    @Inject(method = "evaluatePredicateSet", at = @At("RETURN"), remap = false, cancellable = true)
    private static void simplehats_evaluatePredicateSet(Set<Identifier> set, ItemStack stack, SlotReference ref, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if(stack.getItem() instanceof HatItem && ref.inventory().getSlotType().getName().equalsIgnoreCase("hat")) {
            cir.setReturnValue(true);
        }
    }
}