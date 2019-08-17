package com.mak.eword.show.fragment.list;

import com.mak.eword.R;
import com.mak.eword.base.BaseListFragment;
import com.mak.eword.base.BaseRecyclerAdapter;
import com.mak.eword.mvp.inface.ISentenceListView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.SentenceBean;
import com.mak.eword.mvp.presenter.SentenceListPresenter;
import com.mak.eword.show.adapter.SentenceItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 句子列表
 */
public class Frag_SentenceList extends BaseListFragment implements ISentenceListView {
    //adapter
    SentenceItemAdapter sentenceItemAdapter;

    private SentenceListPresenter sentenceListPresenter = new SentenceListPresenter(this, getActivity());

    @Override
    protected void initInstance() {
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
        //每次查询20个
        sentenceListPresenter.getSentenceList(mContext, currentPage, 20);
    }

    @Override
    public void showSentenceListResult(List<SentenceBean> sentenceList) {
        if (isRefresh) {
            if (sentenceList.size() == 0) {
                sentenceItemAdapter.refresh(localAddSentence());
            } else {
                //因为第一个是一个推荐轮播图，我们这里需要占个位
                sentenceList.add(0, null);
                sentenceItemAdapter.refresh(sentenceList);
            }
        } else {
            sentenceItemAdapter.loadMore(sentenceList);
        }
        notifyAdapterUi(isRefresh, true, 0);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(BaseErrorBean bean) {
        if (isRefresh) {
            sentenceItemAdapter.refresh(localAddSentence());
        }
        notifyAdapterUi(isRefresh, true, 0);
    }

    /**
     * 加载本地数据
     */
    private List<SentenceBean> localAddSentence() {
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
        return sentenceBeans;
    }
}
