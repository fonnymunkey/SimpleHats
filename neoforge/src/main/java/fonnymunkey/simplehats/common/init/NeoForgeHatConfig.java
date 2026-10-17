package fonnymunkey.simplehats.common.init;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeHatConfig implements IHatConfig {
	
	private static final String LANG = "text.config.simplehats.";
	
	private static final ModConfigSpec COMMON_SPEC;
	private static final ModConfigSpec CLIENT_SPEC;
	
	private static final ModConfigSpec.BooleanValue KEEP_HAT_ON_DEATH;
	private static final ModConfigSpec.IntValue SEASONAL_BAG_CHANCE;
	private static final ModConfigSpec.BooleanValue ALLOW_HAT_IN_HELMET_SLOT;
	
	private static final ModConfigSpec.BooleanValue ENABLE_MOB_LOOT;
	private static final ModConfigSpec.IntValue ENTITY_NONE_WEIGHT;
	private static final ModConfigSpec.IntValue ENTITY_COMMON_WEIGHT;
	private static final ModConfigSpec.IntValue ENTITY_UNCOMMON_WEIGHT;
	private static final ModConfigSpec.IntValue ENTITY_RARE_WEIGHT;
	private static final ModConfigSpec.IntValue ENTITY_EPIC_WEIGHT;
	
	private static final ModConfigSpec.BooleanValue ENABLE_CHEST_LOOT;
	private static final ModConfigSpec.IntValue CHEST_NONE_WEIGHT;
	private static final ModConfigSpec.IntValue CHEST_COMMON_WEIGHT;
	private static final ModConfigSpec.IntValue CHEST_UNCOMMON_WEIGHT;
	private static final ModConfigSpec.IntValue CHEST_RARE_WEIGHT;
	private static final ModConfigSpec.IntValue CHEST_EPIC_WEIGHT;
	
	private static final ModConfigSpec.BooleanValue FORCE_FIRST_PERSON_NO_RENDER;
	private static final ModConfigSpec.DoubleValue HAT_Y_OFFSET;
	
	static {
		ModConfigSpec.Builder common = new ModConfigSpec.Builder();
		KEEP_HAT_ON_DEATH = common.translation(LANG + "option.COMMON.keepHatOnDeath").define("keepHatOnDeath", true);
		SEASONAL_BAG_CHANCE = common.translation(LANG + "option.COMMON.seasonalBagChance").defineInRange("seasonalBagChance", 20, 0, 100);
		ALLOW_HAT_IN_HELMET_SLOT = common.translation(LANG + "option.COMMON.allowHatInHelmetSlot").define("allowHatInHelmetSlot", false);
		
		common.translation(LANG + "category.COMMON.entityLoot").push("entityLoot");
		ENABLE_MOB_LOOT = common.translation(LANG + "option.COMMON.entityLoot.enableMobLoot").define("enableMobLoot", true);
		ENTITY_NONE_WEIGHT = common.translation(LANG + "option.COMMON.entityLoot.entityNoneWeight").defineInRange("entityNoneWeight", 200, 1, 500);
		ENTITY_COMMON_WEIGHT = common.translation(LANG + "option.COMMON.entityLoot.entityCommonWeight").defineInRange("entityCommonWeight", 20, 0, 500);
		ENTITY_UNCOMMON_WEIGHT = common.translation(LANG + "option.COMMON.entityLoot.entityUncommonWeight").defineInRange("entityUncommonWeight", 15, 0, 500);
		ENTITY_RARE_WEIGHT = common.translation(LANG + "option.COMMON.entityLoot.entityRareWeight").defineInRange("entityRareWeight", 10, 0, 500);
		ENTITY_EPIC_WEIGHT = common.translation(LANG + "option.COMMON.entityLoot.entityEpicWeight").defineInRange("entityEpicWeight", 5, 0, 500);
		common.pop();
		
		common.translation(LANG + "category.COMMON.chestLoot").push("chestLoot");
		ENABLE_CHEST_LOOT = common.translation(LANG + "option.COMMON.chestLoot.enableChestLoot").define("enableChestLoot", true);
		CHEST_NONE_WEIGHT = common.translation(LANG + "option.COMMON.chestLoot.chestNoneWeight").defineInRange("chestNoneWeight", 150, 1, 500);
		CHEST_COMMON_WEIGHT = common.translation(LANG + "option.COMMON.chestLoot.chestCommonWeight").defineInRange("chestCommonWeight", 20, 0, 500);
		CHEST_UNCOMMON_WEIGHT = common.translation(LANG + "option.COMMON.chestLoot.chestUncommonWeight").defineInRange("chestUncommonWeight", 15, 0, 500);
		CHEST_RARE_WEIGHT = common.translation(LANG + "option.COMMON.chestLoot.chestRareWeight").defineInRange("chestRareWeight", 10, 0, 500);
		CHEST_EPIC_WEIGHT = common.translation(LANG + "option.COMMON.chestLoot.chestEpicWeight").defineInRange("chestEpicWeight", 5, 0, 500);
		common.pop();
		COMMON_SPEC = common.build();
		
		ModConfigSpec.Builder client = new ModConfigSpec.Builder();
		FORCE_FIRST_PERSON_NO_RENDER = client.translation(LANG + "option.CLIENT.forceFirstPersonNoRender").define("forceFirstPersonNoRender", false);
		HAT_Y_OFFSET = client.translation(LANG + "option.CLIENT.hatYOffset").defineInRange("hatYOffset", 0.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
		CLIENT_SPEC = client.build();
	}
	
	public static void register(ModContainer container) {
		container.registerConfig(ModConfig.Type.STARTUP, COMMON_SPEC);
		container.registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
	}
	
	@Override
	public boolean keepHatOnDeath() {
		return KEEP_HAT_ON_DEATH.get();
	}
	
	@Override
	public int seasonalBagChance() {
		return SEASONAL_BAG_CHANCE.get();
	}
	
	@Override
	public boolean allowHatInHelmetSlot() {
		return ALLOW_HAT_IN_HELMET_SLOT.get();
	}
	
	@Override
	public boolean forceFirstPersonNoRender() {
		return FORCE_FIRST_PERSON_NO_RENDER.get();
	}
	
	@Override
	public double hatYOffset() {
		return HAT_Y_OFFSET.get();
	}
	
	@Override
	public boolean enableChestLoot() {
		return ENABLE_CHEST_LOOT.get();
	}
	
	@Override
	public int chestCommonWeight() {
		return CHEST_COMMON_WEIGHT.get();
	}
	
	@Override
	public int chestUncommonWeight() {
		return CHEST_UNCOMMON_WEIGHT.get();
	}
	
	@Override
	public int chestRareWeight() {
		return CHEST_RARE_WEIGHT.get();
	}
	
	@Override
	public int chestEpicWeight() {
		return CHEST_EPIC_WEIGHT.get();
	}
	
	@Override
	public int chestNoneWeight() {
		return CHEST_NONE_WEIGHT.get();
	}
	
	@Override
	public boolean enableMobLoot() {
		return ENABLE_MOB_LOOT.get();
	}
	
	@Override
	public int entityCommonWeight() {
		return ENTITY_COMMON_WEIGHT.get();
	}
	
	@Override
	public int entityUncommonWeight() {
		return ENTITY_UNCOMMON_WEIGHT.get();
	}
	
	@Override
	public int entityRareWeight() {
		return ENTITY_RARE_WEIGHT.get();
	}
	
	@Override
	public int entityEpicWeight() {
		return ENTITY_EPIC_WEIGHT.get();
	}
	
	@Override
	public int entityNoneWeight() {
		return ENTITY_NONE_WEIGHT.get();
	}
}
