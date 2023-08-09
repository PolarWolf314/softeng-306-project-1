package nz.ac.aucklanduni.se306project1.models.items;

public class SerializedCartItem extends CartItemSpecifications {
    private String itemId;

    public SerializedCartItem() {
    }

    public SerializedCartItem(final int quantity, final String colour, final String size, final String itemId) {
        super(quantity, colour, size);
        this.itemId = itemId;
    }

    public String getItemId() {
        return this.itemId;
    }
}
