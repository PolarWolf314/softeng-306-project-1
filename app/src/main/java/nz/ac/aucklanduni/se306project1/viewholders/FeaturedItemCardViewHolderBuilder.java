package nz.ac.aucklanduni.se306project1.viewholders;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.viewmodels.WatchlistItemViewModel;

public class FeaturedItemCardViewHolderBuilder extends ItemCardViewHolder.Builder {


    public FeaturedItemCardViewHolderBuilder(final WatchlistItemViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.featured_item_card;
    }
}

