package org.byteam.superadapter.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base of SuperAdapter.
 * Created by Cheney on 15/11/28.
 */
public abstract class BaseSuperAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH>
        implements IHeaderFooter, ILayoutManager {
    private static final String TAG = "BaseSuperAdapter";
    protected static final int TYPE_HEADER = -0x100;
    protected static final int TYPE_FOOTER = -0x101;
    protected int mLayoutResId;
    protected IMultiItemViewType<T> mMultiItemViewType;
    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected List<T> mList;
    protected RecyclerView mRecyclerView;
    private View mHeader;
    private View mFooter;

    public BaseSuperAdapter(Context context, List<T> list, int layoutResId) {
        this.mContext = context;
        this.mList = list == null ? new ArrayList<T>() : new ArrayList<>(list);
        this.mLayoutResId = layoutResId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseSuperAdapter(Context context, List<T> list, IMultiItemViewType<T> multiItemViewType) {
        this.mContext = context;
        this.mList = list == null ? new ArrayList<T>() : new ArrayList<>(list);
        this.mMultiItemViewType = multiItemViewType;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getList() {
        return mList;
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
                if (hasHeaderView()) {
                    position--;
                }
                return mMultiItemViewType.getItemViewType(position, mList.get(position));
            }
            return 0;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        int size = mList == null ? 0 : mList.size();
        if (hasHeaderView())
            size++;
        if (hasFooterView())
            size++;
        return size;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            onBind(viewType, holder, position, mList.get(position));
        }
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
        ifGridLayoutManager();
        notifyItemInserted(0);
    }

    @Override
    public void addFooterView(View footer) {
        if (hasFooterView())
            throw new IllegalStateException("You have already added a footer view.");
        mFooter = footer;
        ifGridLayoutManager();
        notifyItemInserted(getItemCount() - 1);
    }

    private void ifGridLayoutManager() {
        final RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
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
            notifyItemRemoved(getItemCount() - 1);
            mFooter = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLayoutManager() {
        return mRecyclerView != null && mRecyclerView.getLayoutManager() != null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return hasLayoutManager() ? mRecyclerView.getLayoutManager() : null;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null && mRecyclerView != recyclerView)
            Log.i(TAG, "Does not support multiple RecyclerViews now.");
        mRecyclerView = recyclerView;
        // Ensure a situation that add header or footer before setAdapter().
        ifGridLayoutManager();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = null;
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            // add header or footer to StaggeredGridLayoutManager
            if (isHeaderView(holder.getLayoutPosition()) || isFooterView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
            }
        }
    }


    public void add(T item) {
        mList.add(item);
        int index = mList.size() - 1;
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);
    }

    public void insert(int index, T item) {
        mList.add(index, item);
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);
    }

    public void addAll(List<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        int start = mList.size();
        mList.addAll(items);
        if (hasHeaderView())
            start++;
        notifyItemRangeInserted(start, items.size());
    }

    public void remove(T item) {
        if (mList.contains(item)) {
            int index = mList.indexOf(item);
            remove(index);
        }
    }

    public void remove(int index) {
        mList.remove(index);
        if (hasHeaderView())
            index++;
        notifyItemRemoved(index);
    }

    public void set(T oldItem, T newItem) {
        set(mList.indexOf(oldItem), newItem);
    }

    public void set(int index, T item) {
        mList.set(index, item);
        if (hasHeaderView())
            index++;
        notifyItemChanged(index);
    }

    public void replaceAll(List<T> items) {
        clear();
        addAll(items);
    }

    public boolean contains(T item) {
        return mList.contains(item);
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

}
