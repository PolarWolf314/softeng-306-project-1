package nz.ac.aucklanduni.se306project1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nz.ac.aucklanduni.se306project1.EngiWearApplication;
import nz.ac.aucklanduni.se306project1.utils.ListDiff;
import nz.ac.aucklanduni.se306project1.viewholders.BindableViewHolder;
import nz.ac.aucklanduni.se306project1.viewholders.ViewHolderBuilder;

public class ListRecyclerAdapter<Item, ViewHolder extends BindableViewHolder<Item>>
        extends RecyclerView.Adapter<ViewHolder> {

    private final EngiWearApplication engiWear;
    private final List<Item> items;
    private final ViewHolderBuilder<ViewHolder> viewHolderBuilder;

    public ListRecyclerAdapter(
            final EngiWearApplication engiWear,
            final LiveData<List<Item>> items,
            final ViewHolderBuilder<ViewHolder> viewHolderBuilder
    ) {
        this.engiWear = engiWear;
        this.items = new ArrayList<>(Objects.requireNonNullElse(items.getValue(), Collections.emptyList()));
        this.viewHolderBuilder = viewHolderBuilder;
        items.observeForever(this::updateItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(this.viewHolderBuilder.getLayoutId(), parent, false);

        return this.viewHolderBuilder.createViewHolder(this.engiWear, view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Item item = this.items.get(position);
        holder.bindFrom(item);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    /**
     * Updates the displayed items to be the provided {@link List}.
     *
     * @param newItems The new items to display
     */
    public void updateItems(final List<Item> newItems) {
        final ListDiff<Item> listDiff = new ListDiff<>(this.items, newItems);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(listDiff);

        this.items.clear();
        this.items.addAll(newItems);
        diffResult.dispatchUpdatesTo(this);
    }
}
