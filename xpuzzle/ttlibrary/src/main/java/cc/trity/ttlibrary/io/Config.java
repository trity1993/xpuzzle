package cc.trity.ttlibrary.io;

import android.content.Context;

import java.io.File;

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
    private static Context context = BaseApplication.getContext();

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
}
