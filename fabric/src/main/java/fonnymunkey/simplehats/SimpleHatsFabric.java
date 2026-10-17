package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.loot.LootRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
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
        
        LootTableEvents.MODIFY.register((id, tableBuilder, source, registryLookup) -> {
            if(SimpleHatsConfigAbstract.enableChestLoot() && LootRegistry.LOOT_HATINJECT_CHEST.contains(id)) {
                LootPool.Builder pool = LootPool.lootPool()
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.chestCommonWeight()))
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.chestUncommonWeight()))
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.chestRareWeight()))
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.chestEpicWeight()))
                                                .add(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.chestNoneWeight()))
                                                .setRolls(UniformGenerator.between(1.0F, 2.0F));
                tableBuilder.withPool(pool);
            }
            else if(SimpleHatsConfigAbstract.enableMobLoot() && LootRegistry.LOOT_HATINJECT_ENTITY.contains(id)) {
                LootPool.Builder pool = LootPool.lootPool()
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.entityCommonWeight()))
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.entityUncommonWeight()))
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.entityRareWeight()))
                                                .add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.entityEpicWeight()))
                                                .add(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.entityNoneWeight()))
                                                .setRolls(ConstantValue.exactly(1.0F))
                                                .when(LootItemKilledByPlayerCondition.killedByPlayer());
                tableBuilder.withPool(pool);
            }
        });
    }
}