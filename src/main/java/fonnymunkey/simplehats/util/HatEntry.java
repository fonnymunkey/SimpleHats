package fonnymunkey.simplehats.util;

import net.minecraft.world.item.Rarity;

public class HatEntry {
    public static final HatDyeSettings DYE_NONE = new HatDyeSettings(false, 0);
    public static final HatParticleSettings PARTICLE_NONE = new HatParticleSettings(false, false, "HEART", 0);


    private String hatName = "error";
    private Rarity hatRarity = Rarity.COMMON;
    private int hatWeight = 0;
    private int variantRange = 0;
    private HatDyeSettings hatDyeSettings= DYE_NONE;
    private HatParticleSettings hatParticleSettings = PARTICLE_NONE;
    private HatSeason hatSeason = HatSeason.NONE;


    public HatEntry(String name) {
        this.hatName = name;
    }

    public HatEntry(String name, Rarity rarity, int weight) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
        this.hatDyeSettings = dye;
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatParticleSettings particle) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
        this.hatParticleSettings = particle;
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatSeason season) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
        this.hatSeason = season;
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye, HatParticleSettings particle) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
        this.hatDyeSettings = dye;
        this.hatParticleSettings = particle;
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye, HatSeason season) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
        this.hatDyeSettings = dye;
        this.hatSeason = season;
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatParticleSettings particle, HatSeason season) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
        this.hatParticleSettings = particle;
        this.hatSeason = season;
    }

    public HatEntry(String name, Rarity rarity, int weight, int variantRange, HatDyeSettings dye, HatParticleSettings particle, HatSeason season) {
        this.hatName = name;
        this.hatRarity = rarity;
        this.hatWeight = Math.max(0, weight);
        this.variantRange = variantRange;
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

    public HatDyeSettings getHatDyeSettings() {
        return this.hatDyeSettings;
    }

    public HatParticleSettings getHatParticleSettings() {
        return this.hatParticleSettings;
    }

    public HatSeason getHatSeason() {
        return this.hatSeason;
    }
}
