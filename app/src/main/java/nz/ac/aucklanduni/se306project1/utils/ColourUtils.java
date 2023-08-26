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

    private static List<Double> hexcodeToRgb(final String hexcode) {
        final List<Double> rgb = new ArrayList<>();
        rgb.add((double) Integer.parseInt(hexcode.substring(1, 3), 16));
        rgb.add((double) Integer.parseInt(hexcode.substring(3, 5), 16));
        rgb.add((double) Integer.parseInt(hexcode.substring(5, 7), 16));

        return rgb;
    }

    private static double getLuminance(final List<Double> rgb) {
        final List<Double> normalisedValues = rgb.stream().map(value -> {
            value /= 255D;
            return value <= 0.03928 ? value / 12.92 : Math.pow((value + 0.055) / 1.055, GAMMA);
        }).collect(Collectors.toList());

        return normalisedValues.get(0) * RED + normalisedValues.get(1) * GREEN + normalisedValues.get(2) * BLUE;
    }

    public static double calculateContrast(final String hexcode1, final String hexcode2) {
        final double luminance1 = getLuminance(hexcodeToRgb(hexcode1));
        final double luminance2 = getLuminance(hexcodeToRgb(hexcode2));

        final double brightest = Math.max(luminance1, luminance2);
        final double darkest = Math.min(luminance1, luminance2);
        return (brightest + 0.05) / (darkest + 0.05);
    }
}
