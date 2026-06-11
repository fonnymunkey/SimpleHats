package fonnymunkey.simplehats.loot;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public abstract class LootRegistry {

	public static final List<Identifier> LOOT_HATINJECT_CHEST = Arrays.asList(
			BuiltInLootTables.ABANDONED_MINESHAFT.identifier(),
			BuiltInLootTables.NETHER_BRIDGE.identifier(),
			BuiltInLootTables.STRONGHOLD_LIBRARY.identifier(),
			BuiltInLootTables.DESERT_PYRAMID.identifier(),
			BuiltInLootTables.JUNGLE_TEMPLE.identifier(),
			BuiltInLootTables.WOODLAND_MANSION.identifier(),
			BuiltInLootTables.BURIED_TREASURE.identifier(),
			BuiltInLootTables.SHIPWRECK_TREASURE.identifier(),
			BuiltInLootTables.PILLAGER_OUTPOST.identifier(),
			BuiltInLootTables.SPAWN_BONUS_CHEST.identifier(),
			BuiltInLootTables.END_CITY_TREASURE.identifier(),
			BuiltInLootTables.SIMPLE_DUNGEON.identifier(),
			BuiltInLootTables.VILLAGE_ARMORER.identifier(),
			BuiltInLootTables.VILLAGE_TEMPLE.identifier(),
			BuiltInLootTables.PILLAGER_OUTPOST.identifier(),
			BuiltInLootTables.BASTION_TREASURE.identifier()
	);
	
	public static final List<Identifier> LOOT_HATINJECT_ENTITY = Stream.of(
			EntityType.BLAZE.getDefaultLootTable(),
			EntityType.CAVE_SPIDER.getDefaultLootTable(),
			EntityType.CREEPER.getDefaultLootTable(),
			EntityType.DROWNED.getDefaultLootTable(),
			EntityType.ELDER_GUARDIAN.getDefaultLootTable(),
			EntityType.ENDERMAN.getDefaultLootTable(),
			EntityType.EVOKER.getDefaultLootTable(),
			EntityType.GHAST.getDefaultLootTable(),
			EntityType.GUARDIAN.getDefaultLootTable(),
			EntityType.HUSK.getDefaultLootTable(),
			EntityType.RAVAGER.getDefaultLootTable(),
			EntityType.ILLUSIONER.getDefaultLootTable(),
			EntityType.PHANTOM.getDefaultLootTable(),
			EntityType.PILLAGER.getDefaultLootTable(),
			EntityType.SKELETON.getDefaultLootTable(),
			EntityType.SPIDER.getDefaultLootTable(),
			EntityType.STRAY.getDefaultLootTable(),
			EntityType.VINDICATOR.getDefaultLootTable(),
			EntityType.WITCH.getDefaultLootTable(),
			EntityType.WITHER_SKELETON.getDefaultLootTable(),
			EntityType.ZOGLIN.getDefaultLootTable(),
			EntityType.ZOMBIE.getDefaultLootTable(),
			EntityType.ZOMBIFIED_PIGLIN.getDefaultLootTable(),
			EntityType.HOGLIN.getDefaultLootTable(),
			EntityType.ZOMBIE_VILLAGER.getDefaultLootTable()
	)
		.filter(Optional::isPresent)
		.map(Optional::orElseThrow)
		.map(ResourceKey::identifier)
		.toList();
}