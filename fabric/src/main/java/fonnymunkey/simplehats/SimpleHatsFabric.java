package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.loot.LootRegistry;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class SimpleHatsFabric implements ModInitializer {
   
    @Override
    public void onInitialize() {
        SimpleHatsCommon.init();
        
        ModRegistry.registerHats();
        
        FabricDefaultAttributeRegistry.register(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), HatDisplay.createAttributes().build());
        
        LootTableEvents.MODIFY.register((id, tableBuilder, source, registryLookup) -> {
            if(SimpleHatsConfigAbstract.enableChestLoot() && LootRegistry.LOOT_HATINJECT_CHEST.contains(id)) {
                LootPool.Builder pool = LootPool.lootPool()
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.chestCommonWeight()).build())
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.chestUncommonWeight()).build())
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.chestRareWeight()).build())
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.chestEpicWeight()).build())
                                                .add(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.chestNoneWeight()).build())
                                                .setRolls(UniformGenerator.between(1.0F, 2.0F));
                tableBuilder.pool(pool.build());
            }
            else if(SimpleHatsConfigAbstract.enableMobLoot() && LootRegistry.LOOT_HATINJECT_ENTITY.contains(id)) {
                LootPool.Builder pool = LootPool.lootPool()
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.entityCommonWeight()).build())
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.entityUncommonWeight()).build())
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.entityRareWeight()).build())
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.entityEpicWeight()).build())
                                                .add(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.entityNoneWeight()).build())
                                                .setRolls(ConstantValue.exactly(1.0F))
                                                .when(LootItemKilledByPlayerCondition.killedByPlayer().build());
                tableBuilder.pool(pool.build());
            }
        });
    }
}