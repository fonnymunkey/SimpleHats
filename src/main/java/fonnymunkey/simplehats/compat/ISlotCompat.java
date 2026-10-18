package fonnymunkey.simplehats.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISlotCompat {

    ItemStack getVisibleHat(EntityPlayer entity);

    boolean tryEquip(EntityPlayer player, ItemStack stack);
}
