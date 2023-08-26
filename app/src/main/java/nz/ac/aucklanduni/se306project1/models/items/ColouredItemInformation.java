package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import nz.ac.aucklanduni.se306project1.models.ImageInfo;
import nz.ac.aucklanduni.se306project1.utils.ColourUtils;

public class ColouredItemInformation {
    private String colour;
    private List<ImageInfo> images;
    private Map<String, Integer> sizeQuantities;

    public ColouredItemInformation() {
    }

    public ColouredItemInformation(
            final String colour,
            final List<ImageInfo> images,
            final Map<String, Integer> sizeQuantities
    ) {
        this.colour = colour;
        this.images = images;
        this.sizeQuantities = sizeQuantities;
    }

    public String getColour() {
        return this.colour;
    }

    public List<ImageInfo> getImages() {
        return this.images;
    }

    public Map<String, Integer> getSizeQuantities() {
        return Collections.unmodifiableMap(this.sizeQuantities);
    }

    public String getContrastTextColour() {
        final double contrastWithWhite = ColourUtils.calculateContrast(this.colour, ColourUtils.WHITE_HEXCODE);
        if (contrastWithWhite >= ColourUtils.WCAG_AA_TEXT_CONTRAST_RATIO) {
            return ColourUtils.WHITE_HEXCODE;
        }
        return ColourUtils.BLACK_HEXCODE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ColouredItemInformation that = (ColouredItemInformation) o;
        return Objects.equals(this.colour, that.colour) &&
                Objects.equals(this.images, that.images) &&
                Objects.equals(this.sizeQuantities, that.sizeQuantities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.colour, this.images, this.sizeQuantities);
    }
}
