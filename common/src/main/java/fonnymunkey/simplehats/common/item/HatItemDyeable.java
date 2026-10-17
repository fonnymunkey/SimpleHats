package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class HatItemDyeable extends HatItem {

    public HatItemDyeable(HatEntry entry, Properties properties) {
        super(entry, properties);
    }

    private static final String[] colorList = new String[]{"§c", "§e", "§a", "§b", "§9", "§d", "§5"};

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, tooltipDisplay, tooltipAdder, tooltipFlag);
        MutableComponent component = Component.empty();
        char[] c = (Component.translatable("tooltip.simplehats.dyeable")).getString().toCharArray();
        for(int i=0; i<c.length; i++) {
            component.append(colorList[i%colorList.length] + c[i]);
        }
        tooltipAdder.accept(component);
    }
}
