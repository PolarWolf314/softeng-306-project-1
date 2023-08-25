package nz.ac.aucklanduni.se306project1.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.activities.DetailsActivity;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.models.ImageInfo;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class ItemCardViewHolder extends BindableViewHolder<Item> {

    private final EngiWearApplication engiWear;
    private final CardView cardView;
    private final ImageView itemImage;
    private final TextView itemName;
    private final TextView itemPrice;
    private final CheckBox favouriteItemCheckbox;

    public ItemCardViewHolder(@NonNull final EngiWearApplication engiWear, @NonNull final View itemView) {
        super(itemView);

        this.engiWear = engiWear;
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

        final ColouredItemInformation colourInformation = item.getColours().get(0);
        final ImageInfo imageInfo = colourInformation.getImages().get(0);

        Glide.with(this.engiWear).load(imageInfo.getUrl()).into(this.itemImage);
        this.itemImage.setContentDescription(imageInfo.getDescription());
        this.cardView.setCardBackgroundColor(Color.parseColor(colourInformation.getColour()));
        this.itemName.setText(item.getDisplayName());
        this.itemPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
        this.engiWear.getUserDataProvider().getWatchlist().thenAccept(items ->
                this.favouriteItemCheckbox.setChecked(items.contains(item)));
        this.favouriteItemCheckbox.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                this.engiWear.getUserDataProvider().addToWatchlist(item.getId());
            } else {
                this.engiWear.getUserDataProvider().removeFromWatchlist(item.getId());
            }
        });

        this.cardView.setOnClickListener((v) -> {
            final Intent intent = new Intent(this.engiWear, DetailsActivity.class);
            intent.putExtra(Constants.IntentKeys.ITEM_ID, item.getId());
            this.engiWear.startActivity(intent);
        });
    }

    public static class Builder implements ViewHolderBuilder<ItemCardViewHolder> {

        public static final Builder INSTANCE = new Builder();

        @Override
        public int getLayoutId() {
            return R.layout.item_card;
        }

        @Override
        public ItemCardViewHolder createViewHolder(final Context context, final View view) {
            return new ItemCardViewHolder((EngiWearApplication) context, view);
        }
    }
}
