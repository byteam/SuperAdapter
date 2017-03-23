package org.byteam.superadapter.demo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.byteam.superadapter.animation.AlphaInAnimation;
import org.byteam.superadapter.demo.R;

import java.util.List;

public class SingleAdapter extends SuperAdapter<String> {
    public SingleAdapter(Context context, List<String> list, @LayoutRes int layoutResId) {
        super(context, list, layoutResId);
        enableLoadAnimation(500, new AlphaInAnimation());
        setOnlyOnce(false);
    }

    @Override
    public SuperViewHolder onCreate(View convertView, ViewGroup parent, int viewType) {
        // These code show how to add click listener to item view of ViewHolder.
        final SuperViewHolder holder = super.onCreate(convertView, parent, viewType);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "" + holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), ((TextView) (holder.findViewById(R.id.tv_name))).getText(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, final int layoutPosition, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
