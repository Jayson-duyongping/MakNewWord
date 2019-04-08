package com.mak.newword.show.fragment.list;

import com.mak.newword.base.BaseListFragment;
import com.mak.newword.base.BaseRecyclerAdapter;
import com.mak.newword.greendao.service.WordService;
import com.mak.newword.mvp.model.WordBean;
import com.mak.newword.show.adapter.WordItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 单词列表
 */
public class Frag_WordList extends BaseListFragment {
    //adapter
    WordItemAdapter wordItemAdapter;

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
        if (isRefresh) {
            //刷新，每次查询20个
            List<WordBean> wordList = WordService.getInstance(mContext)
                    .queryWordList((currentPage - 1) * 20, 20);
            wordItemAdapter.refresh(wordList);
        } else {
            //加载，每次查询20个
            List<WordBean> wordList = WordService.getInstance(mContext)
                    .queryWordList((currentPage - 1) * 20, 20);
            wordItemAdapter.loadMore(wordList);
        }
        notifyAdapterUi(isRefresh, true, 0);
    }
}
