package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.SimpleHatsCommon;

public abstract class SimpleHatsConfigAbstract {
	
	public static boolean keepHatOnDeath() {
		return SimpleHatsCommon.CONFIG.COMMON.keepHatOnDeath();
	}
	
	public static int seasonalBagChance() {
		return SimpleHatsCommon.CONFIG.COMMON.seasonalBagChance();
	}
	
	public static boolean allowHatInHelmetSlot() {
		return SimpleHatsCommon.CONFIG.COMMON.allowHatInHelmetSlot();
	}
	
	public static boolean forceFirstPersonNoRender() {
		return SimpleHatsCommon.CONFIG.CLIENT.forceFirstPersonNoRender();
	}
	
	public static double hatYOffset() {
		return SimpleHatsCommon.CONFIG.CLIENT.hatYOffset();
	}
	
	public static boolean enableChestLoot() {
		return SimpleHatsCommon.CONFIG.COMMON.chestLoot.enableChestLoot();
	}
	
	public static int chestCommonWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.chestLoot.chestCommonWeight();
	}
	
	public static int chestUncommonWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.chestLoot.chestUncommonWeight();
	}
	
	public static int chestRareWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.chestLoot.chestRareWeight();
	}
	
	public static int chestEpicWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.chestLoot.chestEpicWeight();
	}
	
	public static int chestNoneWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.chestLoot.chestNoneWeight();
	}
	
	public static boolean enableMobLoot() {
		return SimpleHatsCommon.CONFIG.COMMON.entityLoot.enableMobLoot();
	}
	
	public static int entityCommonWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.entityLoot.entityCommonWeight();
	}
	
	public static int entityUncommonWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.entityLoot.entityUncommonWeight();
	}
	
	public static int entityRareWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.entityLoot.entityRareWeight();
	}
	
	public static int entityEpicWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.entityLoot.entityEpicWeight();
	}
	
	public static int entityNoneWeight() {
		return SimpleHatsCommon.CONFIG.COMMON.entityLoot.entityNoneWeight();
	}
}