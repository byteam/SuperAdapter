package org.byteam.superadapter.list;

/**
 * Interface for multiple types.
 */
public interface IMultiItemViewType<T> {
    int getViewTypeCount();

    int getItemViewType(int position, T t);

    int getLayoutId(int viewType);
}