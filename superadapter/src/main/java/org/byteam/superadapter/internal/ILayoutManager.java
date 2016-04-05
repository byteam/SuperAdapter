package org.byteam.superadapter.internal;

import android.support.v7.widget.RecyclerView;

/**
 * <p>Methods about layout manager.</p>
 * Created by Cheney on 16/1/18.
 */
interface ILayoutManager {
    boolean hasLayoutManager();

    RecyclerView.LayoutManager getLayoutManager();
}
