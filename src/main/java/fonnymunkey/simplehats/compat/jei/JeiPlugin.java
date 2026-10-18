package fonnymunkey.simplehats.compat.jei;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.handlers.ModRegistry;
import fonnymunkey.simplehats.item.HatItem;
import fonnymunkey.simplehats.item.HatItemDyeable;
import fonnymunkey.simplehats.recipe.HatScrapRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    private static final ResourceLocation GROUP = new ResourceLocation(SimpleHats.MODID, "jei");

    @Override
    public void register(IModRegistry registry) {
        List<IRecipe> recipes = new ArrayList<>();

        Map<Item, List<ItemStack>> scraps = new LinkedHashMap<>();
        for(HatItem hat : ModRegistry.HAT_LIST) {
            scraps.computeIfAbsent(HatScrapRecipe.getScraps(hat.getHatEntry()), k -> new ArrayList<>()).add(new ItemStack(hat));
        }
        for(Map.Entry<Item, List<ItemStack>> entry : scraps.entrySet()) {
            recipes.add(new ShapelessOreRecipe(GROUP, new ItemStack(entry.getKey()), Items.SHEARS, Ingredient.fromStacks(entry.getValue().toArray(new ItemStack[0]))));
        }

        for(HatItem hat : ModRegistry.HAT_LIST) {
            int range = hat.getHatEntry().getHatVariantRange();
            for(int i = 0; i < range + 1 && range > 0; i++) {
                recipes.add(new ShapelessOreRecipe(GROUP, withVariant(hat, (i + 1) % (range + 1)), withVariant(hat, i)));
            }
            if(hat instanceof HatItemDyeable) {
                recipes.add(new ShapelessOreRecipe(GROUP, new ItemStack(hat), "dye", new ItemStack(hat)));
            }
        }

        registry.addRecipes(recipes, VanillaRecipeCategoryUid.CRAFTING);
    }

    private static ItemStack withVariant(HatItem hat, int variant) {
        ItemStack stack = new ItemStack(hat);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("CustomModelData", variant);
        stack.setTagCompound(tag);
        return stack;
    }
}
