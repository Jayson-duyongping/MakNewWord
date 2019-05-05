package com.mak.eword.show.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mak.eword.R;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.show.fragment.list.Frag_MessageList;
import com.mak.eword.widget.HeaderView;

import butterknife.BindView;

/**
 * 消息管理
 */
public class MessageManagerActivity extends BaseFragmentActivity {
    @BindView(R.id.headerView)
    HeaderView headerView;

    private FragmentTransaction transaction;
    private Frag_MessageList messageListFrag;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message_manager;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("消息管理");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        transaction = getSupportFragmentManager().beginTransaction();
        if (messageListFrag == null) {
            messageListFrag = new Frag_MessageList();
            //创建fragment
            transaction.add(R.id.contentLayout, messageListFrag);
            transaction.commit();
        }
        transaction.show(messageListFrag);
    }
}
