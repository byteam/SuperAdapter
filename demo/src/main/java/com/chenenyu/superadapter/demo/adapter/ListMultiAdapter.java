package com.chenenyu.superadapter.demo.adapter;

import android.content.Context;

import com.chenenyu.superadapter.demo.R;
import com.chenenyu.superadapter.demo.model.MockModel;
import com.chenenyu.superadapter.list.BaseViewHolder;
import com.chenenyu.superadapter.list.IMultiItemViewType;
import com.chenenyu.superadapter.list.SuperAdapter;

import java.util.List;

public class ListMultiAdapter extends SuperAdapter<MockModel> {
    public ListMultiAdapter(Context context, List<MockModel> data, IMultiItemViewType<MockModel> multiItemViewType) {
        super(context, data, multiItemViewType);
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, MockModel item) {
        switch (getItemViewType(position)) {
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
