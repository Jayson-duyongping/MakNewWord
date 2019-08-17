package com.mak.eword.mvp.inface;

import com.mak.eword.base.IBaseView;
import com.mak.eword.mvp.model.WordPlanBean;
import com.mak.eword.mvp.model.common.CommonBean;

/**
 * Created by jayson on 2019/4/16.
 * Content:单词编辑view
 */
public interface IWordEditView extends IBaseView {
    //单词编辑返回
    void showWordEditResult(CommonBean bean);
}
