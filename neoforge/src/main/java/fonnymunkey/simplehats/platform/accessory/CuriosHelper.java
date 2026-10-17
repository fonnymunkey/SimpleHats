package fonnymunkey.simplehats.platform.accessory;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.platform.Services;
import fonnymunkey.simplehats.platform.services.IAccessoryHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosSlotTypes;
import top.theillusivec4.curios.api.CuriosTags;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CuriosHelper implements IAccessoryHelper {
    public CuriosHelper() {
        if (this.isActive()) {
            Impl.setupCuriosPredicate();
        }
    }

    @Override
    public ItemStack getFirstHatEquipped(LivingEntity entity) {
        var inventory = CuriosApi.getCuriosInventory(entity).orElse(null);
        if (inventory != null) {
            for (String slotId : inventory.getCurios().keySet()) {
                var handler = inventory.getStacksHandler(slotId).orElse(null);
                if (handler == null || !handler.isVisible())
                    continue;

                for (int slot = 0; slot < handler.getCosmeticStacks().getSlots(); slot++) {
                    var stack = handler.getCosmeticStacks().getStackInSlot(slot);

                    if (!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                        return stack;
                    }
                }

                for (int slot = 0; slot < handler.getStacks().getSlots(); slot++) {
                    var stack = handler.getStacks().getStackInSlot(slot);

                    if (!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                        return stack;
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean shouldRenderHat(LivingEntity entity) {
        var inventory = CuriosApi.getCuriosInventory(entity).orElse(null);
        if (inventory != null) {
            for (String slotId : inventory.getCurios().keySet()) {
                var handler = inventory.getStacksHandler(slotId).orElse(null);
                if (handler == null || !handler.isVisible())
                    continue;

                for (int slot = 0; slot < handler.getCosmeticStacks().getSlots(); slot++) {
                    var stack = handler.getCosmeticStacks().getStackInSlot(slot);

                    if (!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                        return true;
                    }
                }

                for (int slot = 0; slot < handler.getStacks().getSlots(); slot++) {
                    var stack = handler.getStacks().getStackInSlot(slot);

                    if (!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isActive() {
        return Services.PLATFORM.isModLoaded("curios");
    }

    private static class Impl {
        public static void setupCuriosPredicate() {
            CuriosSlotTypes.registerPredicate(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "validator"), (context, stack) -> {
                if (stack.getItem() instanceof HatItem)
                    return true;

                return stack.is(CuriosTags.HEAD);
            });
        }
    }
}
