package nz.ac.aucklanduni.se306project1.viewholders;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.Locale;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class FeaturedItemCardViewHolder extends BindableViewHolder<Item> {

    private final CardView cardView;
    private final ImageView itemImage;
    private final TextView itemName;
    private final TextView itemPrice;
    private final CheckBox favouriteItemCheckbox;

    public FeaturedItemCardViewHolder(@NonNull final View itemView) {
        super(itemView);

        this.cardView = itemView.findViewById(R.id.item_card);
        this.itemImage = itemView.findViewById(R.id.item_card_image);
        this.itemName = itemView.findViewById(R.id.item_card_name);
        this.itemPrice = itemView.findViewById(R.id.item_card_price);
        this.favouriteItemCheckbox = itemView.findViewById(R.id.item_card_favourite);
    }

    @Override
    public void bindFrom(final Item item) {
        if (item.getColours().size() == 0) {
            throw new IllegalArgumentException(
                    String.format("The item %s (%s) has no colour information", item.getDisplayName(), item.getId()));
        }

        this.cardView.setCardBackgroundColor(Color.parseColor(item.getColours().get(0).getColour()));
        // TODO: Convert image URL to image
        // this.itemImage = ...
        this.itemName.setText(item.getDisplayName());
        this.itemPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
        this.favouriteItemCheckbox.setOnCheckedChangeListener((button, isChecked) -> {
            // TODO: Toggle whether the item is in the watchlist
        });
    }

    public static class Builder implements ViewHolderBuilder<ItemCardViewHolder> {

        public static final ItemCardViewHolder.Builder INSTANCE = new ItemCardViewHolder.Builder();

        @Override
        public int getLayoutId() {
            return R.layout.featured_item_card;
        }

        @Override
        public ItemCardViewHolder createViewHolder(final View view) {
            return new ItemCardViewHolder(view);
        }
    }
}
