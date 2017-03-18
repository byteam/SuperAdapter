package org.byteam.superadapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * The adapter you need to implement.
 * <p>
 * Created by Cheney on 16/3/30.
 */
public abstract class SuperAdapter<T> extends ListSupportAdapter<T> implements CRUD<T> {
    private LayoutInflater mLayoutInflater;

    /**
     * Constructor for single itemView type.
     */
    public SuperAdapter(Context context, List<T> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * Constructor for multiple itemView types.
     */
    public SuperAdapter(Context context, List<T> items, IMulItemViewType<T> mulItemViewType) {
        super(context, items, mulItemViewType);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @CallSuper
    @Override
    public SuperViewHolder onCreate(@Nullable View convertView, ViewGroup parent, int viewType) {
        if (convertView == null) {
            View itemView = mLayoutInflater.inflate(mMulItemViewType == null ?
                    mLayoutResId : mMulItemViewType.getLayoutId(viewType), parent, false);
            return SuperViewHolder.get(null, itemView);
        } else { // When convertView != null, parent must be an AbsListView.
            return SuperViewHolder.get(convertView, null);
        }
    }

    /**
     * ------------------------------------ CRUD ------------------------------------
     */

    @Override
    public final void add(T item) {
        mData.add(item);
        int location = mData.size() - 1;
        if (hasHeaderView())
            location++;
        notifyItemInserted(location);
        notifyDataSetHasChanged();
    }

    @Override
    public void add(int location, T item) {
        mData.add(location, item);
        if (hasHeaderView())
            location++;
        notifyItemInserted(location);
        notifyDataSetHasChanged();
    }

    @Override
    @Deprecated
    public final void insert(int location, T item) {
        add(location, item);
    }

    @Override
    public final void addAll(List<T> items) {
        if (items == null || items.isEmpty()) {
            Log.w(TAG, "addAll: The list you passed contains no elements.");
            return;
        }
        int location = getCount();
        mData.addAll(items);
        if (hasHeaderView())
            location++;
        notifyItemRangeInserted(location, items.size());
        notifyDataSetHasChanged();
    }

    @Override
    public void addAll(int location, List<T> items) {
        if (items == null || items.isEmpty()) {
            Log.w(TAG, "addAll: The list you passed contains no elements.");
            return;
        }
        if (location < 0 || location > getCount()) {
            Log.w(TAG, "addAll: IndexOutOfBoundsException");
            return;
        }
        mData.addAll(location, items);
        if (hasHeaderView())
            location++;
        notifyItemRangeInserted(location, items.size());
        notifyDataSetHasChanged();
    }

    @Override
    public final void remove(T item) {
        if (contains(item)) {
            remove(mData.indexOf(item));
        }
    }

    @Override
    public final void remove(int location) {
        mData.remove(location);
        if (hasHeaderView())
            location++;
        notifyItemRemoved(location);
        notifyDataSetHasChanged();
    }

    @Override
    public void removeAll(List<T> items) {
        mData.removeAll(items);
        notifyDataSetChanged(); // RecyclerView
        notifyDataSetHasChanged(); // AdapterView
    }

    @Override
    public void retainAll(List<T> items) {
        mData.retainAll(items);
        notifyDataSetChanged(); // RecyclerView
        notifyDataSetHasChanged(); // AdapterView
    }

    @Override
    public final void set(T oldItem, T newItem) {
        set(mData.indexOf(oldItem), newItem);
    }

    @Override
    public final void set(int location, T item) {
        mData.set(location, item);
        if (hasHeaderView())
            location++;
        notifyItemChanged(location);
        notifyDataSetHasChanged();
    }

    @Override
    public final void replaceAll(List<T> items) {
        if (mData == items) {
            notifyDataSetChanged();
            notifyDataSetHasChanged();
            return;
        }
        if (items == null || items.isEmpty()) {
            clear();
            return;
        }
        if (mData.isEmpty()) {
            addAll(items);
        } else {
            int start = hasHeaderView() ? 1 : 0;
            int originalSize = getCount();
            int newSize = items.size();
            mData.clear();
            mData.addAll(items);
            if (originalSize > newSize) {
                notifyItemRangeChanged(start, newSize);
                notifyItemRangeRemoved(start + newSize, originalSize - newSize);
            } else if (originalSize == newSize) {
                notifyItemRangeChanged(start, newSize);
            } else {
                notifyItemRangeChanged(start, originalSize);
                notifyItemRangeInserted(start + originalSize, newSize - originalSize);
            }
            notifyDataSetHasChanged();
        }
    }

    @Override
    public final boolean contains(T item) {
        return mData.contains(item);
    }

    @Override
    public boolean containsAll(List<T> items) {
        return mData.containsAll(items);
    }

    @Override
    public final void clear() {
        int count = getCount();
        if (count > 0) {
            mData.clear();
            notifyItemRangeRemoved(hasHeaderView() ? 1 : 0, count);
            notifyDataSetHasChanged();
        }
    }

    /**
     * Calculate the difference between two lists and output a list of update operations
     * that converts the first list into the second one.
     * <pre>
     *     List oldList = mAdapter.getData();
     *     DefaultDiffCallback<T> callback = new DefaultDiffCallback(oldList, newList);
     *     mAdapter.diff(callback);
     * </pre>
     * Note: This method only works on revision 24.2.0 or above.
     *
     * @param callback {@link DefaultDiffCallback}
     */
    @Override
    public void diff(final DefaultDiffCallback<T> callback) {
        if (checkDiff(callback)) {
            new AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                @Override
                protected DiffUtil.DiffResult doInBackground(Void... params) {
                    return DiffUtil.calculateDiff(callback);
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    setData(callback.getNewList());
                    if (diffResult != null) {
                        diffResult.dispatchUpdatesTo(SuperAdapter.this);
                    }
                }
            }.execute();
        }
    }

    private boolean checkDiff(DiffUtil.Callback callback) {
        if (mRecyclerView == null) {
            throw new IllegalStateException("'diff(DefaultDiffCallback)' only works with RecyclerView");
        }

        if (callback == null || callback.getNewListSize() < 1) {
            Log.w(TAG, "Invalid size of the new list.");
            return false;
        }
        return true;
    }

}
