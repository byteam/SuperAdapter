package com.chenenyu.superadapter.demo.adapter;

import android.content.Context;

import com.chenenyu.superadapter.demo.R;
import com.chenenyu.superadapter.demo.model.MockModel;
import com.chenenyu.superadapter.recycler.BaseViewHolder;
import com.chenenyu.superadapter.recycler.IMultiItemViewType;
import com.chenenyu.superadapter.recycler.SuperAdapter;

import java.util.List;

public class RecyclerMultiAdapter extends SuperAdapter<MockModel> {
    public RecyclerMultiAdapter(Context context, List<MockModel> list, IMultiItemViewType<MockModel> multiItemViewType) {
        super(context, list, multiItemViewType);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, MockModel item) {
        switch (viewType) {
            case 0:
                holder.setText(R.id.tv_name, item.getName());
                break;
            case 1:
                holder.setText(R.id.tv_name, item.getName());
                holder.setImageResource(R.id.iv_portrait, R.mipmap.ic_launcher);
                holder.setText(R.id.tv_age, String.valueOf(item.getAge()));
                break;
        }
    }
}
