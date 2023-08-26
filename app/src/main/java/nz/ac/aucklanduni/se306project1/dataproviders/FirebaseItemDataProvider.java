package nz.ac.aucklanduni.se306project1.dataproviders;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.models.enums.Category;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.utils.FutureUtils;

public class FirebaseItemDataProvider implements ItemDataProvider {

    private final Map<String, Item> cachedItems = new HashMap<>();

    @Override
    public CompletableFuture<List<Item>> getItemsByCategory(final Category category) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        return FutureUtils.fromTask(db.collection("items").whereEqualTo("categoryId", category.getId()).get()
                , () -> new RuntimeException("Error reading items from firestore")).thenApply(documentSnapshots -> {
                    final List<Item> categoryItemList = new ArrayList<>();
                    for (final DocumentSnapshot document : documentSnapshots) {
                        categoryItemList.add(this.parseDocumentForItem(document, category));
                    }
                    return categoryItemList;
                }
        );
    }

    @Override
    public CompletableFuture<Item> getItemById(final String itemId) {
        if (this.cachedItems.containsKey(itemId)) {
            return CompletableFuture.completedFuture(this.cachedItems.get(itemId));
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference itemRef = db.collection("items").document(itemId);

        return FutureUtils.fromTask(itemRef.get(), () -> new RuntimeException("Error reading item from Firestore")).thenApply(
                document -> {
                    if (document.exists()) {
                        return this.parseDocumentForItem(document, Category.fromId(document.get("categoryId").toString()));
                    } else {
                        throw new RuntimeException("Invalid item ID");
                    }
                }
        );
    }

    @Override
    public CompletableFuture<List<Item>> getFeaturedItems(final int numItems) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        return FutureUtils.fromTask(db.collection("orders").get(),
                () -> new RuntimeException("Error retrieving featured items")).thenCompose(documentSnapshots -> {
            final Map<String, Integer> itemPurchaseCount = new HashMap<>();
            String itemId;
            Long temp;
            int quantityPurchased;
            for (final DocumentSnapshot document : documentSnapshots) {
                final List<Map<String, Object>> itemsArr = (List<Map<String, Object>>) document.get("items");
                for (final Map<String, Object> currentItem : itemsArr) {
                    itemId = currentItem.get("itemId").toString();
                    temp = (Long) currentItem.get("quantity");
                    quantityPurchased = temp.intValue();
                    if (itemPurchaseCount.containsKey(itemId)) {
                        itemPurchaseCount.replace(itemId, itemPurchaseCount.get(itemId) + quantityPurchased);
                    } else {
                        itemPurchaseCount.put(itemId, quantityPurchased);
                    }
                }
            }

            final List<String> itemsPurchased = new ArrayList<>(itemPurchaseCount.keySet());
            final ItemIDComparator itemIDComparator = new ItemIDComparator(itemPurchaseCount);
            itemsPurchased.sort(itemIDComparator);
            final CompletableFuture<?>[] futures = new CompletableFuture[itemsPurchased.size()];
            final List<Item> items = new ArrayList<>();
            for (int i = 0; i < Math.min(numItems, itemsPurchased.size()); i++) {
                futures[i] = this.getItemById(itemsPurchased.get(i)).thenAccept(items::add);
            }

            return CompletableFuture.allOf(futures).thenApply(nothing -> items);
        });
    }

    @Override
    public CompletableFuture<Integer> getItemCountPerCategory(final Category category) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        return FutureUtils.fromTask(
                        db.collection("items")
                                .whereEqualTo("categoryId", category.getId())
                                .get())
                .thenApply(QuerySnapshot::size);
    }

    private Item parseDocumentForItem(final DocumentSnapshot document, final Category category) {
        final Item item = document.toObject(category.getItemClass());
        item.setId(document.getId());

        this.cachedItems.put(item.getId(), item);

        return item;
    }

    private static class ItemIDComparator implements java.util.Comparator<String> {
        Map<String, Integer> itemPurchaseCount;

        public ItemIDComparator(final Map<String, Integer> itemPurchaseCount) {
            this.itemPurchaseCount = itemPurchaseCount;
        }

        @Override
        public int compare(final String itemId1, final String itemId2) {
            return this.itemPurchaseCount.getOrDefault(itemId2, 0) - this.itemPurchaseCount.getOrDefault(itemId1, 0);
        }
    }
}
