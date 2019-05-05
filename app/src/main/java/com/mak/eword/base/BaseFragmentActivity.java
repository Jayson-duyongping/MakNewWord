package com.mak.eword.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewConfiguration;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jayson.commonlib.mvp.listener.LifeCycleListener;
import com.mak.eword.R;
import com.mak.eword.application.AppManager;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 基类Activity
 * 备注:所有的Activity都继承自此Activity
 * 1.规范团队开发
 * 2.统一处理Activity所需配置,初始化
 *
 * @author ZhongDaFeng
 */
public abstract class BaseFragmentActivity<T> extends RxFragmentActivity implements EasyPermissions.PermissionCallbacks {

    protected Context mContext;
    protected Unbinder unBinder;

    public static boolean isForeground = false;
    //沉浸式控件(https://github.com/gyf-dev/ImmersionBar)
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setOverflowShowingAlways();
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        ActivityStackManager.getManager().push(this);
        setContentView(getContentViewId());
        //沉浸初始化 0表示默认样式
        mImmersionBar = ImmersionBar.with(this);
        mStatusBar(0);

        mContext = this;
        unBinder = ButterKnife.bind(this);
        initView();
        initData();
        AppManager.getAppManager().addActivity(this);
    }

    /**
     * 自己设置状态栏颜色,传0表示透明度沉浸
     *
     * @param color
     */
    protected void mStatusBar(int color) {
        if (color == 0) {
            //判断当前设备支不支持状态栏字体设置为黑色
            if (!mImmersionBar.isSupportStatusBarDarkFont()) {
                mImmersionBar
                        .statusBarDarkFont(false, 0f)//当白色背景状态栏遇到不能改变状态栏字体为深色的设备时，解决方案
                        .fitsSystemWindows(false)
                        .statusBarColor(R.color.transparent)
                        .init();
                return;
            }
            mImmersionBar
                    .fitsSystemWindows(false)
                    .statusBarColor(R.color.transparent)
                    .statusBarDarkFont(false)
                    .init();
        } else {
            if (!mImmersionBar.isSupportStatusBarDarkFont()) {
                mImmersionBar
                        .statusBarDarkFont(true, 0.5f)//当白色背景状态栏遇到不能改变状态栏字体为深色的设备时，解决方案
                        .fitsSystemWindows(true)
                        .statusBarColor(color)
                        .init();
                return;
            }
            mImmersionBar
                    .fitsSystemWindows(true)
                    .statusBarColor(color)
                    .statusBarDarkFont(true)
                    .init();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
        //必须调用该方法，防止内存泄漏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        AppManager.getAppManager().finishActivity(this);
        //ActivityStackManager.getManager().remove(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }

    /**
     * 获取显示view的xml文件ID
     */
    protected abstract int getContentViewId();

    /**
     * 初始化应用程序,设置界面
     */
    protected abstract void initView();

    /**
     * 初始化数据，传递数据等等
     */
    protected abstract void initData();


    /**
     * 回调函数
     */
    public LifeCycleListener mListener;

    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }

    /**
     * 设置总是显示溢出菜单
     *
     * @return void
     * @date 2015-7-25 12:01:31
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected RequestBody setRequestBody(T entity) {
        Gson gson = new Gson();
        String route = gson.toJson(entity);
        Log.d("TAG", "参数：" + route);
        //调用接口
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), route);
        return body;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (null == context)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}