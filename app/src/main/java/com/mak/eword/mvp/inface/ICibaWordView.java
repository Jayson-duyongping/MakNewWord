package com.mak.eword.mvp.inface;

import com.mak.eword.base.IBaseView;
import com.mak.eword.mvp.model.CibaWordEnBean;
import com.mak.eword.mvp.model.CibaWordZhBean;

/**
 * Created by jayson on 2019/4/16.
 * Content:
 */
public interface ICibaWordView extends IBaseView {
    //查询的单词(英文查词)
    void showCibaWordEnResult(CibaWordEnBean bean);

    //查询的单词(中文查词)
    void showCibaWordZhResult(CibaWordZhBean bean);
}
