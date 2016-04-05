package org.byteam.superadapter.internal;

import android.view.View;

/**
 * <p>Methods about header and footer.</p>
 * Created by Cheney on 16/1/12.
 */
interface IHeaderFooter {
    View getHeaderView();

    View getFooterView();

    void addHeaderView(View header);

    void addFooterView(View footer);

    boolean removeHeaderView();

    boolean removeFooterView();

    boolean hasHeaderView();

    boolean hasFooterView();

    boolean isHeaderView(int position);

    boolean isFooterView(int position);
}
