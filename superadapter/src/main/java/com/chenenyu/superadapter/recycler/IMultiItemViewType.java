package com.chenenyu.superadapter.recycler;

/**
 * Interface for multiple types.
 * Created by Cheney on 15/11/28.
 */
public interface IMultiItemViewType<T> {
    /**
     * ItemView type
     *
     * @param position current position of ViewHolder
     * @param t        model item
     * @return viewType
     */
    int getItemViewType(int position, T t);

    /**
     * Layout Res
     *
     * @param viewType {@link #getItemViewType(int, Object)}
     * @return {@link android.support.annotation.LayoutRes}
     */
    int getLayoutId(int viewType);
}