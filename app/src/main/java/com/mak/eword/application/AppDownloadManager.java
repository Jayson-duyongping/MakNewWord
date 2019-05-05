package com.mak.eword.application;

import android.content.Context;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.mak.eword.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jayson on 2019/4/24.
 * Content: app文件下载管理器
 * 文档地址：https://github.com/lingochamp/FileDownloader/blob/master/README-zh.md
 */
public class AppDownloadManager {
    public static final String TAG = AppDownloadManager.class.getSimpleName();

    public static final String apkDirector = "/NewWord/word_apk";

    public static AppDownloadManager instance;
    private Context context;
    //下载map-key下载的目录，value下载的信息
    public Map<String, DownInfo> downMap = new HashMap<>();

    //如果要app要用到下载的功能，最好再HomeActivity onCreate就初始化
    public AppDownloadManager(Context context) {
        this.context = context;
        instance = this;
        //不使用定制组件，直接调用则可初始化-下载管理器单例对象
        FileDownloader.setup(context);
    }

    public interface IDownStatus {
        void onProgress(int percent);

        void onCompleted();

        void onError();
    }

    /**
     * 下载逻辑
     *
     * @param url
     * @param apkPath
     */
    public void startDown(String url, String apkPath, final IDownStatus iDownStatus) {
        //
        FileDownloader.getImpl().create(url)
                .setPath(apkPath)
                .setForceReDownload(true)
                //因为下载的apk可能大于1.99G，所以用FileDownloadLargeFileListener
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        LogUtil.d(TAG, "等待pending..." + soFarBytes + "," + totalBytes);
                        DownInfo downInfo = downMap.get(task.getTargetFilePath());
                        //这个apk没有下载过
                        if (downInfo == null) {
                            downInfo = new DownInfo();
                            downInfo.setApkPath(task.getTargetFilePath());
                            downInfo.setDownUrl(task.getUrl());
                            downMap.put(task.getTargetFilePath(), downInfo);
                        }
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        int percent = (int) ((soFarBytes / 1000) * 100 / (totalBytes / 1000));
                        LogUtil.d(TAG, "进度..." + soFarBytes + "-" + totalBytes + "-" + percent + "%");

                        DownInfo downInfo = downMap.get(task.getTargetFilePath());
                        //这个apk没有下载过
                        if (downInfo == null) {
                            downInfo = new DownInfo();
                        }
                        downInfo.setDownLoad(true);
                        downInfo.setApkPath(task.getTargetFilePath());
                        downInfo.setPercentPro(percent);
                        downInfo.setDownUrl(task.getUrl());
                        downMap.put(task.getTargetFilePath(), downInfo);
                        //通知下载状态
                        //更新
                        iDownStatus.onProgress(percent);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        LogUtil.d(TAG, "暂停..." + soFarBytes + "," + totalBytes);
                        //暂停也要清空(清空下载map,因为只有一个)
                        downMap.clear();
                        //通知下载状态
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.d(TAG, "完成下载，去安装");
                        //下载完成(清空下载map,因为只有一个)
                        downMap.clear();
                        //通知下载状态
                        //更新
                        iDownStatus.onCompleted();
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.d(TAG, "下载出错");
                        //下载错误(清空下载map,因为只有一个)
                        downMap.clear();
                        //通知下载状态
                        //更新
                        iDownStatus.onError();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        LogUtil.d(TAG, "已存在相同下载");
                        //更新
                        iDownStatus.onError();
                    }
                }).start();
    }

    /**
     * 暂停所有下载
     */
    public void pauseAll() {
        //暂停所有下载
        FileDownloader.getImpl().pauseAll();
    }

    /**
     * 下载基本状态类
     */
    static class DownInfo {
        //是否在下载
        private boolean isDownLoad;
        //下载进度
        private int percentPro;
        //下载目录
        private String apkPath;
        //下地地址
        private String downUrl;

        public boolean isDownLoad() {
            return isDownLoad;
        }

        public void setDownLoad(boolean downLoad) {
            isDownLoad = downLoad;
        }

        public int getPercentPro() {
            return percentPro;
        }

        public void setPercentPro(int percentPro) {
            this.percentPro = percentPro;
        }

        public String getApkPath() {
            return apkPath;
        }

        public void setApkPath(String apkPath) {
            this.apkPath = apkPath;
        }

        public String getDownUrl() {
            return downUrl;
        }

        public void setDownUrl(String downUrl) {
            this.downUrl = downUrl;
        }
    }
}
