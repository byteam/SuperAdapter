![Download](https://api.bintray.com/packages/chenenyu/maven/SuperAdapter/images/download.svg)
# SuperAdapter
*Adapter(ListAdapter, RecyclerView.Adapter) wrapperr for android.*

**Less code for redundant adapter.** You won't need to write ViewHolder, createView, setTag, getTag, and confuse how to setOnItemClickListener to RecyclerView, etc. SuperAdapter does everything for you! The only thing you really need to do is that implement a method `onBind()`.  

### How to use?

In build.gradle:

`compile 'com.chenenyu.superadapter:superadapter:1.0.+'`  

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

### Contribution
Ray Zhang.

Welcome to pull requests and open iusses! : )

