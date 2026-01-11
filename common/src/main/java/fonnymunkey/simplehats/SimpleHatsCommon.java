package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.init.*;
import fonnymunkey.simplehats.common.init.SimpleHatsConfig;
import fonnymunkey.simplehats.platform.Services;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SimpleHatsCommon {
    
    public static SimpleHatsConfig CONFIG;
    
    public static final IModRegistry MOD_REGISTRY = Services.PLATFORM.initModRegistry();
    
    public static final TagKey<Item> ALL_HATS = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "all_hats"));

    public static void init() {
        CONFIG = AutoConfig.register(SimpleHatsConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new)).getConfig();
        HatJson.registerHatJson();
    }
}