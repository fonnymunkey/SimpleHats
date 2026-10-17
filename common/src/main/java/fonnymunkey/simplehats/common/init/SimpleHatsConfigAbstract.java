package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.SimpleHatsCommon;

public abstract class SimpleHatsConfigAbstract {
	
	public static boolean keepHatOnDeath() {
		return SimpleHatsCommon.CONFIG.keepHatOnDeath();
	}
	
	public static int seasonalBagChance() {
		return SimpleHatsCommon.CONFIG.seasonalBagChance();
	}
	
	public static boolean allowHatInHelmetSlot() {
		return SimpleHatsCommon.CONFIG.allowHatInHelmetSlot();
	}
	
	public static boolean forceFirstPersonNoRender() {
		return SimpleHatsCommon.CONFIG.forceFirstPersonNoRender();
	}
	
	public static double hatYOffset() {
		return SimpleHatsCommon.CONFIG.hatYOffset();
	}
	
	public static boolean enableChestLoot() {
		return SimpleHatsCommon.CONFIG.enableChestLoot();
	}
	
	public static int chestCommonWeight() {
		return SimpleHatsCommon.CONFIG.chestCommonWeight();
	}
	
	public static int chestUncommonWeight() {
		return SimpleHatsCommon.CONFIG.chestUncommonWeight();
	}
	
	public static int chestRareWeight() {
		return SimpleHatsCommon.CONFIG.chestRareWeight();
	}
	
	public static int chestEpicWeight() {
		return SimpleHatsCommon.CONFIG.chestEpicWeight();
	}
	
	public static int chestNoneWeight() {
		return SimpleHatsCommon.CONFIG.chestNoneWeight();
	}
	
	public static boolean enableMobLoot() {
		return SimpleHatsCommon.CONFIG.enableMobLoot();
	}
	
	public static int entityCommonWeight() {
		return SimpleHatsCommon.CONFIG.entityCommonWeight();
	}
	
	public static int entityUncommonWeight() {
		return SimpleHatsCommon.CONFIG.entityUncommonWeight();
	}
	
	public static int entityRareWeight() {
		return SimpleHatsCommon.CONFIG.entityRareWeight();
	}
	
	public static int entityEpicWeight() {
		return SimpleHatsCommon.CONFIG.entityEpicWeight();
	}
	
	public static int entityNoneWeight() {
		return SimpleHatsCommon.CONFIG.entityNoneWeight();
	}
}