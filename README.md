![Download](https://api.bintray.com/packages/chenenyu/maven/SuperAdapter/images/download.svg)
# SuperAdapter
*Adapter(ListAdapter, RecyclerView.Adapter) wrapperr for android.*

**Less code for redundant adapter.** You won't need to write ViewHolder, createView, setTag, getTag, and confuse how to setOnItemClickListener to RecyclerView, etc. SuperAdapter does everything for you! The only thing you really need to do is that implement a method `onBind()`.  

## Download

In build.gradle:

`compile 'com.chenenyu.superadapter:superadapter:1.0.1'`

SuperAdapter requires at minimum Android 4.0.

## How to use

If a simple adapter, it can be simplified like this:  

```
public class RecyclerSingleAdapter extends SuperAdapter<String> {
    public RecyclerSingleAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    public void onBind(BaseViewHolder holder, int position, String item) {
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
    public RecyclerMultiAdapter(Context context, List<MockModel> list, IMultiItemViewType<MockModel> multiItemViewType) {
        super(context, list, multiItemViewType);
    }

    @Override
    public void onBind(BaseViewHolder holder, int position, MockModel item) {
        switch (getItemViewType(position)) {
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


Inspired by Ray Zhang.  

**Welcome to pull requests and open iusses!  : )**

## License

```
Copyright 2015 Chenenyu.

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

