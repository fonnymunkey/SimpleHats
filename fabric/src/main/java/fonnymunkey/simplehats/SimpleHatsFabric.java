package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.loot.LootRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class SimpleHatsFabric implements ModInitializer {
   
    @Override
    public void onInitialize() {
        SimpleHatsCommon.init();
        
        ModRegistry.registerHats();
        
        FabricDefaultAttributeRegistry.register(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), HatDisplay.createAttributes().build());
        
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(SimpleHatsConfigAbstract.enableChestLoot() && LootRegistry.LOOT_HATINJECT_CHEST.contains(id)) {
                LootPool.Builder pool = LootPool.lootPool()
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.chestCommonWeight()).build())
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.chestUncommonWeight()).build())
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.chestRareWeight()).build())
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.chestEpicWeight()).build())
                                                .with(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.chestNoneWeight()).build())
                                                .setRolls(UniformGenerator.between(1.0F, 2.0F));
                tableBuilder.pool(pool.build());
            }
            else if(SimpleHatsConfigAbstract.enableMobLoot() && LootRegistry.LOOT_HATINJECT_ENTITY.contains(id)) {
                LootPool.Builder pool = LootPool.lootPool()
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.entityCommonWeight()).build())
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.entityUncommonWeight()).build())
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.entityRareWeight()).build())
                                                .with(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.entityEpicWeight()).build())
                                                .with(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.entityNoneWeight()).build())
                                                .setRolls(ConstantValue.exactly(1.0F))
                                                .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build());
                tableBuilder.pool(pool.build());
            }
        });
    }
}