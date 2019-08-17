package com.mak.eword.mvp.inface;

import com.mak.eword.base.IBaseView;
import com.mak.eword.mvp.model.WordBean;
import com.mak.eword.mvp.model.common.CommonBean;

import java.util.List;

/**
 * Created by jayson on 2019/4/16.
 * Content:单词列表view
 */
public interface IWordListView extends IBaseView {
    //单词列表返回
    void showWordListResult(List<WordBean> bean);
}
