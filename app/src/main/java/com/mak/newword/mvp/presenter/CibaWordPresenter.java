package com.mak.newword.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jayson.commonlib.mvp.http.exception.ApiException;
import com.jayson.commonlib.mvp.http.observer.HttpRxObservable;
import com.jayson.commonlib.mvp.http.observer.HttpRxObserver;
import com.mak.newword.base.BasePresenter;
import com.mak.newword.mvp.api.ApiUtils;
import com.mak.newword.mvp.inface.ICibaWordView;
import com.mak.newword.mvp.model.BaseErrorBean;
import com.mak.newword.mvp.model.CibaWordEnBean;
import com.mak.newword.mvp.model.CibaWordZhBean;
import com.mak.newword.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;


import io.reactivex.disposables.Disposable;

/**
 * 词霸单词Presenter
 */

public class CibaWordPresenter extends BasePresenter<ICibaWordView, Activity> {

    private final String TAG = CibaWordPresenter.class.getSimpleName();

    public CibaWordPresenter(ICibaWordView view, Activity activity) {
        super(view, activity);
    }

    /**
     * 用户信息
     */
    public void getCibaWordResult(final Context context, final String word) {
        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "getInfo") {

            @Override
            protected void onStart(Disposable d) {
                if (getView() != null) {
                    getView().showLoading();
                }
            }

            @Override
            protected void onError(ApiException e) {
                Log.d(TAG, "onError code:" + e.getStatus());
                if (e.getStatus() == -1) {
                    ToastUtils.showToast(context, e.getMsg());
                    if (getView() != null) {
                        getView().closeLoading();
                        getView().showToast(null);
                    }
                    return;
                }
                if (!TextUtils.isEmpty(e.getErrorString())) {
                    try {
                        BaseErrorBean errorBean = new Gson().fromJson(e.getErrorString(), BaseErrorBean.class);
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showToast(errorBean);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            protected void onSuccess(Object response) {
                Log.d(TAG, "onSuccess response:" + response.toString());
                try {
                    if (response.toString().contains("word_name")) {
                        if (response.toString().contains("\"exchange\":")) {
                            //说明是英语实体
                            CibaWordEnBean bean = new Gson().fromJson(response.toString(), CibaWordEnBean.class);
                            if (getView() != null) {
                                getView().closeLoading();
                                getView().showCibaWordEnResult(bean);
                            }
                        } else {
                            //说明是中文实体
                            CibaWordZhBean bean = new Gson().fromJson(response.toString(), CibaWordZhBean.class);
                            if (getView() != null) {
                                getView().closeLoading();
                                getView().showCibaWordZhResult(bean);
                            }
                        }
                    } else {
                        //word_name都不包含，这个词有问题
                        ToastUtils.showToast(context, "查询不到这个词");
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.showToast(context, "解析错误");
                }
            }
        };

        /**
         * 切入后台移除RxJava监听
         * ActivityEvent.PAUSE(FragmentEvent.PAUSE)
         * 手动管理移除RxJava监听,如果不设置此参数默认自动管理移除RxJava监听（onCrete创建,onDestroy移除）
         */

        HttpRxObservable.getObservable(ApiUtils.getretrofitApi().getQueryWord(word),
                (LifecycleProvider<ActivityEvent>) context, ActivityEvent.PAUSE).subscribe(httpRxObserver);
    }
}
