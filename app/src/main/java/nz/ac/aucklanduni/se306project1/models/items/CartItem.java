package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Objects;

import nz.ac.aucklanduni.se306project1.models.SearchFilterable;

public class CartItem extends CartItemSpecifications implements SearchFilterable {
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

    public double getCollectivePrice() {
        return this.getQuantity() * this.item.getPrice();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final CartItem cartItem = (CartItem) o;
        return Objects.equals(this.item, cartItem.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.item);
    }

    @Override
    public boolean matches(final String query) {
        return this.item.matches(query);
    }
}
