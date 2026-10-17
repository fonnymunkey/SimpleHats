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
			EntityType.BLAZE.getDefaultLootTable().orElseThrow().location(),
			EntityType.CAVE_SPIDER.getDefaultLootTable().orElseThrow().location(),
			EntityType.CREEPER.getDefaultLootTable().orElseThrow().location(),
			EntityType.DROWNED.getDefaultLootTable().orElseThrow().location(),
			EntityType.ELDER_GUARDIAN.getDefaultLootTable().orElseThrow().location(),
			EntityType.ENDERMAN.getDefaultLootTable().orElseThrow().location(),
			EntityType.EVOKER.getDefaultLootTable().orElseThrow().location(),
			EntityType.GHAST.getDefaultLootTable().orElseThrow().location(),
			EntityType.GUARDIAN.getDefaultLootTable().orElseThrow().location(),
			EntityType.HUSK.getDefaultLootTable().orElseThrow().location(),
			EntityType.RAVAGER.getDefaultLootTable().orElseThrow().location(),
			EntityType.ILLUSIONER.getDefaultLootTable().orElseThrow().location(),
			EntityType.PHANTOM.getDefaultLootTable().orElseThrow().location(),
			EntityType.PILLAGER.getDefaultLootTable().orElseThrow().location(),
			EntityType.SKELETON.getDefaultLootTable().orElseThrow().location(),
			EntityType.SPIDER.getDefaultLootTable().orElseThrow().location(),
			EntityType.STRAY.getDefaultLootTable().orElseThrow().location(),
			EntityType.VINDICATOR.getDefaultLootTable().orElseThrow().location(),
			EntityType.WITCH.getDefaultLootTable().orElseThrow().location(),
			EntityType.WITHER_SKELETON.getDefaultLootTable().orElseThrow().location(),
			EntityType.ZOGLIN.getDefaultLootTable().orElseThrow().location(),
			EntityType.ZOMBIE.getDefaultLootTable().orElseThrow().location(),
			EntityType.ZOMBIFIED_PIGLIN.getDefaultLootTable().orElseThrow().location(),
			EntityType.HOGLIN.getDefaultLootTable().orElseThrow().location(),
			EntityType.ZOMBIE_VILLAGER.getDefaultLootTable().orElseThrow().location()
	);
}