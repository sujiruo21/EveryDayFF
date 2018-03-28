package com.zl.everydayff.util;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boom on 2017/7/2.
 * 统一管理类
 */

public class ActivityManagerUtil {

    private Map<String,Activity> activityMap;

    //static代表放在共享区里面只有一份
    public static ActivityManagerUtil mInstance;

    /**
     *  1.整个应用只有单个实例   new肯定不能让其他类实例化对象
     *  2.把构造函数私有化
     */
    private ActivityManagerUtil(){
        activityMap = new HashMap<>();
    }

    /**
     * 其他地方如果需要使用通过什么方式
     * synchronized  你要考虑到同步   线程并发问题
     */
    public static ActivityManagerUtil getInstance(){
        if (mInstance == null){
            synchronized (ActivityManagerUtil.class){
                mInstance = new ActivityManagerUtil();
            }
        }
        return mInstance;
    }

    /**
     *添加activity
     */
    public void addActivity(Activity activity){
        activityMap.put(activity.getClass().getName(),activity);
    }

    /**
     * 关闭 Activity
     */
    public void finishActivity(Activity activity){
        Activity finishActivity = activityMap.get(activity.getClass().getName());
        finishActivity.finish();
        activityMap.remove(activity.getClass().getName());
    }

    /**
     * 关闭 Activity
     */
    public void finishActivity(Class< ? extends Activity> activityClazz){
        Activity finishActivity = activityMap.get(activityClazz.getName());
        finishActivity.finish();
        activityMap.remove(activityClazz.getName());

    }
}
