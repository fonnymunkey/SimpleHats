package fonnymunkey.simplehats.compat;

import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class NoHatSlotCompat implements IHatSlotCompat {
    
    @Override
    public void registerHat(HatItem hat) { }
    
    @Override
    public void registerHatRenderers(List<? extends HatItem> hats) { }
    
    @Override
    public boolean isHatVisible(LivingEntity entity) {
        return false;
    }
}
