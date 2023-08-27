package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nz.ac.aucklanduni.se306project1.models.SearchFilterable;

public abstract class Item implements SearchFilterable {
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

    @Override
    public boolean matches(final String query) {
        return this.displayName.toLowerCase().contains(query);
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String newId) {
        this.id = newId;
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

    public ColouredItemInformation getColourInformation(final String colour) {
        return this.colours.stream()
                .filter(colourInfo -> colourInfo.getColour().equals(colour))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("There is no colour '%s' for '%s (%s)'", colour, this.displayName, this.id)));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Item item = (Item) o;
        return Objects.equals(this.id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
