package cc.trity.ttlibrary.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * 封装简单常用的动画
 * Created by trity on 14/6/16.
 */
public class AnimUtils {
    /**
     * 直接通过setAlpha=0的方式处理bottomIn
     * setAlpha=0->visible->移动到屏幕外->setAlpha=1->从屏幕外移动回来。
     * @param view
     * @param isFirst
     */
    public static void animBottomIn(final View view, boolean isFirst){
        if(isFirst){
            view.setAlpha(0);
            view.setVisibility(View.VISIBLE);
            ObjectAnimator animHide=ObjectAnimator.ofFloat(view,"y",view.getBottom()).setDuration(1);
            animHide.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setAlpha(1);
                    ObjectAnimator animShow=ObjectAnimator.ofFloat(view,"y",view.getBottom(),view.getTop()).setDuration(500);
                    animShow.start();
                }
            });
            animHide.start();
        }else{
            view.animate().y(view.getTop()).setDuration(500);
        }

    }
    public static void animBottomOut(View view){
        view.animate().y(view.getBottom()).setDuration(500);
    }

    /**
     * 同样通过setAlpha的方式，透明度改变显示和不显示的效果
     * @param view
     */
    public static void animAlphaVisible(final View view){
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(1);
            }
        });
    }

    public static void animAlphaInVisible(final View view){
        view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
    }
}
