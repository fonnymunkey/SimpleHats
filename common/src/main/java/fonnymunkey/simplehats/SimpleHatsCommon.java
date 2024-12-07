package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.init.*;
import fonnymunkey.simplehats.common.init.SimpleHatsConfig;
import fonnymunkey.simplehats.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SimpleHatsCommon {
    
    public static final SimpleHatsConfig CONFIG = SimpleHatsConfig.createAndLoad();
    
    public static final IModRegistry MOD_REGISTRY = Services.PLATFORM.initModRegistry();
    
    public static final TagKey<Item> ALL_HATS = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(Constants.MOD_ID, "all_hats"));

    public static void init() {
        HatJson.registerHatJson();
    }
}