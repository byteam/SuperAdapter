package org.byteam.superadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byteam.superadapter.internal.BaseSuperAdapter;
import org.byteam.superadapter.internal.CRUD;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * <p>The core class.</p>
 * Created by Cheney on 16/3/30.
 */
public abstract class SuperAdapter<T> extends BaseSuperAdapter<T> implements CRUD<T> {

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
        final SuperViewHolder holder;
        if (viewType == TYPE_HEADER && hasHeaderView()) {
            return new SuperViewHolder(getHeaderView());
        } else if (viewType == TYPE_FOOTER && hasFooterView()) {
            return new SuperViewHolder(getFooterView());
        }

        if (mMulItemViewType != null) {
            holder = SuperViewHolder.get(convertView, mLayoutInflater.inflate(
                    mMulItemViewType.getLayoutId(viewType), parent, false));
        } else {
            holder = SuperViewHolder.get(convertView, mLayoutInflater.inflate(mLayoutResId, parent, false));
        }

        return holder;
    }

    @Override
    public final void add(T item) {
        mList.add(item);
        int index = mList.size() - 1;
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);
        notifyDataSetHasChanged();
    }

    @Override
    public final void insert(int index, T item) {
        mList.add(index, item);
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);
        notifyDataSetHasChanged();
    }

    @Override
    public final void addAll(List<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        int start = mList.size();
        mList.addAll(items);
        if (hasHeaderView())
            start++;
        notifyItemRangeInserted(start, items.size());
        notifyDataSetHasChanged();
    }

    @Override
    public final void remove(T item) {
        if (contains(item)) {
            int index = mList.indexOf(item);
            remove(index);
        }
    }

    @Override
    public final void remove(int index) {
        mList.remove(index);
        if (hasHeaderView())
            index++;
        notifyItemRemoved(index);
        notifyDataSetHasChanged();
    }

    @Override
    public final void set(T oldItem, T newItem) {
        set(mList.indexOf(oldItem), newItem);
    }

    @Override
    public final void set(int index, T item) {
        mList.set(index, item);
        if (hasHeaderView())
            index++;
        notifyItemChanged(index);
        notifyDataSetHasChanged();
    }

    @Override
    public final void replaceAll(List<T> items) {
        clear();
        addAll(items);
    }

    @Override
    public final boolean contains(T item) {
        return mList.contains(item);
    }

    @Override
    public final void clear() {
        mList.clear();
        notifyDataSetChanged();
        notifyDataSetHasChanged();
    }
}
