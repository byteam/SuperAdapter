package org.byteam.superadapter.demo.adapter;

import android.content.Context;

import org.byteam.superadapter.demo.model.MockModel;
import org.byteam.superadapter.recycler.BaseViewHolder;
import org.byteam.superadapter.recycler.IMultiItemViewType;
import org.byteam.superadapter.recycler.SuperAdapter;

import java.util.List;

public class RecyclerMultiAdapter extends SuperAdapter<MockModel> {
    public RecyclerMultiAdapter(Context context, List<MockModel> list, IMultiItemViewType<MockModel> multiItemViewType) {
        super(context, list, multiItemViewType);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, MockModel item) {
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
