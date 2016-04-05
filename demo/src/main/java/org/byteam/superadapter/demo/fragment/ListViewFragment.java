package org.byteam.superadapter.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.byteam.superadapter.demo.R;
import org.byteam.superadapter.demo.adapter.MultipleAdapter;
import org.byteam.superadapter.demo.adapter.SingleAdapter;
import org.byteam.superadapter.demo.model.MockModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment contains ListView.
 * Created by Cheney on 15/12/19.
 */
public class ListViewFragment extends Fragment {
    private static final String TYPE = "type";
    private int mType;

    private List<String> names = new ArrayList<>();
    private SingleAdapter singleAdapter;

    private List<MockModel> models = new ArrayList<>();
    private MultipleAdapter multiAdapter;

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
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        ListView listView = (ListView) view;
        initData();
        if (mType == 1) {
            singleAdapter = new SingleAdapter(getContext(), names, R.layout.item_type1);
            listView.setAdapter(singleAdapter);
        } else if (mType == 2) {
//            multiAdapter = new MultipleAdapter(getContext(), models, new IMulItemViewType<MockModel>() {
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
            multiAdapter = new MultipleAdapter(getContext(), models, null);
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
