package org.byteam.superadapter.internal;

import android.support.v7.widget.RecyclerView;

import org.byteam.superadapter.animation.BaseAnimation;

/**
 * Interface for adapter.
 *
 * @Author: chenenyu
 * @Created: 16/6/28 14:49.
 */
interface IAnimation {

    void openLoadAnimation();

    void openLoadAnimation(long duration, BaseAnimation animation);

    void setOnlyOnce(boolean onlyOnce);

    void addLoadAnimation(RecyclerView.ViewHolder holder);

}
