## 更新日志 / CHANGELOG
* 2016/4/19 v3.0.0-beta1
* 现在`onBind()`方法里的position参数考虑存在header的情况，即是当前item在整个layout中的位置。
* The param `int position` in method `onBind()` now includes header(if exists).
* 2016/4/06 v3.0.0-alpha1
* 整合BaseAdapter和RecyclerView.Adapter，现在只有一个SuperAdapter，全面重构！
* Revolution! Merge SuperAdapter. Interface-oriented.
* 2016/2/24 v2.2.4
* 添加`setOnLongClickListener`方法以及一些操作ViewHolder的便捷方法。
* Add `setOnLongClickListener` and some convenient methods for recycler adapter.
* 2016/1/25 v2.2.3
* 修复：自定义`SpanSizeLookUp`被GridLayoutManager覆盖。
* Fixed: custom `SpanSizeLookUp` is overridden by header or footer in GridLayoutManager.
* 2016/1/21 v2.2.2
* 现在支持为GridLayoutManager和StaggeredGridLayoutManager添加header和footer!
* Header and footer now support GridLayoutManager and StaggeredGridLayoutManager!
* 2016/1/18 v2.2.1
* Bug fixed: #1。
* 2016/1/16 v2.2.0
* 迁移到组织协作开发。
* Transfer to organization for a teamwork.
* 2016/1/15 v2.1.2
* bug修复:当RecyclerView里面嵌套有adapter时,设置点击事件崩溃。
* Bug fixed: set `OnItemClickListener` to nested adapter caused crash in RecyclerView.
* 2016/1/14 v2.1.1
* 添加便捷方法`setScaleType()`。
* Add convenient method: setScaleType().
* 2016/1/13 v2.1.0
* LinearLayoutManager添加`addHeaderView()`/`addFooterView()`等相关方法。
* `addHeaderView()`/`addFooterView()` for LinearLayoutManager.