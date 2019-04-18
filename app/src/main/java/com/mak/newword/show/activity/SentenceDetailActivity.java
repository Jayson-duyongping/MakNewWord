package com.mak.newword.show.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.mvp.model.SentenceBean;
import com.mak.newword.widget.HeaderView;

import butterknife.BindView;

/**
 * 美文美句详情页面
 */
public class SentenceDetailActivity extends BaseFragmentActivity {
    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.sentence_img_iv)
    ImageView sentenceImgIv;
    @BindView(R.id.sentence_en_tv)
    TextView sentenceEnTv;
    @BindView(R.id.sentence_zh_tv)
    TextView sentenceZhTv;

    private SentenceBean sentenceBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sentence_detail;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("美文美句");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        sentenceBean = (SentenceBean) getIntent().getSerializableExtra("sentence");
        if (sentenceBean != null) {
            if (!TextUtils.isEmpty(sentenceBean.getImgUrl())) {
                Glide.with(mContext).load(sentenceBean.getImgUrl())
                        .placeholder(R.mipmap.place_banner)
                        .into(sentenceImgIv);
            }
            sentenceEnTv.setText(sentenceBean.getEnContent());
            sentenceZhTv.setText(sentenceBean.getZhContent());
        }
    }
}
