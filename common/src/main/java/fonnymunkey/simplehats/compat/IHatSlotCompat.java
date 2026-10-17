package fonnymunkey.simplehats.compat;

import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IHatSlotCompat {
    
    void registerHat(HatItem hat);
    
    void registerHatRenderers(List<? extends HatItem> hats);
    
    ItemStack getVisibleHat(LivingEntity entity);
}
