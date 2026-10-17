package fonnymunkey.simplehats.common.init;

import java.util.List;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatDisplayItem;
import fonnymunkey.simplehats.common.item.HatItem;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

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
	
	RecipeSerializer<? extends CustomRecipe> getHatScrapSerializer();
	RecipeSerializer<? extends CustomRecipe> getHatVariantSerializer();
	RecipeSerializer<? extends CustomRecipe> getHatDyeingSerializer();
}