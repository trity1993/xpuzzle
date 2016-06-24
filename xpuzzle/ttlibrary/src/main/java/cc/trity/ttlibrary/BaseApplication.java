package cc.trity.ttlibrary;

import android.app.Application;
import android.content.Context;

/**
 * Created by trity on 23/6/16.
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
