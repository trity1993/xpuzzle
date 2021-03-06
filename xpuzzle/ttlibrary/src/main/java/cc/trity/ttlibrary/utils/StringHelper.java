package cc.trity.ttlibrary.utils;

/**
 * Created by trity on 27/6/16.
 */
public class StringHelper {
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String... str) {
        if (str == null) {
            return true;
        }
        for (String s : str) {
            if (s == null || s.isEmpty() || s.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    /**
     * 判断是不是一样的
     */
    public static boolean isEquals(String src, String target) {
        if (isEmpty(src, target)) {
            return false;
        }
        return src.equals(target);
    }
}
