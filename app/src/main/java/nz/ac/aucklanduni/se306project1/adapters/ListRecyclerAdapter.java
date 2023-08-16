package nz.ac.aucklanduni.se306project1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import nz.ac.aucklanduni.se306project1.viewholders.BindableViewHolder;
import nz.ac.aucklanduni.se306project1.viewholders.ViewHolderBuilder;

public class ListRecyclerAdapter<Item, ViewHolder extends BindableViewHolder<Item>>
        extends RecyclerView.Adapter<ViewHolder> {

    private final Context context;
    private final LiveData<List<Item>> items;
    private final ViewHolderBuilder<ViewHolder> viewHolderBuilder;

    public ListRecyclerAdapter(
            final Context context,
            final LiveData<List<Item>> items,
            final ViewHolderBuilder<ViewHolder> viewHolderBuilder
    ) {
        this.context = context;
        this.items = items;
        this.viewHolderBuilder = viewHolderBuilder;
        this.items.observeForever((newItems) -> this.notifyDataSetChanged());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(this.viewHolderBuilder.getLayoutId(), parent, false);

        return this.viewHolderBuilder.createViewHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Item item = this.getItems().get(position);
        holder.bindFrom(item);
    }

    @Override
    public int getItemCount() {
        return this.getItems().size();
    }

    /**
     * Get the list of displayed items. If there is none, an {@link Collections#emptyList()} is
     * returned instead.
     *
     * @return The list of displayed items
     */
    private List<Item> getItems() {
        final List<Item> items = this.items.getValue();
        if (items == null) return Collections.emptyList();

        return items;
    }
}
