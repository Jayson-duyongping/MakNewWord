package com.mak.eword.show.activity;

import android.content.Intent;
import android.os.Handler;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.mak.eword.HomeActivity;
import com.mak.eword.R;
import com.mak.eword.application.WordApp;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.mvp.model.UserBean;
import com.mak.eword.show.mak.DownApkActivity;
import com.mak.eword.show.mak.MyWebViewActivity;
import com.mak.eword.utils.SharedPreHelper;

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
                //TODO 这句代码测试用
                avObject = null;
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
        UserBean user = (UserBean) SharedPreHelper.getInstance(mContext)
                .getBeanByFastJson(StringConstant.Share_User, UserBean.class);
        if (user != null) {
            //跳转主页面
            WordApp.user = user;
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            //跳转登录页面
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
