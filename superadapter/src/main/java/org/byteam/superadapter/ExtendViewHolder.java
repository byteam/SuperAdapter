package org.byteam.superadapter;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * More convenient chain call.
 * <p>
 * Created by Cheney on 2017/3/29.
 */
public class ExtendViewHolder {
    private View currentView;

    private ExtendViewHolder(View view) {
        currentView = view;
    }

    static ExtendViewHolder get(View view) {
        return new ExtendViewHolder(view);
    }

    public ExtendViewHolder setText(CharSequence text) {
        ((TextView) currentView).setText(text);
        return this;
    }

    public ExtendViewHolder setTextColor(int textColor) {
        ((TextView) currentView).setTextColor(textColor);
        return this;
    }

    public ExtendViewHolder setTextColor(ColorStateList colorStateList) {
        ((TextView) currentView).setTextColor(colorStateList);
        return this;
    }

    public ExtendViewHolder setMovementMethod(MovementMethod method) {
        ((TextView) currentView).setMovementMethod(method);
        return this;
    }

    public ExtendViewHolder setImageResource(@DrawableRes int resId) {
        ((ImageView) currentView).setImageResource(resId);
        return this;
    }

    public ExtendViewHolder setImageDrawable(Drawable drawable) {
        ((ImageView) currentView).setImageDrawable(drawable);
        return this;
    }

    public ExtendViewHolder setImageBitmap(Bitmap bitmap) {
        ((ImageView) currentView).setImageBitmap(bitmap);
        return this;
    }

    public ExtendViewHolder setImageUri(Uri imageUri) {
        ((ImageView) currentView).setImageURI(imageUri);
        return this;
    }

    public ExtendViewHolder setScaleType(ImageView.ScaleType type) {
        ((ImageView) currentView).setScaleType(type);
        return this;
    }

    public ExtendViewHolder setBackgroundColor(@ColorInt int bgColor) {
        currentView.setBackgroundColor(bgColor);
        return this;
    }

    public ExtendViewHolder setBackgroundResource(@DrawableRes int bgRes) {
        currentView.setBackgroundResource(bgRes);
        return this;
    }

    public ExtendViewHolder setColorFilter(ColorFilter colorFilter) {
        ((ImageView) currentView).setColorFilter(colorFilter);
        return this;
    }

    public ExtendViewHolder setColorFilter(int colorFilter) {
        ((ImageView) currentView).setColorFilter(colorFilter);
        return this;
    }

    public ExtendViewHolder setAlpha(@FloatRange(from = 0.0, to = 1.0) float value) {
        ViewCompat.setAlpha(currentView, value);
        return this;
    }

    public ExtendViewHolder setVisibility(int visibility) {
        currentView.setVisibility(visibility);
        return this;
    }

    public ExtendViewHolder setMax(int max) {
        ((ProgressBar) currentView).setMax(max);
        return this;
    }

    public ExtendViewHolder setProgress(int progress) {
        ((ProgressBar) currentView).setProgress(progress);
        return this;
    }

    public ExtendViewHolder setRating(float rating) {
        ((RatingBar) currentView).setRating(rating);
        return this;
    }

    public ExtendViewHolder setTag(Object tag) {
        currentView.setTag(tag);
        return this;
    }

    public ExtendViewHolder setEnabled(boolean enabled) {
        currentView.setEnabled(enabled);
        return this;
    }

    public ExtendViewHolder setAdapter(Adapter adapter) {
        ((AdapterView<Adapter>) currentView).setAdapter(adapter);
        return this;
    }

    public ExtendViewHolder setAdapter(RecyclerView.Adapter adapter) {
        ((RecyclerView) currentView).setAdapter(adapter);
        return this;
    }

    public ExtendViewHolder setChecked(boolean checked) {
        ((Checkable) currentView).setChecked(checked);
        return this;
    }

    public ExtendViewHolder setOnClickListener(View.OnClickListener listener) {
        currentView.setOnClickListener(listener);
        return this;
    }

    public ExtendViewHolder setOnLongClickListener(View.OnLongClickListener listener) {
        currentView.setOnLongClickListener(listener);
        return this;
    }

    public ExtendViewHolder setOnTouchListener(View.OnTouchListener listener) {
        currentView.setOnTouchListener(listener);
        return this;
    }

}
