package com.mak.eword.mvp.inface;

import com.mak.eword.base.IBaseView;
import com.mak.eword.mvp.model.SentenceBean;

import java.util.List;

/**
 * Created by jayson on 2019/4/16.
 * Content:句子列表view
 */
public interface ISentenceListView extends IBaseView {
    //句子列表返回
    void showSentenceListResult(List<SentenceBean> bean);
}
