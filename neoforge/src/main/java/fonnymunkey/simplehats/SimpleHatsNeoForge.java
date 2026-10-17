package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.init.NeoForgeHatConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class SimpleHatsNeoForge {

    public SimpleHatsNeoForge(IEventBus eventBus, ModContainer container) {
        NeoForgeHatConfig.register(container);
        
        SimpleHatsCommon.init();
        
        ModRegistry.registerHats(eventBus);
    }
}
