package org.byteam.superadapter.demo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.SimpleMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.demo.R;
import org.byteam.superadapter.demo.adapter.MultipleAdapter;
import org.byteam.superadapter.demo.adapter.SingleAdapter;
import org.byteam.superadapter.demo.model.DataUtils;
import org.byteam.superadapter.demo.model.MockModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Fragment contains RecyclerView.
 * Created by Cheney on 15/12/19.
 */
public class RecyclerViewFragment extends Fragment {

    private int mType;
    private static final String TYPE = "type";

    private RecyclerView recyclerView;

    private SuperAdapter mAdapter;

    private TextView header, footer;

    public static RecyclerViewFragment newInstance(int type) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mType = getArguments().getInt(TYPE, 1);
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
                    recyclerView.scrollToPosition(0);
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
                if (mType == 2) {
                    mAdapter.add(0, DataUtils.generateData().get(0));
                } else {
                    mAdapter.add(0, Arrays.asList(DataUtils.names).get(0));
                }
                return true;
            case R.id.action_addAll_data:
                if (mAdapter != null && mType == 2) {
                    mAdapter.addAll(DataUtils.generateData());
                }
                return true;
            case R.id.action_replaceAll_data:
                if (mAdapter != null && mType == 2) {
                    mAdapter.replaceAll(DataUtils.generateData());
                }
                return true;
            case R.id.action_clear_data:
                mAdapter.clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(org.byteam.superadapter.demo.R.layout.fragment_recyclerview, container, false);
        header = new TextView(getContext());
        header.setBackgroundColor(Color.YELLOW);
        header.setText("header");
        footer = new TextView(getContext());
        footer.setBackgroundColor(Color.BLUE);
        footer.setText("footer");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (mType == 1) {
                mAdapter = new SingleAdapter(getContext(), new ArrayList<>(Arrays.asList(DataUtils.names)), R.layout.item_type1);
                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int viewType, int position) {
                        Log.d("onItemClick", "" + position);
                    }
                });
                recyclerView.setAdapter(mAdapter);
            } else if (mType == 2) {
                mAdapter = new MultipleAdapter(getContext(), DataUtils.generateData(),
                        new SimpleMulItemViewType<MockModel>() {
                            @Override
                            public int getItemViewType(int position, MockModel mockModel) {
                                if (position % 2 == 0) {
                                    return 0;
                                }
                                return 1;
                            }

                            @Override
                            public int getLayoutId(int viewType) {
                                if (viewType == 0) {
                                    return R.layout.item_type1;
                                }
                                return R.layout.item_type2;
                            }
                        });
                recyclerView.setAdapter(mAdapter);
            }
        }
        return view;
    }

}
