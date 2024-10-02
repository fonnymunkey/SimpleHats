package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class HatItemDyeable extends HatItem {

    public HatItemDyeable(HatEntry entry) {
        super(entry);
    }

    public int getColor(ItemStack stack) {
        return 0xFF000000 | (stack.contains(DataComponentTypes.DYED_COLOR) ? stack.get(DataComponentTypes.DYED_COLOR).rgb() : this.getHatEntry().getHatDyeSettings().getColorCode());
    }

    private static final String[] colorList = new String[]{"\u00A7c", "\u00A7e", "\u00A7a", "\u00A7b", "\u00A79", "\u00A7d", "\u00A75"};

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(itemStack, context, tooltip, type);
        MutableText component = (MutableText) Text.of("");
        char[] c = (Text.translatable("tooltip.simplehats.dyeable")).getString().toCharArray();
        for(int i=0; i<c.length; i++) {
            component.append(colorList[i%colorList.length] + c[i]);
        }
        tooltip.add(component);
    }
}