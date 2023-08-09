package nz.ac.aucklanduni.se306project1.models;

import java.util.Collections;
import java.util.List;

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
}
