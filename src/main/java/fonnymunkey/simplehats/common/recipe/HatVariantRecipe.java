package fonnymunkey.simplehats.common.recipe;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class HatVariantRecipe extends CustomRecipe {
    public HatVariantRecipe(ResourceLocation location, CraftingBookCategory category) {
        super(location, category);
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(SimpleHats.modId, "hatvariants");
    }

    @Override
    public boolean matches(CraftingContainer craftingInventory, Level level) {
        return processInventory(craftingInventory) != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingInventory, RegistryAccess reg) {
        ItemStack hat = processInventory(craftingInventory);
        if(hat != null) {
            ItemStack hat1 = hat.copy();
            CompoundTag tag = hat1.getOrCreateTag();
            if(tag.getInt("CustomModelData")+1 > ((HatItem)hat1.getItem()).getHatEntry().getHatVariantRange()) tag.putInt("CustomModelData", 0);
            else tag.putInt("CustomModelData", tag.getInt("CustomModelData")+1);
            hat1.setTag(tag);
            return hat1;
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    private static ItemStack processInventory(CraftingContainer craftingInventory) {
        int totalItems = 0;
        ItemStack hatItem = null;
        for(int i =0; i < craftingInventory.getContainerSize(); i++) {
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
        return ModRegistry.HATVARIANTS_SERIALIZER.get();
    }
}