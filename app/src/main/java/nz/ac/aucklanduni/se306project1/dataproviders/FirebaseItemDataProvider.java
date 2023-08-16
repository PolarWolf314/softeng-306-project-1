package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.List;

import nz.ac.aucklanduni.se306project1.models.items.Item;

public class FirebaseItemDataProvider implements ItemDataProvider {
    @Override
    public List<Item> getItemsByCategoryId(String categoryId) {
        return null;
    }

    @Override
    public Item getItemById(String itemId) {
        return null;
    }

    @Override
    public List<Item> getFeaturedItems() {
        return null;
    }

    @Override
    public int getItemCountPerCategory(String categoryId) {
        return 0;
    }
}
