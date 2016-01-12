package com.chenenyu.superadapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base of QuickAdapter.
 * Created by Cheney on 15/11/28.
 */
public abstract class BaseSuperAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;
    protected int mLayoutResId;
    protected List<T> mItems;
    protected IMultiItemViewType<T> mMultiItemViewType;
    protected LayoutInflater mLayoutInflater;

    public BaseSuperAdapter(Context context, List<T> items, int layoutResId) {
        this.mContext = context;
        this.mItems = items == null ? new ArrayList<T>() : new ArrayList<>(items);
        this.mLayoutResId = layoutResId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseSuperAdapter(Context context, List<T> items, IMultiItemViewType<T> multiItemViewType) {
        this.mContext = context;
        this.mItems = items == null ? new ArrayList<T>() : new ArrayList<>(items);
        this.mMultiItemViewType = multiItemViewType;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemViewType != null) {
            return mMultiItemViewType.getItemViewType(position, mItems.get(position));
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBind(getItemViewType(position), holder, position, mItems.get(position));
    }

    public abstract VH onCreate(ViewGroup parent, int viewType);

    /**
     * Abstract method for binding view and data.
     *
     * @param viewType {@link #getItemViewType}
     * @param holder   ViewHolder
     * @param position position
     * @param item     data
     */
    public abstract void onBind(int viewType, VH holder, int position, T item);

    public void add(T item) {
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void add(int index, T item) {
        mItems.add(index, item);
        notifyItemInserted(index);
    }

    public void addAll(List<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        int position = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(position, items.size());
    }

    public void remove(T item) {
        if (mItems.contains(item)) {
            int index = mItems.indexOf(item);
            remove(index);
        }
    }

    public void remove(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public void set(T oldItem, T newItem) {
        set(mItems.indexOf(oldItem), newItem);
    }

    public void set(int index, T item) {
        mItems.set(index, item);
        notifyItemChanged(index);
    }

    public void replaceAll(List<T> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyItemRangeInserted(0, items.size());
    }

    public boolean contains(T item) {
        return mItems.contains(item);
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public List<T> getAllData() {
        return mItems;
    }

}
