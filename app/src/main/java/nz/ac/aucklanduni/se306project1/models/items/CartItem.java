package nz.ac.aucklanduni.se306project1.models.items;

public class CartItem extends CartItemSpecifications {
    private Item item;

    public CartItem() {
    }

    public CartItem(final int quantity, final String colour, final String size, final Item item) {
        super(quantity, colour, size);
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }
}
