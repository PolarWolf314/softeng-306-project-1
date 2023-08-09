package nz.ac.aucklanduni.se306project1.models.items;

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
}
