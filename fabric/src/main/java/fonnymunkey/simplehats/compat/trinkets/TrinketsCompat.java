package fonnymunkey.simplehats.compat.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.compat.IHatSlotCompat;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TrinketsCompat implements IHatSlotCompat {
    
    private static final Trinket HAT_TRINKET = new Trinket() {
        @Override
        public TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
            if(entity instanceof Player && SimpleHatsConfigAbstract.keepHatOnDeath()) return TrinketEnums.DropRule.KEEP;
            else return TrinketEnums.DropRule.DEFAULT;
        }
    };
    
    public TrinketsCompat() {
        UseItemCallback.EVENT.register((player, level, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            if(stack.getItem() instanceof HatItem && TrinketItem.equipItem(player, stack)) {
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }
            return InteractionResultHolder.pass(ItemStack.EMPTY);
        });
    }
    
    @Override
    public void registerHat(HatItem hat) {
        TrinketsApi.registerTrinket(hat, HAT_TRINKET);
    }
    
    @Override
    public void registerHatRenderers(List<? extends HatItem> hats) { }
    
    @Override
    public ItemStack getVisibleHat(LivingEntity entity) {
        return TrinketsApi.getTrinketComponent(entity)
                .flatMap(component -> component.getEquipped(stack -> stack.getItem() instanceof HatItem).stream().findFirst())
                .map(Tuple::getB)
                .orElse(ItemStack.EMPTY);
    }
}
