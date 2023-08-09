package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Objects;

public abstract class CartItemSpecifications {
    private int quantity;
    private String colour;
    private String size;

    public CartItemSpecifications() {
    }

    public CartItemSpecifications(final int quantity, final String colour, final String size) {
        this.quantity = quantity;
        this.colour = colour;
        this.size = size;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getColour() {
        return this.colour;
    }

    public String getSize() {
        return this.size;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemSpecifications)) return false;
        final CartItemSpecifications that = (CartItemSpecifications) o;
        return this.quantity == that.quantity &&
                Objects.equals(this.colour, that.colour) &&
                Objects.equals(this.size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.quantity, this.colour, this.size);
    }
}
