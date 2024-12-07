package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.Constants;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Config(name = Constants.MOD_ID, wrapperName = "SimpleHatsConfig")
@Modmenu(modId = Constants.MOD_ID)
public class SimpleHatsConfigModel {
	
	@Nest
	@Expanded
	public CommonOptions COMMON = new CommonOptions();
	
	@Nest
	@Expanded
	public ClientOptions CLIENT = new ClientOptions();
	
	public static class CommonOptions {
		
		public boolean keepHatOnDeath = true;
		
		@RangeConstraint(min = 0, max = 100)
		public int seasonalBagChance = 20;
		
		@Sync(value = Option.SyncMode.OVERRIDE_CLIENT)
		@RestartRequired
		public boolean allowHatInHelmetSlot = false;
		
		@Nest
		public EntityLootOptions entityLoot = new EntityLootOptions();
		
		@Nest
		public ChestLootOptions chestLoot = new ChestLootOptions();
	}
	
	public static class ClientOptions {
		
		public boolean forceFirstPersonNoRender = false;
		
		public double hatYOffset = 0.0D;
	}
	
	public static class EntityLootOptions {
		
		@RestartRequired
		public boolean enableMobLoot = true;
		
		@RestartRequired
		@RangeConstraint(min = 1, max = 500)
		public int entityNoneWeight = 200;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int entityCommonWeight = 20;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int entityUncommonWeight = 15;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int entityRareWeight = 10;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int entityEpicWeight = 5;
	}
	
	public static class ChestLootOptions {
		
		@RestartRequired
		public boolean enableChestLoot = true;
		
		@RestartRequired
		@RangeConstraint(min = 1, max = 500)
		public int chestNoneWeight = 150;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int chestCommonWeight = 20;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int chestUncommonWeight = 15;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int chestRareWeight = 10;
		
		@RestartRequired
		@RangeConstraint(min = 0, max = 500)
		public int chestEpicWeight = 5;
	}
}