package nz.ac.aucklanduni.se306project1.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public class Order {
    private String userId;
    private LocalDateTime orderDate;
    private List<SerializedCartItem> items;

    public Order() {
    }

    public Order(
            final String userId,
            final LocalDateTime orderDate,
            final List<SerializedCartItem> items
    ) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.items = items;
    }

    public String getuserId() {
        return this.userId;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }

    public List<SerializedCartItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Order order = (Order) o;
        return Objects.equals(this.userId, ((Order) o).getuserId()) && Objects.equals(this.orderDate, ((Order) o).getOrderDate())
                && Objects.equals(this.items, ((Order) o).getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId) + Objects.hash(this.orderDate) + Objects.hash(this.items);
    }
}
