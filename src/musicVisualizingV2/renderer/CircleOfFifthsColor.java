package musicVisualizingV2.renderer;

import javafx.scene.paint.Color;

/**
 * Declare the usable colors for identifying notes
 */
public enum CircleOfFifthsColor {
    /**
     * Chromatic order: [I, I#, II, IIIb, III, IV, IV#, V, V#, VI, VIIb, VII]
     *             [0,  1,  2,    3,   4,  5,   6, 7,  8,  9,   10,  11]
     * Circle of fifths: [I, V, II, VI, III, VII, IV#, I#, V#, IIIb, VIIb, IV]
     *                   [0, 7,  2,  9,   4,  11,   6,  1,  8,    3,   10,  5]
     */
    I(240),
    I_augmented(90),
    II(300),
    III_diminished(150),
    III(0),
    IV(210),
    IV_augmented(60),
    V(270),
    V_augmented(120),
    VI(330),
    VII_diminished(180),
    VII(30);

    private final float hue;
    private final float saturation;
    private final float brightness;

    CircleOfFifthsColor(float colorHue) {
        hue = colorHue;
        saturation = 1;
        brightness = 1;
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
        return Color.hsb(getHue(), getSaturation(), getBrightness());
    }
}
