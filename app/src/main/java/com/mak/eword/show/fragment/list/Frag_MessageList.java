package com.mak.eword.show.fragment.list;

import com.mak.eword.base.BaseListFragment;
import com.mak.eword.base.BaseRecyclerAdapter;
import com.mak.eword.greendao.service.WordService;
import com.mak.eword.mvp.model.WordBean;
import com.mak.eword.show.adapter.MessageItemAdapter;

import java.util.List;

/**
 * 消息列表
 */
public class Frag_MessageList extends BaseListFragment {
    //adapter
    MessageItemAdapter messageItemAdapter;

    @Override
    protected void initInstance() {

    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        if (messageItemAdapter == null) {
            messageItemAdapter = new MessageItemAdapter(mContext);
        }
        return messageItemAdapter;
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
            messageItemAdapter.refresh(wordList);
        } else {
            //加载，每次查询20个
            List<WordBean> wordList = WordService.getInstance(mContext)
                    .queryWordList((currentPage - 1) * 20, 20);
            messageItemAdapter.loadMore(wordList);
        }
        notifyAdapterUi(isRefresh, true, 0);
    }
}
