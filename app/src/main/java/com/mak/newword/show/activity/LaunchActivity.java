package com.mak.newword.show.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.show.mak.DownApkActivity;
import com.mak.newword.show.mak.MyWebViewActivity;
import com.mak.newword.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 简单启动页面
 */
public class LaunchActivity extends BaseFragmentActivity {

    private Handler mHandler = new Handler();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataByLeancloudCd();
            }
        }, 2000);
    }

    /**
     * MAK - 通过Leancloud控制
     */
    private void getDataByLeancloudCd() {
        AVQuery<AVObject> avQuery = new AVQuery<>("switch");
        avQuery.getInBackground("5ca38232a3180b0068a5b858", new GetCallback<AVObject>() {

            @Override
            public void done(AVObject avObject, AVException e) {
                if (avObject != null) {
                    if (avObject.getBoolean("openUrl")) {//开关判断，是否打开H5。如果为true
                        String wapurl = avObject.getString("url");  // webview加载H5
                        Intent intent = new Intent(mContext, MyWebViewActivity.class);
                        intent.putExtra("url", wapurl);
                        startActivity(intent);
                    } else if (avObject.getBoolean("openUp")) {//开关判断，是否打开。如果为true
                        String upUrl = avObject.getString("urlUp");  // 强更地址
                        Intent intent = new Intent(mContext, DownApkActivity.class);
                        intent.putExtra("url", upUrl);
                        startActivity(intent);
                    } else {
                        //走正常流程
                        goNormalWay();
                    }
                } else {
                    //走正常流程
                    goNormalWay();
                }
                finish();
            }
        });
    }

    private void goNormalWay() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
