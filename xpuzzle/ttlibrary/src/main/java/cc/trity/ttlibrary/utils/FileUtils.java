package cc.trity.ttlibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by trity on 29/6/16.
 */
public class FileUtils {
    protected static final String TAG="FileUtils";

    public static final int DEFAULT_COMPRESS=80;//默认压缩的质量

    /**
     * 检查是否挂载SDCard
     *
     * @return
     */
    public static boolean isSdCardMounted() {
        String status = Environment.getExternalStorageState();
        return StringHelper.isEquals(status, Environment.MEDIA_MOUNTED);
    }
    /**
     * 获取存储目录
     * @param context
     * @param dirName
     * @return 当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取路径，
     * 否则就调用getCacheDir()方法来获取缓存路径。
     * 前者获取到的就是 /sdcard/Android/data/<application package>/ 这个路径，
     * 而后者获取到的是 /data/data/<application package>/cache 这个路径。
     */
    public static File getStorageDirectory(Context context, String dirName) {
        File file;
        if (isSdCardMounted()) {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + dirName);
        } else {
            file = new File(context.getCacheDir() + File.separator + dirName);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 压缩图片
     *
     * @return
     */
    public static File saveBitmapFile(Bitmap bitmap, File file) {
        if (bitmap != null) {
            try {
                FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, DEFAULT_COMPRESS, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                Log.e(TAG,"failed to seveBitmapFile", e);
            }
        }
        return file;
    }

    /**
     * 获取目录大小
     *
     * @param dir
     * @return
     */
    public static long getFolderSize(File dir) {
        // check
        if (dir == null || !dir.exists()) {
            return 0L;
        }
        // is folder
        if (dir.isDirectory()) {
            long dirSize = 0L;
            File[] files = dir.listFiles();
            if (files == null) {
                return dirSize;
            }
            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getFolderSize(file); // 递归调用继续统计
                }
            }
            return dirSize;
        }
        // is file
        return dir.length();
    }

    /**
     * 删除目录中的所有文件
     *
     * @param file
     */
    public static boolean deleteFiles(File file) {
        // check
        if (file == null) {
            return false;
        } else if (!file.exists()) {
            return true;
        }
        // delete all
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (child.isFile()) {
                    child.delete();
                } else if (child.isDirectory()) {
                    deleteFiles(child);
                }
            }
        }
        return file.delete();
    }
}
