package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HatItemDyeable extends HatItem {

    public HatItemDyeable(HatEntry entry) {
        super(entry);
    }

    public int getColor(ItemStack stack) {
        return 0xFF000000 | (stack.has(DataComponents.DYED_COLOR) ? stack.get(DataComponents.DYED_COLOR).rgb() : this.getHatEntry().getHatDyeSettings().getColorCode());
    }

    private static final String[] colorList = new String[]{"\u00A7c", "\u00A7e", "\u00A7a", "\u00A7b", "\u00A79", "\u00A7d", "\u00A75"};

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, tooltipComponents, tooltipFlag);
        MutableComponent component = Component.empty();
        char[] c = (Component.translatable("tooltip.simplehats.dyeable")).getString().toCharArray();
        for(int i=0; i<c.length; i++) {
            component.append(colorList[i%colorList.length] + c[i]);
        }
        tooltipComponents.add(component);
    }
}