package org.byteam.superadapter.demo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.demo.R;
import org.byteam.superadapter.demo.adapter.MultipleAdapter;
import org.byteam.superadapter.demo.adapter.SingleAdapter;
import org.byteam.superadapter.demo.model.DataUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fragment contains ListView.
 * Created by Cheney on 15/12/19.
 */
public class ListViewFragment extends Fragment {
    private static final String TYPE = "type";
    private int mType;

    private TextView header, footer;
    private ListView mListView;
    private SuperAdapter mAdapter;
    private List<String> mData;

    public static ListViewFragment newInstance(int type) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(TYPE, 1);
            if (mType == 1) {
                setHasOptionsMenu(true);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_header:
                if (!mAdapter.hasHeaderView()) {
                    mAdapter.addHeaderView(header);
                    mListView.scrollTo(0, 0);
                }
                return true;
            case R.id.action_remove_header:
                if (mAdapter.hasHeaderView())
                    mAdapter.removeHeaderView();
                return true;
            case R.id.action_add_footer:
                if (!mAdapter.hasFooterView())
                    mAdapter.addFooterView(footer);
                return true;
            case R.id.action_remove_footer:
                if (mAdapter.hasFooterView())
                    mAdapter.removeFooterView();
                return true;
            case R.id.action_insert_data:
                if (mType == 1) {
                    mData.add(0, Arrays.asList(DataUtils.names).get(0));
                    mAdapter.notifyDataSetHasChanged();
//                    mAdapter.add(0, Arrays.asList(DataUtils.names).get(0));
                } else if (mType == 2) {
                    mAdapter.add(0, DataUtils.generateData().get(0));
                }
                return true;
            case R.id.action_addAll_data:
                if (mType == 1) {
                    mAdapter.addAll(Arrays.asList(DataUtils.names));
                } else if (mType == 2) {
                    mAdapter.addAll(DataUtils.generateData());
                }
                return true;
            case R.id.action_replaceAll_data:
                if (mType == 1) {
                    Toast.makeText(getContext(), "偷个懒", Toast.LENGTH_SHORT).show();
                } else if (mType == 2) {
                    mAdapter.replaceAll(DataUtils.generateData());
                }
                return true;
            case R.id.action_clear_data:
                mAdapter.clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        mListView = (ListView) view;
        header = new TextView(getContext());
        header.setBackgroundColor(Color.YELLOW);
        header.setText("header");
        footer = new TextView(getContext());
        footer.setBackgroundColor(Color.BLUE);
        footer.setText("footer");
        if (mType == 1) {
            mData = new ArrayList<>(Arrays.asList(DataUtils.names));
            mAdapter = new SingleAdapter(getContext(), mData, R.layout.item_type1);
            mListView.setAdapter(mAdapter);
        } else if (mType == 2) {
//            multiAdapter = new MultipleAdapter(getContext(), DataUtils.generateData(), new IMulItemViewType<MockModel>() {
//                @Override
//                public int getItemViewType(int position, MockModel mockModel) {
//                    if (position % 2 == 0) {
//                        return 0;
//                    }
//                    return 1;
//                }
//
//                @Override
//                public int getLayoutId(int viewType) {
//                    if (viewType == 0) {
//                        return R.layout.item_type1;
//                    }
//                    return R.layout.item_type2;
//                }
//
//                @Override
//                public int getViewTypeCount() {
//                    return 2;
//                }
//            });
            mAdapter = new MultipleAdapter(getContext(), DataUtils.generateData(), null);
            mListView.setAdapter(mAdapter);
        }
        return view;
    }

}
