package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
}
