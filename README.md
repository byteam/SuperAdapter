![Download](https://api.bintray.com/packages/chenenyu/maven/SuperAdapter/images/download.svg) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SuperAdapter-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3027)
## [Chinese Version 中文版](README-zh-rCN.md)
# SuperAdapter
*Adapter(ListAdapter, RecyclerView.Adapter) wrapper for android.*

**Less code for redundant adapter.** You won't need to write ViewHolder, createView, setTag, getTag, and confuse how to setOnItemClickListener to RecyclerView, etc. SuperAdapter does everything for you! The only thing you really need to do is that implement a method `onBind()`.  

## Android Studio:

In build.gradle:

`compile 'org.byteam.superadapter:superadapter:latestVersion'`
## Eclipse:
Add [latest Jar](https://github.com/byteam/SuperAdapter/releases) to libs dir manually.

## CHANGELOG
* 2016/2/24 v2.2.4
* Add `setOnLongClickListener` and some convenient methods for recycler adapter.
* 2016/1/25 v2.2.3
* Fixed: custom `SpanSizeLookUp` is overridden by header or footer in GridLayoutManager.
* 2016/1/21 v2.2.2
* Header and footer now support GridLayoutManager and StaggeredGridLayoutManager!
* 2016/1/18 v2.2.1
* Bug fixed: #1.
* 2016/1/16 v2.2.0
* Transfer to organization for a teamwork.
* 2016/1/15 v2.1.2
* Bug fixed: set `OnItemClickListener` to nested adapter caused crash in RecyclerView.
* 2016/1/14 v2.1.1
* Add convenient method: setScaleType().
* Bug fixed：Support public method `getView()` for ViewHolder in BaseAdapter.
* 2016/1/13 v2.1.0 
* `addHeaderView()`/`addFooterView()` for LinearLayoutManager.

## How to use

If a simple adapter, it can be simplified like this:  

```
public class RecyclerSingleAdapter extends SuperAdapter<String> {
    public RecyclerSingleAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
```  

Then:  

```
mSingleAdapter = new RecyclerSingleAdapter(getContext(), names, R.layout.item_type1);  
recyclerView.setAdapter(mSingleAdapter);
```  
If a complex adapter, it can be simplified like this:  

```
public class RecyclerMultiAdapter extends SuperAdapter<MockModel> {
    public RecyclerMultiAdapter(Context context, List<MockModel> list, IMultiItemViewType<MockModel> mulItemViewType) {
        super(context, list, mulItemViewType);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, MockModel item) {
        switch (viewType) {
            case 0:
                holder.setText(R.id.tv_name, item.getName());
                break;
            case 1:
                holder.setText(R.id.tv_name, item.getName());
                holder.setImageResource(R.id.iv_portrait, R.mipmap.ic_launcher);
                holder.setText(R.id.tv_age, String.valueOf(item.getAge()));
                break;
        }
    }
}
```  

Then:  

```
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
```  
The usages between AdapterView(ListView, GridView) and RecyclerView are almost the same.


Inspired by Ray.  

**Welcome to submit pull requests and open issues!  : )**

## License

```
Copyright 2015-2016 byteam.org.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

