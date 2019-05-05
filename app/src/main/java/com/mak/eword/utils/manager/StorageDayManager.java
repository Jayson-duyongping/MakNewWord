package com.mak.eword.utils.manager;

import android.content.Context;
import android.text.TextUtils;

import com.mak.eword.utils.DateUtils;
import com.mak.eword.utils.SharedPreHelper;

/**
 * Created by jayson on 2019/4/10.
 * Content: 存储当日记录的管理类
 */
public class StorageDayManager {

    private static StorageDayManager mInstance;
    private SharedPreHelper sharedPreHelper;

    public StorageDayManager(Context context) {
        this.sharedPreHelper = SharedPreHelper.getInstance(context);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static StorageDayManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (StorageDayManager.class) {
                if (mInstance == null) {
                    mInstance = new StorageDayManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 存一个当日记忆数到本地
     */
    public void handlerDayNumber(String shareId) {
        String shareCountStr = (String) sharedPreHelper
                .getSharedPreference(shareId, "");
        long currentTime = System.currentTimeMillis();
        String currentDay = DateUtils.convertToString(DateUtils.FORMAT_YYYYMMDD, currentTime);
        String newShare = "";
        if (!TextUtils.isEmpty(shareCountStr)
                && shareCountStr.contains("_")) {
            String[] towStr = shareCountStr.split("_");
            if (towStr[0].equals(currentDay)) {
                //等于当天+1
                int num = Integer.parseInt(towStr[1]);
                num += 1;
                newShare = towStr[0] + "_" + num;
            } else {
                //新的一天，重新开始计算
                newShare = currentDay + "_" + 1;
            }
        } else {
            //新的一天，重新开始计算
            newShare = currentDay + "_" + 1;
        }
        sharedPreHelper.put(shareId, newShare);
    }

    /**
     * 获取当天记忆数
     *
     * @return
     */
    public int getRememberNum(String shareId) {
        int num = 0;
        String shareCountStr = (String) sharedPreHelper
                .getSharedPreference(shareId, "");
        long currentTime = System.currentTimeMillis();
        String currentDay = DateUtils.convertToString(DateUtils.FORMAT_YYYYMMDD, currentTime);
        if (!TextUtils.isEmpty(shareCountStr)
                && shareCountStr.contains("_")) {
            String[] towStr = shareCountStr.split("_");
            if (towStr[0].equals(currentDay)) {
                //等于当天+1
                num = Integer.parseInt(towStr[1]);
            } else {
                //新的一天，重新开始计算
                num = 0;
            }
        } else {
            //新的一天，重新开始计算
            num = 0;
        }
        return num;
    }
}
