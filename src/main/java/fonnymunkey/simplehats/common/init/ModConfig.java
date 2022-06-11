package fonnymunkey.simplehats.common.init;

import com.google.common.io.Files;
import fonnymunkey.simplehats.SimpleHats;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.charset.Charset;

public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Common COMMON = new Common(BUILDER);
    public static final ForgeConfigSpec COMMON_SPEC = BUILDER.build();

    public static class Common {
        public final ForgeConfigSpec.BooleanValue keepHatOnDeath;
        public final ForgeConfigSpec.BooleanValue allowUpdates;
        public final ForgeConfigSpec.DoubleValue seasonalBagChance;

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