package fonnymunkey.simplehats.platform.services;

import fonnymunkey.simplehats.platform.ConditionalService;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IAccessoryHelper extends ConditionalService {
    ItemStack getFirstHatEquipped(LivingEntity entity);
    boolean shouldRenderHat(LivingEntity entity);
}
