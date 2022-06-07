package fonnymunkey.simplehats.common.recipe;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class HatScrapRecipe extends CustomRecipe {
    public HatScrapRecipe(ResourceLocation location) {
        super(location);
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(SimpleHats.modId, "hatscraps");
    }

    @Override
    public boolean matches(CraftingContainer craftingInventory, Level level) {
        int[] list = processInventory(craftingInventory);
        return list[0] != -1 && list[1] != -1;
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingInventory) {
        int[] list  = processInventory(craftingInventory);
        if(list[0] != -1 && list[1] != -1) {
            return switch(((HatItem)craftingInventory.getItem(list[0]).getItem()).getHatEntry().getHatSeason()) {
                case EASTER -> new ItemStack(ModRegistry.HATSCRAPS_EASTER.get());
                case SUMMER -> new ItemStack(ModRegistry.HATSCRAPS_SUMMER.get());
                case HALLOWEEN -> new ItemStack(ModRegistry.HATSCRAPS_HALLOWEEN.get());
                case FESTIVE -> new ItemStack(ModRegistry.HATSCRAPS_FESTIVE.get());
                case NONE -> switch (((HatItem)craftingInventory.getItem(list[0]).getItem()).getHatEntry().getHatRarity()) {
                    case COMMON -> new ItemStack(ModRegistry.HATSCRAPS_COMMON.get());
                    case UNCOMMON -> new ItemStack(ModRegistry.HATSCRAPS_UNCOMMON.get());
                    case RARE, EPIC -> new ItemStack(ModRegistry.HATSCRAPS_RARE.get());
                };
            };
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer craftingInventory) {
        NonNullList<ItemStack> remainList = NonNullList.withSize(craftingInventory.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < craftingInventory.getContainerSize(); ++i) {
            ItemStack slot = craftingInventory.getItem(i);
            if(!slot.isEmpty() && slot.getItem() instanceof ShearsItem) {
                ItemStack slot1 = slot.copy();
                if(slot1.isDamageableItem()) {
                    slot1.setDamageValue(slot.getDamageValue() + 1);
                    if(slot1.getDamageValue() >= slot1.getMaxDamage()) {
                        slot1 = ItemStack.EMPTY;
                    }
                }
                remainList.set(i, slot1);
                break;
            }
        }
        return remainList;
    }

    private static int[] processInventory(CraftingContainer craftingInventory) {
        int totalItems = 0;
        int[] list = new int[]{-1, -1};
        for(int i =0; i < craftingInventory.getContainerSize(); i++) {
            ItemStack slot = craftingInventory.getItem(i);
            if(!slot.isEmpty()) {
                totalItems++;
                if(slot.getItem() instanceof HatItem hat && hat!=ModRegistry.HATSPECIAL.get()) list[0] = i;
                if(slot.getItem() instanceof ShearsItem) list[1] = i;
            }
        }
        if(totalItems == 2) return list;
        return new int[]{-1, -1};
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width*height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.HATSCRAP_SERIALIZER.get();
    }
}