package fonnymunkey.simplehats.common.item;

import java.util.function.Consumer;

import fonnymunkey.simplehats.util.HatEntry;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class HatItem extends Item {

    private final HatEntry hatEntry;

    public HatItem(Item.Properties properties, HatEntry entry) {
        super(properties
                .stacksTo(1)
                .rarity(entry.getHatRarity())
                .fireResistant());
        this.hatEntry = entry;
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatVariantRange()>0) builder.accept(Component.translatable("tooltip.simplehats.variant"));
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatName().equalsIgnoreCase("special")) {
            if(itemStack.has(DataComponents.CUSTOM_MODEL_DATA)) {
                builder.accept(Component.translatable("tooltip.simplehats.special_true"));
            }
            else {
                builder.accept(Component.translatable("tooltip.simplehats.special_false"));
            }
        }
    }
}