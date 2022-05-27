package fonnymunkey.simplehats.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class Config {
//TODO all this blegh
    public static void reloadConfig(ModConfigEvent event) {
        if(event.getConfig().getSpec() == CLIENT_SPEC) cachedClient.refresh();
        else if(event.getConfig().getSpec() == COMMON_SPEC) cachedCommon.refresh();
        else if(event.getConfig().getSpec() == SERVER_SPEC) cachedServer.refresh();
    }

    public static ForgeConfigSpec CLIENT_SPEC;
    public static ForgeConfigSpec.BooleanValue CLIENT_BOOL;

    public static ForgeConfigSpec COMMON_SPEC;
    public static ForgeConfigSpec.IntValue COMMON_INT;

    public static ForgeConfigSpec SERVER_SPEC;
    public static ForgeConfigSpec.ConfigValue<String> SERVER_STRING;

    static {
        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        clientBuilder.push("Client Category").comment("Test Client");
        CLIENT_BOOL = clientBuilder
                .comment("Test Client Bool").define("clientBool", false);
        clientBuilder.pop();
        CLIENT_SPEC = clientBuilder.build();

        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        commonBuilder.push("Common Category").comment("Test Common");
        COMMON_INT = commonBuilder
                .comment("Test Common Int").defineInRange("commonInt", 1, 0, 10);
        commonBuilder.pop();
        COMMON_SPEC = commonBuilder.build();

        ForgeConfigSpec.Builder serverBuilder = new ForgeConfigSpec.Builder();
        serverBuilder.push("Server Category").comment("Test Server");
        SERVER_STRING = serverBuilder
                .comment("Test Server String").define("serverString", "ExampleString");
        serverBuilder.pop();
        SERVER_SPEC = serverBuilder.build();
    }

    public static class cachedClient {
        public static boolean CLIENT_BOOL;

        public static void refresh() {
            CLIENT_BOOL = Config.CLIENT_BOOL.get();
        }
    }

    public static class cachedCommon {
        public static int COMMON_INT;

        public static void refresh() { COMMON_INT = Config.COMMON_INT.get(); }
    }

    public static class cachedServer {
        public static String SERVER_STRING;

        public static void refresh() {
            SERVER_STRING = Config.SERVER_STRING.get();
        }
    }
}
