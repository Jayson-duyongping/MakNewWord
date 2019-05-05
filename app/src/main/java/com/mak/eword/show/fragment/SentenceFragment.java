package com.mak.eword.show.fragment;

import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.mak.eword.HomeActivity;
import com.mak.eword.R;
import com.mak.eword.base.BaseFragment;
import com.mak.eword.show.fragment.list.Frag_SentenceList;

import butterknife.BindView;

/**
 * 记句子fragment
 */
public class SentenceFragment extends BaseFragment {

    @BindView(R.id.sentence_parent)
    LinearLayout sentenceParent;


    private FragmentTransaction transaction;
    private Frag_SentenceList sentenceListFrag;

    @Override
    protected int getLayout() {
        return R.layout.fragment_sentence;
    }

    @Override
    protected void initView() {
        //padding状态栏高度
        HomeActivity activity = (HomeActivity) getContext();
        sentenceParent.setPadding(0, activity.statusBarHeight, 0, 0);
    }

    @Override
    protected void initData() {
        transaction = getChildFragmentManager().beginTransaction();
        if (sentenceListFrag == null) {
            sentenceListFrag = new Frag_SentenceList();
            //创建fragment
            transaction.add(R.id.contentLayout1, sentenceListFrag);
            transaction.commit();
        }
        transaction.show(sentenceListFrag);
    }

}
