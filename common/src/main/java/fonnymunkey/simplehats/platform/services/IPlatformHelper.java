package fonnymunkey.simplehats.platform.services;

import fonnymunkey.simplehats.common.init.IModRegistry;

import java.io.File;

public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }
    
    File getConfigDir();
    
    IModRegistry initModRegistry();
}