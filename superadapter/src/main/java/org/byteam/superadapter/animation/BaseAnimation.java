package org.byteam.superadapter.animation;

import android.animation.Animator;
import android.view.View;

/**
 * Base animation for item loading.
 *
 * @Author: chenenyu
 * @Created: 16/6/28 14:41.
 */
public interface BaseAnimation {

    Animator[] getAnimators(View view);

}
