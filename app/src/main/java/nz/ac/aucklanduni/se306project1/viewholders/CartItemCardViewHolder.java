package nz.ac.aucklanduni.se306project1.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.Locale;

import nz.ac.aucklanduni.se306project1.R;
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

    public CartItemCardViewHolder(final ShoppingCartItemViewModel viewModel, @NonNull final Context context, @NonNull final View itemView) {
        super(itemView);

        this.viewModel = viewModel;
        this.context = context;
        this.itemImage = itemView.findViewById(R.id.cart_item_card_image);
        this.itemName = itemView.findViewById(R.id.cart_item_card_name);
        this.itemPrice = itemView.findViewById(R.id.cart_item_card_price);
        this.itemQuantity = itemView.findViewById(R.id.cart_item_card_quantity);
    }

    @Override
    public void bindFrom(final CartItem cartItem) {
        final Item item = cartItem.getItem();

        if (item.getColours().size() == 0) {
            throw new IllegalArgumentException(
                    String.format("The item %s (%s) has no colour information", item.getDisplayName(), item.getId()));
        }

        final ColouredItemInformation colourInformation = item.getColours().get(0);
        final ImageInfo imageInfo = colourInformation.getImages().get(0);

        Glide.with(this.context).load(imageInfo.getUrl()).into(this.itemImage);
        this.itemImage.setContentDescription(imageInfo.getDescription());
        this.itemName.setText(item.getDisplayName());
        this.itemQuantity.setText(String.format(Locale.getDefault(), "%d", cartItem.getQuantity()));
        this.itemPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
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
