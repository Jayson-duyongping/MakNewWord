package com.jayson.commonlib.mvp.listener;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by duyongping on 2018/8/13.
 * Content:判断App是否在前台后台（Application生命周期监听）
 * 下面这个工具类原理是通过统计所有Activity回调onStart和onStop的次数关系，调用最后面的两个static方法即可判断App是处于前台还是后台
 */

public class LifeHandler implements Application.ActivityLifecycleCallbacks {
    private static boolean isAppInForeground;
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    public LifeHandler() {
        resumed = 0;
        paused = 0;
        started = 0;
        stopped = 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private static boolean isAppShowFromBackground() {
        return started == stopped;
    }

    //外部调用
    public static boolean isAppInForeground() {
        return isAppInForeground;
    }

    public static boolean isAppInBackground() {
        return started == stopped;
    }

}
