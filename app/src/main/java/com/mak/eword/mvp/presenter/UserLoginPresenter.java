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
import com.mak.eword.mvp.inface.IUserLoginView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.UserBean;
import com.mak.eword.mvp.model.common.CommonBean;
import com.mak.eword.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * 用户登录Presenter
 */

public class UserLoginPresenter extends BasePresenter<IUserLoginView, Activity> {

    private final String TAG = UserLoginPresenter.class.getSimpleName();

    public UserLoginPresenter(IUserLoginView view, Activity activity) {
        super(view, activity);
    }

    /**
     * 用户登录信息
     */
    public void getUserLoginResult(final Context context, RequestBody body) {
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
                    CommonBean commonBean = new Gson().fromJson(response.toString(), CommonBean.class);
                    if (getView() != null) {
                        getView().closeLoading();
                        ToastUtils.show(commonBean.getMsg());
                        if (commonBean.getData() != null) {
                            UserBean userBean = new Gson().fromJson(commonBean.getData().toString(), UserBean.class);
                            getView().showUserLoginResult(userBean);
                        }
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.show("解析错误");
                }
            }
        };

        /**
         * 切入后台移除RxJava监听
         * ActivityEvent.PAUSE(FragmentEvent.PAUSE)
         * 手动管理移除RxJava监听,如果不设置此参数默认自动管理移除RxJava监听（onCrete创建,onDestroy移除）
         */

        HttpRxObservable.getObservable(ApiUtils.getretrofitApi().userLogin(body),
                (LifecycleProvider<ActivityEvent>) context, ActivityEvent.PAUSE).subscribe(httpRxObserver);
    }
}
