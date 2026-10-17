package fonnymunkey.simplehats.platform.accessory;

import fonnymunkey.simplehats.platform.services.IAccessoryHelper;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class NoAccessoryHelper implements IAccessoryHelper {
    @Override
    public ItemStack getFirstHatEquipped(LivingEntity entity) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean shouldRenderHat(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public int priority() {
        return Integer.MAX_VALUE;
    }
}
