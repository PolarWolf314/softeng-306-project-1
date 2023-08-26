package nz.ac.aucklanduni.se306project1.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The contrast functionality has been adapted from
 * <a href="https://stackoverflow.com/a/9733420">this</a> stackoverflow answer.
 */
public class ColourUtils {
    private static final double RED = 0.2126;
    private static final double GREEN = 0.7152;
    private static final double BLUE = 0.0722;
    private static final double GAMMA = 2.4;

    /**
     * Converts a hexcode in the format <code>#123456</code> into it's 3 red, green, blue
     * components. Each component is an integer from 0-255.
     *
     * @param hexcode The hexcode to convert to rgb
     * @return A {@link List} containing the 3 rgb components
     */
    private static List<Integer> hexcodeToRgb(final String hexcode) {
        final List<Integer> rgb = new ArrayList<>();
        rgb.add(Integer.parseInt(hexcode.substring(1, 3), 16));
        rgb.add(Integer.parseInt(hexcode.substring(3, 5), 16));
        rgb.add(Integer.parseInt(hexcode.substring(5, 7), 16));

        return rgb;
    }

    /**
     * Calculates the luminance of an rgb colour.
     *
     * @param rgb A {@link List} containing the 3 rgb components
     * @return The luminance of the rgb colour
     */
    private static double getLuminance(final List<Integer> rgb) {
        final List<Double> normalisedValues = rgb.stream().map(value -> {
            final double normalisedValue = value / 255D;
            return normalisedValue <= 0.03928 ? normalisedValue / 12.92 : Math.pow((normalisedValue + 0.055) / 1.055, GAMMA);
        }).collect(Collectors.toList());

        return normalisedValues.get(0) * RED + normalisedValues.get(1) * GREEN + normalisedValues.get(2) * BLUE;
    }

    /**
     * Calculates the contrast between two hexcode colours in the format <code>#123456</code>.
     *
     * @param hexcode1 The first hexcode
     * @param hexcode2 The second hexcode
     * @return The contrast between the two colours
     */
    public static double calculateContrast(final String hexcode1, final String hexcode2) {
        final double luminance1 = getLuminance(hexcodeToRgb(hexcode1));
        final double luminance2 = getLuminance(hexcodeToRgb(hexcode2));

        final double brightest = Math.max(luminance1, luminance2);
        final double darkest = Math.min(luminance1, luminance2);
        return (brightest + 0.05) / (darkest + 0.05);
    }
}
