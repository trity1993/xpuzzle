package cc.trity.ttlibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cc.trity.ttlibrary.R;
import cc.trity.ttlibrary.helper.TipHelper;

/**
 * 处理拍照相关
 * http://www.jianshu.com/p/f269bcda335f#
 * Created by trity on 28/6/16.
 */
public class PhotoHelper {
    protected static final String TAG="PhotoHelper";
    public final static String EXTRA_RESTORE_PHOTO = "extra_restore_photo";

    public final static int REQUEST_CODE_PICK = 333;
    public final static int REQUEST_CODE_CAMERA = 666;
    public final static int REQUEST_CODE_CROP = 999;

    public static String imgPath;//为了在拍照闪退的情况下，仍然保存着路径

    /**
     * 拍照,并默认保存此照片的路径
     *
     * @param context
     * @param imagePath
     * @return
     */
    public static File takePhoto(Activity context, String imagePath) {
        if(hasCamera(context)){
            File tempFile = new File(imagePath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            intent.putExtra("noFaceDetection", true);
            PhotoHelper.imgPath=imagePath;
            context.setResult(Activity.RESULT_OK);
            context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
            return tempFile;
        }else{
            TipHelper.showToast(R.string.camera_error);
            return null;
        }
    }
    /**
     * 调用默认相册,挑选照片
     * 返回对应的onActivityResult的参数intent进行取值
     * intent.getData() 获取其图片的uri
     *
     * @param context
     */
    public static void pickPhoto(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("return-data", true);
        context.startActivityForResult(intent, REQUEST_CODE_PICK);
        context.setResult(Activity.RESULT_OK);
    }

    /**
     * 裁剪图片
     *  当path和output相同的时候，就不会有自动旋转的bug
     * @param context
     * @param path 源图file
     * @param output 裁剪后的输出路径
     * @param outputXY 最终的尺寸
     * @return
     */
    public static File cropPhoto(Activity context, File path, String output, int outputXY) {
        File tempFile = new File(output);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(path), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputXY);
        intent.putExtra("outputY", outputXY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, REQUEST_CODE_CROP);
        return tempFile;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Log.e(TAG,"failed to readPictureDegree", e);
        }
        return degree;
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasCamera(Activity context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
