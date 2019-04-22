package com.mak.newword.show.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.utils.CommonUtil;
import com.mak.newword.utils.LogUtil;
import com.mak.newword.utils.SDCardUtil;
import com.mak.newword.utils.downapk.HProgressDialogUtils;
import com.mak.newword.utils.downapk.UpdateAppHttpUtil;
import com.mak.newword.widget.HeaderView;
import com.mak.newword.widget.RoundImageView;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.service.DownloadService;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseFragmentActivity {
    private static final String TAG = AboutActivity.class.getSimpleName();

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
        //ToastUtils.showToast(mContext, "已是最新版本");
        UpdateAppBean updateAppBean = new UpdateAppBean();
        //设置apk下载地址
        updateAppBean.setApkFileUrl("https://qaqgame.obs.myhwclouds.com/app/QAQGame_1.0.3_official.apk");
        //设置下载的目标地址 - /mnt/sdcard/NewWord/word_apk/qaqgame.apk
        updateAppBean.setTargetPath(SDCardUtil.getDirector("/NewWord/word_apk"));
        //这个要设置一下，因为作为下载路径的一部分，不然会创建一个null的目录
        updateAppBean.setNewVersion("1.0.3");
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new UpdateAppHttpUtil());
        //下载框架
        UpdateAppManager.download(mContext, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                LogUtil.d(TAG, "onStart() called");
                HProgressDialogUtils.showHorizontalProgressDialog(AboutActivity.this, "下载进度", false);
            }

            @Override
            public void onProgress(float progress, long totalSize) {
                HProgressDialogUtils.setProgress(Math.round(progress * 100));
                LogUtil.d(TAG, "onProgress() called with: progress = [" + progress + "], totalSize = [" + totalSize + "]");
            }

            @Override
            public void setMax(long totalSize) {
                LogUtil.d(TAG, "setMax() called with: totalSize = [" + totalSize + "]");
            }

            @Override
            public boolean onFinish(File file) {
                HProgressDialogUtils.cancel();
                LogUtil.d(TAG, "onFinish() called with: file = [" + file.getAbsolutePath() + "]");
                return true;
            }

            @Override
            public void onError(String msg) {
                HProgressDialogUtils.cancel();
                LogUtil.e(TAG, "onError() called with: msg = [" + msg + "]");
            }

            @Override
            public boolean onInstallAppAndAppOnForeground(File file) {
                LogUtil.d(TAG, "onInstallAppAndAppOnForeground() called with: file = [" + file + "]");
                return false;
            }
        });
    }
}
