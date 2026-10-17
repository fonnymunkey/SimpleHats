package fonnymunkey.simplehats.compat.curios;

import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.compat.IHatSlotCompat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.common.DropRule;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;

public class CuriosCompat implements IHatSlotCompat {
    
    private static final ICurioItem HAT_CURIO = new ICurioItem() {
        @Override
        public DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack) {
            if(slotContext.entity() instanceof Player && SimpleHatsConfigAbstract.keepHatOnDeath()) return DropRule.ALWAYS_KEEP;
            else return ICurioItem.super.getDropRule(slotContext, source, recentlyHit, stack);
        }
        
        @Override
        public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
            return true;
        }
    };
    
    @Override
    public void registerHat(HatItem hat) {
        CuriosApi.registerCurio(hat, HAT_CURIO);
    }
    
    @Override
    public void registerHatRenderers(List<? extends HatItem> hats) {
        CuriosHatRenderer.register(hats);
    }
    
    @Override
    public boolean isHatVisible(LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity).map(inventory -> {
            for(ICurioStacksHandler handler : inventory.getCurios().values()) {
                for(int i = 0; i < handler.getSlots(); i++) {
                    if(handler.getRenders().get(i) &&
                            (handler.getCosmeticStacks().getStackInSlot(i).getItem() instanceof HatItem || handler.getStacks().getStackInSlot(i).getItem() instanceof HatItem)) {
                        return true;
                    }
                }
            }
            return false;
        }).orElse(false);
    }
}
