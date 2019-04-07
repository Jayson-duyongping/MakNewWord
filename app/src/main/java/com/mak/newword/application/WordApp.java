package com.mak.newword.application;

import android.app.Application;
import android.content.Context;

import com.jayson.commonlib.widget.smartrefresh.SmartRefreshLayout;
import com.jayson.commonlib.widget.smartrefresh.api.DefaultRefreshFooterCreator;
import com.jayson.commonlib.widget.smartrefresh.api.DefaultRefreshHeaderCreator;
import com.jayson.commonlib.widget.smartrefresh.api.RefreshFooter;
import com.jayson.commonlib.widget.smartrefresh.api.RefreshHeader;
import com.jayson.commonlib.widget.smartrefresh.api.RefreshLayout;
import com.jayson.commonlib.widget.smartrefresh.footer.ClassicsFooter;
import com.jayson.commonlib.widget.smartrefresh.header.ClassicsHeader;
import com.mak.newword.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 创建人：jayson
 * 创建时间：2019/4/7
 * 创建内容：
 */

public class WordApp extends Application {
    public static WordApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 默认设置刷新框架的主题
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent, android.R.color.darker_gray);//全局设置主题颜色
                //指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context)
                        .setTextSizeTitle(14)//设置标题文字大小
                        .setTextSizeTime(12)//设置时间文字大小
                        .setDrawableSize(20)
                        .setDrawableMarginRight(10)//设置图片和箭头和文字的间距（dp单位）
                        .setTimeFormat(new SimpleDateFormat("更新于 yyyy-MM-dd HH:mm", Locale.CHINA));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent, android.R.color.darker_gray);//全局设置主题颜色
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context)
                        .setTextSizeTitle(14)//设置标题文字大小
                        .setDrawableSize(20)
                        .setDrawableMarginRight(10);//设置图片和箭头和文字的间距（dp单位）
            }
        });
    }
}
