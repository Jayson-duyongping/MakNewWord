package com.mak.newword.show.fragment.list;

import com.mak.newword.base.BaseListFragment;
import com.mak.newword.base.BaseRecyclerAdapter;
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

    @Override
    protected void getHttpData() {
        if (wordItemAdapter.getCount() == 100) {
            notifyAdapterUi(isRefresh, true, 0);
            return;
        }
        List<String> temList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            temList.add("");
        }
        wordItemAdapter.loadMore(temList);
        notifyAdapterUi(isRefresh, true, 0);
    }
}
