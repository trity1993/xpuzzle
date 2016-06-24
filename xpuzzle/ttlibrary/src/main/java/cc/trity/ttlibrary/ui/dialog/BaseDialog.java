package cc.trity.ttlibrary.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import cc.trity.ttlibrary.R;
import cc.trity.ttlibrary.ui.BaseInit;

/**
 * 对话框 - 基类
 * 重设默认的主题样式
 * 修改成databinding的形式
 * Created by trity on 16-6-22.
 */
public abstract class BaseDialog extends Dialog implements BaseInit {

    public BaseDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init resources
        initVariables();
        initView(savedInstanceState);
        initListener();
        loadData();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        EventHub.register(this); //关于eventbus的设置
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        EventHub.unregister(this);
    }

    /**
     * 对话框大小
     *
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        getWindow().setLayout(width, height);
    }

    /**
     * 对话框位置
     *
     * @param gravity
     */
    public void setLocation(int gravity) {
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(gravity);
    }

    /**
     * 对话框高度适应，设置宽度
     *
     * @param width
     */
    public void setWidthSize(int width) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = width;
        getWindow().setAttributes(params);
    }

    /**
     * 设置动画
     *
     * @param resId
     */
    public void setAnim(int resId) {
        getWindow().setWindowAnimations(resId);
    }
}
