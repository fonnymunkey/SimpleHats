package fonnymunkey.simplehats.platform;

import fonnymunkey.simplehats.common.init.IModRegistry;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
    
    @Override
    public File getConfigDir() {
        return FabricLoader.getInstance().getConfigDir().toFile();
    }
    
    @Override
    public IModRegistry initModRegistry() {
        return new ModRegistry();
    }
}