package com.mak.newword.show.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.utils.ToastUtils;
import com.mak.newword.widget.HeaderView;

import butterknife.BindView;

public class CommentActivity extends BaseFragmentActivity {

    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.content_et)
    EditText contentEt;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("意见反馈");
        headerView.setRightText("提交");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        headerView.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交
                if (!isNetworkConnected(mContext)) {
                    ToastUtils.showToast(mContext, "网络连接失败");
                    return;
                }
                if (TextUtils.isEmpty(contentEt.getText().toString())) {
                    ToastUtils.showToast(mContext, "请输入内容");
                    return;
                }
                ToastUtils.showToast(mContext, "提交成功");
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
