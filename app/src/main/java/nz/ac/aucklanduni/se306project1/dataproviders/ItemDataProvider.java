package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.List;

import nz.ac.aucklanduni.se306project1.models.items.Item;

public interface ItemDataProvider {
    List<Item> getItemsByCategoryId(String categoryId);

    Item getItemById(String itemId);

    List<Item> getFeaturedItems();

    int getItemCountPerCategory(String categoryId);
}
