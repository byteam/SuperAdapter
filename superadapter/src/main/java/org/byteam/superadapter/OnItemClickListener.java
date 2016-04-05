package org.byteam.superadapter;

import android.view.View;

/**
 * OnItemClickListener for RecyclerView.
 * Created by Cheney on 16/1/13.
 */
public interface OnItemClickListener {
    void onItemClick(View itemView, int viewType, int position);
}
