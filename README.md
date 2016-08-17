![Download](https://api.bintray.com/packages/chenenyu/maven/SuperAdapter/images/download.svg) ![API](https://img.shields.io/badge/API-11%2B-brightgreen.svg?style=flat) ![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SuperAdapter-brightgreen.svg?style=flat) 

### [Chinese Version 中文版](http://www.jianshu.com/p/d6a76fd3ea5b)
QQ群：271849001(新)  

![QQ群二维码](img/qq_qun.png)
# SuperAdapter
*Adapter(ListAdapter, RecyclerView.Adapter) wrapper for android.*

**Less code for redundant adapter.** You won't need to write ViewHolder, createView, setTag, getTag, and confuse how to setOnItemClickListener to RecyclerView, etc. The only thing you really need to do is that implement a method `onBind()`.  

## Android Studio:

In build.gradle:

`compile 'org.byteam.superadapter:superadapter:latestVersion'`

## Eclipse:
Add [latest Jar](https://github.com/byteam/SuperAdapter/releases) to libs dir manually.

## Features
* Less code!
* Header and footer.
* OnItemClickListener and OnItemLongClickListener.
* Hide view holder.
* Supports both `BaseAdapter` and `RecyclerView.Adapter`.
* Wraps CRUD.
* Loading animation supporting!

## How to use

### Simple adapter

```
public class SingleAdapter extends SuperAdapter<String> {
	public SingleAdapter(Context context, List<String> list, int layoutResId) {
		super(context, list, layoutResId);
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int position, String item) {
		holder.setText(R.id.tv_name, item);
	}
}
```  

Then:  

```
mSingleAdapter = new RecyclerSingleAdapter(getContext(), names, R.layout.your_item);  
recyclerView.setAdapter(mSingleAdapter);
```  
### Multiple item types adapter

```
public class MultipleAdapter extends SuperAdapter<MockModel> {
	public MultipleAdapter(Context context, List<MockModel> list, IMulItemViewType<MockModel> multiItemViewType) {
		super(context, list, multiItemViewType);
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int position, MockModel item) {
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
multiAdapter = new MultipleAdapter(getContext(), models, new IMulItemViewType<MockModel>() {
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
recyclerView.setAdapter(mMultiAdapter);
```  

If you don't want to offer `IMulItemViewType` when creating adapter, you can override `offerMultiItemViewType()` in adapter:

```
@Override
protected IMulItemViewType<MockModel> offerMultiItemViewType() {
	return new IMulItemViewType<MockModel>() {
		@Override
		public int getViewTypeCount() {
				return 2;
		}

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
	};
}
```    

Then:  
`multiAdapter = new MultipleAdapter(getContext(), models, null);`   
If using `RecyclerView`, you can also use class `SimpleMulItemViewType`:  
 
```
mAdapter = new MultipleAdapter(getContext(), models, new SimpleMulItemViewType<MockModel>() {
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
```  

### Load animation:
Open default animation:  
`adapter.openLoadAnimation();`  
 or  
`adapter.openLoadAnimation(long duration, new SlideInBottomAnimation());`  
if you want to show animation when item shows each time:  
`adapter.setOnlyOnce(false);`  
Note that you can set custom animation by implementing **[BaseAnimation](superadapter/src/main/java/org/byteam/superadapter/animation/BaseAnimation.java)**.  


**Welcome to submit PRs and open issues!  : )**

## License

```
Copyright 2016 byteam.org.

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

