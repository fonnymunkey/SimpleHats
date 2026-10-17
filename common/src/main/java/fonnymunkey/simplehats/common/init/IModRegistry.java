package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatDisplayItem;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.recipe.HatScrapRecipe;
import fonnymunkey.simplehats.common.recipe.HatVariantRecipe;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public interface IModRegistry {
	
	List<HatItem> getHatList();
	
	BagItem getHatBagCommon();
	BagItem getHatBagUncommon();
	BagItem getHatBagRare();
	BagItem getHatBagEpic();
	BagItem getHatBagEaster();
	BagItem getHatBagSummer();
	BagItem getHatBagHalloween();
	BagItem getHatBagFestive();
	
	Item getHatScrapsCommon();
	Item getHatScrapsUncommon();
	Item getHatScrapsRare();
	Item getHatScrapsEaster();
	Item getHatScrapsSummer();
	Item getHatScrapsHalloween();
	Item getHatScrapsFestive();
	
	Item getHatIcon();
	
	HatDisplayItem getHatDisplayItem();
	
	HatItem getHatSpecial();
	
	EntityType<HatDisplay> getHatDisplayEntity();
	
	RecipeSerializer<HatScrapRecipe> getHatScrapSerializer();
	RecipeSerializer<HatVariantRecipe> getHatVariantSerializer();
}