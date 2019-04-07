package com.mak.newword.show.fragment.list;

import com.mak.newword.base.BaseListFragment;
import com.mak.newword.base.BaseRecyclerAdapter;
import com.mak.newword.show.adapter.SentenceItemAdapter;
import com.mak.newword.show.adapter.WordItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 句子列表
 */
public class Frag_SentenceList extends BaseListFragment {
    //adapter
    SentenceItemAdapter sentenceItemAdapter;

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
        if (sentenceItemAdapter.getCount() == 100) {
            notifyAdapterUi(isRefresh, true, 0);
            return;
        }
        List<String> temList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            temList.add("");
        }
        sentenceItemAdapter.loadMore(temList);
        notifyAdapterUi(isRefresh, true, 0);
    }
}
