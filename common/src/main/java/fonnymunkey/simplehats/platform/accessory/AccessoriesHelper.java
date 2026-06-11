//package fonnymunkey.simplehats.platform.accessory;
//
//import fonnymunkey.simplehats.common.item.HatItem;
//import fonnymunkey.simplehats.platform.Services;
//import fonnymunkey.simplehats.platform.services.IAccessoryHelper;
//import fonnymunkey.simplehats.platform.services.IPlatformHelper;
//import io.wispforest.accessories.api.AccessoriesCapability;
//import io.wispforest.accessories.api.AccessoriesContainer;
//import io.wispforest.accessories.api.equip.EquipmentChecking;
//import io.wispforest.accessories.api.slot.SlotEntryReference;
//
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//
//public class AccessoriesHelper implements IAccessoryHelper {
//    @Override
//    public ItemStack getFirstHatEquipped(LivingEntity entity) {
//        var component = AccessoriesCapability.get(entity);
//        SlotEntryReference slotCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.COSMETICALLY_OVERRIDABLE);
//        SlotEntryReference slotNonCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.ACCESSORIES_ONLY);
//
//        if(slotCos != null) {
//            AccessoriesContainer container = slotCos.reference().slotContainer();
//            if(container != null && container.shouldRender(slotCos.reference().slot())) {
//                return slotCos.stack();
//            }
//        }
//        if(slotNonCos != null) {
//            AccessoriesContainer container = slotNonCos.reference().slotContainer();
//            if(container != null && container.shouldRender(slotNonCos.reference().slot())) {
//                return slotNonCos.stack();
//            }
//        }
//
//        return ItemStack.EMPTY;
//    }
//
//    @Override
//    public boolean shouldRenderHat(LivingEntity entity) {
//        var component = AccessoriesCapability.get(entity);
//        SlotEntryReference slotCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.COSMETICALLY_OVERRIDABLE);
//        SlotEntryReference slotNonCos = component.getFirstEquipped(stack -> stack.getItem() instanceof HatItem, EquipmentChecking.ACCESSORIES_ONLY);
//
//        if(slotCos != null) {
//            AccessoriesContainer container = slotCos.reference().slotContainer();
//            if(container != null && container.shouldRender(slotCos.reference().slot())) {
//                return true;
//            }
//        }
//
//        if(slotNonCos != null) {
//            AccessoriesContainer container = slotNonCos.reference().slotContainer();
//            if (container != null && container.shouldRender(slotNonCos.reference().slot())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean isActive() {
//        return Services.PLATFORM.isModLoaded("accessories");
//    }
//
//    @Override
//    public int priority() {
//        return 2000;
//    }
//}
