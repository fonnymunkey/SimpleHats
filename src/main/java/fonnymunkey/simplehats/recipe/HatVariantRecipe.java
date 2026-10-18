package fonnymunkey.simplehats.recipe;

import fonnymunkey.simplehats.item.HatItem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HatVariantRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return !processInventory(inv).isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack hat = processInventory(inv);
        if(hat.isEmpty()) return ItemStack.EMPTY;
        ItemStack result = hat.copy();
        result.setCount(1);
        if(!result.hasTagCompound()) result.setTagCompound(new NBTTagCompound());
        int variant = result.getTagCompound().getInteger("CustomModelData") + 1;
        result.getTagCompound().setInteger("CustomModelData", variant > ((HatItem)result.getItem()).getHatEntry().getHatVariantRange() ? 0 : variant);
        return result;
    }

    private static ItemStack processInventory(InventoryCrafting inv) {
        int total = 0;
        ItemStack hat = ItemStack.EMPTY;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                total++;
                if(stack.getItem() instanceof HatItem && ((HatItem)stack.getItem()).getHatEntry().getHatVariantRange() > 0) hat = stack;
            }
        }
        return total == 1 ? hat : ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 1;
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
