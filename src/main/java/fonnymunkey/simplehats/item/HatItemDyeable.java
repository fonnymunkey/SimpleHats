package fonnymunkey.simplehats.item;

import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class HatItemDyeable extends HatItem {

    private static final String[] colorList = new String[]{"\u00A7c", "\u00A7e", "\u00A7a", "\u00A7b", "\u00A79", "\u00A7d", "\u00A75"};

    public HatItemDyeable(HatEntry entry) {
        super(entry);
    }

    public boolean hasColor(ItemStack stack) {
        NBTTagCompound display = stack.getSubCompound("display");
        return display != null && display.hasKey("color", 3);
    }

    public int getColor(ItemStack stack) {
        return this.hasColor(stack) ? stack.getSubCompound("display").getInteger("color") : this.getHatEntry().getHatDyeSettings().getColorCode();
    }

    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubCompound("display").setInteger("color", color);
    }

    public void removeColor(ItemStack stack) {
        NBTTagCompound display = stack.getSubCompound("display");
        if(display != null) display.removeTag("color");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        StringBuilder builder = new StringBuilder();
        char[] c = I18n.format("tooltip.simplehats.dyeable").toCharArray();
        for(int i = 0; i < c.length; i++) {
            builder.append(colorList[i % colorList.length]).append(c[i]);
        }
        tooltip.add(builder.toString());
    }
}
