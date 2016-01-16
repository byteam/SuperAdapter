package org.byteam.superadapter.demo.adapter;

import android.content.Context;

import org.byteam.superadapter.demo.model.MockModel;
import org.byteam.superadapter.list.BaseViewHolder;
import org.byteam.superadapter.list.IMultiItemViewType;
import org.byteam.superadapter.list.SuperAdapter;

import java.util.List;

public class ListMultiAdapter extends SuperAdapter<MockModel> {
    public ListMultiAdapter(Context context, List<MockModel> data, IMultiItemViewType<MockModel> multiItemViewType) {
        super(context, data, multiItemViewType);
    }

    @Override
    protected void onBind(int viewType, BaseViewHolder holder, int position, MockModel item) {
        switch (viewType) {
            case 0:
                holder.setText(org.byteam.superadapter.demo.R.id.tv_name, item.getName());
                break;
            case 1:
                holder.setText(org.byteam.superadapter.demo.R.id.tv_name, item.getName());
                holder.setImageResource(org.byteam.superadapter.demo.R.id.iv_portrait, org.byteam.superadapter.demo.R.mipmap.ic_launcher);
                holder.setText(org.byteam.superadapter.demo.R.id.tv_age, String.valueOf(item.getAge()));
                break;
        }
    }
}
