package fonnymunkey.simplehats.util;

import com.google.gson.annotations.SerializedName;
import fonnymunkey.simplehats.SimpleHats;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.apache.logging.log4j.Level;

import java.util.Date;

public class HatEntry {
    public static final HatDyeSettings DYE_NONE = new HatDyeSettings(false, 0);
    public static final HatParticleSettings PARTICLE_NONE = new HatParticleSettings(false, "minecraft:heart", 0, HatParticleSettings.HatParticleMovement.TRAILING_FULL);

    @SerializedName("name")
    private String hatName;
    @SerializedName("rarity")
    private Rarity hatRarity;
    @SerializedName("weight")
    private int hatWeight;
    @SerializedName("variants")
    private int variantRange;
    @SerializedName("season")
    private HatSeason hatSeason;
    @SerializedName("Dye Settings")
    private HatDyeSettings hatDyeSettings;
    @SerializedName("Particle Settings")
    private HatParticleSettings hatParticleSettings;

    public HatEntry(String name) {
        this(name, Rarity.COMMON, 0, 0, DYE_NONE, PARTICLE_NONE, HatSeason.NONE);
    }
    public HatEntry(String name, Rarity rarity, int weight) {
        this(name, rarity, weight, 0, DYE_NONE, PARTICLE_NONE, HatSeason.NONE);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange) {
        this(name, rarity, weight, variantRange, DYE_NONE, PARTICLE_NONE, HatSeason.NONE);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye) {
        this(name, rarity, weight, variantRange, dye, PARTICLE_NONE, HatSeason.NONE);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatParticleSettings particle) {
        this(name, rarity, weight, variantRange, DYE_NONE, particle, HatSeason.NONE);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatSeason season) {
        this(name, rarity, weight, variantRange, DYE_NONE, PARTICLE_NONE, season);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye, HatParticleSettings particle) {
        this(name, rarity, weight, variantRange, dye, particle, HatSeason.NONE);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye, HatSeason season) {
        this(name, rarity, weight, variantRange, dye, PARTICLE_NONE, season);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatParticleSettings particle, HatSeason season) {
        this(name, rarity, weight, variantRange, DYE_NONE, particle, season);
    }
    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye, HatParticleSettings particle, HatSeason season) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = Math.max(0, variantRange);
        this.hatDyeSettings = dye;
        this.hatParticleSettings = particle;
        this.hatSeason = season;
    }

    public String getHatName() {
        return this.hatName;
    }

    public Rarity getHatRarity() {
        return this.hatRarity;
    }

    public int getHatWeight() {
        return this.hatWeight;
    }

    public int getHatVariantRange() { return this.variantRange; }

    public HatDyeSettings getHatDyeSettings() { return this.hatDyeSettings; }

    public HatParticleSettings getHatParticleSettings() { return this.hatParticleSettings; }

    public HatSeason getHatSeason() { return this.hatSeason; }

    public void validateDeserializedEntry() {
        //name already checked
        if(this.hatRarity == null) this.hatRarity = Rarity.COMMON;
        this.hatWeight = Math.max(0, this.hatWeight);
        this.variantRange = Math.max(0, this.variantRange);
        if(this.hatDyeSettings == null) this.hatDyeSettings = DYE_NONE;
        if(this.hatParticleSettings == null) this.hatParticleSettings = PARTICLE_NONE;
        else this.hatParticleSettings.validateParticleSettings();
        if(this.hatSeason == null) this.hatSeason = HatSeason.NONE;
    }
    ///////////
    //Dye
    ///////////
    public static class HatDyeSettings {
        @SerializedName("enabled")
        private boolean useDye;
        @SerializedName("decimal color")
        private int defaultColor;

        public HatDyeSettings(boolean useDye, int defaultColor) {
            this.useDye = useDye;
            this.defaultColor = defaultColor;
        }

        public boolean getUseDye() {
            return useDye;
        }

        public int getColorCode() {
            return defaultColor;
        }
    }
    ///////////
    //Particle
    ///////////
    public static class HatParticleSettings {
        @SerializedName("enabled")
        private boolean useParticle;
        @SerializedName("name")
        private String particleTypeString;
        @SerializedName("frequency")
        private float particleFrequency;
        @SerializedName("movement")
        private HatParticleMovement particleMovement;

        private transient SimpleParticleType particleTypeParsed = ParticleTypes.HEART;

        public HatParticleSettings(boolean useParticle, String particleTypeString, float particleFrequency, HatParticleMovement particleMovement) {
            this.useParticle = useParticle;
            this.particleTypeString = particleTypeString;
            this.particleFrequency = particleFrequency;
            this.particleMovement = particleMovement;
            this.parseParticleString();
        }

        public boolean getUseParticles() { return this.useParticle; }

        public SimpleParticleType getParticleType() { return this.particleTypeParsed; }

        public float getParticleFrequency() { return this.particleFrequency; }

        public HatParticleMovement getParticleMovement() { return this.particleMovement; }

        public void validateParticleSettings() {
            //use defaults false
            if(this.particleTypeString.isEmpty()) this.particleTypeString = "minecraft:heart";
            if(this.particleMovement == null) this.particleMovement = HatParticleMovement.TRAILING_FULL;
            this.parseParticleString();
        }

        private void parseParticleString() {
            var particleType = Registries.PARTICLE_TYPE.get(Identifier.tryParse(this.particleTypeString));

            if (particleType instanceof SimpleParticleType simpleParticleType) {
                this.particleTypeParsed = simpleParticleType;
            }

            if(this.particleTypeParsed == null) {
                SimpleHats.logger.log(Level.ERROR, "Particle type \"" + this.particleTypeString + "\" failed to parse, setting default.");
                this.particleTypeParsed = ParticleTypes.HEART;
            }
        }

        public enum HatParticleMovement {
            TRAILING_HEAD,
            TRAILING_FEET,
            TRAILING_FULL;
        }
    }
    ///////////
    //Season
    ///////////
    public enum HatSeason {
        EASTER {
            @Override
            public boolean compareDate(int date) {
                if(easterDayMin == 0 || easterDayMax == 0) {
                    int year = (new Date()).getYear();
                    int a = year % 19,
                            b = year / 100,
                            c = year % 100,
                            d = b / 4,
                            e = b % 4,
                            g = (8 * b + 13) / 25,
                            h = (19 * a + b - d - g + 15) % 30,
                            j = c / 4,
                            k = c % 4,
                            m = (a + 11 * h) / 319,
                            r = (2 * e + 2 * j - k - h + m + 32) % 7,
                            n = (h - m + r + 90) / 25,
                            p = (h - m + r + n + 19) % 32;
                    easterDayMin = p-7<=0 ? ((n-1)*100)+(p+24) : (n*100)+(p-7);
                    easterDayMax = p+7>=31 ? ((n+1)*100)+(p-24) : (n*100)+(p+7);
                }
                return date >= easterDayMin && date <= easterDayMax;
            }
        },
        SUMMER {
            @Override
            public boolean compareDate(int date) {
                return date >= 627 && date <= 711;
            }
        },
        HALLOWEEN {
            @Override
            public boolean compareDate(int date) {
                return date >= 1017 && date <= 1031;
            }
        },
        FESTIVE {
            @Override
            public boolean compareDate(int date) {
                return date >= 1217 && date <= 1231;
            }
        },
        NONE {
            @Override
            public boolean compareDate(int date) {
                return true;
            }
        };

        private static int easterDayMin = 0;
        private static int easterDayMax = 0;
        private static HatSeason cachedSeason = HatSeason.NONE;
        private static int cachedDay = 0;

        public static HatSeason getSeason() {
            int cachedDayPre = cachedDay;
            int date = convertDate();

            if(cachedDayPre != cachedDay) {
                for(HatSeason season : HatSeason.values()) {
                    if(!season.equals(HatSeason.NONE) && season.compareDate(date)) {
                        cachedSeason = season;
                        break;
                    }
                }
            }
            return cachedSeason;
        }

        public abstract boolean compareDate(int date);

        private static int convertDate() {
            Date date = new Date();
            cachedDay = date.getDate();
            return ((date.getMonth()+1)*100)+date.getDate();
        }
    }
}