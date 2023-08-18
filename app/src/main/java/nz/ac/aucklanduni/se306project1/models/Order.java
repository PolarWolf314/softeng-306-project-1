package nz.ac.aucklanduni.se306project1.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nz.ac.aucklanduni.se306project1.models.items.SerializedCartItem;

public class Order {
    private String id;
    private LocalDateTime orderDate;
    private List<SerializedCartItem> items;

    public Order() {
    }

    public Order(
            final String id,
            final String userId,
            final LocalDateTime orderDate,
            final List<SerializedCartItem> items
    ) {
        this.id = id;
        this.orderDate = orderDate;
        this.items = items;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }

    public List<SerializedCartItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public void addItem(final SerializedCartItem item) {
        this.items.add(item);
    }

    public void removeItem(final SerializedCartItem item) {
        this.items.remove(item);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Order order = (Order) o;
        return Objects.equals(this.id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
