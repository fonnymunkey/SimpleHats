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
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;

public class CuriosCompat implements IHatSlotCompat {
    
    private static final ICurioItem HAT_CURIO = new ICurioItem() {
        @Override
        public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack) {
            if(slotContext.entity() instanceof Player && SimpleHatsConfigAbstract.keepHatOnDeath()) return ICurio.DropRule.ALWAYS_KEEP;
            else return ICurio.DropRule.DEFAULT;
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
    public void registerHatRenderers(List<? extends HatItem> hats) { }
    
    @Override
    public ItemStack getVisibleHat(LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity).map(inventory -> {
            for(ICurioStacksHandler handler : inventory.getCurios().values()) {
                for(int i = 0; i < handler.getSlots(); i++) {
                    if(!handler.getRenders().get(i)) continue;
                    ItemStack cosmetic = handler.getCosmeticStacks().getStackInSlot(i);
                    if(cosmetic.getItem() instanceof HatItem) return cosmetic;
                    ItemStack stack = handler.getStacks().getStackInSlot(i);
                    if(stack.getItem() instanceof HatItem) return stack;
                }
            }
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }
}
