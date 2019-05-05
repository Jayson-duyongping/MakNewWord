package com.mak.eword.show.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mak.eword.R;
import com.mak.eword.application.AppDownloadManager;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.utils.CommonUtil;
import com.mak.eword.utils.InstallUtil;
import com.mak.eword.utils.NetUtils;
import com.mak.eword.utils.SDCardUtil;
import com.mak.eword.utils.ToastUtils;
import com.mak.eword.widget.CommomUpdateDialog;
import com.mak.eword.widget.HeaderView;
import com.mak.eword.widget.RoundImageView;

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
    //更新弹框
    CommomUpdateDialog updateDialog;

    private Activity activity;
    //需要安装的apk路径
    private String globalApkPath;

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
        activity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //暂停所有下载
        AppDownloadManager.instance.pauseAll();
    }

    @OnClick({R.id.check_version_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_version_tv:
                showDownDialog();
                break;
        }
    }

    /**
     * 弹出更新提示框
     */
    public void showDownDialog() {
        if (updateDialog != null && updateDialog.isShowing()) {
            return;
        }
        updateDialog = new CommomUpdateDialog(mContext,
                R.style.commonDialog,
                "发现新版本1.0.3", "1.修复了已知bug\n2.完善了apk的下载功能\n3.应用体验性更好",
                new CommomUpdateDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            downFile();
                        }
                    }
                });
        updateDialog.show();
    }

    /**
     * 取消更新提示框
     */
    public void dismissDownDialog() {
        if (updateDialog != null && updateDialog.isShowing()) {
            updateDialog.dismiss();
        }
    }

    /**
     * FileDownloader框架下载文件
     */
    private void downFile() {
        if (!NetUtils.isWifi(mContext)) {
            ToastUtils.show("文件较大，请在WIFI环境下载");
            return;
        }
        String apkPath = SDCardUtil.getDirector(AppDownloadManager.apkDirector) + File.separator + "QAQGame_1.0.3_official.apk";
        globalApkPath = apkPath;
        File apkFile = new File(apkPath);
        if (apkFile.exists()) {
            dismissDownDialog();
            //安装apk
            InstallUtil.installApk(activity, globalApkPath, false);
            return;
        }
        String apkUrl = "https://qaqgame.obs.myhwclouds.com/app/QAQGame_1.0.3_official.apk";
        AppDownloadManager.instance.startDown(apkUrl, apkPath, new AppDownloadManager.IDownStatus() {
            @Override
            public void onProgress(int percent) {
                if (updateDialog != null && updateDialog.isShowing()) {
                    updateDialog.setProgress(percent);
                }
            }

            @Override
            public void onCompleted() {
                dismissDownDialog();
                ToastUtils.show("下载完成");
                //安装apk
                InstallUtil.installApk(activity, globalApkPath, false);
            }

            @Override
            public void onError() {
                dismissDownDialog();
                ToastUtils.show("下载出错，请稍后重试");
            }
        });
    }

    /**
     * 处理安装apk的onActivityResult(针对8.0)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case InstallUtil.RequestCode:
                InstallUtil.installApk(activity, globalApkPath, true);
                break;
            default:
                break;
        }
    }
}
