package com.mak.eword.show.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mak.eword.R;
import com.mak.eword.application.WordApp;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.utils.SharedPreHelper;
import com.mak.eword.widget.CommomDialog;
import com.mak.eword.widget.HeaderView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置页面
 */
public class SettingActivity extends BaseFragmentActivity {

    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.account_ll)
    LinearLayout accountLl;
    @BindView(R.id.clock_ll)
    LinearLayout clockLl;
    @BindView(R.id.quit_login_tv)
    TextView quitLoginTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("设置");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.account_ll, R.id.clock_ll, R.id.message_ll, R.id.quit_login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_ll:
                break;
            case R.id.clock_ll:
                //跳转闹钟提醒页面
                Intent remindIntent = new Intent(mContext, ClockRemindActivity.class);
                startActivity(remindIntent);
                break;
            case R.id.message_ll:
                //跳转消息管理页面
                Intent messageIntent = new Intent(mContext, MessageManagerActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.quit_login_tv:
                //到这里开始正式下载，弹框提示
                new CommomDialog(mContext, R.style.commonDialog, "确定要退出登录？",
                        new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    //用户信息清除
                                    WordApp.user = null;
                                    SharedPreHelper.getInstance(mContext).saveBeanByFastJson(StringConstant.Share_User, null);
                                    //跳转到登录页面
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }).show();
                break;
        }
    }
}
