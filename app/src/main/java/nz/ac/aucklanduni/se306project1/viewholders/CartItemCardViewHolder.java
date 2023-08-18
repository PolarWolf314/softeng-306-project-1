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
import nz.ac.aucklanduni.se306project1.models.items.ColouredItemInformation;
import nz.ac.aucklanduni.se306project1.models.items.Item;

public class CartItemCardViewHolder extends BindableViewHolder<Item> {


    private final Context context;
    private final ImageView itemImage;
    private final TextView itemName;
    private final TextView itemPrice;

    public CartItemCardViewHolder(@NonNull final Context context, @NonNull final View itemView) {
        super(itemView);

        this.context = context;
        this.itemImage = itemView.findViewById(R.id.image_position);
        this.itemName = itemView.findViewById(R.id.item_card_name);
        this.itemPrice = itemView.findViewById(R.id.item_card_price);
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
        this.itemName.setText(item.getDisplayName());
        this.itemPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
    }

    public static class Builder implements ViewHolderBuilder<CartItemCardViewHolder> {

        public static final CartItemCardViewHolder.Builder INSTANCE = new CartItemCardViewHolder.Builder();

        @Override
        public int getLayoutId() {
            return R.layout.cart_item_card;
        }

        @Override
        public CartItemCardViewHolder createViewHolder(final Context context, final View view) {
            return new CartItemCardViewHolder(context, view);
        }
    }
}
