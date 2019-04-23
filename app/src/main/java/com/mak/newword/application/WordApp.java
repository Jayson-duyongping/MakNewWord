package com.mak.newword.application;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.jayson.commonlib.widget.smartrefresh.SmartRefreshLayout;
import com.jayson.commonlib.widget.smartrefresh.api.DefaultRefreshFooterCreator;
import com.jayson.commonlib.widget.smartrefresh.api.DefaultRefreshHeaderCreator;
import com.jayson.commonlib.widget.smartrefresh.api.RefreshFooter;
import com.jayson.commonlib.widget.smartrefresh.api.RefreshHeader;
import com.jayson.commonlib.widget.smartrefresh.api.RefreshLayout;
import com.jayson.commonlib.widget.smartrefresh.footer.ClassicsFooter;
import com.jayson.commonlib.widget.smartrefresh.header.ClassicsHeader;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.mak.newword.R;
import com.mak.newword.greendao.service.UserService;
import com.mak.newword.mvp.model.UserBean;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.tinker.entry.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 创建人：jayson
 * 创建时间：2019/4/7
 * 创建内容：
 */

public class WordApp extends Application {
    public static WordApp instance;

    public static Context context;

    private ApplicationLike tinkerApplicationLike;

    /**
     * 获取渠道号
     * 360加固后会擦出渠道信息，需要使用ProtectedApkResignerForWalle再次打包
     *
     * @return
     */
    public static String getChannel() {
        return WalleChannelReader.getChannel(context);
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        /**
         * 我们可以从这里获得Tinker加载过程的信息
         */
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setFetchPatchIntervalByHours(1);

        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)
        // 去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
        //new FetchPatchHandler().fetchPatchWithInterval(3);
        /**
         * MAK - AVOSCloud初始化-参数依次为 this, AppId, AppKey
         */
        AVOSCloud.initialize(this, "4maGo9tHHbDdlPGLd8jwxkh8-gzGzoHsz", "VQnIYDvroOtYcxLrPD7oILuV");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserBean getUser() {
        UserBean userBean;
        long count = UserService.getInstance(getApplicationContext()).queryUserListCount();
        if (count == 0) {
            //新建一个用户
            userBean = new UserBean();
            userBean.setName("英语学习者");
            userBean.setRecordDayNum(0);
            userBean.setRememberDayNum(0);
            userBean.setRecordTotalNum(50);
            userBean.setRememberTotalNum(30);
            UserService.getInstance(getApplicationContext()).insertUser(userBean);
        } else {
            //获取数据库的用户
            List<UserBean> users = UserService.getInstance(getApplicationContext()).queryUserList();
            userBean = users.get(0);
        }
        return userBean;
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
