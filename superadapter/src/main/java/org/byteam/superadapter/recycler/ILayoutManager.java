package org.byteam.superadapter.recycler;

import android.support.v7.widget.RecyclerView;

/**
 * Methods about layout manager.
 * Created by Cheney on 16/1/18.
 */
interface ILayoutManager {
    boolean hasLayoutManager();

    RecyclerView.LayoutManager getLayoutManager();
}
