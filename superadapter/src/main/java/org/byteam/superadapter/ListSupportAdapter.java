package org.byteam.superadapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import java.util.List;

/**
 * Bridge adapter for {@link android.widget.BaseAdapter} supporting.
 * <p>
 * Created by Cheney on 16/6/28.
 */
abstract class ListSupportAdapter<T> extends RecyclerSupportAdapter<T>
        implements ListAdapter, SpinnerAdapter {

    private AbsListView mAbsListView;

    private DataSetObservable mDataSetObservable = new DataSetObservable();

    public ListSupportAdapter(Context context, List<T> list, @LayoutRes int layoutResId) {
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
        if (mRecyclerView == null) {
            mDataSetObservable.notifyChanged();
        } else {
            notifyDataSetChanged();
        }
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
        return mData == null ? 0 : mData.size();
    }

    /**
     * @see android.widget.BaseAdapter#getItem(int).
     */
    @Override
    public T getItem(int position) {
        if (position >= mData.size())
            return null;
        return mData.get(position);
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
     * Note that you must override this method if using <code>ListView</code> with multiple item types.
     * <p>
     * 在使用ListView的多布局的情况下,你必须重写此方法,因为ListView和RV的实现机制不同。
     *
     * @see android.widget.BaseAdapter#getItemViewType(int).
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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

    @Override
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            mAbsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemClickListener.onItemClick(view, getItemViewType(position), position);
                }
            });
        } else {
            super.setOnItemClickListener(onItemClickListener);
        }
    }

    @Override
    public void setOnItemLongClickListener(final OnItemLongClickListener onItemLongClickListener) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            mAbsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemLongClickListener.onItemLongClick(view, getItemViewType(position), position);
                }
            });
        } else {
            super.setOnItemLongClickListener(onItemLongClickListener);
        }
    }

    @Override
    public void addHeaderView(View header) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            ((ListView) mAbsListView).addHeaderView(header);
            mHeader = header;
        } else {
            super.addHeaderView(header);
        }
    }

    @Override
    public boolean removeHeaderView() {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            return ((ListView) mAbsListView).removeHeaderView(mHeader);
        } else {
            return super.removeHeaderView();
        }
    }

    @Override
    public boolean hasHeaderView() {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            return ((ListView) mAbsListView).getHeaderViewsCount() > 0;
        } else {
            return super.hasHeaderView();
        }
    }

    @Override
    public void addFooterView(View footer) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            ((ListView) mAbsListView).addFooterView(footer);
            mFooter = footer;
        } else {
            super.addFooterView(footer);
        }
    }

    @Override
    public boolean removeFooterView() {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            return ((ListView) mAbsListView).removeFooterView(mFooter);
        } else {
            return super.removeFooterView();
        }
    }

    @Override
    public boolean hasFooterView() {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            return ((ListView) mAbsListView).getFooterViewsCount() > 0;
        } else {
            return super.hasFooterView();
        }
    }
}
