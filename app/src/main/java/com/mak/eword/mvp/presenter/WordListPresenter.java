package com.mak.eword.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jayson.commonlib.mvp.http.exception.ApiException;
import com.jayson.commonlib.mvp.http.observer.HttpRxObservable;
import com.jayson.commonlib.mvp.http.observer.HttpRxObserver;
import com.mak.eword.base.BasePresenter;
import com.mak.eword.mvp.api.ApiUtils;
import com.mak.eword.mvp.inface.IWordListView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.WordListBean;
import com.mak.eword.mvp.model.WordBean;
import com.mak.eword.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 单词计划Presenter
 */

public class WordListPresenter extends BasePresenter<IWordListView, Activity> {

    private final String TAG = WordListPresenter.class.getSimpleName();

    public WordListPresenter(IWordListView view, Activity activity) {
        super(view, activity);
    }

    /**
     * 单词列表
     */
    public void getWordList(final Context context, final int page, final int page_size) {
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
                    ToastUtils.show(e.getMsg());
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
                    WordListBean commonBean = new Gson().fromJson(response.toString(), WordListBean.class);
                    if (getView() != null) {
                        getView().closeLoading();
                        //ToastUtils.show(commonBean.getMsg());
                        List<WordBean> list = commonBean.getList();
                        getView().showWordListResult(list);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.show("解析错误");
                    if (getView() != null) {
                        getView().closeLoading();
                        getView().showToast(null);
                    }
                }
            }
        };

        /**
         * 切入后台移除RxJava监听
         * ActivityEvent.PAUSE(FragmentEvent.PAUSE)
         * 手动管理移除RxJava监听,如果不设置此参数默认自动管理移除RxJava监听（onCrete创建,onDestroy移除）
         */

        HttpRxObservable.getObservable(ApiUtils.getretrofitApi().getWordList(page, page_size),
                (LifecycleProvider<ActivityEvent>) context, ActivityEvent.PAUSE).subscribe(httpRxObserver);
    }
}
