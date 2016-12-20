![Version](https://api.bintray.com/packages/chenenyu/maven/SuperAdapter/images/download.svg)![API](https://img.shields.io/badge/API-9%2B-orange.svg)![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SuperAdapter-brightgreen.svg?style=flat)

QQ群：271849001(新)

![QQ群二维码](img/qq_qun.png)
# SuperAdapter
*一个封装了BaseAdapter和RecyclerView.Adapter的简洁的Adapter。*

**旨在减少Adapter冗余的代码。** 你不必再写ViewHolder以及其他必须覆写的方法，只需要实现`onBind()`方法就够了。  

## Android Studio:

在module的build.gradle中:

`compile 'org.byteam.superadapter:superadapter:最新版本号'`

当前最新版本号为：![Version](https://api.bintray.com/packages/chenenyu/maven/SuperAdapter/images/download.svg)

## Eclipse:
可以手动添加最新的[jar包](https://github.com/chenenyu/SuperAdapter/releases)到libs文件夹下，建议尽早迁移到Android Studio开发。
## 特性
* 减少大量代码*3!
* 支持Header和footer。
* 支持item的点击事件。
* 隐藏ViewHolder相关代码。
* 一个SuperAdapter同时支持`BaseAdapter`和`RecyclerView.Adapter`。
* 封装Adapter数据源变动操作。

## 如何使用

如果是个单布局的Adapter，可以简写为如下示例代码:  

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

然后在Activity(or Fragment)中调用:  

```
mSingleAdapter = new RecyclerSingleAdapter(getContext(), names, R.layout.your_item);  
recyclerView.setAdapter(mSingleAdapter);
```
如果是个多布局的Adapter，可以简写为如下示例代码:  

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

然后调用（注意构造方法的参数与单布局的区别）:  

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

如果不想在创建Adapter时提供IMulItemViewType接口，也可以在Adapter中重写`offerMultiItemViewType()`方法：  

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

然后在创建Adapter时提供`null`：  
`multiAdapter = new MultipleAdapter(getContext(), models, null);`  

如果使用的是RecyclerView，在使用多布局时，还可以使用`SimpleMulItemViewType`类，因为`getViewTypeCount()`方法仅在使用ListView、GridView等控件时是必须的: 

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


**欢迎提交代码、bug以及讨论  : )**

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

