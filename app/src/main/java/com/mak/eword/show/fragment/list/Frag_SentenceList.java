package com.mak.eword.show.fragment.list;

import com.mak.eword.R;
import com.mak.eword.base.BaseListFragment;
import com.mak.eword.base.BaseRecyclerAdapter;
import com.mak.eword.greendao.service.SentenceService;
import com.mak.eword.mvp.model.SentenceBean;
import com.mak.eword.show.adapter.SentenceItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 句子列表
 */
public class Frag_SentenceList extends BaseListFragment {
    //adapter
    SentenceItemAdapter sentenceItemAdapter;

    @Override
    protected void initInstance() {
        //如果没有数据，初始化一些数据到数据库
        long count = SentenceService.getInstance(mContext).querySentenceListCount();
        if (count == 0) {
            List<String> localSentences = Arrays.asList(
                    getActivity().getResources().getStringArray(R.array.sentence_local));
            List<SentenceBean> sentenceBeans = new ArrayList<>();
            for (int i = 0; i < localSentences.size(); i++) {
                String content = localSentences.get(i);
                String[] contents = content.split("##local##");
                SentenceBean sentence = new SentenceBean();
                if (contents.length == 2) {
                    sentence.setEnContent(contents[0]);
                    sentence.setZhContent(contents[1]);
                    sentenceBeans.add(sentence);
                } else if (contents.length == 3) {
                    sentence.setImgUrl(contents[0]);
                    sentence.setEnContent(contents[1]);
                    sentence.setZhContent(contents[2]);
                    sentenceBeans.add(sentence);
                }
            }
            //插入数据库
            SentenceService.getInstance(mContext).insertSentenceList(sentenceBeans);
        }
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        if (sentenceItemAdapter == null) {
            sentenceItemAdapter = new SentenceItemAdapter(mContext);
        }
        return sentenceItemAdapter;
    }

    @Override
    protected void setItemClickListener(int position) {

    }

    @Override
    protected void getHttpData() {
        if (isRefresh) {
            //刷新，每次查询20个
            List<SentenceBean> sentenceList = SentenceService.getInstance(mContext)
                    .querySentenceList((currentPage - 1) * 20, 20);
            //因为第一个是一个推荐轮播图，我们这里需要占个位
            sentenceList.add(0, null);
            sentenceItemAdapter.refresh(sentenceList);
        } else {
            //加载，每次查询20个
            List<SentenceBean> sentenceList = SentenceService.getInstance(mContext)
                    .querySentenceList((currentPage - 1) * 20, 20);
            sentenceItemAdapter.loadMore(sentenceList);
        }
        notifyAdapterUi(isRefresh, true, 0);
    }
}
