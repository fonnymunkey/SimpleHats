package fonnymunkey.simplehats.recipe;

import fonnymunkey.simplehats.handlers.ModRegistry;
import fonnymunkey.simplehats.item.HatItem;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HatScrapRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        int[] slots = processInventory(inv);
        return slots[0] != -1 && slots[1] != -1;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        int[] slots = processInventory(inv);
        if(slots[0] == -1 || slots[1] == -1) return ItemStack.EMPTY;
        return new ItemStack(getScraps(((HatItem)inv.getStackInSlot(slots[0]).getItem()).getHatEntry()));
    }

    public static Item getScraps(HatEntry entry) {
        switch(entry.getHatSeason()) {
            case EASTER: return ModRegistry.HATSCRAPS_EASTER;
            case SUMMER: return ModRegistry.HATSCRAPS_SUMMER;
            case HALLOWEEN: return ModRegistry.HATSCRAPS_HALLOWEEN;
            case FESTIVE: return ModRegistry.HATSCRAPS_FESTIVE;
            default:
                switch(entry.getHatRarity()) {
                    case COMMON: return ModRegistry.HATSCRAPS_COMMON;
                    case UNCOMMON: return ModRegistry.HATSCRAPS_UNCOMMON;
                    default: return ModRegistry.HATSCRAPS_RARE;
                }
        }
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack.getItem() instanceof ItemShears) {
                ItemStack shears = stack.copy();
                if(shears.isItemStackDamageable()) {
                    shears.setItemDamage(shears.getItemDamage() + 1);
                    if(shears.getItemDamage() > shears.getMaxDamage()) shears = ItemStack.EMPTY;
                }
                remaining.set(i, shears);
                break;
            }
        }
        return remaining;
    }

    private static int[] processInventory(InventoryCrafting inv) {
        int total = 0;
        int[] slots = new int[]{-1, -1};
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                total++;
                if(stack.getItem() instanceof HatItem && stack.getItem() != ModRegistry.HATSPECIAL) slots[0] = i;
                if(stack.getItem() instanceof ItemShears) slots[1] = i;
            }
        }
        return total == 2 ? slots : new int[]{-1, -1};
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
