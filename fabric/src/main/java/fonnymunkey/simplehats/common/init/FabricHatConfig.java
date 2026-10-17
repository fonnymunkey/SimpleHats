package fonnymunkey.simplehats.common.init;

public class FabricHatConfig implements IHatConfig {
	
	private final SimpleHatsConfig config = SimpleHatsConfig.createAndLoad();
	
	@Override
	public boolean keepHatOnDeath() {
		return this.config.COMMON.keepHatOnDeath();
	}
	
	@Override
	public int seasonalBagChance() {
		return this.config.COMMON.seasonalBagChance();
	}
	
	@Override
	public boolean allowHatInHelmetSlot() {
		return this.config.COMMON.allowHatInHelmetSlot();
	}
	
	@Override
	public boolean forceFirstPersonNoRender() {
		return this.config.CLIENT.forceFirstPersonNoRender();
	}
	
	@Override
	public double hatYOffset() {
		return this.config.CLIENT.hatYOffset();
	}
	
	@Override
	public boolean enableChestLoot() {
		return this.config.COMMON.chestLoot.enableChestLoot();
	}
	
	@Override
	public int chestCommonWeight() {
		return this.config.COMMON.chestLoot.chestCommonWeight();
	}
	
	@Override
	public int chestUncommonWeight() {
		return this.config.COMMON.chestLoot.chestUncommonWeight();
	}
	
	@Override
	public int chestRareWeight() {
		return this.config.COMMON.chestLoot.chestRareWeight();
	}
	
	@Override
	public int chestEpicWeight() {
		return this.config.COMMON.chestLoot.chestEpicWeight();
	}
	
	@Override
	public int chestNoneWeight() {
		return this.config.COMMON.chestLoot.chestNoneWeight();
	}
	
	@Override
	public boolean enableMobLoot() {
		return this.config.COMMON.entityLoot.enableMobLoot();
	}
	
	@Override
	public int entityCommonWeight() {
		return this.config.COMMON.entityLoot.entityCommonWeight();
	}
	
	@Override
	public int entityUncommonWeight() {
		return this.config.COMMON.entityLoot.entityUncommonWeight();
	}
	
	@Override
	public int entityRareWeight() {
		return this.config.COMMON.entityLoot.entityRareWeight();
	}
	
	@Override
	public int entityEpicWeight() {
		return this.config.COMMON.entityLoot.entityEpicWeight();
	}
	
	@Override
	public int entityNoneWeight() {
		return this.config.COMMON.entityLoot.entityNoneWeight();
	}
}
