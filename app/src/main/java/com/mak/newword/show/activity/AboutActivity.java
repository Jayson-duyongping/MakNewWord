package com.mak.newword.show.activity;


import android.view.View;
import android.widget.TextView;

import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.utils.CommonUtil;
import com.mak.newword.utils.ToastUtils;
import com.mak.newword.widget.HeaderView;
import com.mak.newword.widget.RoundImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseFragmentActivity {
    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.app_icon_iv)
    RoundImageView appIconIv;
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.check_version_tv)
    TextView checkVersionTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
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
        appIconIv.setType(RoundImageView.TYPE_ROUND);
        versionTv.setText("版本号 V " + CommonUtil.getVersionName(this));
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.check_version_tv)
    public void onViewClicked() {
        ToastUtils.showToast(mContext, "已是最新版本");
    }
}
