package cc.trity.ttlibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by trity on 24/6/16.
 */
public class BitmapUtils {
    protected static final String TAG = "BitmapUtils";

    /**
     * 通过matrix尽心计算来设置缩放比例
     * 再创建新的bitmap来设置bitmap的宽高
     * return 新的bitmap
     */
    public static Bitmap setSize(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotatingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap reAngleBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return reAngleBitmap;
    }

    /**
     * 压缩Bitmap的大小
     *
     * @param imagePath     图片文件路径
     * @param requestWidth  压缩到想要的宽度
     * @param requestHeight 压缩到想要的高度
     * @return
     */
    public static Bitmap decodeBitmapFromFile(String imagePath, int requestWidth, int requestHeight) {
        if (!StringHelper.isEmpty(imagePath)) {
            if (requestWidth <= 0 || requestHeight <= 0) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                return bitmap;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//不加载图片到内存，仅获得图片宽高
            BitmapFactory.decodeFile(imagePath, options);
            if(options.outHeight==-1||options.outWidth==-1){//图片的宽高信息都丢失了的情况下
                try {
                    ExifInterface e = new ExifInterface(imagePath);
                    int height = e.getAttributeInt("ImageLength", 1);
                    int width = e.getAttributeInt("ImageWidth", 1);
                    Log.i(TAG, "exif height: " + height);
                    Log.i(TAG, "exif width: " + width);
                    options.outWidth = width;
                    options.outHeight = height;
                } catch (IOException var7) {
                    var7.printStackTrace();
                }
            }
            options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight); //计算获取新的采样率
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(imagePath, options);

        } else {
            return null;
        }
    }

    /**
     * 计算压缩比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            long totalPixels = width * height / inSampleSize;

            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 将path路径上的文件转为bitmap
     *
     * @param context
     * @param pathImg
     * @return
     */
    public static Bitmap pathToBitmap(Context context, Uri pathImg) {
        Bitmap bitmap = null;
        try {
            if (pathImg != null) {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), pathImg);
            }
        } catch (IOException e) {
            Log.e(TAG, "failed to Media.getBitmap when uriToBitmap", e);
            return null;
        }
        return bitmap;
    }
    public static Bitmap pathToBitmap(Context context, String pathImg) {
        return pathToBitmap(context,Uri.parse(pathImg));
    }
    public static Bitmap pathToBitmap(Context context, File pathImg) {
        return pathToBitmap(context,Uri.fromFile(pathImg));
    }

    public static File bitmapToPath(Bitmap bitmp,File path){
        return FileUtils.saveBitmapFile(bitmp,path);
    }
}
