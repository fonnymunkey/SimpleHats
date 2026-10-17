package fonnymunkey.simplehats.loot;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Arrays;
import java.util.List;

public abstract class LootRegistry {

	public static final List<ResourceKey<LootTable>> LOOT_HATINJECT_CHEST = Arrays.asList(
			BuiltInLootTables.ABANDONED_MINESHAFT,
			BuiltInLootTables.NETHER_BRIDGE,
			BuiltInLootTables.STRONGHOLD_LIBRARY,
			BuiltInLootTables.DESERT_PYRAMID,
			BuiltInLootTables.JUNGLE_TEMPLE,
			BuiltInLootTables.WOODLAND_MANSION,
			BuiltInLootTables.BURIED_TREASURE,
			BuiltInLootTables.SHIPWRECK_TREASURE,
			BuiltInLootTables.PILLAGER_OUTPOST,
			BuiltInLootTables.SPAWN_BONUS_CHEST,
			BuiltInLootTables.END_CITY_TREASURE,
			BuiltInLootTables.SIMPLE_DUNGEON,
			BuiltInLootTables.VILLAGE_ARMORER,
			BuiltInLootTables.VILLAGE_TEMPLE,
			BuiltInLootTables.PILLAGER_OUTPOST,
			BuiltInLootTables.BASTION_TREASURE
	);
	
	public static final List<ResourceKey<LootTable>> LOOT_HATINJECT_ENTITY = Arrays.asList(
			EntityType.BLAZE.getDefaultLootTable().orElseThrow(),
			EntityType.CAVE_SPIDER.getDefaultLootTable().orElseThrow(),
			EntityType.CREEPER.getDefaultLootTable().orElseThrow(),
			EntityType.DROWNED.getDefaultLootTable().orElseThrow(),
			EntityType.ELDER_GUARDIAN.getDefaultLootTable().orElseThrow(),
			EntityType.ENDERMAN.getDefaultLootTable().orElseThrow(),
			EntityType.EVOKER.getDefaultLootTable().orElseThrow(),
			EntityType.GHAST.getDefaultLootTable().orElseThrow(),
			EntityType.GUARDIAN.getDefaultLootTable().orElseThrow(),
			EntityType.HUSK.getDefaultLootTable().orElseThrow(),
			EntityType.RAVAGER.getDefaultLootTable().orElseThrow(),
			EntityType.ILLUSIONER.getDefaultLootTable().orElseThrow(),
			EntityType.PHANTOM.getDefaultLootTable().orElseThrow(),
			EntityType.PILLAGER.getDefaultLootTable().orElseThrow(),
			EntityType.SKELETON.getDefaultLootTable().orElseThrow(),
			EntityType.SPIDER.getDefaultLootTable().orElseThrow(),
			EntityType.STRAY.getDefaultLootTable().orElseThrow(),
			EntityType.VINDICATOR.getDefaultLootTable().orElseThrow(),
			EntityType.WITCH.getDefaultLootTable().orElseThrow(),
			EntityType.WITHER_SKELETON.getDefaultLootTable().orElseThrow(),
			EntityType.ZOGLIN.getDefaultLootTable().orElseThrow(),
			EntityType.ZOMBIE.getDefaultLootTable().orElseThrow(),
			EntityType.ZOMBIFIED_PIGLIN.getDefaultLootTable().orElseThrow(),
			EntityType.HOGLIN.getDefaultLootTable().orElseThrow(),
			EntityType.ZOMBIE_VILLAGER.getDefaultLootTable().orElseThrow()
	);
}