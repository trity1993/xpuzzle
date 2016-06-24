package cc.trity.ttlibrary.helper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import cc.trity.ttlibrary.BaseApplication;

/**
 * Toast,snackbar 辅助类
 * 关于snackbar后面再补充，感觉跟toast一样
 * Created by trity on 23/6/16.
 */
public class TipHelper {
    private static Toast toast;
    private static Context context=BaseApplication.getContext();

    /**
     * 弹出Toast消息
     *
     * @param charSequence
     */
    public static void showToast(final CharSequence charSequence) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(charSequence);
        }
        toast.show();
    }

    /**
     * 弹出Toast消息
     *
     * @param charSequence
     */
    public static void showToastMiddle(final CharSequence charSequence) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(charSequence);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 弹出Toast消息
     *
     * @param resId
     */

    public static void showToastMiddle(int resId) {
        showToastMiddle(context.getText(resId));
    }

    /**
     * 资源ID信息显示
     *
     * @param resId
     */
    public static void showToast(int resId) {
        showToast(context.getText(resId));
    }

    /**
     * 资源ID信息显示
     *
     * @param resId
     * @param duration Toast.LENGTH_SHORT | Toast.LENGTH_LONG
     */
    public static void showToast(int resId, int duration) {
        showToast(context.getText(resId), duration);
    }

    /**
     * 指定消息显示时间
     *
     * @param charSequence
     * @param duration     Toast.LENGTH_SHORT | Toast.LENGTH_LONG
     */
    public static void showToast(final CharSequence charSequence, final int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, duration);
        } else {
            toast.setText(charSequence);
        }
        toast.show();
    }

    public static void showSnackbar(View parentView, String msg){
        Snackbar.make(parentView,msg,Snackbar.LENGTH_SHORT).show();
    }
    public static void showSnackbar(View parentView,int resInt){
        Snackbar.make(parentView,resInt,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 取消所有toast
     */
    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 显示一个snackbar
     * 只适用于{@link android.support.v7.app.AppCompatActivity}
     *
     * @param view
     * @param text
     */
    public static void showSnackBar(View view, String text) {
        showSnackBar(view, text, null, null);
    }
    public static void showSnackBar(View view, int resId) {
        showSnackBar(view, context.getText(resId).toString());
    }

    /**
     * 显示一个snackbar
     *
     * @param view
     * @param text
     * @param action
     * @param listener
     */
    public static void showSnackBar(View view, String text, String action, final View.OnClickListener listener) {
        Snackbar sb = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
                .setAction(action, listener);
        sb.show();
    }
}
