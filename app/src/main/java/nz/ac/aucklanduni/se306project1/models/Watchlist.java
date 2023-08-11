package nz.ac.aucklanduni.se306project1.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Watchlist {
    private String userId;
    private List<String> itemIds;

    public Watchlist() {
    }

    public Watchlist(final String userId, final List<String> itemIds) {
        this.userId = userId;
        this.itemIds = itemIds;
    }

    public void addItemId(final String itemId) {
        this.itemIds.add(itemId);
    }

    public void removeItemId(final String itemId) {
        this.itemIds.remove(itemId);
    }

    public String getUserId() {
        return this.userId;
    }

    public List<String> getItemIds() {
        return Collections.unmodifiableList(this.itemIds);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Watchlist watchlist = (Watchlist) o;
        return Objects.equals(this.userId, watchlist.userId) &&
                Objects.equals(this.itemIds, watchlist.itemIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.itemIds);
    }
}
