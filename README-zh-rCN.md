![Download](https://api.bintray.com/packages/chenenyu/maven/SuperAdapter/images/download.svg)
# SuperAdapter
*一个封装了BaseAdapter和RecyclerView.Adapter的简洁的Adapter。*

**旨在减少Adapter冗余的代码。** 你不必再写ViewHolder以及其他必须覆写的方法，也不必再惆怅怎样方便的为RecyclerView的item设置点击事件，SuperAdapter为你做好了一切！你只需要实现`onBind()`方法就够了。  

## Android Studio:

在module的build.gradle中:

`compile 'com.chenenyu.superadapter:superadapter:2.0.0'`
## Eclipse:
可以手动添加最新的[jar包](https://github.com/chenenyu/SuperAdapter/releases)到libs文件夹下，建议尽早迁移到Android Studio开发。
## 更新日志
* 2016/1/13 v2.1.0 
* RecyclerView.Adapter添加addHeaderView()/addFooterView()等相关方法。

## 如何使用

如果是个单布局的Adapter，可以简写为如下示例代码:  

```
public class RecyclerSingleAdapter extends SuperAdapter<String> {
    public RecyclerSingleAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, String item) {
    	// 便捷的绑定数据的方法
        holder.setText(R.id.tv_name, item);
        // 或者手动查找View，然后赋值，如下：
        // TextView tvName = getView(R.id.tv_name);
        // tvName.setText(item);
    }
}
```  

然后调用:  

```
mSingleAdapter = new RecyclerSingleAdapter(getContext(), names, R.layout.item_type1);  
recyclerView.setAdapter(mSingleAdapter);
```  
如果是个多布局的Adapter，可以简写为如下示例代码:  

```
public class RecyclerMultiAdapter extends SuperAdapter<MockModel> {
    public RecyclerMultiAdapter(Context context, List<MockModel> items, IMultiItemViewType<MockModel> multiItemViewType) {
        super(context, items, multiItemViewType);
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

然后调用（注意构造方法的参数与单布局的区别）:  

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
AdapterView(ListView, GridView)和RecyclerView的用法几乎完全一样。
  

**欢迎提交代码、bug以及讨论  : )**

## License

```
Copyright 2015-2016 chenenyu.

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

