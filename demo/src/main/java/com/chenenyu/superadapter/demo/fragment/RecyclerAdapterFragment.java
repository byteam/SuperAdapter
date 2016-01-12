package com.chenenyu.superadapter.demo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenenyu.superadapter.demo.R;
import com.chenenyu.superadapter.demo.adapter.RecyclerMultiAdapter;
import com.chenenyu.superadapter.demo.adapter.RecyclerSingleAdapter;
import com.chenenyu.superadapter.demo.model.MockModel;
import com.chenenyu.superadapter.recycler.IMultiItemViewType;
import com.chenenyu.superadapter.recycler.SuperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment contains RecyclerView.
 * Created by Cheney on 15/12/19.
 */
public class RecyclerAdapterFragment extends Fragment {

    private int mType;

    private List<String> names = new ArrayList<>();
    private RecyclerSingleAdapter mSingleAdapter;

    private List<MockModel> models = new ArrayList<>();
    private RecyclerMultiAdapter mMultiAdapter;

    public static RecyclerAdapterFragment newInstance(int type) {
        RecyclerAdapterFragment fragment = new RecyclerAdapterFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mType = getArguments().getInt("type", 1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        initData();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (mType == 1) {
                mSingleAdapter = new RecyclerSingleAdapter(getContext(), names, R.layout.item_type1);
                TextView header = new TextView(getContext());
                header.setBackgroundColor(Color.YELLOW);
                header.setText("header");
                mSingleAdapter.addHeaderView(header);
                TextView footer = new TextView(getContext());
                footer.setBackgroundColor(Color.BLUE);
                footer.setText("footer");
                mSingleAdapter.addFooterView(footer);
                mSingleAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int viewType, int position) {
                        Log.d("onItemClick", "" + position);
                    }
                });
                recyclerView.setAdapter(mSingleAdapter);
            } else if (mType == 2) {
                mMultiAdapter = new RecyclerMultiAdapter(getContext(), models, new IMultiItemViewType<MockModel>() {
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
                recyclerView.setAdapter(mMultiAdapter);
            }
        }
        return view;
    }

    private void initData() {
        names.add("John");
        names.add("Michelle");
        names.add("Amy");
        names.add("Kim");
        names.add("Mary");
        names.add("David");
        names.add("Sunny");
        names.add("James");
        names.add("Maria");
        names.add("Betty");
        names.add("Brian");
        names.add("Candy");
        names.add("Charles");
        names.add("Vicky");
        names.add("James");

        int size = names.size();
        for (int i = 0; i < size; i++) {
            models.add(new MockModel(names.get(i), 16 + (int) (Math.random() * 24)));
        }
    }

}
