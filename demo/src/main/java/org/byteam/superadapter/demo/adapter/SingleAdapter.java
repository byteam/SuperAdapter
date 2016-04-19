package org.byteam.superadapter.demo.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.demo.R;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class SingleAdapter extends SuperAdapter<String> {
    public SingleAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
