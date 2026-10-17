package fonnymunkey.simplehats.compat;

import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class NoHatSlotCompat implements IHatSlotCompat {
    
    @Override
    public void registerHat(HatItem hat) { }
    
    @Override
    public void registerHatRenderers(List<? extends HatItem> hats) { }
    
    @Override
    public ItemStack getVisibleHat(LivingEntity entity) {
        return ItemStack.EMPTY;
    }
}
