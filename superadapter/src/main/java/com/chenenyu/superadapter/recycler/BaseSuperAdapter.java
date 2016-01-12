package com.chenenyu.superadapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base of QuickAdapter.
 * Created by Cheney on 15/11/28.
 */
public abstract class BaseSuperAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> implements IHeaderFooter {
    protected Context mContext;
    protected int mLayoutResId;
    protected List<T> mItems;
    protected IMultiItemViewType<T> mMultiItemViewType;
    protected LayoutInflater mLayoutInflater;

    protected static final int TYPE_HEADER = -0x100;
    protected static final int TYPE_FOOTER = -0x101;
    private View mHeader;
    private View mFooter;

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
        int viewType;
        if (isHeaderView(position)) {
            viewType = TYPE_HEADER;
        } else if (isFooterView(position)) {
            viewType = TYPE_FOOTER;
        } else {
            if (mMultiItemViewType != null) {
                return mMultiItemViewType.getItemViewType(position, mItems.get(position));
            }
            return 0;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        int size = mItems == null ? 0 : mItems.size();
        if (hasHeaderView())
            size++;
        if (hasFooterView())
            size++;
        return size;
    }

    public T getItem(int position) {
        if (hasHeaderView())
            position--;
        return mItems.get(position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int viewType = getItemViewType(position);
        if (hasHeaderView())
            position--;
        if (viewType != TYPE_HEADER && viewType != TYPE_FOOTER) {
            onBind(viewType, holder, position, mItems.get(position));
        }
        // TODO: 16/1/12 是否需要考虑header和footer的情况 
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

    @Override
    public boolean hasHeaderView() {
        return getHeaderView() != null;
    }

    @Override
    public boolean hasFooterView() {
        return getFooterView() != null;
    }

    @Override
    public boolean isHeaderView(int position) {
        return hasHeaderView() && position == 0;
    }

    @Override
    public boolean isFooterView(int position) {
        return hasFooterView() && position == getItemCount() - 1;
    }

    @Override
    public View getHeaderView() {
        return mHeader;
    }

    @Override
    public View getFooterView() {
        return mFooter;
    }

    @Override
    public void addHeaderView(View header) {
        if (hasHeaderView())
            throw new IllegalStateException("You have already added a header view.");
        mHeader = header;
        notifyItemInserted(0);
    }

    @Override
    public void addFooterView(View footer) {
        if (hasFooterView())
            throw new IllegalStateException("You have already added a footer view.");
        mFooter = footer;
        notifyItemInserted(getItemCount());
    }

    @Override
    public boolean removeHeaderView() {
        if (hasHeaderView()) {
            notifyItemRemoved(0);
            mHeader = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFooterView() {
        if (hasFooterView()) {
            notifyItemRemoved(getItemCount());
            mFooter = null;
            return true;
        }
        return false;
    }

    public void add(T item) {
        mItems.add(item);
        int index = mItems.size() - 1;
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);
    }

    public void insert(int index, T item) {
        mItems.add(index, item);
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);
    }

    public void addAll(List<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        int start = mItems.size();
        mItems.addAll(items);
        if (hasHeaderView())
            start++;
        notifyItemRangeInserted(start, items.size());
    }

    public void remove(T item) {
        if (mItems.contains(item)) {
            int index = mItems.indexOf(item);
            remove(index);
        }
    }

    public void remove(int index) {
        mItems.remove(index);
        if (hasHeaderView())
            index++;
        notifyItemRemoved(index);
    }

    public void set(T oldItem, T newItem) {
        set(mItems.indexOf(oldItem), newItem);
    }

    public void set(int index, T item) {
        mItems.set(index, item);
        if (hasHeaderView())
            index++;
        notifyItemChanged(index);
    }

    public void replaceAll(List<T> items) {
        mItems.clear();
        mItems.addAll(items);
        int start = 0;
        if (hasHeaderView())
            start++;
        notifyItemRangeInserted(start, items.size());
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
