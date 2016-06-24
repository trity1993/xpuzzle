package cc.trity.ttlibrary.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;

import cc.trity.ttlibrary.R;
import cc.trity.ttlibrary.ui.BaseInit;

/**
 * 修改成databinding的形式,并修复BottomSheetDialog自身的bug
 * 以下是bug解决
 * http://www.jianshu.com/p/38af0cf77352#
 * https://code.google.com/p/android/issues/detail?id=201793 每个版本都有问题，看最新的回答就好
 * Created by trity on 22/6/16.
 */
public abstract class BaseBottomSheetDialog<B extends ViewDataBinding> extends BottomSheetDialog implements BaseInit {
    protected B binding;
    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
        createBottomView();
    }
    private void createBottomView(){
        initVariables();
        binding= DataBindingUtil.inflate(getLayoutInflater(),getLayoutResId(),null,false);
        setContentView(binding.getRoot());
        measureHeight(binding.getRoot());
        initView(null);
        initListener();
        loadData();
    }

    /**
     * 需要重新测量高度，源码是256dp
     *
     * @param bottomView
     */
    private void measureHeight(View bottomView) {
        View viewParent = (View) bottomView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(viewParent);
        bottomView.measure(0, 0);
        behavior.setPeekHeight(bottomView.getMeasuredHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) viewParent.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        viewParent.setLayoutParams(params);
    }
    /**
     * 下滑隐藏后，show不出现问题
     */
    @Override
    public void show() {
        super.show();
        final View view = findViewById(R.id.design_bottom_sheet);
        view.post(new Runnable() {
            @Override
            public void run() {
                BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });
    }

    /**
     * @return 加载布局layoutId
     */
    public abstract int getLayoutResId();
}
