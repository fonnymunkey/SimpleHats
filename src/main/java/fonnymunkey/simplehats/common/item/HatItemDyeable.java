package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class HatItemDyeable extends HatItem implements DyeableLeatherItem {

    public HatItemDyeable(HatEntry entry) {
        super(entry);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : this.getHatEntry().getHatDyeSettings().getColorCode();
    }

    private static final String[] colorList = new String[]{"\u00A7c", "\u00A7e", "\u00A7a", "\u00A7b", "\u00A79", "\u00A7d", "\u00A75"};

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
        MutableComponent component = Component.empty();
        char[] c = (Component.translatable("tooltip.simplehats.dyeable")).getString().toCharArray();
        for(int i=0; i<c.length; i++) {
            component.append(colorList[i%colorList.length] + c[i]);
        }
        tooltip.add(component);
    }
}