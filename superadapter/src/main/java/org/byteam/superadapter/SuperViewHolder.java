package org.byteam.superadapter;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.MovementMethod;
import android.util.SparseArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Universal view holder.
 * <p>
 * Created by Cheney on 16/3/30.
 */
public class SuperViewHolder extends RecyclerView.ViewHolder implements ChainSetter<SuperViewHolder> {

    private SparseArray<View> childViews = new SparseArray<>();

    SuperViewHolder(View itemView) {
        super(itemView);
    }

    public static SuperViewHolder get(View convertView, View itemView) {
        SuperViewHolder holder;
        if (convertView == null) {
            holder = new SuperViewHolder(itemView);
            convertView = itemView;
            convertView.setTag(holder);
        } else {
            holder = (SuperViewHolder) convertView.getTag();
        }
        return holder;
    }

    /**
     * Deprecated. Use {@link #findViewById(int)} instead for a better understanding.
     */
    @Deprecated
    public <T extends View> T getView(int id) {
        return findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id) {
        View childView = childViews.get(id);
        if (childView == null) {
            childView = itemView.findViewById(id);
            if (childView != null)
                childViews.put(id, childView);
        }
        return (T) childView;
    }

    @Override
    public SuperViewHolder setText(int viewId, CharSequence text) {
        TextView textView = findViewById(viewId);
        textView.setText(text);
        return this;
    }

    @Override
    public SuperViewHolder setTextColor(int viewId, int textColor) {
        TextView view = findViewById(viewId);
        view.setTextColor(textColor);
        return this;
    }

    @Override
    public SuperViewHolder setTextColor(int viewId, ColorStateList colorStateList) {
        TextView view = findViewById(viewId);
        view.setTextColor(colorStateList);
        return this;
    }

    @Override
    public SuperViewHolder setMovementMethod(int viewId, MovementMethod method) {
        TextView textView = findViewById(viewId);
        textView.setMovementMethod(method);
        return this;
    }

    @Override
    public SuperViewHolder setImageResource(int viewId, int imgResId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(imgResId);
        return this;
    }

    @Override
    public SuperViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = findViewById(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public SuperViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = findViewById(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    @Override
    public SuperViewHolder setImageUri(int viewId, Uri imageUri) {
        ImageView view = findViewById(viewId);
        view.setImageURI(imageUri);
        return this;
    }

    @Override
    public SuperViewHolder setScaleType(int viewId, ImageView.ScaleType type) {
        ImageView view = findViewById(viewId);
        view.setScaleType(type);
        return this;
    }

    @Override
    public SuperViewHolder setBackgroundColor(int viewId, int bgColor) {
        View view = findViewById(viewId);
        view.setBackgroundColor(bgColor);
        return this;
    }

    @Override
    public SuperViewHolder setBackgroundResource(int viewId, int bgRes) {
        View view = findViewById(viewId);
        view.setBackgroundResource(bgRes);
        return this;
    }

    @Override
    public SuperViewHolder setColorFilter(int viewId, ColorFilter colorFilter) {
        ImageView view = findViewById(viewId);
        view.setColorFilter(colorFilter);
        return this;
    }

    @Override
    public SuperViewHolder setColorFilter(int viewId, int colorFilter) {
        ImageView view = findViewById(viewId);
        view.setColorFilter(colorFilter);
        return this;
    }

    @Override
    public SuperViewHolder setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        View view = findViewById(viewId);
        ViewCompat.setAlpha(view, value);
        return this;
    }

    @Override
    public SuperViewHolder setVisibility(int viewId, int visibility) {
        View view = findViewById(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @Override
    public SuperViewHolder setMax(int viewId, int max) {
        ProgressBar view = findViewById(viewId);
        view.setMax(max);
        return this;
    }

    @Override
    public SuperViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = findViewById(viewId);
        view.setProgress(progress);
        return this;
    }

    @Override
    public SuperViewHolder setRating(int viewId, float rating) {
        RatingBar view = findViewById(viewId);
        view.setRating(rating);
        return this;
    }

    @Override
    public SuperViewHolder setTag(int viewId, Object tag) {
        View view = findViewById(viewId);
        view.setTag(tag);
        return this;
    }

    @Override
    public SuperViewHolder setTag(int viewId, int key, Object tag) {
        View view = findViewById(viewId);
        view.setTag(key, tag);
        return this;
    }

    @Override
    public SuperViewHolder setEnabled(int viewId, boolean enabled) {
        View view = findViewById(viewId);
        view.setEnabled(enabled);
        return this;
    }

    @Override
    public SuperViewHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView<Adapter> view = findViewById(viewId);
        view.setAdapter(adapter);
        return this;
    }

    @Override
    public SuperViewHolder setAdapter(int viewId, RecyclerView.Adapter adapter) {
        RecyclerView view = findViewById(viewId);
        view.setAdapter(adapter);
        return this;
    }

    @Override
    public SuperViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = findViewById(viewId);
        view.setChecked(checked);
        return this;
    }

    @Override
    public SuperViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
        return this;
    }

    @Override
    public SuperViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        findViewById(viewId).setOnLongClickListener(listener);
        return this;
    }

    @Override
    public SuperViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        findViewById(viewId).setOnTouchListener(listener);
        return this;
    }
}
