package fonnymunkey.simplehats.common.init;

import java.util.ArrayList;
import java.util.List;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatDisplayItem;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.recipe.HatDyeingRecipe;
import fonnymunkey.simplehats.common.recipe.HatScrapRecipe;
import fonnymunkey.simplehats.common.recipe.HatVariantRecipe;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRegistry implements IModRegistry {
    
    private static final List<HatItem> HAT_LIST = new ArrayList<>();
    
    private static final DeferredRegister<CreativeModeTab> TAB_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> HAT_TAB = TAB_REG.register(Constants.MOD_ID, () -> CreativeModeTab.builder()
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
                            }
                        }).build());
    
    private static final DeferredRegister.Items ITEM_REG = DeferredRegister.createItems(Constants.MOD_ID);
    private static final DeferredHolder<Item, BagItem> HATBAG_COMMON = ITEM_REG.registerItem("hatbag_common", properties -> new BagItem(properties, Rarity.COMMON));
    private static final DeferredHolder<Item, BagItem> HATBAG_UNCOMMON = ITEM_REG.registerItem("hatbag_uncommon", properties -> new BagItem(properties, Rarity.UNCOMMON));
    private static final DeferredHolder<Item, BagItem> HATBAG_RARE = ITEM_REG.registerItem("hatbag_rare", properties -> new BagItem(properties, Rarity.RARE));
    private static final DeferredHolder<Item, BagItem> HATBAG_EPIC = ITEM_REG.registerItem("hatbag_epic", properties -> new BagItem(properties, Rarity.EPIC));
    private static final DeferredHolder<Item, BagItem> HATBAG_EASTER = ITEM_REG.registerItem("hatbag_easter", properties -> new BagItem(properties, HatSeason.EASTER));
    private static final DeferredHolder<Item, BagItem> HATBAG_SUMMER = ITEM_REG.registerItem("hatbag_summer", properties -> new BagItem(properties, HatSeason.SUMMER));
    private static final DeferredHolder<Item, BagItem> HATBAG_HALLOWEEN = ITEM_REG.registerItem("hatbag_halloween", properties -> new BagItem(properties, HatSeason.HALLOWEEN));
    private static final DeferredHolder<Item, BagItem> HATBAG_FESTIVE = ITEM_REG.registerItem("hatbag_festive", properties -> new BagItem(properties, HatSeason.FESTIVE));
    private static final DeferredHolder<Item, Item> HATSCRAPS_COMMON = ITEM_REG.registerItem("hatscraps_common", properties -> new Item(properties.rarity(Rarity.COMMON)));
    private static final DeferredHolder<Item, Item> HATSCRAPS_UNCOMMON = ITEM_REG.registerItem("hatscraps_uncommon", properties -> new Item(properties.rarity(Rarity.UNCOMMON)));
    private static final DeferredHolder<Item, Item> HATSCRAPS_RARE = ITEM_REG.registerItem("hatscraps_rare", properties -> new Item(properties.rarity(Rarity.RARE)));
    private static final DeferredHolder<Item, Item> HATSCRAPS_EASTER = ITEM_REG.registerItem("hatscraps_easter", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final DeferredHolder<Item, Item> HATSCRAPS_SUMMER = ITEM_REG.registerItem("hatscraps_summer", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final DeferredHolder<Item, Item> HATSCRAPS_HALLOWEEN = ITEM_REG.registerItem("hatscraps_halloween", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final DeferredHolder<Item, Item> HATSCRAPS_FESTIVE = ITEM_REG.registerItem("hatscraps_festive", properties -> new Item(properties.rarity(Rarity.EPIC)));
    private static final DeferredHolder<Item, Item> HATICON = ITEM_REG.registerItem("haticon", properties -> new Item(properties));
    private static final DeferredHolder<Item, HatDisplayItem> HATDISPLAYITEM = ITEM_REG.registerItem("hatdisplay", properties -> new HatDisplayItem(properties));
    private static final DeferredHolder<Item, HatItem> HATSPECIAL = ITEM_REG.registerItem("special", properties -> new HatItem(properties, new HatEntry("special", Rarity.EPIC, 0)));

    private static final DeferredRegister<EntityType<?>> ENTITY_REG = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);
    private static final DeferredHolder<EntityType<?>,EntityType<HatDisplay>> HATDISPLAYENTITY = ENTITY_REG.register("hatdisplay", key -> EntityType.Builder.<HatDisplay>of(HatDisplay::new, MobCategory.MISC).sized(0.75F, 0.8125F).clientTrackingRange(10).build(ResourceKey.create(Registries.ENTITY_TYPE, key)));

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_REG = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Constants.MOD_ID);
    private static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HatScrapRecipe>> HATSCRAP_SERIALIZER = RECIPE_REG.register("custom_hatscraps", () -> new RecipeSerializer<>(HatScrapRecipe.CODEC, HatScrapRecipe.STREAM_CODEC));
    private static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HatVariantRecipe>> HATVARIANTS_SERIALIZER = RECIPE_REG.register("custom_hatvariants", () -> new RecipeSerializer<>(HatVariantRecipe.CODEC, HatVariantRecipe.STREAM_CODEC));
    private static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HatDyeingRecipe>> HAT_DYEING_SERIALIZER = RECIPE_REG.register("custom_hat_dyeing", () -> new RecipeSerializer<>(HatDyeingRecipe.CODEC, HatDyeingRecipe.STREAM_CODEC));

    public static void registerHats(IEventBus eventBus) {
        ModRegistry.ITEM_REG.register(eventBus);
        ModRegistry.TAB_REG.register(eventBus);
        ModRegistry.ENTITY_REG.register(eventBus);
        ModRegistry.RECIPE_REG.register(eventBus);
    }
    
    @Override
    public List<HatItem> getHatList() {
        return HAT_LIST;
    }
    
    @Override
    public BagItem getHatBagCommon() {
        return HATBAG_COMMON.get();
    }
    
    @Override
    public BagItem getHatBagUncommon() {
        return HATBAG_UNCOMMON.get();
    }
    
    @Override
    public BagItem getHatBagRare() {
        return HATBAG_RARE.get();
    }
    
    @Override
    public BagItem getHatBagEpic() {
        return HATBAG_EPIC.get();
    }
    
    @Override
    public BagItem getHatBagEaster() {
        return HATBAG_EASTER.get();
    }
    
    @Override
    public BagItem getHatBagSummer() {
        return HATBAG_SUMMER.get();
    }
    
    @Override
    public BagItem getHatBagHalloween() {
        return HATBAG_HALLOWEEN.get();
    }
    
    @Override
    public BagItem getHatBagFestive() {
        return HATBAG_FESTIVE.get();
    }
    
    @Override
    public Item getHatScrapsCommon() {
        return HATSCRAPS_COMMON.get();
    }
    
    @Override
    public Item getHatScrapsUncommon() {
        return HATSCRAPS_UNCOMMON.get();
    }
    
    @Override
    public Item getHatScrapsRare() {
        return HATSCRAPS_RARE.get();
    }
    
    @Override
    public Item getHatScrapsEaster() {
        return HATSCRAPS_EASTER.get();
    }
    
    @Override
    public Item getHatScrapsSummer() {
        return HATSCRAPS_SUMMER.get();
    }
    
    @Override
    public Item getHatScrapsHalloween() {
        return HATSCRAPS_HALLOWEEN.get();
    }
    
    @Override
    public Item getHatScrapsFestive() {
        return HATSCRAPS_FESTIVE.get();
    }
    
    @Override
    public Item getHatIcon() {
        return HATICON.get();
    }
    
    @Override
    public HatDisplayItem getHatDisplayItem() {
        return HATDISPLAYITEM.get();
    }
    
    @Override
    public HatItem getHatSpecial() {
        return HATSPECIAL.get();
    }
    
    @Override
    public EntityType<HatDisplay> getHatDisplayEntity() {
        return HATDISPLAYENTITY.get();
    }
    
    @Override
    public RecipeSerializer<? extends CustomRecipe> getHatScrapSerializer() {
        return HATSCRAP_SERIALIZER.get();
    }
    
    @Override
    public RecipeSerializer<? extends CustomRecipe> getHatVariantSerializer() {
        return HATVARIANTS_SERIALIZER.get();
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getHatDyeingSerializer() {
        return HAT_DYEING_SERIALIZER.get();
    }
}