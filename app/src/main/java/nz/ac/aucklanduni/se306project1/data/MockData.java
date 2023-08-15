package nz.ac.aucklanduni.se306project1.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nz.ac.aucklanduni.se306project1.models.enums.CivilSubcategory;
import nz.ac.aucklanduni.se306project1.models.items.CivilItem;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class MockData {
    private static final String CIVIL_CATEGORY = "civil";

    public static final List<Item> ITEMS = new ArrayList<>(List.of(
            new CivilItem(
                    "d2d6a453-8870-48ee-98de-49a596c9de7b",
                    "The Hard Helmet",
                    "No Civil Engineer can be caught without their hard helmet...",
                    CIVIL_CATEGORY,
                    39D,
                    List.of(new ColouredItemInformation(
                            "#F26F18",
                            List.of("https://cdn.discordapp.com/attachments/713550649937231925/1140786088554799174/Hard_Hat.png"),
                            Map.of("s", 10, "m", 12, "l", 13))),
                    CivilSubcategory.PPE
            ),
            new CivilItem(
                    "e50fb9a4-4f49-4517-b60c-f401e57fd40c",
                    "Hi-Vis Safety Vest",
                    "We love safety vests :)",
                    CIVIL_CATEGORY,
                    11.99D,
                    List.of(new ColouredItemInformation(
                            "#C0F147",
                            List.of("https://cdn.discordapp.com/attachments/713550649937231925/1140787296124289065/Hi-Vis.png"),
                            Map.of("s", 10, "m", 12, "l", 13))),
                    CivilSubcategory.PPE
            ),
            new CivilItem(
                    "d2d6a453-8870-48ee-98de-49a596c9de7c",
                    "The Hard Helmet",
                    "No Civil Engineer can be caught without their hard helmet...",
                    CIVIL_CATEGORY,
                    39D,
                    List.of(new ColouredItemInformation(
                            "#F26F18",
                            List.of("https://cdn.discordapp.com/attachments/713550649937231925/1140786088554799174/Hard_Hat.png"),
                            Map.of("s", 10, "m", 12, "l", 13))),
                    CivilSubcategory.PPE
            ),
            new CivilItem(
                    "e50fb9a4-4f49-4517-b60c-f401e57fd40d",
                    "Hi-Vis Safety Vest",
                    "We love safety vests :)",
                    CIVIL_CATEGORY,
                    11.99D,
                    List.of(new ColouredItemInformation(
                            "#C0F147",
                            List.of("https://cdn.discordapp.com/attachments/713550649937231925/1140787296124289065/Hi-Vis.png"),
                            Map.of("s", 10, "m", 12, "l", 13))),
                    CivilSubcategory.PPE
            )
    ));
}
