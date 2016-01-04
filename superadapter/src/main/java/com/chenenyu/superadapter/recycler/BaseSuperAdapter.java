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
public abstract class BaseSuperAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<H> {
    protected Context mContext;
    protected int mLayoutResId;
    protected List<T> mList;
    protected IMultiItemViewType<T> mMultiItemViewType;
    protected LayoutInflater mLayoutInflater;

    public BaseSuperAdapter(Context context, List<T> list, int layoutResId) {
        this.mContext = context;
        this.mList = list == null ? new ArrayList<T>() : new ArrayList<>(list);
        this.mLayoutResId = layoutResId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseSuperAdapter(Context context, List<T> data, IMultiItemViewType<T> multiItemViewType) {
        this.mContext = context;
        this.mList = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mMultiItemViewType = multiItemViewType;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemViewType != null) {
            return mMultiItemViewType.getItemViewType(position, mList.get(position));
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        onBind(getItemViewType(position), holder, position, mList.get(position));
    }

    public abstract H onCreate(ViewGroup parent, int viewType);

    /**
     * Abstract method for binding view and data.
     *
     * @param viewType {@link #getItemViewType}
     * @param holder   ViewHolder
     * @param position position
     * @param item     data
     */
    public abstract void onBind(int viewType, H holder, int position, T item);

    public void add(T item) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(item);
        notifyItemInserted(mList.size() - 1);
    }

    public void add(int index, T item) {
        mList.add(index, item);
        notifyItemInserted(index);
    }

    public void addAll(List<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        if (mList == null) {
            mList = new ArrayList<>();
        }
        int position = mList.size();
        mList.addAll(items);
        notifyItemRangeInserted(position, items.size());
    }

    public void remove(T item) {
        if (mList.contains(item)) {
            int index = mList.indexOf(item);
            remove(index);
        }
    }

    public void remove(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
    }

    public void set(T oldItem, T newItem) {
        set(mList.indexOf(oldItem), newItem);
    }

    public void set(int index, T item) {
        mList.set(index, item);
        notifyItemChanged(index);
    }

    public void replaceAll(List<T> items) {
        mList.clear();
        mList.addAll(items);
        notifyItemRangeInserted(0, items.size());
    }

    public boolean contains(T item) {
        return mList.contains(item);
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<T> getAllData() {
        return mList;
    }

}
