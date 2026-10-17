package fonnymunkey.simplehats.common.recipe;

import com.mojang.serialization.MapCodec;
import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.item.HatItem;

import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class HatScrapRecipe extends CustomRecipe {
    public static final HatScrapRecipe INSTANCE = new HatScrapRecipe();
    public static final MapCodec<HatScrapRecipe> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, HatScrapRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    
    private HatScrapRecipe() {
        super();
    }

    @Override
    public String group() {
        return Constants.MOD_ID + ":hatscraps";
    }

    @Override
    public boolean matches(CraftingInput craftingInventory, Level level) {
        int[] list = processInventory(craftingInventory);
        return list[0] != -1 && list[1] != -1;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInventory) {
        int[] list  = processInventory(craftingInventory);
        if(list[0] != -1 && list[1] != -1) {
            return switch(((HatItem)craftingInventory.getItem(list[0]).getItem()).getHatEntry().getHatSeason()) {
                case EASTER -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsEaster());
                case SUMMER -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsSummer());
                case HALLOWEEN -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsHalloween());
                case FESTIVE -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsFestive());
                case NONE -> switch (((HatItem)craftingInventory.getItem(list[0]).getItem()).getHatEntry().getHatRarity()) {
                    case COMMON -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsCommon());
                    case UNCOMMON -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsUncommon());
                    case RARE, EPIC -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsRare());
                };
            };
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInventory) {
        NonNullList<ItemStack> remainList = NonNullList.withSize(craftingInventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < craftingInventory.size(); ++i) {
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

    private static int[] processInventory(CraftingInput craftingInventory) {
        int totalItems = 0;
        int[] list = new int[]{-1, -1};
        for(int i =0; i < craftingInventory.size(); i++) {
            ItemStack slot = craftingInventory.getItem(i);
            if(!slot.isEmpty()) {
                totalItems++;
                if(slot.getItem() instanceof HatItem hat && hat!=SimpleHatsCommon.MOD_REGISTRY.getHatSpecial()) list[0] = i;
                if(slot.getItem() instanceof ShearsItem) list[1] = i;
            }
        }
        if(totalItems == 2) return list;
        return new int[]{-1, -1};
    }

//    @Override
//    public boolean canCraftInDimensions(int width, int height) {
//        return width*height >= 2;
//    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SimpleHatsCommon.MOD_REGISTRY.getHatScrapSerializer();
    }
}