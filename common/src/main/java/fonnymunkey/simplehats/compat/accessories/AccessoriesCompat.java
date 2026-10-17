package fonnymunkey.simplehats.compat.accessories;

import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.compat.IHatSlotCompat;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.EquipmentChecking;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AccessoriesCompat implements IHatSlotCompat {
    
    private static final Accessory HAT_ACCESSORY = new Accessory() {
        @Override
        public DropRule getDropRule(ItemStack stack, SlotReference reference, DamageSource source) {
            if(reference.entity() instanceof Player && SimpleHatsConfigAbstract.keepHatOnDeath()) return DropRule.KEEP;
            else return DropRule.DEFAULT;
        }
        
        @Override
        public boolean canEquipFromUse(ItemStack stack) {
            return true;
        }
    };
    
    @Override
    public void registerHat(HatItem hat) {
        AccessoriesAPI.registerAccessory(hat, HAT_ACCESSORY);
    }
    
    @Override
    public void registerHatRenderers(List<? extends HatItem> hats) {
        for(HatItem hat : hats) {
            AccessoriesRendererRegistry.registerNoRenderer(hat);
        }
    }
    
    @Override
    public ItemStack getVisibleHat(LivingEntity entity) {
        AccessoriesCapability capability = AccessoriesCapability.get(entity);
        if(capability == null) return ItemStack.EMPTY;
        SlotEntryReference slotCos = capability.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.COSMETICALLY_OVERRIDABLE);
        if(isSlotRendered(slotCos)) return slotCos.stack();
        SlotEntryReference slotNonCos = capability.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.ACCESSORIES_ONLY);
        if(isSlotRendered(slotNonCos)) return slotNonCos.stack();
        return ItemStack.EMPTY;
    }
    
    private static boolean isSlotRendered(SlotEntryReference entry) {
        if(entry == null) return false;
        AccessoriesContainer container = entry.reference().slotContainer();
        return container != null && container.shouldRender(entry.reference().slot());
    }
}
