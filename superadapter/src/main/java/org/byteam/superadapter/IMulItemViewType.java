package org.byteam.superadapter;


/**
 * Interface for multiple types.
 * Created by Cheney on 15/11/28.
 */
public interface IMulItemViewType<T> {

    /**
     * @return Will not be called if using a RecyclerView.
     */
    int getViewTypeCount();

    /**
     * ItemView type, a non-negative integer is better.
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