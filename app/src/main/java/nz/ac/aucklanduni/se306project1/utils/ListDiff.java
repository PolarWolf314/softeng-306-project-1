package nz.ac.aucklanduni.se306project1.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Collections;
import java.util.List;

public class ListDiff<Item> extends DiffUtil.Callback {

    private final List<Item> oldList;
    private final List<Item> newList;

    public ListDiff(final List<Item> oldList, final List<Item> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return this.oldList.size();
    }

    @Override
    public int getNewListSize() {
        return this.newList.size();
    }

    @Override
    public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
        return this.oldList.get(oldItemPosition).equals(this.newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
        return this.oldList.get(oldItemPosition).equals(this.newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(final int oldItemPosition, final int newItemPosition) {
        // TODO: Implement this when we get to adding animations (ItemAnimator)
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

    public List<Item> getOldList() {
        return Collections.unmodifiableList(this.oldList);
    }

    public List<Item> getNewList() {
        return Collections.unmodifiableList(this.newList);
    }
}
