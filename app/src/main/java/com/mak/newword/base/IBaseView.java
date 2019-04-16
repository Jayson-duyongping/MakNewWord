package com.mak.newword.base;


import com.mak.newword.mvp.model.BaseErrorBean;

/**
 * IBaseView
 *
 * @author jayson
 */

public interface IBaseView {

    //显示loading
    void showLoading();

    //关闭loading
    void closeLoading();

    //显示吐司
    void showToast(BaseErrorBean bean);

}
