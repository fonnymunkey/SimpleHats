package fonnymunkey.simplehats.compat.accessories;

import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.compat.IHatSlotCompat;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.equip.EquipmentChecking;
import io.wispforest.accessories.api.events.DropRule;
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
            else return Accessory.super.getDropRule(stack, reference, source);
        }
        
        @Override
        public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
            return true;
        }
    };
    
    @Override
    public void registerHat(HatItem hat) {
        AccessoryRegistry.register(hat, HAT_ACCESSORY);
    }
    
    @Override
    public void registerHatRenderers(List<? extends HatItem> hats) {
        AccessoriesHatRenderer.register(hats);
    }
    
    @Override
    public boolean isHatVisible(LivingEntity entity) {
        AccessoriesCapability capability = AccessoriesCapability.get(entity);
        if(capability == null) return false;
        return isSlotRendered(capability.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.COSMETICALLY_OVERRIDABLE)) ||
                isSlotRendered(capability.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.ACCESSORIES_ONLY));
    }
    
    private static boolean isSlotRendered(SlotEntryReference entry) {
        if(entry == null) return false;
        AccessoriesContainer container = entry.reference().slotContainer();
        return container != null && container.shouldRender(entry.reference().slot());
    }
}
