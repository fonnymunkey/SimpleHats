package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.ModConfig;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleHats implements ModInitializer {
    public static final String modId = "simplehats";
    public static Logger logger = LogManager.getLogger();
    public static ModConfig config;

    public static RegistryKey<ItemGroup> HAT_TAB = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(modId, "hat_group"));

    public static final TagKey<Item> ALL_HATS = TagKey.of(RegistryKeys.ITEM, Identifier.of(modId, "all_hats"));

    @Override
    public void onInitialize() {
        config = AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new)).getConfig();
        HatJson.registerHatJson();

        /*
        if(SimpleHats.config.common.allowUpdates) {
            UUIDHandler.setupUUIDMap();
        }
        */

        ModRegistry.registerHats();

        Registry.register(Registries.ITEM_GROUP, HAT_TAB, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModRegistry.HATICON))
                .displayName(Text.translatable("itemGroup.simplehats.hat_group"))
                .entries((context, entries) -> {
                    entries.add(ModRegistry.HATBAG_COMMON);
                    entries.add(ModRegistry.HATBAG_UNCOMMON);
                    entries.add(ModRegistry.HATBAG_RARE);
                    entries.add(ModRegistry.HATBAG_EPIC);
                    entries.add(ModRegistry.HATBAG_EASTER);
                    entries.add(ModRegistry.HATBAG_SUMMER);
                    entries.add(ModRegistry.HATBAG_HALLOWEEN);
                    entries.add(ModRegistry.HATBAG_FESTIVE);
                    entries.add(ModRegistry.HATSCRAPS_COMMON);
                    entries.add(ModRegistry.HATSCRAPS_UNCOMMON);
                    entries.add(ModRegistry.HATSCRAPS_RARE);
                    entries.add(ModRegistry.HATSCRAPS_EASTER);
                    entries.add(ModRegistry.HATSCRAPS_SUMMER);
                    entries.add(ModRegistry.HATSCRAPS_HALLOWEEN);
                    entries.add(ModRegistry.HATSCRAPS_FESTIVE);
                    entries.add(ModRegistry.HATICON);
                    entries.add(ModRegistry.HATDISPLAYITEM);

                    for(HatItem hat : ModRegistry.hatList) {
                        entries.add(hat);
                    }
                })
                .build());
/*
        ItemGroupEvents.modifyEntriesEvent(HAT_TAB).register(content -> {
            content.add(ModRegistry.HATBAG_COMMON);
            content.add(ModRegistry.HATBAG_UNCOMMON);
            content.add(ModRegistry.HATBAG_RARE);
            content.add(ModRegistry.HATBAG_EPIC);
            content.add(ModRegistry.HATBAG_EASTER);
            content.add(ModRegistry.HATBAG_SUMMER);
            content.add(ModRegistry.HATBAG_HALLOWEEN);
            content.add(ModRegistry.HATBAG_FESTIVE);
            content.add(ModRegistry.HATSCRAPS_COMMON);
            content.add(ModRegistry.HATSCRAPS_UNCOMMON);
            content.add(ModRegistry.HATSCRAPS_RARE);
            content.add(ModRegistry.HATSCRAPS_EASTER);
            content.add(ModRegistry.HATSCRAPS_SUMMER);
            content.add(ModRegistry.HATSCRAPS_HALLOWEEN);
            content.add(ModRegistry.HATSCRAPS_FESTIVE);
            content.add(ModRegistry.HATICON);
            content.add(ModRegistry.HATDISPLAYITEM);

            for(HatItem hat : ModRegistry.hatList) {
                content.add(hat);
            }
        });

 */

        FabricDefaultAttributeRegistry.register(ModRegistry.HATDISPLAYENTITY, HatDisplay.createLivingAttributes());

        LootTableEvents.MODIFY.register((id, tableBuilder, source, registryLookup) -> {
            if(SimpleHats.config.common.enableChestLoot && ModRegistry.LOOT_HATINJECT_CHEST.contains(id)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModRegistry.HATBAG_COMMON).weight(SimpleHats.config.common.chestCommonWeight))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_UNCOMMON).weight(SimpleHats.config.common.chestUncommonWeight))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_RARE).weight(SimpleHats.config.common.chestRareWeight))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_EPIC).weight(SimpleHats.config.common.chestEpicWeight))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(SimpleHats.config.common.chestNoneWeight))
                        .rolls(UniformLootNumberProvider.create(1.0F, 2.0F));
                tableBuilder.pool(pool);
            }
            else if(SimpleHats.config.common.enableMobLoot && ModRegistry.LOOT_HATINJECT_ENTITY.contains(id)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModRegistry.HATBAG_COMMON).weight(SimpleHats.config.common.entityCommonWeight))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_UNCOMMON).weight(SimpleHats.config.common.entityUncommonWeight))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_RARE).weight(SimpleHats.config.common.entityRareWeight))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_EPIC).weight(SimpleHats.config.common.entityEpicWeight))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(SimpleHats.config.common.entityNoneWeight))
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .conditionally(KilledByPlayerLootCondition.builder());
                tableBuilder.pool(pool);
            }
        });
    }
}