package com.mak.newword.show.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragment;
import com.mak.newword.show.activity.AddWordActivity;
import com.mak.newword.show.fragment.list.Frag_WordList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 记单词fragment
 */
public class WordFragment extends BaseFragment {
    @BindView(R.id.word_parent)
    LinearLayout wordParent;

    private FragmentTransaction transaction;
    private Frag_WordList wordListFrag;

    @Override
    protected int getLayout() {
        return R.layout.fragment_word;
    }

    @Override
    protected void initView() {
        //pading状态栏高度
        HomeActivity activity = (HomeActivity) getContext();
        wordParent.setPadding(0, activity.statusBarHeight, 0, 0);
    }

    @Override
    protected void initData() {
        transaction = getChildFragmentManager().beginTransaction();
        if (wordListFrag == null) {
            wordListFrag = new Frag_WordList();
            //创建fragment
            transaction.add(R.id.contentLayout2, wordListFrag);
            transaction.commit();
        }
        transaction.show(wordListFrag);
    }

    @OnClick(R.id.add_word_tv)
    public void onAddWord() {
        Intent intent = new Intent(getActivity(), AddWordActivity.class);
        startActivity(intent);
    }

    /**
     * 刷新当前页面
     */
    public void refreshList() {
        //刷新当前页面
        wordListFrag.refreshList();
    }
}
