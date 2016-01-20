package org.byteam.superadapter.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * ViewHolder
 * <p>
 * Contains convenient method for getter, setter and listener.
 */
public class BaseViewHolder {
    private final Context mContext;
    private final SparseArray<View> childViews;

    private View mItemView;
    private int mLayoutResId;

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public View getItemView() {
        return mItemView;
    }

    public static BaseViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new BaseViewHolder(context, parent, layoutId);
        }
        BaseViewHolder existingHolder = (BaseViewHolder) convertView.getTag();
        if (existingHolder.mLayoutResId != layoutId) {
            return new BaseViewHolder(context, parent, layoutId);
        }
        return existingHolder;
    }

    private BaseViewHolder(Context context, ViewGroup parent, int layoutResId) {
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.childViews = new SparseArray<>();
        this.mItemView = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        mItemView.setTag(this);
    }

    public <T extends View> T getView(int viewId) {
        View view = childViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            if (view != null)
                childViews.put(viewId, view);
        }
        try {
            return (T) view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        if (view != null) {
            if (TextUtils.isEmpty(value))
                view.setText("");
            else
                view.setText(value);
        }
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageResource(imageResId);
        }
        return this;
    }

    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageBitmap(bitmap);
        }
        return this;
    }

    public BaseViewHolder setImageUri(int viewId, Uri imageUri) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageURI(imageUri);
        }
        return this;
    }

    public BaseViewHolder setScaleType(int viewId, ImageView.ScaleType type) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setScaleType(type);
        }
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int bgColor) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundColor(bgColor);
        }
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(backgroundRes);
        }
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTextColor(textColor);
        }
        return this;
    }

    public BaseViewHolder setColorFilter(int viewId, ColorFilter colorFilter) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setColorFilter(colorFilter);
        }
        return this;
    }

    public BaseViewHolder setColorFilter(int viewId, int colorFilter) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setColorFilter(colorFilter);
        }
        return this;
    }

    public BaseViewHolder setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        View view = getView(viewId);
        if (view != null) {
            ViewCompat.setAlpha(view, value);
        }
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visible) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visible);
        }
        return this;
    }

    public BaseViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        if (view != null) {
            view.setMax(max);
        }
        return this;
    }

    public BaseViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        if (view != null) {
            view.setProgress(progress);
        }
        return this;
    }

    public BaseViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        if (view != null) {
            view.setRating(rating);
        }
        return this;
    }

    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(tag);
        }
        return this;
    }

    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(key, tag);
        }
        return this;
    }

    /**
     * Set adapter for AbsListView.
     *
     * @param viewId  id
     * @param adapter BaseAdapter
     * @return BaseViewHolder
     */
    public BaseViewHolder setAdapter(int viewId, BaseAdapter adapter) {
        View view = getView(viewId);
        if (view instanceof ListView) {
            ((ListView) view).setAdapter(adapter);
        } else if (view instanceof GridView) {
            ((GridView) view).setAdapter(adapter);
        }
        return this;
    }

    /**
     * Set adapter for RecyclerView.
     *
     * @param viewId  id
     * @param adapter RecyclerView.Adapter
     * @return BaseViewHolder
     */
    public BaseViewHolder setAdapter(int viewId, RecyclerView.Adapter adapter) {
        View view = getView(viewId);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(adapter);
        }
        return this;
    }

    public BaseViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        if (view != null) {
            view.setChecked(checked);
        }
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(listener);
        }
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
        return this;
    }

}
