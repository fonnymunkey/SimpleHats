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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import java.util.ArrayList;
import java.util.List;

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
    
    private static final BagItem HATBAG_COMMON = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_common"), new BagItem(Rarity.COMMON));
    private static final BagItem HATBAG_UNCOMMON = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_uncommon"), new BagItem(Rarity.UNCOMMON));
    private static final BagItem HATBAG_RARE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_rare"), new BagItem(Rarity.RARE));
    private static final BagItem HATBAG_EPIC = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_epic"), new BagItem(Rarity.EPIC));
    private static final BagItem HATBAG_EASTER = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_easter"), new BagItem(HatSeason.EASTER));
    private static final BagItem HATBAG_SUMMER = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_summer"), new BagItem(HatSeason.SUMMER));
    private static final BagItem HATBAG_HALLOWEEN = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_halloween"), new BagItem(HatSeason.HALLOWEEN));
    private static final BagItem HATBAG_FESTIVE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatbag_festive"), new BagItem(HatSeason.FESTIVE));
    private static final Item HATSCRAPS_COMMON = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatscraps_common"), new Item(new Item.Properties().rarity(Rarity.COMMON)));
    private static final Item HATSCRAPS_UNCOMMON = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatscraps_uncommon"), new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    private static final Item HATSCRAPS_RARE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatscraps_rare"), new Item(new Item.Properties().rarity(Rarity.RARE)));
    private static final Item HATSCRAPS_EASTER = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatscraps_easter"), new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_SUMMER = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatscraps_summer"), new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_HALLOWEEN = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatscraps_halloween"), new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final Item HATSCRAPS_FESTIVE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatscraps_festive"), new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final Item HATICON = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "haticon"), new Item(new Item.Properties()));
    private static final HatDisplayItem HATDISPLAYITEM = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "hatdisplay"), new HatDisplayItem(new Item.Properties()));
    private static final HatItem HATSPECIAL = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "special"), new HatItem(new HatEntry("special", Rarity.EPIC, 0)));
    
    private static final EntityType<HatDisplay> HATDISPLAYENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.tryBuild(Constants.MOD_ID, "hatdisplay"), EntityType.Builder.<HatDisplay>of(HatDisplay::new, MobCategory.MISC).sized(0.75F, 0.8125F).clientTrackingRange(10).build("hatdisplay"));
    
    private static final RecipeSerializer<?> HATSCRAP_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.tryBuild(Constants.MOD_ID, "custom_hatscraps"), new SimpleCraftingRecipeSerializer(HatScrapRecipe::new));
    private static final RecipeSerializer<?> HATVARIANTS_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.tryBuild(Constants.MOD_ID, "custom_hatvariants"), new SimpleCraftingRecipeSerializer(HatVariantRecipe::new));
    
    public static void registerHats() {
        SimpleHatsCommon.MOD_REGISTRY.getHatList().add(SimpleHatsCommon.MOD_REGISTRY.getHatSpecial());
        for(HatEntry entry : HatJson.getHatList()) {
            HatItem hat = entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(entry) : new HatItem(entry);
            hat = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, entry.getHatName()), hat);
            SimpleHatsCommon.MOD_REGISTRY.getHatList().add(hat);

            if(hat instanceof HatItemDyeable) {
                CauldronInteraction.WATER.map().put((HatItemDyeable)hat, CauldronInteraction.DYED_ITEM);
                TagInjector.inject(BuiltInRegistries.ITEM, ItemTags.DYEABLE.location(), hat);
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
    public RecipeSerializer<?> getHatScrapSerializer() {
        return HATSCRAP_SERIALIZER;
    }
    
    @Override
    public RecipeSerializer<?> getHatVariantSerializer() {
        return HATVARIANTS_SERIALIZER;
    }
}