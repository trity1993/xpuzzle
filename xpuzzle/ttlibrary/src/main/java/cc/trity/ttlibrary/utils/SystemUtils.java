package cc.trity.ttlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 获取应用系统的相关信息
 * Created by trity on 24/6/16.
 */
public class SystemUtils {

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取App安装包信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("程序包名无法找到", e);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 获取当前程序版本名称
     */
    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取程序版本名称
     */
    public static String getVersionName(Context context, String packageName) {
        String versionName;
        try {
            // Get the package info
            PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("获取程序版本名称", e);
            return "";
        }
        return versionName;
    }

    /**
     * 获取当前代码版本号
     */
    public static int getVersionCode(Context context) {
        int localVersion = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            localVersion = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("获取当前代码版本号", e);
        }
        return localVersion;
    }
    /**
     * 获取设备唯一码
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
