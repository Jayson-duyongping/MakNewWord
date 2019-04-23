package com.mak.newword.show.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.utils.CommonUtil;
import com.mak.newword.utils.LogUtil;
import com.mak.newword.utils.NetUtils;
import com.mak.newword.utils.SDCardUtil;
import com.mak.newword.utils.ToastUtils;
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
        //不需要插件化就直接这样初始化
        FileDownloader.setup(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //暂停所有下载
        FileDownloader.getImpl().pauseAll();
    }

    @OnClick({R.id.check_version_tv, R.id.down_version_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_version_tv:
                downApk();
                break;
            case R.id.down_version_tv:
                downFile();
                break;
        }
    }

    /**
     * FileDownloader框架下载文件
     */
    private void downFile() {
        if(!NetUtils.isWifi(mContext)){
            ToastUtils.show("文件较大，请在WIFI环境下载");
            return;
        }
        String apkPath = SDCardUtil.getDirector("/NewWord/word_apk") + File.separator + "镇魂曲.apk";
        File apkFile = new File(apkPath);
        if (apkFile.exists()) {
            LogUtil.d(TAG, "apk存在，去安装");
            return;
        }
        //文档地址：https://github.com/lingochamp/FileDownloader/blob/master/README-zh.md
        FileDownloader.getImpl().create("https://open.game.163.com/web-download-apk/?gameId=ga8a49e80b6a1fb302016a34bd34a20d7b")
                .setPath(apkPath)
                .setForceReDownload(true)
                //因为下载的apk可能大于1.99G，所以用FileDownloadLargeFileListener
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        LogUtil.d(TAG, "等待pending..." + soFarBytes + "," + totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        int percent = (int) ((soFarBytes / 1000) * 100 / (totalBytes / 1000));
                        LogUtil.d(TAG, "进度..." + soFarBytes + "-" + totalBytes + "-" + percent + "%");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        LogUtil.d(TAG, "暂停..." + soFarBytes + "," + totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.d(TAG, "完成下载，去安装");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.d(TAG, "下载出错");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        LogUtil.d(TAG, "已存在相同下载");
                    }
                }).start();
    }

    /**
     * AppUpdate框架下载更新apk
     */
    private void downApk() {
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
