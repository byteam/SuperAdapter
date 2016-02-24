package org.byteam.superadapter.recycler;

import android.view.View;

/**
 * OnItemLongClickListener for RecyclerView.
 * Created by Cheney on 16/2/24.
 */
public interface OnItemLongClickListener {
    void onItemLongClick(View itemView, int viewType, int position);
}
