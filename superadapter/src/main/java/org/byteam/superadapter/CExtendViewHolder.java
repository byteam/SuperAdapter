package org.byteam.superadapter;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 扩展ViewHolder
 */
public class CExtendViewHolder {
    static CExtendViewHolder instance;
    View view;

    public static CExtendViewHolder getInstance(View textView) {
        instance = new CExtendViewHolder(textView);
        return instance;
    }


    private CExtendViewHolder(View view) {
        this.view = view;
    }


    public CExtendViewHolder setTag(Object tag) {
        view.setTag(tag);
        return this;
    }

    public CExtendViewHolder setOnClickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
        return this;
    }


    public CExtendViewHolder setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        }
        return this;
    }

    public CExtendViewHolder setBackgroundColor(int color) {
        view.setBackgroundColor(color);
        return this;
    }

    public CExtendViewHolder setBackgroundResource(@DrawableRes int resid) {
        view.setBackgroundResource(resid);
        return this;
    }

    public CExtendViewHolder setScaleType(ImageView.ScaleType scaleType) {
        if (view instanceof ImageView) {
            ((ImageView) view).setScaleType(scaleType);
        }
        return this;
    }

    public CExtendViewHolder setText(String text) {
        if (view instanceof TextView) {//Button、EditText等控件继承自TextView
            ((TextView) view).setText(text);
        }
        return this;
    }

    public CExtendViewHolder setTextColor(int color) {
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
        return this;
    }

    public CExtendViewHolder setTextSize(float size) {
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(size);
        }
        return this;
    }
}