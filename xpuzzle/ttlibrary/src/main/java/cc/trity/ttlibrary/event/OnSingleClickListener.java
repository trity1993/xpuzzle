package cc.trity.ttlibrary.event;

import android.view.View;

/**
 * 防抖动设置
 * http://www.jianshu.com/p/dc63a4b636fa
 * Created by trity on 14/6/16.
 */
public abstract class OnSingleClickListener implements View.OnClickListener {

    private static final int DOUBLE_PRESS_INTERVAL = 500;//多次点击的时间间隔
    private static long lastPressTime;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPressTime >= DOUBLE_PRESS_INTERVAL) {
            onSingleClick(v);
        }
        lastPressTime = currentTime;
    }
}
