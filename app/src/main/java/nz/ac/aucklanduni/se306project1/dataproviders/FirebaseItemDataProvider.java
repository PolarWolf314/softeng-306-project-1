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
                        categoryItemList.add(this.parseDocumentForItem(document, categoryId));
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
                        return this.parseDocumentForItem(document, document.get("categoryId").toString());
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

    private Item parseDocumentForItem(DocumentSnapshot document, String categoryId) {
        String displayName = document.get("displayName").toString();
        String description = document.get("description").toString();
        Long temp = (Long) document.get("price");
        double price = temp.doubleValue();

        SoftwareSubcategory softwareSubcategory;
        CivilSubcategory civilSubcategory;
        double meltingPoint;

        List<Map<String, Object>> documentImagesInfo;
        Map<String, Object> documentSizeQuantities;
        List<ImageInfo> imagesInfo = new ArrayList<>();
        String colour;
        List<ColouredItemInformation> colouredItems = new ArrayList<>();
        Map<String, Integer> sizeQuantities = new HashMap<>();
        List<Map<String, Object>> coloursInfo = (List<Map<String, Object>>) document.get("colours");
        for (Map<String, Object> colourInfo : coloursInfo) {
            colour = colourInfo.get("colour").toString();
            documentImagesInfo = (List<Map<String, Object>>) colourInfo.get("images");
            for (Map<String, Object> imageInfo : documentImagesInfo) {
                imagesInfo.add(new ImageInfo(imageInfo.get("url").toString(), imageInfo.get("description").toString()));
            }
            documentSizeQuantities = (Map<String, Object>) colourInfo.get("sizeQuantities");
            temp = (Long) documentSizeQuantities.get("s");
            sizeQuantities.put("s", temp.intValue());
            temp = (Long) documentSizeQuantities.get("m");
            sizeQuantities.put("m", temp.intValue());
            temp = (Long) documentSizeQuantities.get("l");
            sizeQuantities.put("l", temp.intValue());
            temp = (Long) documentSizeQuantities.get("xl");
            sizeQuantities.put("xl", temp.intValue());
            colouredItems.add(new ColouredItemInformation(colour, imagesInfo, sizeQuantities));
        }

        switch (categoryId) {
            case "mechanical":
                return new MechanicalItem(document.getId(), displayName, categoryId, description, price, colouredItems);
            case "software":
                softwareSubcategory = SoftwareSubcategory.valueOf(document.get("subCategory").toString());
                return new SoftwareItem(document.getId(), displayName, categoryId, description, price, colouredItems, softwareSubcategory);
            case "civil":
                civilSubcategory = CivilSubcategory.valueOf(document.get("subCategory").toString());
                return new CivilItem(document.getId(), displayName, categoryId, description, price, colouredItems, civilSubcategory);
            case "chemmat":
                temp = (Long) document.get("meltingPoint");
                meltingPoint = temp.doubleValue();
                return new ChemmatItem(document.getId(), displayName, categoryId, description, price, colouredItems, meltingPoint);
        }

        throw new RuntimeException("Invalid category ID");
    }
}
