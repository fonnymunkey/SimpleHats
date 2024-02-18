package fonnymunkey.simplehats.common.recipe;

import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public class HatVariantRecipe extends SpecialCraftingRecipe {
    public HatVariantRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public String getGroup() {
        return "simplehats:hatvariants";
    }

    @Override
    public boolean matches(RecipeInputInventory craftingInventory, World level) {
        return processInventory(craftingInventory) != null;
    }

    @Override
    public ItemStack craft(RecipeInputInventory craftingInventory, DynamicRegistryManager reg) {
        ItemStack hat = processInventory(craftingInventory);
        if(hat != null) {
            ItemStack hat1 = hat.copy();
            NbtCompound tag = hat1.getOrCreateNbt();
            if(tag.getInt("CustomModelData")+1 > ((HatItem)hat1.getItem()).getHatEntry().getHatVariantRange()) tag.putInt("CustomModelData", 0);
            else tag.putInt("CustomModelData", tag.getInt("CustomModelData")+1);
            hat1.setNbt(tag);
            return hat1;
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack processInventory(RecipeInputInventory craftingInventory) {
        int totalItems = 0;
        ItemStack hatItem = null;
        for(int i =0; i < craftingInventory.size(); i++) {
            ItemStack slot = craftingInventory.getStack(i);
            if(!slot.isEmpty()) {
                totalItems++;
                if(slot.getItem() instanceof HatItem hat) {
                    if(hat.getHatEntry().getHatVariantRange() > 0) {
                        hatItem = slot;
                    }
                }
            }
        }
        return totalItems == 1 && hatItem != null ? hatItem : null;
    }

    @Override
    public boolean fits(int width, int height) {
        return width*height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.HATVARIANTS_SERIALIZER;
    }
}