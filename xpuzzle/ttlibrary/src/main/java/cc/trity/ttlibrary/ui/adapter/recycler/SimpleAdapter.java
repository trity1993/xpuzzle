package cc.trity.ttlibrary.ui.adapter.recycler;

import android.content.Context;
import android.databinding.ViewDataBinding;

import cc.trity.ttlibrary.ui.adapter.viewholder.BaseViewHolder;
import cc.trity.ttlibrary.ui.adapter.viewholder.LayoutId;

/**
 * Created by trity on 2016/5/24.
 */
public class SimpleAdapter<B extends ViewDataBinding> extends LoadMoreAdapter<LayoutId, B> {
    public SimpleAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewBinding(BaseViewHolder<B> holder, int position) {
    }

    @Override
    protected int getItemLayoutId(int position) {
        return get(position).getLayoutId();
    }
}
