package org.byteam.superadapter.animation;

import android.support.v7.widget.RecyclerView;

/**
 * Interface for adapter.
 *
 * @Author: chenenyu
 * @Created: 16/6/28 14:49.
 */
public interface IAnimation {

    void openLoadAnimation();

    void openLoadAnimation(long duration, BaseAnimation animation);

    void setOnlyOnce(boolean onlyOnce);

    void addLoadAnimation(RecyclerView.ViewHolder holder);

}
