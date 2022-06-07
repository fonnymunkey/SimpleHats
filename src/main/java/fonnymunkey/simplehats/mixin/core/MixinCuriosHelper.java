package fonnymunkey.simplehats.mixin.core;

import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.common.CuriosHelper;

import java.util.Set;

@Mixin(CuriosHelper.class)
public class MixinCuriosHelper {

    @Inject(method = "getCurioTags", at = @At("RETURN"), cancellable = true, remap = false)
    public void simplehats_getCurioTags(Item item, CallbackInfoReturnable<Set<String>> ci) {
        if(item instanceof HatItem) {
            Set<String> set = ci.getReturnValue();
            set.add("head");
            ci.setReturnValue(set);
        }
    }
}
