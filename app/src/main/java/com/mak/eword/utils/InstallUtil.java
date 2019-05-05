package com.mak.eword.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * Created by jayson on 2019/4/23.
 * Content:安装apk工具类
 */
public class InstallUtil {
    public static final int RequestCode = 10012;
    public static final String AUTHORITY_HOST = "com.mak.newword.fileprovider";

    /**
     * 安装apk
     *
     * @param activity 上下文
     * @param apkPath  安装文件路径
     * @param isResult 是否是result的返回
     */
    public static void installApk(Activity activity, String apkPath, boolean isResult) {
        if (TextUtils.isEmpty(apkPath)) {
            return;
        }
        File apkfile = new File(apkPath);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //参数1-上下文,参数2-Provider主机地址和配置文件中保持一致,参数3-共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(activity, AUTHORITY_HOST, apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            //兼容8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    if (isResult) {
                        return;
                    }
                    //开启设置安装未知来源应用权限界面
                    //加packageURI会一步到位
                    Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                    Intent intent2 = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    activity.startActivityForResult(intent2, RequestCode);
                    return;
                }
            }
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }

    /**
     * 判断应用是否已经安装（通过获取应用列表对比判断）
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean checkAppInstalled(Context context, String pkgName) {
        if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info == null || info.isEmpty())
            return false;
        for (int i = 0; i < info.size(); i++) {
            if (pkgName.equals(info.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }
}
