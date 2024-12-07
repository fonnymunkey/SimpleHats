package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.init.ModRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class SimpleHatsNeoForge {

    public SimpleHatsNeoForge(IEventBus eventBus) {
        SimpleHatsCommon.init();
        
        ModRegistry.registerHats(eventBus);
    }
}