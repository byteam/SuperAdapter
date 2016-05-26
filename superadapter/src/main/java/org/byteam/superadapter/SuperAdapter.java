package org.byteam.superadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byteam.superadapter.internal.BaseSuperAdapter;
import org.byteam.superadapter.internal.CRUD;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * <p>
 * The core class.
 * </p>
 * Created by Cheney on 16/3/30.
 */
public abstract class SuperAdapter<T> extends BaseSuperAdapter<T> implements CRUD<T> {
    private final String TAG = "SuperAdapter";
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

    @Override
    public SuperViewHolder onCreate(View convertView, ViewGroup parent, int viewType) {
        int resource;
        if (mMulItemViewType != null) {
            resource = mMulItemViewType.getLayoutId(viewType);
        } else {
            resource = mLayoutResId;
        }
        return SuperViewHolder.get(convertView, convertView == null ?
                mLayoutInflater.inflate(resource, parent, false) : null);
    }

    /**
     * ------------------------------------ CRUD ------------------------------------
     */

    @Override
    public final void add(T item) {
        mList.add(item);
        int location = mList.size() - 1;
        if (hasHeaderView())
            location++;
        notifyItemInserted(location);
        notifyDataSetHasChanged();
    }

    @Override
    public void add(int location, T item) {
        mList.add(location, item);
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
        mList.addAll(items);
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
        if (hasHeaderView())
            location++;
        mList.addAll(location, items);
        notifyItemRangeInserted(location, items.size());
        notifyDataSetHasChanged();
    }

    @Override
    public final void remove(T item) {
        if (contains(item)) {
            remove(mList.indexOf(item));
        }
    }

    @Override
    public final void remove(int location) {
        mList.remove(location);
        if (hasHeaderView())
            location++;
        notifyItemRemoved(location);
        notifyDataSetHasChanged();
    }

    @Override
    public void removeAll(List<T> items) {
        mList.removeAll(items);
        notifyDataSetChanged(); // RecyclerView
        notifyDataSetHasChanged(); // AdapterView
    }

    @Override
    public void retainAll(List<T> items) {
        mList.retainAll(items);
        notifyDataSetChanged(); // RecyclerView
        notifyDataSetHasChanged(); // AdapterView
    }

    @Override
    public final void set(T oldItem, T newItem) {
        set(mList.indexOf(oldItem), newItem);
    }

    @Override
    public final void set(int location, T item) {
        mList.set(location, item);
        if (hasHeaderView())
            location++;
        notifyItemChanged(location);
        notifyDataSetHasChanged();
    }

    @Override
    public final void replaceAll(List<T> items) {
        if (items == null || items.isEmpty()) {
            Log.w(TAG, "replaceAll: The list you passed contains no elements.");
            return;
        }
        if (mList.isEmpty()) {
            addAll(items);
        } else {
            int start = hasHeaderView() ? 1 : 0;
            int originalSize = getCount();
            int newSize = items.size();
            mList.clear();
            mList.addAll(items);
            if (originalSize > newSize) {
                notifyItemRangeChanged(start, newSize);
                notifyItemRangeRemoved(start + newSize, originalSize - newSize);
            } else if (originalSize == newSize) {
                notifyItemRangeChanged(start, newSize);
            } else {
                notifyItemRangeChanged(start, originalSize);
                notifyItemRangeInserted(start + originalSize, newSize - originalSize);
            }
            notifyDataSetHasChanged(); // AdapterView
        }

    }

    @Override
    public final boolean contains(T item) {
        return mList.contains(item);
    }

    @Override
    public boolean containsAll(List<T> items) {
        return mList.containsAll(items);
    }

    @Override
    public final void clear() {
        if (!mList.isEmpty()) {
            mList.clear();
            notifyItemRangeRemoved(hasHeaderView() ? 1 : 0, getCount());
            notifyDataSetHasChanged(); // AdapterView
        }
    }
}
