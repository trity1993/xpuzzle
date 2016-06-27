package cc.trity.ttlibrary.core;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.Map;

import cc.trity.ttlibrary.utils.StringHelper;

/**
 * 一个工具类，可以用来跟踪程序效率问题
 * 目前暂时支持多线程，如果没有end时，会有LRU替换掉
 *
 * @author Tony <6208317@qq.com>
 */
public class Benchmark {
    public static final String TAG="Benchmark";
    private static final Map<String, BenchEntry> mBenchStack = new LinkedHashMap<>(100);

    private Benchmark() {
    }

    /**
     * 开始标记
     *
     * @param tag
     */
    public static void start(String tag) {
        if (StringHelper.isEmpty(tag)) {
            Log.w(TAG,"tag is null");
            return;
        }
        BenchEntry entry = new BenchEntry();
        entry.tag = tag;
        entry.depth = mBenchStack.size();
        entry.start = System.currentTimeMillis();
        mBenchStack.put(tag, entry);
    }

    /**
     * 结束标记
     *
     * @param tag
     */
    public static void end(String tag) {
        if (StringHelper.isEmpty(tag)) {
            Log.w(TAG, "end: tag is null");
            return;
        }
        BenchEntry entry = mBenchStack.get(tag);
        if (entry == null) {
            Log.w(TAG,"Benchmark Not Match, tag spell mistake or forgot Benchmark.end(tag) invoke at somewhere ??");
            return;
        }
        entry.end = System.currentTimeMillis();

        long used = entry.end - entry.start;
        Log.d(TAG,"Benchmark [ " + entry.tag + " ] - Used: " + (used) + " ms. ");

        mBenchStack.remove(tag);
    }

    private static class BenchEntry {
        public long depth;
        public long start;
        public long end;
        public String tag;
    }

}
