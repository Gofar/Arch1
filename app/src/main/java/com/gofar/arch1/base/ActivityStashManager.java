package com.gofar.arch1.base;

import android.app.Activity;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Activity管理类
 *
 * @author lcf
 * @date 2017/4/21
 * @since 1.0
 */
public class ActivityStashManager {
    /**
     * 存放所有Activity的Map
     */
    private static final Map<String, List<ActivityInfo>> COUNTER = new HashMap<>();
    /**
     * 当前Activity
     */
    private static WeakReference<Activity> mCurrentActivity;
    /**
     * Activity 启动顺序列表
     */
    private static LinkedList<String> mActivityList = new LinkedList<>();

    /**
     * Activity创建时调用此方法
     *
     * @param activity
     */
    public static void onCreate(Activity activity) {
        if (activity == null) {
            return;
        }
        String activityName = activity.getClass().getName();
        List<ActivityInfo> activityInfoList = COUNTER.get(activityName);
        if (activityInfoList == null) {
            activityInfoList = new ArrayList<>();
            COUNTER.put(activityName, activityInfoList);
        }
        activityInfoList.add(new ActivityInfo(activity, mActivityList.pollLast()));
        mActivityList.add(activityName);
    }

    /**
     * Activity销毁时调用此方法
     *
     * @param activity
     */
    public static void onDestory(Activity activity) {
        String activityName = activity.getClass().getName();
        List<ActivityInfo> activityInfoList = COUNTER.get(activityName);
        if (activityInfoList != null) {
            Iterator<ActivityInfo> iterator = activityInfoList.iterator();
            while (iterator.hasNext()) {
                ActivityInfo item = iterator.next();
                Activity activity1 = item.referenceActivity.get();
                if (activity1 != null && activity1 == activity) {
                    iterator.remove();
                    break;
                }
            }
        }
        if (mCurrentActivity.get() != null && mCurrentActivity.get() == activity) {
            setCurrentActivity(null);
        }
    }

    /**
     * 关闭指定Activity
     *
     * @param clazz
     */
    public static void finishActivity(Class<? extends Activity> clazz) {
        finishActivity(clazz.getName());
    }

    /**
     * 关闭指定Activity
     *
     * @param activityName
     */
    public static void finishActivity(String activityName) {
        List<ActivityInfo> activityInfoList = COUNTER.get(activityName);
        if (activityInfoList != null && !activityInfoList.isEmpty()) {
            for (ActivityInfo info : activityInfoList) {
                if (info.referenceActivity != null && info.referenceActivity.get() != null) {
                    info.referenceActivity.get().finish();
                }
            }
        }
    }

    /**
     * 关闭所用Activity
     */
    public static void finishAll() {
        for (String key : COUNTER.keySet()) {
            finishActivity(key);
        }
        COUNTER.clear();
        mActivityList.clear();
    }

    /**
     * 判断是否有指定Activity
     *
     * @param activityName
     * @return
     */
    public static boolean hasActivityInStack(String activityName) {
        List<ActivityInfo> activityInfoList = COUNTER.get(activityName);
        return activityInfoList != null && !activityInfoList.isEmpty();
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity == null ? null : mCurrentActivity.get();
    }

    public static void setCurrentActivity(Activity activity) {
        ActivityStashManager.mCurrentActivity = new WeakReference<Activity>(activity);
    }

    /**
     * 根据activityName获取Activity
     *
     * @param activityName
     * @return
     */
    public static List<Activity> getActivity(String activityName) {
        List<ActivityInfo> activityInfoList = COUNTER.get(activityName);
        List<Activity> activityList = null;
        if (activityInfoList != null && !activityInfoList.isEmpty()) {
            activityList = new ArrayList<>();
            for (ActivityInfo info : activityInfoList) {
                if (info != null && info.referenceActivity.get() != null) {
                    activityList.add(info.referenceActivity.get());
                }
            }
        }
        return activityList;
    }

    /**
     * 根据activityName获取最后一个ActivityInfo
     *
     * @param activityName
     * @return
     */
    public static ActivityInfo getLastActivityInfo(String activityName) {
        List<ActivityInfo> activityInfoList = COUNTER.get(activityName);
        if (activityInfoList == null || activityInfoList.isEmpty()) {
            return null;
        }
        ActivityInfo activityInfo = activityInfoList.get(activityInfoList.size() - 1);
        if (activityInfo.lastActivityName == null) {
            return null;
        }
        activityInfoList = COUNTER.get(activityInfo.lastActivityName);
        if (activityInfo == null || activityInfoList.isEmpty()) {
            return null;
        }
        return activityInfoList.get(activityInfoList.size() - 1);
    }

    /**
     * 根据activityName获取最后一个Activity
     *
     * @param activityName
     * @return
     */
    public static Activity getLastActivity(String activityName) {
        int index = mActivityList.lastIndexOf(activityName);
        if (index > 0) {
            String previousActivity = mActivityList.get(index);
            List<Activity> activityList = getActivity(previousActivity);
            if (activityList != null && !activityList.isEmpty()) {
                return activityList.get(activityList.size() - 1);
            }
        }
        return null;
    }


    public static class ActivityInfo {
        public WeakReference<Activity> referenceActivity;
        public Bundle data;
        public Class<? extends Activity> clazz;
        public String lastActivityName;

        public ActivityInfo(Activity activity, String lastActivityName) {
            this.referenceActivity = new WeakReference<>(activity);
            this.data = activity.getIntent().getExtras();
            this.clazz = activity.getClass();
            this.lastActivityName = lastActivityName;
        }
    }
}
