package musicVisualizingV2;

import java.awt.*;

/**
 * Declare the usable colors for identifying notes
 */
public enum CircleOfFifthsColor {
    I(240),
    V(270),
    II(300),
    VI(330),
    III(0),
    VII(30),
    IV_augmented(60),
    I_augmented(90),
    V_augmented(120),
    III_diminished(150),
    VII_diminished(180),
    IV(210);

    private final float hue;
    private final float saturation;
    private final float brightness;

    CircleOfFifthsColor(float colorHue) {
        hue = colorHue;
        saturation = 100;
        brightness = 100;
    }

    CircleOfFifthsColor(float colorHue, float colorSaturation, float colorBrightness) {
        hue = colorHue;
        saturation = colorSaturation;
        brightness = colorBrightness;
    }

    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    public Color getColor() {
        return new Color(Color.HSBtoRGB(getHue(), getSaturation(), getBrightness()));
    }
}
