package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ColouredItemInformation {
    private String colour;
    private List<String> imageUrls;
    private Map<String, Integer> sizeQuantities;

    public ColouredItemInformation() {
    }

    public ColouredItemInformation(
            final String colour,
            final List<String> imageUrls,
            final Map<String, Integer> sizeQuantities
    ) {
        this.colour = colour;
        this.imageUrls = imageUrls;
        this.sizeQuantities = sizeQuantities;
    }

    public String getColour() {
        return this.colour;
    }

    public List<String> getImageUrls() {
        return this.imageUrls;
    }

    public Map<String, Integer> getSizeQuantities() {
        return Collections.unmodifiableMap(this.sizeQuantities);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ColouredItemInformation that = (ColouredItemInformation) o;
        return Objects.equals(this.colour, that.colour) &&
                Objects.equals(this.imageUrls, that.imageUrls) &&
                Objects.equals(this.sizeQuantities, that.sizeQuantities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.colour, this.imageUrls, this.sizeQuantities);
    }
}
