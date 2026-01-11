package fonnymunkey.simplehats.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.common.item.HatItem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.EquipmentChecking;
import io.wispforest.accessories.api.slot.SlotEntryReference;
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

import java.util.Optional;

@Mixin(HumanoidArmorLayer.class)
public abstract class MixinHumanoidArmorLayer {
    
    @Inject(
            method = "renderArmorPiece",
            at = @At("HEAD"),
            cancellable = true
    )
    public void simplehats_renderArmorPiece(PoseStack matrices, MultiBufferSource vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, HumanoidModel<LivingEntity> model, CallbackInfo ci) {
        if(entity instanceof Player && armorSlot.equals(EquipmentSlot.HEAD)) {
            Optional.ofNullable(AccessoriesCapability.get(entity)).ifPresent(component -> {
                SlotEntryReference slotCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.COSMETICALLY_OVERRIDABLE);
                SlotEntryReference slotNonCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.ACCESSORIES_ONLY);
                boolean hide = false;
                if(slotCos != null) {
                    AccessoriesContainer container = slotCos.reference().slotContainer();
                    if(container != null && container.shouldRender(slotCos.reference().slot())) hide = true;
                }
                if(slotNonCos != null) {
                    AccessoriesContainer container = slotNonCos.reference().slotContainer();
                    if(container != null && container.shouldRender(slotNonCos.reference().slot())) hide = true;
                }
                if(hide) ci.cancel();
            });
        }
    }
}