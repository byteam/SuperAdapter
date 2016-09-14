package org.byteam.superadapter.internal;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.OnItemLongClickListener;
import org.byteam.superadapter.animation.AlphaInAnimation;
import org.byteam.superadapter.animation.BaseAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Base adapter.
 * </p>
 * Created by Cheney on 16/3/30.
 */
public abstract class BaseSuperAdapter<T> extends RecyclerView.Adapter<SuperViewHolder>
        implements IViewBindData<T, SuperViewHolder>, IAnimation, ILayoutManager, IHeaderFooter {

    protected Context mContext;
    protected List<T> mData;

    protected int mLayoutResId;
    protected IMulItemViewType<T> mMulItemViewType;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    protected RecyclerView mRecyclerView;

    protected static final int TYPE_HEADER = -0x100;
    protected static final int TYPE_FOOTER = -0x101;
    protected View mHeader;
    protected View mFooter;

    private Interpolator mInterpolator = new LinearInterpolator();
    private long mDuration = 300;
    private boolean mLoadAnimationEnable;
    private boolean mOnlyOnce = true;
    private BaseAnimation mLoadAnimation;
    private int mLastPosition = -1;


    /**
     * Constructor for single item view type.
     *
     * @param context     Context.
     * @param list        Data list.
     * @param layoutResId {@link android.support.annotation.LayoutRes}
     */
    public BaseSuperAdapter(Context context, List<T> list, int layoutResId) {
        this.mContext = context;
        this.mData = list == null ? new ArrayList<T>() : list;
        this.mLayoutResId = layoutResId;
        this.mMulItemViewType = null;
    }

    /**
     * Constructor for multiple item view type.
     *
     * @param context         Context.
     * @param list            Data list.
     * @param mulItemViewType If null, plz override {@link #offerMultiItemViewType()}.
     */
    public BaseSuperAdapter(Context context, List<T> list, IMulItemViewType<T> mulItemViewType) {
        this.mContext = context;
        this.mData = list == null ? new ArrayList<T>() : list;
        this.mMulItemViewType = mulItemViewType == null ? offerMultiItemViewType() : mulItemViewType;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * Deprecated. Use {@link #getData()} instead.
     */
    @Deprecated
    public List<T> getList() {
        return mData;
    }

    public List<T> getData() {
        return mData;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * @return Offered an {@link IMulItemViewType} by override this method.
     */
    protected IMulItemViewType<T> offerMultiItemViewType() {
        return null;
    }

    /**
     * How many items are represented by this RecyclerView.Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getItemCount() {
        int size = mData == null ? 0 : mData.size();
        if (hasHeaderView())
            size++;
        if (hasFooterView())
            size++;
        return size;
    }

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
                return mMulItemViewType.getItemViewType(position, mData.get(position));
            }
            return 0;
        }
        return viewType;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final SuperViewHolder holder;
        if (viewType == TYPE_HEADER && hasHeaderView()) {
            return new SuperViewHolder(getHeaderView());
        } else if (viewType == TYPE_FOOTER && hasFooterView()) {
            return new SuperViewHolder(getFooterView());
        } else {
            holder = onCreate(null, parent, viewType);
        }
        if (!(holder.itemView instanceof AdapterView)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, viewType, holder.getAdapterPosition());
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(v, viewType, holder.getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType != TYPE_HEADER && viewType != TYPE_FOOTER) {
            onBind(holder, viewType, position, mData.get(hasHeaderView() ? --position : position));
            addLoadAnimation(holder); // Load animation
        }
    }

    /**
     * ------------------------------------ Header / Footer ------------------------------------
     */

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null && mRecyclerView != recyclerView)
            Log.i("SuperAdapter", "Does not support multiple RecyclerViews now.");
        mRecyclerView = recyclerView;
        // Ensure a situation that add header or footer before setAdapter().
        ifGridLayoutManager();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = null;
    }

    @Override
    public void onViewAttachedToWindow(SuperViewHolder holder) {
        if (isHeaderView(holder.getLayoutPosition()) || isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
            }
        }
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
        setLayoutParams(mHeader);
        ifGridLayoutManager();
        notifyItemInserted(0);
    }

    @Override
    public void addFooterView(View footer) {
        if (hasFooterView())
            throw new IllegalStateException("You have already added a footer view.");
        mFooter = footer;
        setLayoutParams(mFooter);
        ifGridLayoutManager();
        notifyItemInserted(getItemCount() - 1);
    }

    private void setLayoutParams(View view) {
        if (hasHeaderView() || hasFooterView()) {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                view.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(
                        StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                        StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT));
            } else if (layoutManager instanceof GridLayoutManager) {
                view.setLayoutParams(new GridLayoutManager.LayoutParams(
                        GridLayoutManager.LayoutParams.MATCH_PARENT,
                        GridLayoutManager.LayoutParams.WRAP_CONTENT));
            } else {
                view.setLayoutParams(new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    public boolean removeHeaderView() {
        if (hasHeaderView()) {
            mHeader = null;
            notifyItemRemoved(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFooterView() {
        if (hasFooterView()) {
            int footerPosition = getItemCount() - 1;
            mFooter = null;
            notifyItemRemoved(footerPosition);
            return true;
        }
        return false;
    }

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

    private void ifGridLayoutManager() {
        if (hasHeaderView() || hasFooterView()) {
            final RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                        ((GridLayoutManager) layoutManager).getSpanSizeLookup();
                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeaderView(position) || isFooterView(position)) ?
                                ((GridLayoutManager) layoutManager).getSpanCount() :
                                originalSpanSizeLookup.getSpanSize(position);
                    }
                });
            }
        }
    }

    /**
     * ------------------------------------ Load animation ------------------------------------
     */

    @Override
    public void openLoadAnimation() {
        openLoadAnimation(mDuration, new AlphaInAnimation());
    }

    @Override
    public void openLoadAnimation(long duration, BaseAnimation animation) {
        if (duration > 0) {
            mDuration = duration;
        } else {
            Log.w("SuperAdapter", "Invalid animation duration");
        }
        mLoadAnimationEnable = true;
        mLoadAnimation = animation;
    }

    @Override
    public void setOnlyOnce(boolean onlyOnce) {
        mOnlyOnce = onlyOnce;
    }

    @Override
    public final void addLoadAnimation(RecyclerView.ViewHolder holder) {
        if (mLoadAnimationEnable) {
            if (!mOnlyOnce || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = mLoadAnimation == null ? new AlphaInAnimation() : mLoadAnimation;
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    public void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

}
