package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatDisplayItem;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import fonnymunkey.simplehats.common.recipe.HatScrapRecipe;
import fonnymunkey.simplehats.common.recipe.HatVariantRecipe;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import fonnymunkey.simplehats.util.TagInjector;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModRegistry implements IModRegistry {

    private static final List<HatItem> HAT_LIST = new ArrayList<>();
    
    public static final CreativeModeTab HAT_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.tryBuild(Constants.MOD_ID, "hat_group")), FabricItemGroup.builder()
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
    
    private static final BagItem HATBAG_COMMON = registerItem("hatbag_common", properties -> new BagItem(Rarity.COMMON, properties));
    private static final BagItem HATBAG_UNCOMMON = registerItem("hatbag_uncommon", properties -> new BagItem(Rarity.UNCOMMON, properties));
    private static final BagItem HATBAG_RARE = registerItem("hatbag_rare", properties -> new BagItem(Rarity.RARE, properties));
    private static final BagItem HATBAG_EPIC = registerItem("hatbag_epic", properties -> new BagItem(Rarity.EPIC, properties));
    private static final BagItem HATBAG_EASTER = registerItem("hatbag_easter", properties -> new BagItem(HatSeason.EASTER, properties));
    private static final BagItem HATBAG_SUMMER = registerItem("hatbag_summer", properties -> new BagItem(HatSeason.SUMMER, properties));
    private static final BagItem HATBAG_HALLOWEEN = registerItem("hatbag_halloween", properties -> new BagItem(HatSeason.HALLOWEEN, properties));
    private static final BagItem HATBAG_FESTIVE = registerItem("hatbag_festive", properties -> new BagItem(HatSeason.FESTIVE, properties));
    private static final Item HATSCRAPS_COMMON = registerItem("hatscraps_common", properties -> new Item(properties.rarity(Rarity.COMMON)));
    private static final Item HATSCRAPS_UNCOMMON = registerItem("hatscraps_uncommon", properties -> new Item(properties.rarity(Rarity.UNCOMMON)));
    private static final Item HATSCRAPS_RARE = registerItem("hatscraps_rare", properties -> new Item(properties.rarity(Rarity.RARE)));
    private static final Item HATSCRAPS_EASTER = registerItem("hatscraps_easter", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_SUMMER = registerItem("hatscraps_summer", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_HALLOWEEN = registerItem("hatscraps_halloween", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_FESTIVE = registerItem("hatscraps_festive", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final Item HATICON = registerItem("haticon", Item::new);
    private static final HatDisplayItem HATDISPLAYITEM = registerItem("hatdisplay", HatDisplayItem::new);
    private static final HatItem HATSPECIAL = registerItem("special", properties -> new HatItem(new HatEntry("special", Rarity.EPIC, 0), properties));
    
    private static final EntityType<HatDisplay> HATDISPLAYENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.tryBuild(Constants.MOD_ID, "hatdisplay"), EntityType.Builder.<HatDisplay>of(HatDisplay::new, MobCategory.MISC).sized(0.75F, 0.8125F).clientTrackingRange(10).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.tryBuild(Constants.MOD_ID, "hatdisplay"))));
    
    private static final RecipeSerializer<HatScrapRecipe> HATSCRAP_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.tryBuild(Constants.MOD_ID, "custom_hatscraps"), new CustomRecipe.Serializer<>(HatScrapRecipe::new));
    private static final RecipeSerializer<HatVariantRecipe> HATVARIANTS_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.tryBuild(Constants.MOD_ID, "custom_hatvariants"), new CustomRecipe.Serializer<>(HatVariantRecipe::new));
    
    public static void registerHats() {
        SimpleHatsCommon.MOD_REGISTRY.getHatList().add(SimpleHatsCommon.MOD_REGISTRY.getHatSpecial());
        for(HatEntry entry : HatJson.getHatList()) {
            HatItem hat = registerItem(entry.getHatName(), properties -> entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(entry, properties) : new HatItem(entry, properties));
            SimpleHatsCommon.MOD_REGISTRY.getHatList().add(hat);

            if(hat instanceof HatItemDyeable) {
                CauldronInteraction.WATER.map().put((HatItemDyeable)hat, CauldronInteraction.WATER.map().get(Items.LEATHER_HELMET));
                TagInjector.inject(BuiltInRegistries.ITEM, ItemTags.DYEABLE.location(), hat);
            }
        }
        Constants.LOG.info("Generated " + SimpleHatsCommon.MOD_REGISTRY.getHatList().size() + " hat items from hat entries.");

        TagInjector.inject(BuiltInRegistries.ITEM, SimpleHatsCommon.ALL_HATS.location(), SimpleHatsCommon.MOD_REGISTRY.getHatList().stream().map(hatItem -> (Item)hatItem).toList());
    }

    private static <T extends Item> T registerItem(String name, Function<Item.Properties, T> factory) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(new Item.Properties().setId(key)));
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
    public RecipeSerializer<HatScrapRecipe> getHatScrapSerializer() {
        return HATSCRAP_SERIALIZER;
    }
    
    @Override
    public RecipeSerializer<HatVariantRecipe> getHatVariantSerializer() {
        return HATVARIANTS_SERIALIZER;
    }
}