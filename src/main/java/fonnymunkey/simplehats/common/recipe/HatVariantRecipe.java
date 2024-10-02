package fonnymunkey.simplehats.common.recipe;

import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
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
    public boolean matches(CraftingRecipeInput craftingInventory, World level) {
        return processInventory(craftingInventory) != null;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput craftingInventory, RegistryWrapper.WrapperLookup reg) {
        ItemStack hat = processInventory(craftingInventory);
        if(hat != null) {
            ItemStack hat1 = hat.copy();
            if (hat1.contains(DataComponentTypes.CUSTOM_MODEL_DATA)) {
                var modelData = hat1.get(DataComponentTypes.CUSTOM_MODEL_DATA).value();

                if (modelData + 1 > ((HatItem)hat1.getItem()).getHatEntry().getHatVariantRange())
                    modelData = 0;
                else
                    modelData += 1;

                hat1.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(modelData));
            }
            else {
                hat1.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(1));
            }
            return hat1;
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack processInventory(CraftingRecipeInput craftingInventory) {
        int totalItems = 0;
        ItemStack hatItem = null;
        for(int i =0; i < craftingInventory.getSize(); i++) {
            ItemStack slot = craftingInventory.getStackInSlot(i);
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