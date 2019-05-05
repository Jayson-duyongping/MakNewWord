package com.mak.eword.mvp.inface;

import com.mak.eword.base.IBaseView;
import com.mak.eword.mvp.model.common.CommonBean;

/**
 * Created by jayson on 2019/4/16.
 * Content:用户注册view
 */
public interface IUserRegisterView extends IBaseView {
    //用户注册返回
    void showUserRegisterResult(CommonBean bean);
}
