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
        //@ConfigEntry.Gui.Tooltip
        //public boolean allowUpdates = true;
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
        public int hatWeightCommon = 20;
        @ConfigEntry.Gui.Tooltip
        public int hatWeightUncommon = 15;
        @ConfigEntry.Gui.Tooltip
        public int hatWeightRare = 10;
        @ConfigEntry.Gui.Tooltip
        public int hatWeightEpic = 10;
        @ConfigEntry.Gui.Tooltip
        public int hatWeightNoneChest = 400;
        @ConfigEntry.Gui.Tooltip
        public int hatWeightNoneMob = 400;

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