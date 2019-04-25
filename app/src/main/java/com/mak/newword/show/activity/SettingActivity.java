package com.mak.newword.show.activity;

import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.utils.ToastUtils;
import com.mak.newword.widget.CommomDialog;
import com.mak.newword.widget.HeaderView;

import butterknife.BindView;
import butterknife.OnClick;

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
        headerView.setCenterText("关于我们");
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

    @OnClick({R.id.account_ll, R.id.clock_ll, R.id.quit_login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_ll:
                break;
            case R.id.clock_ll:
                break;
            case R.id.quit_login_tv:
                //到这里开始正式下载，弹框提示
                new CommomDialog(mContext, R.style.commonDialog, "确定要退出登录？",
                        new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    ToastUtils.show("退出登录");
                                }
                            }
                        }).show();
                break;
        }
    }
}
