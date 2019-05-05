package com.mak.eword.mvp.inface;

import com.mak.eword.base.IBaseView;
import com.mak.eword.mvp.model.UserBean;

/**
 * Created by jayson on 2019/4/16.
 * Content:用户登录view
 */
public interface IUserLoginView extends IBaseView {
    //用户登录返回
    void showUserLoginResult(UserBean bean);
}
