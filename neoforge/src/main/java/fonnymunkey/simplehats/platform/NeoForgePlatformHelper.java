package fonnymunkey.simplehats.platform;

import fonnymunkey.simplehats.common.init.IModRegistry;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
    
    @Override
    public File getConfigDir() {
        return FMLPaths.CONFIGDIR.get().toFile();
    }
    
    @Override
    public IModRegistry initModRegistry() {
        return new ModRegistry();
    }
}