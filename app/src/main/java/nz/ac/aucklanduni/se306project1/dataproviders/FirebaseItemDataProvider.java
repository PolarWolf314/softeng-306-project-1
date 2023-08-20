package nz.ac.aucklanduni.se306project1.dataproviders;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import nz.ac.aucklanduni.se306project1.models.ImageInfo;
import nz.ac.aucklanduni.se306project1.models.enums.CivilSubcategory;
import nz.ac.aucklanduni.se306project1.models.enums.SoftwareSubcategory;
import nz.ac.aucklanduni.se306project1.models.items.ChemmatItem;
import nz.ac.aucklanduni.se306project1.models.items.CivilItem;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.models.items.MechanicalItem;
import nz.ac.aucklanduni.se306project1.models.items.SoftwareItem;
import nz.ac.aucklanduni.se306project1.utils.FutureUtils;

public class FirebaseItemDataProvider implements ItemDataProvider {
    private static class ItemIDComparator implements java.util.Comparator<String> {
        Map<String, Integer> itemPurchaseCount;
        public ItemIDComparator(Map<String, Integer> itemPurchaseCount) {
            this.itemPurchaseCount = itemPurchaseCount;
        }
        @Override
        public int compare(final String itemId1, final String itemId2) {
            return this.itemPurchaseCount.getOrDefault(itemId2, 0) - this.itemPurchaseCount.getOrDefault(itemId1, 0);
        }
    }

    @Override
    public CompletableFuture<List<Item>> getItemsByCategoryId(String categoryId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return FutureUtils.fromTask(db.collection("items").whereEqualTo("categoryId", categoryId).get()
                , () -> new RuntimeException("Error reading items from firestore")).thenApply(documentSnapshots -> {
                    List<Item> categoryItemList = new ArrayList<>();
                    for (DocumentSnapshot document : documentSnapshots) {
                        categoryItemList.add(this.parseDocumentForItem(document, this.getItemClassFromCategoryId(categoryId)));
                    }
                    return categoryItemList;
                }
        );
    }

    @Override
    public CompletableFuture<Item> getItemById(String itemId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference itemRef = db.collection("items").document(itemId);

        return FutureUtils.fromTask(itemRef.get(), () -> new RuntimeException("Error reading item from Firestore")).thenApply(
                document -> {
                    if (document.exists()) {
                        return this.parseDocumentForItem(document, this.getItemClassFromCategoryId(document.get("categoryId").toString()));
                    } else {
                        throw new RuntimeException("Invalid item ID");
                    }
                }
        );
    }

    @Override
    public CompletableFuture<List<Item>> getFeaturedItems(int numItems) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return FutureUtils.fromTask(db.collection("orders").get(),
                () -> new RuntimeException("Error retrieving featured items")).thenCompose(documentSnapshots -> {
                    Map<String, Integer> itemPurchaseCount = new HashMap<>();
                    String itemId;
                    Long temp;
                    int quantityPurchased;
                    for (DocumentSnapshot document : documentSnapshots) {
                        List<Map<String, Object>> itemsArr = (List<Map<String, Object>>) document.get("items");
                        for (Map<String, Object> currentItem : itemsArr) {
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

                    List<String> itemsPurchased = new ArrayList<>(itemPurchaseCount.keySet());
                    ItemIDComparator itemIDComparator = new ItemIDComparator(itemPurchaseCount);
                    itemsPurchased.sort(itemIDComparator);
                    final CompletableFuture<Void>[] futures = new CompletableFuture[itemsPurchased.size()];
                    List<Item> items = new ArrayList<>();
                    for (int i = 0; i < Math.min(numItems, itemsPurchased.size()); i++) {
                        futures[i] = this.getItemById(itemsPurchased.get(i)).thenAccept(item -> {
                            items.add(item);
                        });
                    }

                    return CompletableFuture.allOf(futures).thenApply(nothing -> items);
        });
    }

    @Override
    public CompletableFuture<Integer> getItemCountPerCategory(String categoryId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return FutureUtils.fromTask(db.collection("items").whereEqualTo("categoryId", categoryId).get()
                , () -> new RuntimeException("Error reading items from firestore")).thenApply(
                        documentSnapshots -> documentSnapshots.size());
    }

    private Class<? extends Item> getItemClassFromCategoryId(final String categoryId) {
        switch (categoryId) {
            case "mechanical":
                return MechanicalItem.class;
            case "software":
                return SoftwareItem.class;
            case "civil":
                return CivilItem.class;
            case "chemmat":
                return ChemmatItem.class;
            default:
                throw new IllegalArgumentException(String.format("The category id %s is not valid", categoryId));
        }
    }

    private Item parseDocumentForItem(final DocumentSnapshot document, final Class<? extends Item> itemClass) {
        final Item item = document.toObject(itemClass);
        item.setId(document.getId());

        return item;
    }
}
