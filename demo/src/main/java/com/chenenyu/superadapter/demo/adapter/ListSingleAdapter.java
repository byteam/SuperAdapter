package com.chenenyu.superadapter.demo.adapter;

import android.content.Context;

import com.chenenyu.superadapter.demo.R;
import com.chenenyu.superadapter.list.BaseViewHolder;
import com.chenenyu.superadapter.list.SuperAdapter;

import java.util.List;

public class ListSingleAdapter extends SuperAdapter<String> {

    public ListSingleAdapter(Context context, List<String> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
