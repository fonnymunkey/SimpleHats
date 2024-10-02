package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.SimpleHats;
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
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModRegistry {

    public static List<HatItem> hatList = new ArrayList<HatItem>();

    ////
    //Items
    ////
    public static final BagItem HATBAG_COMMON = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_common"), new BagItem(Rarity.COMMON));
    public static final BagItem HATBAG_UNCOMMON = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_uncommon"), new BagItem(Rarity.UNCOMMON));
    public static final BagItem HATBAG_RARE = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_rare"), new BagItem(Rarity.RARE));
    public static final BagItem HATBAG_EPIC = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_epic"), new BagItem(Rarity.EPIC));
    public static final BagItem HATBAG_EASTER = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_easter"), new BagItem(HatSeason.EASTER));
    public static final BagItem HATBAG_SUMMER = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_summer"), new BagItem(HatSeason.SUMMER));
    public static final BagItem HATBAG_HALLOWEEN = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_halloween"), new BagItem(HatSeason.HALLOWEEN));
    public static final BagItem HATBAG_FESTIVE = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatbag_festive"), new BagItem(HatSeason.FESTIVE));
    public static final Item HATSCRAPS_COMMON = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatscraps_common"), new Item(new Item.Settings().rarity(Rarity.COMMON)));//.group(SimpleHats.HAT_TAB)));
    public static final Item HATSCRAPS_UNCOMMON = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatscraps_uncommon"), new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));//.group(SimpleHats.HAT_TAB)));
    public static final Item HATSCRAPS_RARE = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatscraps_rare"), new Item(new Item.Settings().rarity(Rarity.RARE)));//.group(SimpleHats.HAT_TAB)));
    public static final Item HATSCRAPS_EASTER = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatscraps_easter"), new Item(new Item.Settings().rarity(Rarity.EPIC)));//.group(SimpleHats.HAT_TAB)));
    public static final Item HATSCRAPS_SUMMER = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatscraps_summer"), new Item(new Item.Settings().rarity(Rarity.EPIC)));//.group(SimpleHats.HAT_TAB)));
    public static final Item HATSCRAPS_HALLOWEEN = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatscraps_halloween"), new Item(new Item.Settings().rarity(Rarity.EPIC)));//.group(SimpleHats.HAT_TAB)));
    public static final Item HATSCRAPS_FESTIVE = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatscraps_festive"), new Item(new Item.Settings().rarity(Rarity.EPIC)));//.group(SimpleHats.HAT_TAB)));
    public static final Item HATICON = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "haticon"), new Item(new Item.Settings()));//.group(SimpleHats.HAT_TAB)));
    public static final HatDisplayItem HATDISPLAYITEM = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "hatdisplay"), new HatDisplayItem(new Item.Settings()));//.group(SimpleHats.HAT_TAB)));
    public static final HatItem HATSPECIAL = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, "special"), new HatItem(new HatEntry("special", Rarity.EPIC, 0)));

    public static void registerHats() {
        for(HatEntry entry : HatJson.getHatList()) {
            HatItem hat = entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(entry) : new HatItem(entry);
            hat = Registry.register(Registries.ITEM, Identifier.of(SimpleHats.modId, entry.getHatName()), hat);
            ModRegistry.hatList.add(hat);

            if(hat instanceof HatItemDyeable) {
                CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put((HatItemDyeable)hat, CauldronBehavior.CLEAN_DYEABLE_ITEM);
                TagInjector.inject(Registries.ITEM, ItemTags.DYEABLE.id(), hat);
            }
        }
        SimpleHats.logger.log(Level.INFO, "Generated " + ModRegistry.hatList.size() + " hat items from hat entries.");

        TagInjector.inject(Registries.ITEM, SimpleHats.ALL_HATS.id(), ModRegistry.hatList.stream().map(hatItem -> (Item) hatItem).toList());
    }

    ////
    //Entity Registry
    ////
    public static final EntityType<HatDisplay> HATDISPLAYENTITY = Registry.register(Registries.ENTITY_TYPE, Identifier.of(SimpleHats.modId, "hatdisplay"), EntityType.Builder.<HatDisplay>create(HatDisplay::new, SpawnGroup.MISC).dimensions(0.75F, 0.8125F).maxTrackingRange(10).build("hatdisplay"));
    ////
    //Recipe Registry
    ////
    public static final RecipeSerializer<?> HATSCRAP_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(SimpleHats.modId, "custom_hatscraps"), new SpecialRecipeSerializer<>(HatScrapRecipe::new));
    public static final RecipeSerializer<?> HATVARIANTS_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(SimpleHats.modId, "custom_hatvariants"), new SpecialRecipeSerializer<>(HatVariantRecipe::new));

    ////
    //Loot registry
    ////
    public static final List<RegistryKey<LootTable>> LOOT_HATINJECT_CHEST = Arrays.asList(
            LootTables.ABANDONED_MINESHAFT_CHEST,
            LootTables.NETHER_BRIDGE_CHEST,
            LootTables.STRONGHOLD_LIBRARY_CHEST,
            LootTables.DESERT_PYRAMID_CHEST,
            LootTables.JUNGLE_TEMPLE_CHEST,
            LootTables.WOODLAND_MANSION_CHEST,
            LootTables.BURIED_TREASURE_CHEST,
            LootTables.SHIPWRECK_TREASURE_CHEST,
            LootTables.PILLAGER_OUTPOST_CHEST,
            LootTables.SPAWN_BONUS_CHEST,
            LootTables.END_CITY_TREASURE_CHEST,
            LootTables.SIMPLE_DUNGEON_CHEST,
            LootTables.VILLAGE_ARMORER_CHEST,
            LootTables.VILLAGE_TEMPLE_CHEST,
            LootTables.PILLAGER_OUTPOST_CHEST,
            LootTables.BASTION_TREASURE_CHEST
    );
    public static final List<RegistryKey<LootTable>> LOOT_HATINJECT_ENTITY = Arrays.asList(
            EntityType.BLAZE.getLootTableId(),
            EntityType.CAVE_SPIDER.getLootTableId(),
            EntityType.CREEPER.getLootTableId(),
            EntityType.DROWNED.getLootTableId(),
            EntityType.ELDER_GUARDIAN.getLootTableId(),
            EntityType.ENDERMAN.getLootTableId(),
            EntityType.EVOKER.getLootTableId(),
            EntityType.GHAST.getLootTableId(),
            EntityType.GUARDIAN.getLootTableId(),
            EntityType.HUSK.getLootTableId(),
            EntityType.RAVAGER.getLootTableId(),
            EntityType.ILLUSIONER.getLootTableId(),
            EntityType.PHANTOM.getLootTableId(),
            EntityType.PILLAGER.getLootTableId(),
            EntityType.SKELETON.getLootTableId(),
            EntityType.SPIDER.getLootTableId(),
            EntityType.STRAY.getLootTableId(),
            EntityType.VINDICATOR.getLootTableId(),
            EntityType.WITCH.getLootTableId(),
            EntityType.WITHER_SKELETON.getLootTableId(),
            EntityType.ZOGLIN.getLootTableId(),
            EntityType.ZOMBIE.getLootTableId(),
            EntityType.ZOMBIFIED_PIGLIN.getLootTableId(),
            EntityType.HOGLIN.getLootTableId(),
            EntityType.ZOMBIE_VILLAGER.getLootTableId()
    );
}