package cc.trity.ttlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by trity on 24/6/16.
 */
public class BitmapUtils {
    /**
     *  通过matrix尽心计算来设置缩放比例
     *  再创建新的bitmap来设置bitmap的宽高
     *  return 新的bitmap
     */
    public static Bitmap setSize(Bitmap bitmap,int newWidth,int newHeight){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
    }
}
