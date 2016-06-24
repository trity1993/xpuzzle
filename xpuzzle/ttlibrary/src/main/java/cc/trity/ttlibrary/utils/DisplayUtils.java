package cc.trity.ttlibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 关于屏幕的转化：dp<->px sp<->px
 * 展示了TypedValue.applyDimension的使用，其本质是一样的
 * Created by trity on 12/6/16.
 */
public class DisplayUtils {
    /**
     * 将px值转换成dpi或者dp值，保持尺寸不变
     *
     * @param content
     * @param pxValus
     * @return
     */
    public static int pxToDp(Context content, float pxValus) {
        final float scale = content.getResources().getDisplayMetrics().density;
        return (int) (pxValus / scale + 0.5f);
    }

    /**
     * 将px转化成sp,保证文字大小不变。
     *
     * @param content
     * @param pxValus
     * @return
     */
    public static int pxToSp(Context content, float pxValus) {
        final float fontScale = content.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValus / fontScale + 0.5f);
    }
    /**
     * dp to px
     * @param dp
     * @param context
     * @return
     */
    public static int dpToPx(Context context, float dp) {
        return (int) applyDimension(context, TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    /**
     * sp to px
     * @param sp
     * @param context
     * @return
     */
    public static int spToPX(Context context,float sp){
        return (int) applyDimension(context, TypedValue.COMPLEX_UNIT_SP, sp);
    }

    /**
     * 单位转换,详情看TypedValue.applyDimension的源码
     * @param context
     * @param unit    TypedValue.COMPLEX_UNIT_DIP/COMPLEX_UNIT_SP
     * @param value   dp/sp
     * @return
     */
    public static float applyDimension(Context context, int unit, float value) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        return TypedValue.applyDimension(unit, value, displayMetrics);
    }
}
