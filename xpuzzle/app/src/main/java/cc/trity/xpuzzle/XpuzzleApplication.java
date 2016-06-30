package cc.trity.xpuzzle;

import cc.trity.ttlibrary.*;
import cc.trity.ttlibrary.io.Config;

/**
 * Created by trity on 30/6/16.
 */
public class XpuzzleApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 初始化环境设置
     */
    private void initEnv(){
        Config.DATA_PATH= BuildConfig.DATA_PATH;//初始化文件夹
    }
}
