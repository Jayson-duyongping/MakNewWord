package com.mak.eword.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by jayson on 2019/4/22.
 * Content: SD存储文件工具类
 */
public class SDCardUtil {
    /**
     * 设置目录并创建文件
     *
     * @param directorPath 目录地址 eg:/SpeedVpn/game_apk
     * @param filePath     文件名字 eg：qaqgame.apk
     * @return 这个文件
     */
    public static File getDirectorAndFile(String directorPath, String filePath) {
        File apkFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            String dirPath = getRootDir() + directorPath;
            //目录
            File file = new File(dirPath);
            //判断文件目录是否存在
            if (!file.exists()) {
                file.mkdirs();
            }
            apkFile = new File(dirPath, filePath);
            try {
                if (!apkFile.exists()) {
                    apkFile.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return apkFile;
    }

    /**
     * 设置目录
     *
     * @param directorPath 目录地址 eg:/SpeedVpn/game_apk
     * @return 这个目录
     */
    public static String getDirector(String directorPath) {
        String apkPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            apkPath = getRootDir() + directorPath;
            //目录
            File file = new File(apkPath);
            //判断文件目录是否存在
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取根目录下公共目录比如Environment.DIRECTORY_DOWNLOADS，Environment.DIRECTORY_PICTURES
     *
     * @param fileType "/mnt/sdcard/Downloads","/mnt/sdcard/Pictures"
     * @return
     */
    public static String getPublicRootDir(String fileType) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取跟目录
     *
     * @return
     */
    public static String getRootDir() {
        // 获取跟目录/mnt/sdcard/
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    /**
     * 获取对应包下面的cache地址
     *
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        //路径"data/data/packagename/cache"
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取对应包下面的files地址
     *
     * @param context
     * @return
     */
    public static String getFilesDir(Context context) {
        //路径"data/data/packagename/files"
        return context.getFilesDir().getAbsolutePath();
    }
}
