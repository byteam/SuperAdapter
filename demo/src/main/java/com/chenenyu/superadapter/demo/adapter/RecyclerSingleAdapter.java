package com.chenenyu.superadapter.demo.adapter;

import android.content.Context;

import com.chenenyu.superadapter.demo.R;
import com.chenenyu.superadapter.recycler.BaseViewHolder;
import com.chenenyu.superadapter.recycler.SuperAdapter;

import java.util.List;

public class RecyclerSingleAdapter extends SuperAdapter<String> {
    public RecyclerSingleAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    public void onBind(BaseViewHolder holder, int position, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
