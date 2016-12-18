package com.forezp.banya.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;


public class ActivityCollector {


    private static Stack<Activity> activityStack;
    private static ActivityCollector instance;
    private ActivityCollector() {

    }

    public  void refreshAllActivity() {
        for (Activity activity : activityStack) {
            activity.recreate();
        }
    }


    public static ActivityCollector getInstance() {
        if (instance == null) {
            instance = new ActivityCollector();
        }
        return instance;
    }


    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }


    public  void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }



    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }


    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }


    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }


    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }


    public void AppExit(Context context) {
        try {
            finishAllActivity();

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }



}