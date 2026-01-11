package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatDisplayItem;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.recipe.HatScrapRecipe;
import fonnymunkey.simplehats.common.recipe.HatVariantRecipe;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModRegistry implements IModRegistry {
    
    private static final List<HatItem> HAT_LIST = new ArrayList<>();
    
    private static final DeferredRegister<CreativeModeTab> TAB_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    private static final RegistryObject<CreativeModeTab> HAT_TAB = TAB_REG.register(Constants.MOD_ID, () -> CreativeModeTab.builder()
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
    
    private static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);
    private static final RegistryObject<BagItem> HATBAG_COMMON = ITEM_REG.register("hatbag_common", () -> new BagItem(Rarity.COMMON));
    private static final RegistryObject<BagItem> HATBAG_UNCOMMON = ITEM_REG.register("hatbag_uncommon", () -> new BagItem(Rarity.UNCOMMON));
    private static final RegistryObject<BagItem> HATBAG_RARE = ITEM_REG.register("hatbag_rare", () -> new BagItem(Rarity.RARE));
    private static final RegistryObject<BagItem> HATBAG_EPIC = ITEM_REG.register("hatbag_epic", () -> new BagItem(Rarity.EPIC));
    private static final RegistryObject<BagItem> HATBAG_EASTER = ITEM_REG.register("hatbag_easter", () -> new BagItem(HatSeason.EASTER));
    private static final RegistryObject<BagItem> HATBAG_SUMMER = ITEM_REG.register("hatbag_summer", () -> new BagItem(HatSeason.SUMMER));
    private static final RegistryObject<BagItem> HATBAG_HALLOWEEN = ITEM_REG.register("hatbag_halloween", () -> new BagItem(HatSeason.HALLOWEEN));
    private static final RegistryObject<BagItem> HATBAG_FESTIVE = ITEM_REG.register("hatbag_festive", () -> new BagItem(HatSeason.FESTIVE));
    private static final RegistryObject<Item> HATSCRAPS_COMMON = ITEM_REG.register("hatscraps_common", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    private static final RegistryObject<Item> HATSCRAPS_UNCOMMON = ITEM_REG.register("hatscraps_uncommon", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    private static final RegistryObject<Item> HATSCRAPS_RARE = ITEM_REG.register("hatscraps_rare", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    private static final RegistryObject<Item> HATSCRAPS_EASTER = ITEM_REG.register("hatscraps_easter", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final RegistryObject<Item> HATSCRAPS_SUMMER = ITEM_REG.register("hatscraps_summer", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final RegistryObject<Item> HATSCRAPS_HALLOWEEN = ITEM_REG.register("hatscraps_halloween", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final RegistryObject<Item> HATSCRAPS_FESTIVE = ITEM_REG.register("hatscraps_festive", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    private static final RegistryObject<Item> HATICON = ITEM_REG.register("haticon", () -> new Item(new Item.Properties()));
    private static final RegistryObject<HatDisplayItem> HATDISPLAYITEM = ITEM_REG.register("hatdisplay", () -> new HatDisplayItem(new Item.Properties()));
    private static final RegistryObject<HatItem> HATSPECIAL = ITEM_REG.register("special", () -> new HatItem(new HatEntry("special", Rarity.EPIC, 0)));

    private static final DeferredRegister<EntityType<?>> ENTITY_REG = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);
    private static final RegistryObject<EntityType<HatDisplay>> HATDISPLAYENTITY = ENTITY_REG.register("hatdisplay", () -> EntityType.Builder.<HatDisplay>of(HatDisplay::new, MobCategory.MISC).sized(0.75F, 0.8125F).clientTrackingRange(10).build("hatdisplay"));

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_REG = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Constants.MOD_ID);
    private static final RegistryObject<SimpleCraftingRecipeSerializer<HatScrapRecipe>> HATSCRAP_SERIALIZER = RECIPE_REG.register("custom_hatscraps", () -> new SimpleCraftingRecipeSerializer<>(HatScrapRecipe::new));
    private static final RegistryObject<SimpleCraftingRecipeSerializer<HatVariantRecipe>> HATVARIANTS_SERIALIZER = RECIPE_REG.register("custom_hatvariants", () -> new SimpleCraftingRecipeSerializer<>(HatVariantRecipe::new));

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
    public RecipeSerializer<?> getHatScrapSerializer() {
        return HATSCRAP_SERIALIZER.get();
    }
    
    @Override
    public RecipeSerializer<?> getHatVariantSerializer() {
        return HATVARIANTS_SERIALIZER.get();
    }
}