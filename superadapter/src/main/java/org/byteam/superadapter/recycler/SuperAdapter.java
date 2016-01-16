package org.byteam.superadapter.recycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

/**
 * Adapter that should be used.
 * Created by Cheney on 15/11/27.
 */
public abstract class SuperAdapter<T> extends BaseSuperAdapter<T, BaseViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * Constructor for single itemView type.
     */
    public SuperAdapter(Context context, List<T> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    /**
     * Constructor for multiple itemView types.
     */
    public SuperAdapter(Context context, List<T> items, IMultiItemViewType<T> multiItemViewType) {
        super(context, items, multiItemViewType);
    }

    @Override
    public BaseViewHolder onCreate(ViewGroup parent, final int viewType) {
        final BaseViewHolder holder;
        if (viewType == TYPE_HEADER && hasHeaderView()) {
            return new BaseViewHolder(getHeaderView());
        } else if (viewType == TYPE_FOOTER && hasFooterView()) {
            return new BaseViewHolder(getFooterView());
        }

        if (mMultiItemViewType != null) {
            holder = new BaseViewHolder(mLayoutInflater.inflate(mMultiItemViewType.getLayoutId(viewType),
                    parent, false));
        } else {
            holder = new BaseViewHolder(mLayoutInflater.inflate(mLayoutResId, parent, false));
        }

        if (!(holder.itemView instanceof AdapterView)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, viewType, holder.getLayoutPosition());
                    }
                }
            });
        }
        return holder;
    }
}
