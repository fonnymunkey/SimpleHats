package fonnymunkey.simplehats.util;

import fonnymunkey.simplehats.SimpleHats;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import org.apache.logging.log4j.Level;

public class HatParticleSettings {
    private boolean useParticle = false;
    private boolean useColor = false;
    private String particleType = "HEART";
    private int color = 0;

    private transient SimpleParticleType particleTypeParsed = ParticleTypes.HEART;

    public HatParticleSettings(boolean useParticle, boolean colorParticle, String particleTypeString, int colorCode) {
        this.useParticle = useParticle;
        this.useColor = colorParticle;
        this.particleType = particleTypeString;
        this.color = colorCode;

        this.particleTypeParsed = this.getParticleType(particleTypeString);
    }

    public boolean getUseParticles() {
        return this.useParticle;
    }

    public boolean getUseColor() {
        return this.useColor;
    }

    public SimpleParticleType getParticleType() { return this.particleTypeParsed; }

    public int getColorCode() { return this.color; }


    public static SimpleParticleType getParticleType(String particleType) {
        try{
            return (SimpleParticleType)ParticleTypes.class.getField(particleType).get(ParticleTypes.class);
        }
        catch(Exception ex) {
            SimpleHats.logger.log(Level.ERROR, "Failed to parse ParticleType \"" + particleType + "\": " + ex);
            return ParticleTypes.HEART;
        }
    }
}
