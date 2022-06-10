package fonnymunkey.simplehats.common.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Common COMMON = new Common(BUILDER);
    public static final ForgeConfigSpec COMMON_SPEC = BUILDER.build();

    public static class Common {
        public final ForgeConfigSpec.BooleanValue allowUpdates;
        public final ForgeConfigSpec.BooleanValue keepHatOnDeath;
        public final ForgeConfigSpec.DoubleValue seasonalBagChance;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("General");

            allowUpdates = builder
                    .comment("Allow automatic file checking and updating for user-specific hats.")
                    .define("allowUpdates", true);

            keepHatOnDeath = builder
                    .comment("Keep equipped hat on death.")
                    .define("keepHatOnDeath", true);

            seasonalBagChance = builder
                    .comment("Chance for a seasonal bag to drop when using a bag during a seasonal event. (0 effectively disables seasons)")
                    .defineInRange("seasonalBagChance", 0.2D, 0.0D, 1.0D);

        builder.pop();
        }
    }
}