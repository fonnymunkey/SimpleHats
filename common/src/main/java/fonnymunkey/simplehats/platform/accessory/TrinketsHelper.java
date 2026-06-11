package fonnymunkey.simplehats.platform.accessory;

import eu.pb4.trinkets.api.TrinketsApi;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.platform.Services;
import fonnymunkey.simplehats.platform.services.IAccessoryHelper;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TrinketsHelper implements IAccessoryHelper {
    @Override
    public ItemStack getFirstHatEquipped(LivingEntity entity) {
        var attachment = TrinketsApi.getAttachment(entity);
        for (String slotId : attachment.getInventories().keySet()) {
            var inventory = attachment.getInventory(slotId);
            if (inventory == null)
                continue;

            for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
                ItemStack stack = inventory.getItem(slot);
                if (!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                    if (!inventory.isVisible(slot))
                        return ItemStack.EMPTY;

                    return stack;
                }
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean shouldRenderHat(LivingEntity entity) {
        var attachment = TrinketsApi.getAttachment(entity);
        for (String slotId : attachment.getInventories().keySet()) {
            var inventory = attachment.getInventory(slotId);
            if (inventory == null)
                continue;

            for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
                ItemStack stack = inventory.getItem(slot);
                if (!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                    return inventory.isVisible(slot);
                }
            }
        }

        return false;
    }

    @Override
    public boolean isActive() {
        return Services.PLATFORM.isModLoaded("trinkets_updated");
    }
}
