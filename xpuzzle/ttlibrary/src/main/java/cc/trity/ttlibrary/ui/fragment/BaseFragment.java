package cc.trity.ttlibrary.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.trity.ttlibrary.ui.BaseInit;

/**
 * Created by trity on 2016/5/15.
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements BaseInit {
    private static final String FRAGMENTATION_STATE_SAVE_IS_HIDDEN = "fragmentation_state_save_status";
    protected final String TAG = getClass().getName();
    protected B binding;
    private boolean mIsHidden = true;   // 用于记录Fragment show/hide 状态

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
            mIsHidden=savedInstanceState.getBoolean(FRAGMENTATION_STATE_SAVE_IS_HIDDEN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initVariables();
        initView(savedInstanceState);
        initListener();
        loadData();
    }
    public String getFragmentTitle() {
        return null;
    }

    public boolean ismIsHidden() {
        return mIsHidden;
    }

    /**
     * @return 加载布局layoutId
     */
    public abstract int getLayoutResId();
}
