package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public interface ItemDataProvider {
    CompletableFuture<List<Item>> getItemsByCategory(Category category);

    CompletableFuture<Item> getItemById(String itemId);

    CompletableFuture<List<Item>> getFeaturedItems(int numItems);

    CompletableFuture<Integer> getItemCountPerCategory(Category category);

    CompletableFuture<List<Item>> getAllItems();
}
