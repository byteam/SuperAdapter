package org.byteam.superadapter.internal;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import org.byteam.superadapter.IMulItemViewType;

import java.util.List;

/**
 * Middle adapter for {@link android.widget.BaseAdapter} supporting.
 *
 * @Author: chenenyu
 * @Created: 16/6/28 15:25.
 */
public abstract class ListSupportAdapter<T> extends BaseSuperAdapter<T>
        implements ListAdapter, SpinnerAdapter {

    private AbsListView mAbsListView;

    private DataSetObservable mDataSetObservable = new DataSetObservable();

    public ListSupportAdapter(Context context, List<T> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    public ListSupportAdapter(Context context, List<T> list, IMulItemViewType<T> mulItemViewType) {
        super(context, list, mulItemViewType);
    }


    /**
     * @see android.widget.BaseAdapter#areAllItemsEnabled().
     */
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    /**
     * @see android.widget.BaseAdapter#isEnabled(int).
     */
    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetHasChanged() {
        if (mRecyclerView == null)
            mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        if (mRecyclerView == null)
            mDataSetObservable.notifyInvalidated();
    }

    /**
     * @see android.widget.BaseAdapter#getCount().
     */
    @Override
    public int getCount() {
        return super.getCount();
    }

    /**
     * @see android.widget.BaseAdapter#getItem(int).
     */
    @Override
    public T getItem(int position) {
        if (position >= mList.size())
            return null;
        return mList.get(position);
    }

    /**
     * @see android.widget.BaseAdapter#getItemId(int).
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @see android.widget.BaseAdapter#getView(int, View, ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mAbsListView == null && parent instanceof AbsListView) {
            mAbsListView = (AbsListView) parent;
        }
        SuperViewHolder holder = onCreate(convertView, parent, getItemViewType(position));
        T item = getItem(position);
        onBind(holder, getItemViewType(position), position, item);
        addLoadAnimation(holder); // Load animation
        return holder.itemView;
    }

    /**
     * @see android.widget.BaseAdapter#getItemViewType(int).
     */
    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (isHeaderView(position)) {
            viewType = TYPE_HEADER;
        } else if (isFooterView(position)) {
            viewType = TYPE_FOOTER;
        } else {
            if (mMulItemViewType != null) {
                if (hasHeaderView()) {
                    position--;
                }
                return mMulItemViewType.getItemViewType(position, mList.get(position));
            }
            return 0;
        }
        return viewType;
    }

    /**
     * @see android.widget.BaseAdapter#getViewTypeCount().
     */
    @Override
    public int getViewTypeCount() {
        if (mMulItemViewType != null)
            return mMulItemViewType.getViewTypeCount();
        return 1;
    }

    /**
     * @see android.widget.BaseAdapter#isEmpty().
     */
    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

}
