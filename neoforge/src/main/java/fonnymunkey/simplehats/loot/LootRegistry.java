package fonnymunkey.simplehats.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Arrays;
import java.util.List;

public abstract class LootRegistry {

	public static final List<ResourceLocation> LOOT_HATINJECT_CHEST = Arrays.asList(
			BuiltInLootTables.ABANDONED_MINESHAFT.location(),
			BuiltInLootTables.NETHER_BRIDGE.location(),
			BuiltInLootTables.STRONGHOLD_LIBRARY.location(),
			BuiltInLootTables.DESERT_PYRAMID.location(),
			BuiltInLootTables.JUNGLE_TEMPLE.location(),
			BuiltInLootTables.WOODLAND_MANSION.location(),
			BuiltInLootTables.BURIED_TREASURE.location(),
			BuiltInLootTables.SHIPWRECK_TREASURE.location(),
			BuiltInLootTables.PILLAGER_OUTPOST.location(),
			BuiltInLootTables.SPAWN_BONUS_CHEST.location(),
			BuiltInLootTables.END_CITY_TREASURE.location(),
			BuiltInLootTables.SIMPLE_DUNGEON.location(),
			BuiltInLootTables.VILLAGE_ARMORER.location(),
			BuiltInLootTables.VILLAGE_TEMPLE.location(),
			BuiltInLootTables.PILLAGER_OUTPOST.location(),
			BuiltInLootTables.BASTION_TREASURE.location()
	);
	
	public static final List<ResourceLocation> LOOT_HATINJECT_ENTITY = Arrays.asList(
			EntityType.BLAZE.getDefaultLootTable().location(),
			EntityType.CAVE_SPIDER.getDefaultLootTable().location(),
			EntityType.CREEPER.getDefaultLootTable().location(),
			EntityType.DROWNED.getDefaultLootTable().location(),
			EntityType.ELDER_GUARDIAN.getDefaultLootTable().location(),
			EntityType.ENDERMAN.getDefaultLootTable().location(),
			EntityType.EVOKER.getDefaultLootTable().location(),
			EntityType.GHAST.getDefaultLootTable().location(),
			EntityType.GUARDIAN.getDefaultLootTable().location(),
			EntityType.HUSK.getDefaultLootTable().location(),
			EntityType.RAVAGER.getDefaultLootTable().location(),
			EntityType.ILLUSIONER.getDefaultLootTable().location(),
			EntityType.PHANTOM.getDefaultLootTable().location(),
			EntityType.PILLAGER.getDefaultLootTable().location(),
			EntityType.SKELETON.getDefaultLootTable().location(),
			EntityType.SPIDER.getDefaultLootTable().location(),
			EntityType.STRAY.getDefaultLootTable().location(),
			EntityType.VINDICATOR.getDefaultLootTable().location(),
			EntityType.WITCH.getDefaultLootTable().location(),
			EntityType.WITHER_SKELETON.getDefaultLootTable().location(),
			EntityType.ZOGLIN.getDefaultLootTable().location(),
			EntityType.ZOMBIE.getDefaultLootTable().location(),
			EntityType.ZOMBIFIED_PIGLIN.getDefaultLootTable().location(),
			EntityType.HOGLIN.getDefaultLootTable().location(),
			EntityType.ZOMBIE_VILLAGER.getDefaultLootTable().location()
	);
}