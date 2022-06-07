package fonnymunkey.simplehats.mixin.core;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mixin(HumanoidArmorLayer.class)
public class MixinHumanoidArmorLayer {

    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    public void simplehats_renderArmorPiece(PoseStack pose, MultiBufferSource buffer, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> model, CallbackInfo ci) {
        if(entity instanceof Player player && slot.equals(EquipmentSlot.HEAD)) {
            CuriosApi.getCuriosHelper().getCuriosHandler(player)
                    .ifPresent(handler -> handler.getCurios()
                    .forEach((id, stacksHandler) -> {
                        IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                        IDynamicStackHandler cosmeticStacksHandler = stacksHandler.getCosmeticStacks();
                        for(int i = 0; i < stackHandler.getSlots(); i++) {
                            ItemStack stack = cosmeticStacksHandler.getStackInSlot(i);
                            if(stack.getItem() instanceof HatItem) ci.cancel();
                        }
                    }));
        }
    }
}
