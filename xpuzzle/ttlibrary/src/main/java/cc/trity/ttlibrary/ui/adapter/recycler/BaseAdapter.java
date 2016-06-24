package cc.trity.ttlibrary.ui.adapter.recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cc.trity.ttlibrary.ui.adapter.viewholder.BaseViewHolder;

/**
 * Created by trity on 2016/5/23.
 */
public abstract class BaseAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<B>> implements List<T> {
    protected Context context;
    protected LayoutInflater inflater;

    public BaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        BaseViewHolder<B> holder = new BaseViewHolder<>(binding);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayoutId(position);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<B> holder, int position) {
        onBindViewBinding(holder,position);
    }

    public abstract void onBindViewBinding(BaseViewHolder<B> holder, int position);//看子类的onBindViewHolder就知道这个方法的意义

    @LayoutRes
    protected abstract int getItemLayoutId(int position);//同上看子类的getItemViewType

}
