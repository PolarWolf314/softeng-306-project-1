package nz.ac.aucklanduni.se306project1.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.activities.DetailsActivity;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.models.ImageInfo;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewmodels.WatchlistItemViewModel;

public class ItemCardViewHolder extends BindableViewHolder<Item> {

    private final WatchlistItemViewModel viewModel;
    private final Context context;
    private final CardView cardView;
    private final ImageView itemImage;
    private final TextView itemName;
    private final TextView itemPrice;
    private final CheckBox favouriteItemCheckbox;

    public ItemCardViewHolder(final WatchlistItemViewModel viewModel, final Context context, final View itemView) {
        super(itemView);

        this.viewModel = viewModel;
        this.context = context;
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

        Glide.with(this.context).load(imageInfo.getUrl()).into(this.itemImage);
        this.itemImage.setContentDescription(imageInfo.getDescription());
        this.cardView.setCardBackgroundColor(Color.parseColor(colourInformation.getColour()));
        this.itemName.setText(item.getDisplayName());
        this.itemPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));

        if (this.viewModel.isUserLoggedIn()) {
            this.favouriteItemCheckbox.setChecked(this.viewModel.isInWatchlist(item));
            this.favouriteItemCheckbox.setOnCheckedChangeListener((button, isChecked) -> {
                if (isChecked) {
                    this.viewModel.addItemToWatchlist(item);
                } else {
                    this.viewModel.removeItemFromWatchlist(item);
                }
            });
        } else {
            this.favouriteItemCheckbox.setVisibility(View.GONE);
        }

        this.cardView.setOnClickListener((v) -> {
            final Intent intent = new Intent(this.context, DetailsActivity.class);
            intent.putExtra(Constants.IntentKeys.ITEM_ID, item.getId());
            this.context.startActivity(intent);
        });
    }

    public static class Builder implements ViewHolderBuilder<ItemCardViewHolder> {

        public final WatchlistItemViewModel viewModel;

        public Builder(final WatchlistItemViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_card;
        }

        @Override
        public ItemCardViewHolder createViewHolder(final Context context, final View view) {
            return new ItemCardViewHolder(this.viewModel, context, view);
        }
    }
}
