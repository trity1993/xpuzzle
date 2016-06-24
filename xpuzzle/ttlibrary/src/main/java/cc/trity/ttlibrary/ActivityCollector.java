package cc.trity.ttlibrary;

import android.app.Activity;

import java.util.LinkedList;

/**进行对Activity的管理
 * Created by TryIT on 2015/12/12.
 */
public class ActivityCollector {
    private static final LinkedList<Activity> activityList=new LinkedList<>();

    public static void addActivity(Activity activity){
        if(!activityList.contains(activity))//不包含的情况再加入，负责测试会报错
            activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void finishAll(){
        for(Activity actTmp:activityList){
            if(!actTmp.isFinishing())
                actTmp.finish();
        }
//        System.exit(0);//最后直接退出虚拟机，停止所有的程序,server也停止
    }

    public static int getStackSize(){
        return activityList.size();
    }

}
