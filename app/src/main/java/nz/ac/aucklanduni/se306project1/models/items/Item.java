package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Collections;
import java.util.List;

public abstract class Item {
    private String id;
    private String displayName;
    private String categoryId;
    private String description;
    private double price;
    private List<ColouredItemInformation> colours;

    public Item() {
    }

    public Item(final String id, final String displayName, final String categoryId, final String description, final double price, final List<ColouredItemInformation> colours) {
        this.id = id;
        this.displayName = displayName;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.colours = colours;
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPrice() {
        return this.price;
    }

    public List<ColouredItemInformation> getColours() {
        return Collections.unmodifiableList(this.colours);
    }
}
