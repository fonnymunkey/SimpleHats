package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
    public int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : this.getHatEntry().getHatDyeSettings().getColorCode();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TextComponent(
                "\u00A7c" + "D" +
                        "\u00A7e" + "y" +
                        "\u00A7a" + "e" +
                        "\u00A7b" + "a" +
                        "\u00A79" + "b" +
                        "\u00A7d" + "l" +
                        "\u00A75" + "e"
                ));
    }
}
