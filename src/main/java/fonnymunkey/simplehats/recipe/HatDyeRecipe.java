package fonnymunkey.simplehats.recipe;

import fonnymunkey.simplehats.item.HatItemDyeable;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HatDyeRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        ItemStack hat = ItemStack.EMPTY;
        boolean dye = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack.isEmpty()) continue;
            if(stack.getItem() instanceof HatItemDyeable) {
                if(!hat.isEmpty()) return false;
                hat = stack;
            }
            else if(DyeUtils.isDye(stack)) dye = true;
            else return false;
        }
        return !hat.isEmpty() && dye;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack result = ItemStack.EMPTY;
        HatItemDyeable hat = null;
        int[] color = new int[3];
        int brightness = 0;
        int count = 0;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack.isEmpty()) continue;
            int r, g, b;
            if(stack.getItem() instanceof HatItemDyeable) {
                if(hat != null) return ItemStack.EMPTY;
                hat = (HatItemDyeable)stack.getItem();
                result = stack.copy();
                result.setCount(1);
                if(!hat.hasColor(stack)) continue;
                int current = hat.getColor(stack);
                r = current >> 16 & 255;
                g = current >> 8 & 255;
                b = current & 255;
            }
            else {
                EnumDyeColor dye = DyeUtils.colorFromStack(stack).orElse(null);
                if(dye == null) return ItemStack.EMPTY;
                float[] values = dye.getColorComponentValues();
                r = (int)(values[0] * 255.0F);
                g = (int)(values[1] * 255.0F);
                b = (int)(values[2] * 255.0F);
            }
            brightness += Math.max(r, Math.max(g, b));
            color[0] += r;
            color[1] += g;
            color[2] += b;
            count++;
        }
        if(hat == null || count == 0) return ItemStack.EMPTY;
        int r = color[0] / count;
        int g = color[1] / count;
        int b = color[2] / count;
        float average = (float)brightness / (float)count;
        float max = (float)Math.max(r, Math.max(g, b));
        r = (int)((float)r * average / max);
        g = (int)((float)g * average / max);
        b = (int)((float)b * average / max);
        hat.setColor(result, (r << 16) + (g << 8) + b);
        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
