package com.chenenyu.superadapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper of BaseAdapter.
 */
public abstract class BaseSuperAdapter<T, VH extends BaseViewHolder> extends BaseAdapter {
    protected Context mContext;
    protected int mLayoutResId;
    protected List<T> mList;
    protected IMultiItemViewType<T> mMultiItemViewType;

    public BaseSuperAdapter(Context context, List<T> data, int layoutResId) {
        this.mContext = context;
        this.mList = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mLayoutResId = layoutResId;
    }

    public BaseSuperAdapter(Context context, List<T> data, IMultiItemViewType<T> multiItemViewType) {
        this.mContext = context;
        this.mList = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mMultiItemViewType = multiItemViewType;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= mList.size())
            return null;
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemViewType != null)
            return mMultiItemViewType.getViewTypeCount();
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemViewType != null) {
            return mMultiItemViewType.getItemViewType(position, mList.get(position));
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VH viewHolder = onCreate(getItemViewType(position), convertView, parent);
        T item = getItem(position);
        onBind(getItemViewType(position), viewHolder, position, item);
        return viewHolder.getItemView();
    }

    protected abstract VH onCreate(int viewType, View convertView, ViewGroup parent);

    /**
     * Abstract method for binding view and data.
     *
     * @param viewType {@link #getItemViewType}
     * @param holder   ViewHolder
     * @param position position
     * @param item     data
     */
    protected abstract void onBind(int viewType, VH holder, int position, T item);

    public void add(T item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    public void add(T item, boolean isChanged) {
        mList.add(item);
        if (isChanged)
            notifyDataSetChanged();
    }

    public void add(int index, T item) {
        mList.add(index, item);
        notifyDataSetChanged();
    }

    public void addAll(List<T> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(T item) {
        mList.remove(item);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    public void remove(int index, boolean isChange) {
        mList.remove(index);
        if (isChange)
            notifyDataSetChanged();
    }

    public void set(T oldItem, T newItem) {
        set(mList.indexOf(oldItem), newItem);
    }

    public void set(int index, T item) {
        mList.set(index, item);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> items) {
        mList.clear();
        addAll(items);
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
