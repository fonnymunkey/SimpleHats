package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import java.util.List;

public class HatItemDyeable extends HatItem implements DyeableItem {

    public HatItemDyeable(HatEntry entry) {
        super(entry);
    }

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound compoundtag = stack.getSubNbt("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : this.getHatEntry().getHatDyeSettings().getColorCode();
    }

    private static final String[] colorList = new String[]{"\u00A7c", "\u00A7e", "\u00A7a", "\u00A7b", "\u00A79", "\u00A7d", "\u00A75"};

    @Override
    public void appendTooltip(ItemStack itemStack, World level, List<Text> tooltip, TooltipContext flag) {
        super.appendTooltip(itemStack, level, tooltip, flag);
        LiteralText component = new LiteralText("");
        char[] c = (new TranslatableText("tooltip.simplehats.dyeable")).getString().toCharArray();
        for(int i=0; i<c.length; i++) {
            component.append(colorList[i%colorList.length] + c[i]);
        }
        tooltip.add(component);
    }
}