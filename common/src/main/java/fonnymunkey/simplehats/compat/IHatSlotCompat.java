package fonnymunkey.simplehats.compat;

import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface IHatSlotCompat {
    
    void registerHat(HatItem hat);
    
    void registerHatRenderers(List<? extends HatItem> hats);
    
    boolean isHatVisible(LivingEntity entity);
}
