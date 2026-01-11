package fonnymunkey.simplehats.platform;

import fonnymunkey.simplehats.common.init.IModRegistry;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
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