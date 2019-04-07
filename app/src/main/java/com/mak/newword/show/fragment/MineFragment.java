package com.mak.newword.show.fragment;

import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_parent)
    LinearLayout mineParent;
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        //pading状态栏高度
        HomeActivity activity = (HomeActivity) getContext();
        mineParent.setPadding(0, activity.statusBarHeight, 0, 0);
    }

    @Override
    protected void initData() {

    }

}
