package com.mak.eword.show.fragment.list;

import com.mak.eword.base.BaseListFragment;
import com.mak.eword.base.BaseRecyclerAdapter;
import com.mak.eword.mvp.inface.IWordListView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.WordBean;
import com.mak.eword.mvp.presenter.WordListPresenter;
import com.mak.eword.show.adapter.WordItemAdapter;

import java.util.List;

/**
 * 单词列表
 */
public class Frag_WordList extends BaseListFragment implements IWordListView {
    //adapter
    WordItemAdapter wordItemAdapter;

    private WordListPresenter wordListPresenter = new WordListPresenter(this, getActivity());

    @Override
    protected void initInstance() {

    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        if (wordItemAdapter == null) {
            wordItemAdapter = new WordItemAdapter(mContext);
        }
        return wordItemAdapter;
    }

    @Override
    protected void setItemClickListener(int position) {

    }

    /**
     * 刷新List
     */
    public void refreshList() {
        autoRefreshList();
    }

    @Override
    protected void getHttpData() {
        //每次查询20个
        wordListPresenter.getWordList(mContext, currentPage, 20);
    }

    @Override
    public void showWordListResult(List<WordBean> wordList) {
        if (isRefresh) {
            //刷新
            wordItemAdapter.refresh(wordList);
        } else {
            //加载
            wordItemAdapter.loadMore(wordList);
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
        notifyAdapterUi(isRefresh, false, 0);
    }
}
