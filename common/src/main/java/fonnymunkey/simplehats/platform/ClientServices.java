package fonnymunkey.simplehats.platform;

import fonnymunkey.simplehats.platform.services.IClientPlatformHelper;

public class ClientServices {
    public static final IClientPlatformHelper CLIENT_PLATFORM = Services.load(IClientPlatformHelper.class);
}
