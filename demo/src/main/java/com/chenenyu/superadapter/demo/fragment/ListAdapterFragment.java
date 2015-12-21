package com.chenenyu.superadapter.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chenenyu.superadapter.demo.R;
import com.chenenyu.superadapter.demo.adapter.ListMultiAdapter;
import com.chenenyu.superadapter.demo.adapter.ListSingleAdapter;
import com.chenenyu.superadapter.demo.model.MockModel;
import com.chenenyu.superadapter.list.IMultiItemViewType;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment contains ListView.
 * Created by Cheney on 15/12/19.
 */
public class ListAdapterFragment extends Fragment {
    private int mType;

    private List<String> names = new ArrayList<>();
    private ListSingleAdapter singleAdapter;

    private List<MockModel> models = new ArrayList<>();
    private ListMultiAdapter multiAdapter;

    public static ListAdapterFragment newInstance(int type) {
        ListAdapterFragment fragment = new ListAdapterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mType = getArguments().getInt("type", 1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        ListView listView = (ListView) view;
        initData();
        if (mType == 1) {
            singleAdapter = new ListSingleAdapter(getContext(), names, R.layout.item_type1);
            listView.setAdapter(singleAdapter);
        } else if (mType == 2) {
            multiAdapter = new ListMultiAdapter(getContext(), models, new IMultiItemViewType<MockModel>() {
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

                @Override
                public int getViewTypeCount() {
                    return 2;
                }
            });
            listView.setAdapter(multiAdapter);
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
