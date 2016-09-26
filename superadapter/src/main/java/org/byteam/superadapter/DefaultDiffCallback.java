package org.byteam.superadapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;


/**
 * Default callback class used by DiffUtil while calculating the diff between two lists.
 * <p>
 * Created by Cheney on 2016/9/26.
 */
public abstract class DefaultDiffCallback<T> extends DiffUtil.Callback {
    private List<T> oldList;
    private List<T> newList;

    public DefaultDiffCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    public List<T> getOldList() {
        return oldList;
    }

    public void setOldList(List<T> oldList) {
        this.oldList = oldList;
    }

    public List<T> getNewList() {
        return newList;
    }

    public void setNewList(List<T> newList) {
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    public abstract boolean areItemsTheSame(int oldItemPosition, int newItemPosition);

    public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
