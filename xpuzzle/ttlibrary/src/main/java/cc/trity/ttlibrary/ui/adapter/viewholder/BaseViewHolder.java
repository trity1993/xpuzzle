package cc.trity.ttlibrary.ui.adapter.viewholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import cc.trity.ttlibrary.BR;

/**
 * Created by trity on 2016/5/23.
 */
public class BaseViewHolder <B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private B mBinding;

    public BaseViewHolder(B binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bindTo(Object obj) {
        mBinding.setVariable(BR.data, obj);
        mBinding.setVariable(BR.vh,this);
        mBinding.executePendingBindings();
    }

    public B getBinding() {
        return mBinding;
    }
}
