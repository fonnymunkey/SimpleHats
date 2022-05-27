package fonnymunkey.simplehats.util;

public class HatDyeSettings {
    private boolean useDye = false;
    private int defaultColor = 0;

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