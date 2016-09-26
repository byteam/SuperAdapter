package org.byteam.superadapter;

import android.view.View;

/**
 * OnItemLongClickListener for RecyclerView.
 * <p>
 * Created by Cheney on 16/2/24.
 */
public interface OnItemLongClickListener {
    void onItemLongClick(View itemView, int viewType, int position);
}
