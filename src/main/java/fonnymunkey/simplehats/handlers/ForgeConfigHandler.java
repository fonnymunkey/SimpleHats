package fonnymunkey.simplehats.handlers;

import fonnymunkey.simplehats.SimpleHats;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = SimpleHats.MODID)
public class ForgeConfigHandler {

	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static ServerConfig server = new ServerConfig();

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static ClientConfig client = new ClientConfig();

	public static class ServerConfig {

		@Config.Comment("Keep equipped hat on death.")
		@Config.Name("Keep Hat On Death")
		public boolean keepHatOnDeath = true;

		@Config.Comment("Chance for a seasonal bag to drop when using a bag during a seasonal event. (0 effectively disables seasons)")
		@Config.Name("Seasonal Bag Chance")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double seasonalBagChance = 0.2D;

		@Config.Comment("Allow equipping hat in helmet slot for compatibility with non-player entities/displays.")
		@Config.Name("Allow Hat In Helmet Slot")
		public boolean allowHatInHelmetSlot = false;

		@Config.Comment("Hide the helmet of players when a hat is visible in a slot.")
		@Config.Name("Hide Helmet With Hat")
		public boolean hideHelmetWithHat = true;
	}

	public static class ClientConfig {

		@Config.Comment("Y Offset for hats to match skin's features if desired.")
		@Config.Name("Hat Y Offset")
		@Config.RangeDouble(min = -1.0D, max = 1.0D)
		public double hatYOffset = 0.0D;

		@Config.Comment("Force hats to not render on self when in first person. (For compatability with First-Person-Model mods, normally not needed)")
		@Config.Name("Force First Person No Render")
		public boolean forceFirstPersonNoRender = false;
	}

	@Mod.EventBusSubscriber(modid = SimpleHats.MODID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(SimpleHats.MODID)) {
				ConfigManager.sync(SimpleHats.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
