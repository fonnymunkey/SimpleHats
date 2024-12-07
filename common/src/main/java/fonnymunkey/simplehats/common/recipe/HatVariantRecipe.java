package fonnymunkey.simplehats.common.recipe;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class HatVariantRecipe extends CustomRecipe {
    
    public HatVariantRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public String getGroup() {
        return Constants.MOD_ID + ":hatvariants";
    }

    @Override
    public boolean matches(CraftingInput craftingInventory, Level level) {
        return processInventory(craftingInventory) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInventory, HolderLookup.Provider reg) {
        ItemStack hat = processInventory(craftingInventory);
        if(hat != null) {
            ItemStack hat1 = hat.copy();
            if(hat1.has(DataComponents.CUSTOM_MODEL_DATA)) {
                var modelData = hat1.get(DataComponents.CUSTOM_MODEL_DATA).value();

                if (modelData + 1 > ((HatItem)hat1.getItem()).getHatEntry().getHatVariantRange())
                    modelData = 0;
                else
                    modelData += 1;

                hat1.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(modelData));
            }
            else {
                hat1.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));
            }
            return hat1;
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack processInventory(CraftingInput craftingInventory) {
        int totalItems = 0;
        ItemStack hatItem = null;
        for(int i =0; i < craftingInventory.size(); i++) {
            ItemStack slot = craftingInventory.getItem(i);
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
    public boolean canCraftInDimensions(int width, int height) {
        return width*height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SimpleHatsCommon.MOD_REGISTRY.getHatVariantSerializer();
    }
}