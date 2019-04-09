package com.mak.newword.show.activity;

import android.content.Intent;
import android.os.Handler;

import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;

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
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
