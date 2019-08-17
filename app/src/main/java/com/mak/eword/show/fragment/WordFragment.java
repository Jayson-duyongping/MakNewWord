package com.mak.eword.show.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.mak.eword.HomeActivity;
import com.mak.eword.R;
import com.mak.eword.application.WordApp;
import com.mak.eword.base.BaseFragment;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.mvp.model.UserBean;
import com.mak.eword.show.activity.AddWordActivity;
import com.mak.eword.show.fragment.list.Frag_WordList;
import com.mak.eword.utils.ToastUtils;
import com.mak.eword.utils.manager.StorageDayManager;

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
        //padding状态栏高度
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
