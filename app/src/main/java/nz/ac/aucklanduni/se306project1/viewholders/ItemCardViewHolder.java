package nz.ac.aucklanduni.se306project1.viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Locale;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class ItemCardViewHolder extends BindableViewHolder<Item> {

    // TODO: Also add reference to card so colour can be set.
    private final ImageView itemImage;
    private final TextView itemName;
    private final TextView itemPrice;
    private final CheckBox favouriteItemCheckbox;

    public ItemCardViewHolder(@NonNull View itemView) {
        super(itemView);

        this.itemImage = itemView.findViewById(R.id.item_card_image);
        this.itemName = itemView.findViewById(R.id.item_card_name);
        this.itemPrice = itemView.findViewById(R.id.item_card_price);
        this.favouriteItemCheckbox = itemView.findViewById(R.id.item_card_favourite);
    }

    @Override
    public void bindFrom(final Item item) {
        // TODO: Convert image URL to image
        // this.itemImage = ...
        this.itemName.setText(item.getDisplayName());
        // TODO: Look into NumberFormatter
        this.itemPrice.setText(String.format(Locale.getDefault(), "$%02f", item.getPrice()));
        this.favouriteItemCheckbox.setOnCheckedChangeListener((button, isChecked) -> {

        });
    }
}
