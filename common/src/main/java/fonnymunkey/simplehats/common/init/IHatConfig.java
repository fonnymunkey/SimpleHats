package fonnymunkey.simplehats.common.init;

public interface IHatConfig {
	
	boolean keepHatOnDeath();
	
	int seasonalBagChance();
	
	boolean allowHatInHelmetSlot();
	
	boolean forceFirstPersonNoRender();
	
	double hatYOffset();
	
	boolean enableChestLoot();
	
	int chestCommonWeight();
	
	int chestUncommonWeight();
	
	int chestRareWeight();
	
	int chestEpicWeight();
	
	int chestNoneWeight();
	
	boolean enableMobLoot();
	
	int entityCommonWeight();
	
	int entityUncommonWeight();
	
	int entityRareWeight();
	
	int entityEpicWeight();
	
	int entityNoneWeight();
}
