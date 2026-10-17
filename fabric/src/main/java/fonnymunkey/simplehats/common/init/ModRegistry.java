package fonnymunkey.simplehats.common.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatDisplayItem;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import fonnymunkey.simplehats.common.recipe.HatDyeingRecipe;
import fonnymunkey.simplehats.common.recipe.HatScrapRecipe;
import fonnymunkey.simplehats.common.recipe.HatVariantRecipe;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import fonnymunkey.simplehats.util.TagInjector;

import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;

public class ModRegistry implements IModRegistry {

    private static final List<HatItem> HAT_LIST = new ArrayList<>();
    
    public static final CreativeModeTab HAT_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "hat_group")), FabricCreativeModeTab.builder()
                         .icon(() -> new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatIcon()))
                         .title(Component.translatable("itemGroup.simplehats.hat_group"))
                         .displayItems((context, entries) -> {
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagEaster());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagSummer());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagHalloween());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatBagFestive());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsCommon());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsUncommon());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsRare());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsEaster());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsSummer());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsHalloween());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatScrapsFestive());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatIcon());
                             entries.accept(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayItem());
                             
                             for(HatItem hat : SimpleHatsCommon.MOD_REGISTRY.getHatList()) {
                                 entries.accept(hat);
                             }}).build());
    
    private static final BagItem HATBAG_COMMON = register("hatbag_common", properties -> new BagItem(properties, Rarity.COMMON));
    private static final BagItem HATBAG_UNCOMMON = register("hatbag_uncommon", properties -> new BagItem(properties, Rarity.UNCOMMON));
    private static final BagItem HATBAG_RARE = register("hatbag_rare", properties -> new BagItem(properties, Rarity.RARE));
    private static final BagItem HATBAG_EPIC = register("hatbag_epic", properties -> new BagItem(properties, Rarity.EPIC));
    private static final BagItem HATBAG_EASTER = register("hatbag_easter", properties -> new BagItem(properties, HatSeason.EASTER));
    private static final BagItem HATBAG_SUMMER = register("hatbag_summer", properties -> new BagItem(properties, HatSeason.SUMMER));
    private static final BagItem HATBAG_HALLOWEEN = register("hatbag_halloween", properties -> new BagItem(properties, HatSeason.HALLOWEEN));
    private static final BagItem HATBAG_FESTIVE = register("hatbag_festive", properties -> new BagItem(properties, HatSeason.FESTIVE));
    private static final Item HATSCRAPS_COMMON = register("hatscraps_common", properties -> new Item(properties.rarity(Rarity.COMMON)));
    private static final Item HATSCRAPS_UNCOMMON = register("hatscraps_uncommon", properties -> new Item(properties.rarity(Rarity.UNCOMMON)));
    private static final Item HATSCRAPS_RARE = register("hatscraps_rare", properties -> new Item(properties.rarity(Rarity.RARE)));
    private static final Item HATSCRAPS_EASTER = register("hatscraps_easter", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_SUMMER = register("hatscraps_summer", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_HALLOWEEN = register("hatscraps_halloween", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_FESTIVE = register("hatscraps_festive", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATICON = register("haticon", properties -> new Item(properties));
    private static final HatDisplayItem HATDISPLAYITEM = register("hatdisplay", properties -> new HatDisplayItem(properties));
    private static final HatItem HATSPECIAL = register("special", properties -> new HatItem(properties, new HatEntry("special", Rarity.EPIC, 0)));
    
    private static final EntityType<HatDisplay> HATDISPLAYENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "hatdisplay"), EntityType.Builder.<HatDisplay>of(HatDisplay::new, MobCategory.MISC).sized(0.75F, 0.8125F).clientTrackingRange(10)
        .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "hatdisplay"))));
    
    private static final RecipeSerializer<? extends CustomRecipe> HATSCRAP_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "custom_hatscraps"), new RecipeSerializer<>(HatScrapRecipe.CODEC, HatScrapRecipe.STREAM_CODEC));
    private static final RecipeSerializer<? extends CustomRecipe> HATVARIANTS_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "custom_hatvariants"), new RecipeSerializer<>(HatVariantRecipe.CODEC, HatVariantRecipe.STREAM_CODEC));
    private static final RecipeSerializer<? extends CustomRecipe> HAT_DYEING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "custom_hat_dyeing"), new RecipeSerializer<>(HatDyeingRecipe.CODEC, HatDyeingRecipe.STREAM_CODEC));

    private static <T extends Item> T register(String name, Function<Item.Properties, T> itemBuilder) {
        return register(name, new Item.Properties(), itemBuilder);
    }

    private static <T extends Item> T register(String name, Item.Properties properties, Function<Item.Properties, T> itemBuilder) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ITEM, key, itemBuilder.apply(properties.setId(key)));
    }
    
    public static void registerHats() {
        SimpleHatsCommon.MOD_REGISTRY.getHatList().add(SimpleHatsCommon.MOD_REGISTRY.getHatSpecial());
        for(HatEntry entry : HatJson.getHatList()) {
            HatItem hat = register(entry.getHatName(), properties -> entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(properties, entry) : new HatItem(properties, entry));
            SimpleHatsCommon.MOD_REGISTRY.getHatList().add(hat);

            if(hat instanceof HatItemDyeable) {
                CauldronInteractions.WATER.put(hat, CauldronInteractions::dyedItemIteration);
                TagInjector.inject(BuiltInRegistries.ITEM, ConventionalItemTags.DYED.location(), hat);
            }
        }
        Constants.LOG.info("Generated " + SimpleHatsCommon.MOD_REGISTRY.getHatList().size() + " hat items from hat entries.");

        TagInjector.inject(BuiltInRegistries.ITEM, SimpleHatsCommon.ALL_HATS.location(), SimpleHatsCommon.MOD_REGISTRY.getHatList().stream().map(hatItem -> (Item)hatItem).toList());
    }

    @Override
    public List<HatItem> getHatList() {
        return HAT_LIST;
    }
    
    @Override
    public BagItem getHatBagCommon() {
        return HATBAG_COMMON;
    }
    
    @Override
    public BagItem getHatBagUncommon() {
        return HATBAG_UNCOMMON;
    }
    
    @Override
    public BagItem getHatBagRare() {
        return HATBAG_RARE;
    }
    
    @Override
    public BagItem getHatBagEpic() {
        return HATBAG_EPIC;
    }
    
    @Override
    public BagItem getHatBagEaster() {
        return HATBAG_EASTER;
    }
    
    @Override
    public BagItem getHatBagSummer() {
        return HATBAG_SUMMER;
    }
    
    @Override
    public BagItem getHatBagHalloween() {
        return HATBAG_HALLOWEEN;
    }
    
    @Override
    public BagItem getHatBagFestive() {
        return HATBAG_FESTIVE;
    }
    
    @Override
    public Item getHatScrapsCommon() {
        return HATSCRAPS_COMMON;
    }
    
    @Override
    public Item getHatScrapsUncommon() {
        return HATSCRAPS_UNCOMMON;
    }
    
    @Override
    public Item getHatScrapsRare() {
        return HATSCRAPS_RARE;
    }
    
    @Override
    public Item getHatScrapsEaster() {
        return HATSCRAPS_EASTER;
    }
    
    @Override
    public Item getHatScrapsSummer() {
        return HATSCRAPS_SUMMER;
    }
    
    @Override
    public Item getHatScrapsHalloween() {
        return HATSCRAPS_HALLOWEEN;
    }
    
    @Override
    public Item getHatScrapsFestive() {
        return HATSCRAPS_FESTIVE;
    }
    
    @Override
    public Item getHatIcon() {
        return HATICON;
    }
    
    @Override
    public HatDisplayItem getHatDisplayItem() {
        return HATDISPLAYITEM;
    }
    
    @Override
    public HatItem getHatSpecial() {
        return HATSPECIAL;
    }
    
    @Override
    public EntityType<HatDisplay> getHatDisplayEntity() {
        return HATDISPLAYENTITY;
    }
    
    @Override
    public RecipeSerializer<? extends CustomRecipe> getHatScrapSerializer() {
        return HATSCRAP_SERIALIZER;
    }
    
    @Override
    public RecipeSerializer<? extends CustomRecipe> getHatVariantSerializer() {
        return HATVARIANTS_SERIALIZER;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getHatDyeingSerializer() {
        return HAT_DYEING_SERIALIZER;
    }
}