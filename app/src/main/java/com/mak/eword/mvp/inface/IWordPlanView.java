package com.mak.eword.mvp.inface;

import com.mak.eword.base.IBaseView;
import com.mak.eword.mvp.model.WordPlanBean;
import com.mak.eword.mvp.model.common.CommonBean;

/**
 * Created by jayson on 2019/4/16.
 * Content:单词计划view
 */
public interface IWordPlanView extends IBaseView {
    //单词计划返回
    void showWordPlanResult(WordPlanBean bean);

    //单词计划修改返回
    void showAlterWordPlanResult(CommonBean bean);
}
