package com.chenenyu.superadapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Adapter that should be used.
 */
public abstract class SuperAdapter<T> extends BaseSuperAdapter<T, BaseViewHolder> {

    public SuperAdapter(Context context, List<T> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    public SuperAdapter(Context context, List<T> data, IMultiItemViewType<T> multiItemViewType) {
        super(context, data, multiItemViewType);
    }

    @Override
    public BaseViewHolder onCreate(int viewType, View convertView, ViewGroup parent) {
        if (mMultiItemViewType != null) {
            return BaseViewHolder.get(mContext, convertView, parent,
                    mMultiItemViewType.getLayoutId(viewType));
        }
        return BaseViewHolder.get(mContext, convertView, parent, mLayoutResId);
    }
}
