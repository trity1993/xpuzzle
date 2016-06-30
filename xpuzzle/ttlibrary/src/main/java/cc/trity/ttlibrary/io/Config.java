package cc.trity.ttlibrary.io;

import android.content.Context;

import java.io.File;
import java.text.DecimalFormat;

import cc.trity.ttlibrary.BaseApplication;
import cc.trity.ttlibrary.utils.FileUtils;

/**
 * 相当于配置文件的相关信息，如：返回路径名等
 * Created by trity on 29/6/16.
 */
public class Config {
    /**
     * 数据目录
     */
    public static String DATA_PATH = "trity";//到实际的项目中需要通过gradle进行设置

    /**
     * 图片目录
     */
    public static String IMAGES_PATH = "images";

    /**
     * 图片缓存目录 ImageLoader
     */
    public static String IMAGE_CACHE_PATH = "imageCache";


    /**
     * 临时目录名称
     */
    public final static String APP_TEMP_PATH = "temp";

    /**
     * 日志文件目录名称
     */
    public final static String APP_LOG_PATH = "logs";

    /**
     * context
     */
    public static Context context = BaseApplication.getContext();

    /**
     * 获取app数据目录
     * @return
     */
    public static File getDataPath() {
        return FileUtils.getStorageDirectory(context, DATA_PATH);
    }

    /**
     * 获取程序图片目录
     * @return
     */
    public static File getImagePath() {
        return FileUtils.getStorageDirectory(context, DATA_PATH + File.separator + IMAGES_PATH);
    }

    /**
     * 获取程序图片缓存目录
     * @return
     */
    public static File getImageCachePath() {
        return FileUtils.getStorageDirectory(context, DATA_PATH + File.separator + IMAGE_CACHE_PATH);
    }

    /**
     * 获取日志目录
     * @return
     */
    public static File getLogPath() {
        return FileUtils.getStorageDirectory(context, DATA_PATH + File.separator + APP_LOG_PATH);
    }

    /**
     * 获取程序临时目录
     * @return
     */
    public static File getTempPath() {
        return FileUtils.getStorageDirectory(context, DATA_PATH + File.separator + APP_TEMP_PATH);
    }

    /**
     * 清空所有app数据
     * 分两部分：默认app的cache+自定义app的cache文件
     */
    public static void clearData() {
        File cacheFile = context.getCacheDir();
        if (cacheFile != null) {
            FileUtils.deleteFiles(cacheFile);
        }
        FileUtils.deleteFiles(getDataPath());
    }

    /**
     * 获取目录的所有大小。即：缓存大小
     *
     * @return
     */
    public static String getDataSize() {
        return formatSize(FileUtils.getFolderSize(getDataPath()));
    }

    /**
     * Formats given size in bytes to KB, MB, GB or whatever. This will work up to 1000 TB
     *  格式化为B,KB,MB等
     * @param size
     * @return
     */
    public static String formatSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
