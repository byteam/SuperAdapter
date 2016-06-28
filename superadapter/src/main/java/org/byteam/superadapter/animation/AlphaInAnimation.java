package org.byteam.superadapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @Author: chenenyu
 * @Created: 16/6/28 16:01.
 */
public class AlphaInAnimation implements BaseAnimation {
    private final float mFrom;

    public AlphaInAnimation() {
        this(0f);
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
