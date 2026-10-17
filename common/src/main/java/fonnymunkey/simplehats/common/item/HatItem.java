package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HatItem extends Item {

    private final HatEntry hatEntry;

    public HatItem(HatEntry entry) {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(entry.getHatRarity())
                .fireResistant());
        this.hatEntry = entry;
        SimpleHatsCommon.HAT_SLOT_COMPAT.registerHat(this);
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatVariantRange()>0) tooltipComponents.add(Component.translatable("tooltip.simplehats.variant"));
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatName().equalsIgnoreCase("special")) {
            if(itemStack.has(DataComponents.CUSTOM_MODEL_DATA)) {
                tooltipComponents.add(Component.translatable("tooltip.simplehats.special_true"));
            }
            else {
                tooltipComponents.add(Component.translatable("tooltip.simplehats.special_false"));
            }
        }
    }
}