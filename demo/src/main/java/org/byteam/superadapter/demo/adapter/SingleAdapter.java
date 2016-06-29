package org.byteam.superadapter.demo.adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.View;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.animation.AlphaInAnimation;
import org.byteam.superadapter.demo.R;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class SingleAdapter extends SuperAdapter<String> {
    public SingleAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
        openLoadAnimation(500, new AlphaInAnimation());
        setOnlyOnce(false);
    }

    @Override
    public void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, final int layoutPosition, String item) {
        holder.setText(R.id.tv_name, item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "" + holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("onLongClick", "" + layoutPosition);
                remove(layoutPosition);
                return true;
            }
        });
    }
}
