package nz.ac.aucklanduni.se306project1.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.activities.DetailsActivity;
import nz.ac.aucklanduni.se306project1.data.Constants;
import nz.ac.aucklanduni.se306project1.models.ImageInfo;
import nz.ac.aucklanduni.se306project1.models.items.CartItem;
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;
import nz.ac.aucklanduni.se306project1.viewmodels.ShoppingCartItemViewModel;

public class CartItemCardViewHolder extends BindableViewHolder<CartItem> {

    private final ShoppingCartItemViewModel viewModel;
    private final Context context;
    private final ImageView itemImage;
    private final TextView itemName;
    private final TextView itemPrice;
    private final TextView itemQuantity;
    private final MaterialButton decrementButton;
    private final MaterialButton incrementButton;
    private final MaterialButton removeButton;
    private final CardView imageContainer;
    private final CardView cartItemCard;


    public CartItemCardViewHolder(final ShoppingCartItemViewModel viewModel, @NonNull final Context context, @NonNull final View itemView) {
        super(itemView);

        this.viewModel = viewModel;
        this.context = context;
        this.itemImage = itemView.findViewById(R.id.cart_item_card_image);
        this.itemName = itemView.findViewById(R.id.cart_item_card_name);
        this.itemPrice = itemView.findViewById(R.id.cart_item_card_price);
        this.itemQuantity = itemView.findViewById(R.id.cart_item_card_quantity);
        this.decrementButton = itemView.findViewById(R.id.cart_item_card_decrement_button);
        this.incrementButton = itemView.findViewById(R.id.cart_item_card_increment_button);
        this.removeButton = itemView.findViewById(R.id.cart_item_card_delete_button);
        this.imageContainer = itemView.findViewById(R.id.cart_image_position);
        this.cartItemCard = itemView.findViewById(R.id.cart_item_card);
    }

    @Override
    public void bindFrom(final CartItem cartItem) {
        final Item item = cartItem.getItem();

        final ColouredItemInformation colourInformation = item.getColourInformation(cartItem.getColour());
        final ImageInfo imageInfo = colourInformation.getImages().get(0);

        Glide.with(this.context).load(imageInfo.getUrl()).into(this.itemImage);
        final String name = this.context.getResources()
                .getString(R.string.cart_item_title, item.getDisplayName(), cartItem.getSize().toUpperCase());

        this.setPriceAndQuantity(cartItem);
        this.itemImage.setContentDescription(imageInfo.getDescription());
        this.itemName.setText(name);
        this.imageContainer.setBackgroundColor(Color.parseColor(cartItem.getColour()));

        this.decrementButton.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                this.viewModel.decrementItemQuantity(cartItem);
                this.setPriceAndQuantity(cartItem);
            }
        });
        this.incrementButton.setOnClickListener(v -> {
            this.viewModel.incrementItemQuantity(cartItem);
            this.setPriceAndQuantity(cartItem);
        });
        this.cartItemCard.setOnClickListener((v) -> {
            final Intent intent = new Intent(this.context, DetailsActivity.class);
            intent.putExtra(Constants.IntentKeys.ITEM_ID, item.getId());
            this.context.startActivity(intent);
        });
        this.removeButton.setOnClickListener(v -> this.viewModel.removeItemFromCart(cartItem));

    }

    private void setPriceAndQuantity(final CartItem cartItem) {
        this.itemQuantity.setText(String.format(Locale.getDefault(), "%d", cartItem.getQuantity()));
        this.itemPrice.setText(String.format(Locale.getDefault(), "$%.2f", cartItem.getCollectivePrice()));
    }

    public static class Builder implements ViewHolderBuilder<CartItemCardViewHolder> {

        public final ShoppingCartItemViewModel viewModel;

        public Builder(final ShoppingCartItemViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public int getLayoutId() {
            return R.layout.cart_item_card;
        }

        @Override
        public CartItemCardViewHolder createViewHolder(final Context context, final View view) {
            return new CartItemCardViewHolder(this.viewModel, context, view);
        }
    }
}
