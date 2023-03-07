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
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleHats implements ModInitializer {
    public static final String modId = "simplehats";
    public static Logger logger = LogManager.getLogger();
    public static ModConfig config;

    public static ItemGroup HAT_TAB = FabricItemGroup.builder(new Identifier(modId, "hat_group"))
            .icon(() -> new ItemStack(ModRegistry.HATICON))
            .build();

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

        FabricDefaultAttributeRegistry.register(ModRegistry.HATDISPLAYENTITY, HatDisplay.createLivingAttributes());

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(SimpleHats.config.common.enableChestLoot && ModRegistry.LOOT_HATINJECT_CHEST.contains(id)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModRegistry.HATBAG_COMMON).weight(20))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_UNCOMMON).weight(15))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_RARE).weight(10))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_EPIC).weight(5))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(150))
                        .rolls(UniformLootNumberProvider.create(1.0F, 2.0F));
                tableBuilder.pool(pool);
            }
            else if(SimpleHats.config.common.enableMobLoot && ModRegistry.LOOT_HATINJECT_ENTITY.contains(id)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModRegistry.HATBAG_COMMON).weight(20))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_UNCOMMON).weight(15))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_RARE).weight(10))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_EPIC).weight(5))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(200))
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .conditionally(KilledByPlayerLootCondition.builder());
                tableBuilder.pool(pool);
            }
        });
    }
}