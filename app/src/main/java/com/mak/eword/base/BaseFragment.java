package com.mak.eword.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T> extends RxFragment {
    public String TAG = "";

    private Unbinder unBinder;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("aaa", "tag" + TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("aaa", "onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(getLayout(), container, false);
        //绑定ButterKnife
        unBinder = ButterKnife.bind(this, fragmentView);
        initView();
        initData();
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null) {
            unBinder.unbind();
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

    /**
     * 设置布局
     */
    abstract protected int getLayout();

    /**
     * 设置界面
     */
    abstract protected void initView();

    /**
     * 初始化数据
     */
    abstract protected void initData();


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
