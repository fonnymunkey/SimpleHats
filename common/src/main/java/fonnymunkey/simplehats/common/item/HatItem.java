package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class HatItem extends Item {

    private final HatEntry hatEntry;

    public HatItem(HatEntry entry, Item.Properties properties) {
        super(applyHelmetSlot(properties
                .stacksTo(1)
                .rarity(entry.getHatRarity())
                .fireResistant()));
        this.hatEntry = entry;
        SimpleHatsCommon.HAT_SLOT_COMPAT.registerHat(this);
    }

    private static Item.Properties applyHelmetSlot(Item.Properties properties) {
        return SimpleHatsConfigAbstract.allowHatInHelmetSlot() ? properties.equippableUnswappable(EquipmentSlot.HEAD) : properties;
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        if(this.getHatEntry().getHatVariantRange()>0) tooltipAdder.accept(Component.translatable("tooltip.simplehats.variant"));
        if(this.getHatEntry().getHatName().equalsIgnoreCase("special")) {
            if(itemStack.has(DataComponents.CUSTOM_MODEL_DATA)) {
                tooltipAdder.accept(Component.translatable("tooltip.simplehats.special_true"));
            }
            else {
                tooltipAdder.accept(Component.translatable("tooltip.simplehats.special_false"));
            }
        }
    }
}