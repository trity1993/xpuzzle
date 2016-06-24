package cc.trity.ttlibrary.ui.adapter.viewholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by trity on 2016/5/23.
 */
public class BaseViewHolder <B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private B mBinding;

    public BaseViewHolder(B binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public B getBinding() {
        return mBinding;
    }
}
