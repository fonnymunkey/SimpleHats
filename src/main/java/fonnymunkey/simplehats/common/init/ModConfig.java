package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.SimpleHats;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = SimpleHats.modId)
public class ModConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject
    public Common common = new Common();

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public Client client = new Client();

    @Config(name = "common")
    public static class Common implements ConfigData {
        @ConfigEntry.Gui.Tooltip
        public boolean keepHatOnDeath = true;
        @ConfigEntry.Gui.Tooltip
        public boolean allowUpdates = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int seasonalBagChance = 20;

        @ConfigEntry.Gui.Tooltip
        public boolean enableMobLoot = true;

        @ConfigEntry.Gui.Tooltip
        public boolean enableChestLoot = true;

        @ConfigEntry.Gui.Tooltip
        public boolean allowHatInHelmetSlot = false;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 500)
        public int chestNoneWeight = 150;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int chestCommonWeight = 20;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int chestUncommonWeight = 15;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int chestRareWeight = 10;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int chestEpicWeight = 5;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 500)
        public int entityNoneWeight = 200;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int entityCommonWeight = 20;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int entityUncommonWeight = 15;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int entityRareWeight = 10;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int entityEpicWeight = 5;

        private Common() {}
    }

    @Config(name = "client")
    public static class Client implements ConfigData {
        @ConfigEntry.Gui.Tooltip
        public boolean forceFirstPersonNoRender = false;

        @ConfigEntry.Gui.Tooltip
        public double hatYOffset = 0.0D;

        private Client() {}
    }
}