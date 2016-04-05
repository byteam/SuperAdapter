package org.byteam.superadapter;

/**
 * <p>Convenient class for RecyclerView.Adapter.</p>
 * Created by Cheney on 16/4/5.
 */
public abstract class SimpleMulItemViewType<T> implements IMulItemViewType<T> {

    @Override
    public int getViewTypeCount() {
        return 1;
    }

}
