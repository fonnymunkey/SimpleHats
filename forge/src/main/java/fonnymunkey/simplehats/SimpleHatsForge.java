package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.init.ModRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class SimpleHatsForge {

    public SimpleHatsForge() {
        SimpleHatsCommon.init();
        
        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.registerHats(eventBus);
    }
}