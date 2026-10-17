package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import fonnymunkey.simplehats.loot.LootRegistry;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.TagInjector;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EventHandler {
	
	@EventBusSubscriber(modid = Constants.MOD_ID)
	public static class EventHandlerMod {
		
		@SubscribeEvent
		public static void registerHats(RegisterEvent event) {
			if(event.getRegistryKey().equals(Registries.ITEM)) {
				for(HatEntry entry : HatJson.getHatList()) {
					Identifier id = Identifier.fromNamespaceAndPath(Constants.MOD_ID, entry.getHatName());
					Item.Properties properties = new Item.Properties()
						.setId(ResourceKey.create(Registries.ITEM, id));
					HatItem hat = entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(properties, entry) : new HatItem(properties, entry);
					event.register(Registries.ITEM, id, () -> hat);
					SimpleHatsCommon.MOD_REGISTRY.getHatList().add(hat);
					
					if(hat instanceof HatItemDyeable) {
						CauldronInteractions.WATER.put(hat, CauldronInteractions::dyedItemIteration);
						TagInjector.inject(BuiltInRegistries.ITEM, Tags.Items.DYED.location(), hat);
					}
				}
				Constants.LOG.info("Generated " + SimpleHatsCommon.MOD_REGISTRY.getHatList().size() + " hat items from hat entries.");
				
				TagInjector.inject(BuiltInRegistries.ITEM, SimpleHatsCommon.ALL_HATS.location(), SimpleHatsCommon.MOD_REGISTRY.getHatList().stream().map(hatItem -> (Item)hatItem).toList());
			}
		}
		
		@SubscribeEvent
		public static void entityAttributeCreation(EntityAttributeCreationEvent event) {
			event.put(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), HatDisplay.createAttributes().build());
		}
	}
	
	@EventBusSubscriber(modid = Constants.MOD_ID)
	public static class EventHandlerNeoForge {
		
		@SubscribeEvent
		public static void onLootTableLoadEvent(LootTableLoadEvent event) {
			if(SimpleHatsConfigAbstract.enableChestLoot() && LootRegistry.LOOT_HATINJECT_CHEST.contains(event.getName())) {
				LootPool.Builder pool = LootPool.lootPool()
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.chestCommonWeight()))
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.chestUncommonWeight()))
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.chestRareWeight()))
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.chestEpicWeight()))
												.add(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.chestNoneWeight()))
												.setRolls(UniformGenerator.between(1.0F, 2.0F));
				event.getTable().addPool(pool.build());
			}
			else if(SimpleHatsConfigAbstract.enableMobLoot() && LootRegistry.LOOT_HATINJECT_ENTITY.contains(event.getName())) {
				LootPool.Builder pool = LootPool.lootPool()
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagCommon()).setWeight(SimpleHatsConfigAbstract.entityCommonWeight()))
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagUncommon()).setWeight(SimpleHatsConfigAbstract.entityUncommonWeight()))
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagRare()).setWeight(SimpleHatsConfigAbstract.entityRareWeight()))
												.add(LootItem.lootTableItem(SimpleHatsCommon.MOD_REGISTRY.getHatBagEpic()).setWeight(SimpleHatsConfigAbstract.entityEpicWeight()))
												.add(LootItem.lootTableItem(ItemStack.EMPTY.getItem()).setWeight(SimpleHatsConfigAbstract.entityNoneWeight()))
												.setRolls(ConstantValue.exactly(1.0F))
												.when(LootItemKilledByPlayerCondition.killedByPlayer());
				event.getTable().addPool(pool.build());
			}
		}
	}
}