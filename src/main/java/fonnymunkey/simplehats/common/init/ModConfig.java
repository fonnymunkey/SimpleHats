package fonnymunkey.simplehats.common.init;

import com.google.common.io.Files;
import fonnymunkey.simplehats.SimpleHats;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.charset.Charset;

public class ModConfig {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final Common COMMON = new Common(COMMON_BUILDER);
    public static final Client CLIENT = new Client(CLIENT_BUILDER);
    public static final ForgeConfigSpec COMMON_SPEC = COMMON_BUILDER.build();
    public static final ForgeConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

    public static class Common {
        public final ForgeConfigSpec.BooleanValue keepHatOnDeath;
        public final ForgeConfigSpec.BooleanValue allowUpdates;
        public final ForgeConfigSpec.DoubleValue seasonalBagChance;
        public final ForgeConfigSpec.BooleanValue allowHatInHelmetSlot;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("General");

            keepHatOnDeath = builder
                    .comment("Keep equipped hat on death.")
                    .define("keepHatOnDeath", true);

            allowUpdates = builder
                    .comment("Allow automatic updating of player-specific hats")
                    .define("allowUpdates", true);

            seasonalBagChance = builder
                    .comment("Chance for a seasonal bag to drop when using a bag during a seasonal event. (0 effectively disables seasons)")
                    .defineInRange("seasonalBagChance", 0.2D, 0.0D, 1.0D);

            allowHatInHelmetSlot = builder
                    .comment("Allow equipping hat in helmet slot for compatibility with non-player entities/displays.")
                    .define("allowHatInHelmetSlot", false);

        builder.pop();
        }
    }

    public static class Client {
        public final ForgeConfigSpec.DoubleValue hatYOffset;
        public final ForgeConfigSpec.BooleanValue forceFirstPersonNoRender;

        Client(ForgeConfigSpec.Builder builder) {
            builder.push("Client");

            hatYOffset = builder
                    .comment("Y Offset for hats to match skin's features if desired.")
                    .defineInRange("hatYOffset", 0.0D, -1.0D, 1.0D);

            forceFirstPersonNoRender = builder
                    .comment("Force hats to not render on self when in first person. (For compatability with First-Person-Model mods, normally not needed)")
                    .define("forceFirstPersonNoRender", false);

            builder.pop();
        }
    }

    public static boolean manualAllowUpdateCheck() {
        try {
            File file = new File(FMLPaths.CONFIGDIR.get().toFile(), SimpleHats.modId + "-common.toml");
            if(!file.exists()) return true;
            file.setWritable(true);
            String fileString = Files.asCharSource(file, Charset.defaultCharset()).read();
            if(fileString.contains("allowUpdates")) return fileString.contains("allowUpdates = true");
            else {
                SimpleHats.logger.log(Level.WARN, "Failed to find allowUpdates value in SimpleHats config, returning default");
                return true;
            }
        }
        catch(Exception ex) {
            SimpleHats.logger.log(Level.WARN, "Failed to manually read SimpleHats config: " + ex);
            return true;
        }
    }
}