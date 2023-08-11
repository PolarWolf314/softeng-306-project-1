package nz.ac.aucklanduni.se306project1.models.items;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final SerializedCartItem that = (SerializedCartItem) o;
        return Objects.equals(this.itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.itemId);
    }
}
