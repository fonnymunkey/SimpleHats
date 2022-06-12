/*
package fonnymunkey.simplehats.compat;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.PartialNBTIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SimpleHats.modId, "jei_compat");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<CraftingRecipe> list = createHatScrapRecipe();
        list.addAll(createHatVariantRecipe());
        list.addAll(createHatDyeRecipe());
        if(!list.isEmpty()) {
            registration.addRecipes(RecipeTypes.CRAFTING, list);
        }
    }

    private static List<CraftingRecipe> createHatScrapRecipe() {
        List<CraftingRecipe> recipes = new ArrayList<>();

        List<Ingredient> easterList = new ArrayList<>(),
                summerList = new ArrayList<>(),
                halloweenList = new ArrayList<>(),
                festiveList = new ArrayList<>(),
                commonList = new ArrayList<>(),
                uncommonList = new ArrayList<>(),
                rareList = new ArrayList<>();
        for(HatItem hat : ModRegistry.hatList) {
            (switch(hat.getHatEntry().getHatSeason()) {
                case EASTER -> easterList;
                case SUMMER -> summerList;
                case HALLOWEEN -> halloweenList;
                case FESTIVE -> festiveList;
                case NONE -> switch(hat.getHatEntry().getHatRarity()) {
                    case COMMON -> commonList;
                    case UNCOMMON -> uncommonList;
                    case RARE, EPIC -> rareList;
                };
            }).add(Ingredient.of(hat));
        }

        Map<Item, List<Ingredient>> map = Map.of(
                ModRegistry.HATSCRAPS_EASTER.get(), easterList,
                ModRegistry.HATSCRAPS_SUMMER.get(), summerList,
                ModRegistry.HATSCRAPS_HALLOWEEN.get(), halloweenList,
                ModRegistry.HATSCRAPS_FESTIVE.get(), festiveList,
                ModRegistry.HATSCRAPS_COMMON.get(), commonList,
                ModRegistry.HATSCRAPS_UNCOMMON.get(), uncommonList,
                ModRegistry.HATSCRAPS_RARE.get(), rareList);
        for(Map.Entry<Item, List<Ingredient>> entry : map.entrySet()) {
            if(entry.getValue().isEmpty()) continue;
            ResourceLocation location = new ResourceLocation(SimpleHats.modId, entry.getKey().getRegistryName().getPath() + "_scrapping");
            recipes.add(new ShapelessRecipe(
                    location,
                    RecipeTypes.CRAFTING.getUid().getPath(),
                    new ItemStack(entry.getKey()),
                    NonNullList.of(
                            Ingredient.EMPTY,
                            Ingredient.of(Items.SHEARS),
                            CompoundIngredient.of(entry.getValue().toArray(new Ingredient[0])))));
        }
        return recipes;
    }

    private static List<CraftingRecipe> createHatVariantRecipe() {
        List<CraftingRecipe> recipes = new ArrayList<>();

        for(HatItem hat : ModRegistry.hatList) {
            if(hat.getHatEntry().getHatVariantRange()>0) {
                for(int i=0; i<=hat.getHatEntry().getHatVariantRange(); i++) {
                    CompoundTag tag = new CompoundTag();
                    tag.putInt("CustomModelData", i);
                    NonNullList<Ingredient> input = NonNullList.of(Ingredient.EMPTY, PartialNBTIngredient.of(hat, tag));

                    CompoundTag tag1 = new CompoundTag();
                    tag1.putInt("CustomModelData", (i+1)%(hat.getHatEntry().getHatVariantRange()+1));
                    ItemStack stack1 = new ItemStack(hat);
                    stack1.setTag(tag1);
                    ItemStack output = stack1;

                    ResourceLocation location = new ResourceLocation(SimpleHats.modId, "hatvariant_" + hat.getRegistryName().getPath() + "_" + i);
                    recipes.add(new ShapelessRecipe(location, RecipeTypes.CRAFTING.getUid().getPath(), output, input));
                }
            }
        }
        return recipes;
    }

    private static List<CraftingRecipe> createHatDyeRecipe() {
        List<CraftingRecipe> recipes = new ArrayList<>();

        for(HatItem hat : ModRegistry.hatList) {
            if(hat instanceof HatItemDyeable hatDyeable) {
                ResourceLocation location = new ResourceLocation(SimpleHats.modId, "hatdyeing_" + hat.getRegistryName().getPath());
                recipes.add(new ShapelessRecipe(
                        location,
                        RecipeTypes.CRAFTING.getUid().getPath(),
                        new ItemStack(hat),
                        NonNullList.of(
                                Ingredient.EMPTY,
                                Ingredient.of(Tags.Items.DYES),
                                Ingredient.of(hat))));
            }
        }
        return recipes;
    }
}
*/