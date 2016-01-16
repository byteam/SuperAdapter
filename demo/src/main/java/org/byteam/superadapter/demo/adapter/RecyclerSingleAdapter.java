package org.byteam.superadapter.demo.adapter;

import android.content.Context;

import org.byteam.superadapter.recycler.BaseViewHolder;
import org.byteam.superadapter.recycler.SuperAdapter;

import java.util.List;

public class RecyclerSingleAdapter extends SuperAdapter<String> {
    public RecyclerSingleAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, String item) {
        holder.setText(org.byteam.superadapter.demo.R.id.tv_name, item);
    }
}
