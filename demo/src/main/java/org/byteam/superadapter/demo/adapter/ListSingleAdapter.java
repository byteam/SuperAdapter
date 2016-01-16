package org.byteam.superadapter.demo.adapter;

import android.content.Context;

import org.byteam.superadapter.list.BaseViewHolder;
import org.byteam.superadapter.list.SuperAdapter;

import java.util.List;

public class ListSingleAdapter extends SuperAdapter<String> {

    public ListSingleAdapter(Context context, List<String> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    protected void onBind(int viewType, BaseViewHolder holder, int position, String item) {
        holder.setText(org.byteam.superadapter.demo.R.id.tv_name, item);
    }
}
