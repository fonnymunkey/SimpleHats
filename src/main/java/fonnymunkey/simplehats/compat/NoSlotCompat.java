package fonnymunkey.simplehats.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class NoSlotCompat implements ISlotCompat {

    @Override
    public ItemStack getVisibleHat(EntityPlayer entity) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean tryEquip(EntityPlayer player, ItemStack stack) {
        return false;
    }
}
