package fonnymunkey.simplehats.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Arrays;
import java.util.List;

public abstract class LootRegistry {

	public static final List<ResourceLocation> LOOT_HATINJECT_CHEST = Arrays.asList(
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
	
	public static final List<ResourceLocation> LOOT_HATINJECT_ENTITY = Arrays.asList(
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
	);
}