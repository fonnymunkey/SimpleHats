package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatDisplayItem;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.loot.HatChestLootModifier;
import fonnymunkey.simplehats.common.loot.HatEntityLootModifier;
import fonnymunkey.simplehats.common.recipe.HatScrapRecipe;
import fonnymunkey.simplehats.common.recipe.HatVariantRecipe;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModRegistry {
    public static final CreativeModeTab HAT_TAB = new CreativeModeTab(SimpleHats.modId) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(HATICON.get());
        }
    };

    public static List<HatItem> hatList = new ArrayList<HatItem>();

    ////
    //Item Registry
    ////
    public static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(ForgeRegistries.ITEMS, SimpleHats.modId);
    public static final RegistryObject<BagItem> HATBAG_COMMON = ITEM_REG.register("hatbag_common", () -> new BagItem(Rarity.COMMON));
    public static final RegistryObject<BagItem> HATBAG_UNCOMMON = ITEM_REG.register("hatbag_uncommon", () -> new BagItem(Rarity.UNCOMMON));
    public static final RegistryObject<BagItem> HATBAG_RARE = ITEM_REG.register("hatbag_rare", () -> new BagItem(Rarity.RARE));
    public static final RegistryObject<BagItem> HATBAG_EPIC = ITEM_REG.register("hatbag_epic", () -> new BagItem(Rarity.EPIC));
    public static final RegistryObject<BagItem> HATBAG_EASTER = ITEM_REG.register("hatbag_easter", () -> new BagItem(HatSeason.EASTER));
    public static final RegistryObject<BagItem> HATBAG_SUMMER = ITEM_REG.register("hatbag_summer", () -> new BagItem(HatSeason.SUMMER));
    public static final RegistryObject<BagItem> HATBAG_HALLOWEEN = ITEM_REG.register("hatbag_halloween", () -> new BagItem(HatSeason.HALLOWEEN));
    public static final RegistryObject<BagItem> HATBAG_FESTIVE = ITEM_REG.register("hatbag_festive", () -> new BagItem(HatSeason.FESTIVE));
    public static final RegistryObject<Item> HATSCRAPS_COMMON = ITEM_REG.register("hatscraps_common", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).tab(HAT_TAB)));
    public static final RegistryObject<Item> HATSCRAPS_UNCOMMON = ITEM_REG.register("hatscraps_uncommon", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(HAT_TAB)));
    public static final RegistryObject<Item> HATSCRAPS_RARE = ITEM_REG.register("hatscraps_rare", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(HAT_TAB)));
    public static final RegistryObject<Item> HATSCRAPS_EASTER = ITEM_REG.register("hatscraps_easter", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(HAT_TAB)));
    public static final RegistryObject<Item> HATSCRAPS_SUMMER = ITEM_REG.register("hatscraps_summer", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(HAT_TAB)));
    public static final RegistryObject<Item> HATSCRAPS_HALLOWEEN = ITEM_REG.register("hatscraps_halloween", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(HAT_TAB)));
    public static final RegistryObject<Item> HATSCRAPS_FESTIVE = ITEM_REG.register("hatscraps_festive", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(HAT_TAB)));
    public static final RegistryObject<Item> HATICON = ITEM_REG.register("haticon", () -> new Item(new Item.Properties().tab(HAT_TAB)));
    public static final RegistryObject<HatDisplayItem> HATDISPLAYITEM = ITEM_REG.register("hatdisplay", () -> new HatDisplayItem(new Item.Properties().tab(HAT_TAB)));
    public static final RegistryObject<HatItem> HATSPECIAL = ITEM_REG.register("special", () -> new HatItem(new HatEntry("special", Rarity.EPIC, 0)));
    ////
    //Entity Registry
    ////
    public static final DeferredRegister<EntityType<?>> ENTITY_REG = DeferredRegister.create(ForgeRegistries.ENTITIES, SimpleHats.modId);
    public static final RegistryObject<EntityType<HatDisplay>> HATDISPLAYENTITY = ENTITY_REG.register("hatdisplay", () -> EntityType.Builder.<HatDisplay>of(HatDisplay::new, MobCategory.MISC).sized(0.75F, 0.8125F).clientTrackingRange(10).build("hatdisplay"));
    ////
    //Recipe Registry
    ////
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_REG = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SimpleHats.modId);
    public static final RegistryObject<RecipeSerializer<?>> HATSCRAP_SERIALIZER = RECIPE_REG.register("custom_hatscraps", () -> new SimpleRecipeSerializer<>(HatScrapRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> HATVARIANTS_SERIALIZER = RECIPE_REG.register("custom_hatvariants", () -> new SimpleRecipeSerializer<>(HatVariantRecipe::new));
    ////
    //Loot registry
    ////
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_REG = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, SimpleHats.modId);
    public static final RegistryObject<HatChestLootModifier.Serializer> HAT_LOOTINJECT_CHEST = LOOT_REG.register("hat_lootinject_chest", HatChestLootModifier.Serializer::new);
    public static final RegistryObject<HatEntityLootModifier.Serializer> HAT_LOOTINJECT_ENTITY = LOOT_REG.register("hat_lootinject_entity", HatEntityLootModifier.Serializer::new);
}