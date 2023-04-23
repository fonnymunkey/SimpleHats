package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.ModConfig;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.util.UUIDHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
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

    public static final ItemGroup HAT_TAB = FabricItemGroupBuilder.build(
            new Identifier(SimpleHats.modId),
            () -> new ItemStack(ModRegistry.HATICON));

    @Override
    public void onInitialize() {
        config = AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new)).getConfig();
        HatJson.registerHatJson();

        if(SimpleHats.config.common.allowUpdates) {
            UUIDHandler.setupUUIDMap();
        }

        ModRegistry.registerHats();

        FabricDefaultAttributeRegistry.register(ModRegistry.HATDISPLAYENTITY, HatDisplay.createLivingAttributes());

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
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