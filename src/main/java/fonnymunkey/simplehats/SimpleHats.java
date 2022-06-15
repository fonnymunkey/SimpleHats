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
            if(ModRegistry.LOOT_HATINJECT_CHEST.contains(id)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModRegistry.HATBAG_COMMON).weight(20))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_UNCOMMON).weight(15))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_RARE).weight(10))
                        .with(ItemEntry.builder(ModRegistry.HATBAG_EPIC).weight(5))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(150))
                        .rolls(UniformLootNumberProvider.create(1.0F, 2.0F));
                tableBuilder.pool(pool);
            }
            else if(ModRegistry.LOOT_HATINJECT_ENTITY.contains(id)) {
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