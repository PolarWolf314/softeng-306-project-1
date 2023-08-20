package nz.ac.aucklanduni.se306project1.dataproviders;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.models.items.Item;

public interface ItemDataProvider {
    CompletableFuture<List<Item>> getItemsByCategoryId(String categoryId);

    CompletableFuture<Item> getItemById(String itemId);

    CompletableFuture<List<Item>> getFeaturedItems(int numItems);

    CompletableFuture<Integer> getItemCountPerCategory(String categoryId);
}
