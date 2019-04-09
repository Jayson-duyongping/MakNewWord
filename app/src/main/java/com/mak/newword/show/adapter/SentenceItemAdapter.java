package com.mak.newword.show.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jayson.commonlib.widget.smartrefresh.util.DensityUtil;
import com.mak.newword.R;
import com.mak.newword.base.BaseRecyclerAdapter;
import com.mak.newword.mvp.model.SentenceBean;
import com.mak.newword.show.activity.SentenceDetailActivity;
import com.mak.newword.widget.SmartViewHolder;

/**
 * 创建人：jayson
 * 创建时间：2019/4/7
 * 创建内容：
 */

public class SentenceItemAdapter extends BaseRecyclerAdapter<SentenceBean> {

    private Context context;

    public SentenceItemAdapter(Context context) {
        super(R.layout.item_sentence);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, SentenceBean model, int position) {
        onHandle(holder, model, position);
    }

    private void onHandle(SmartViewHolder holder, final SentenceBean model, int position) {
        LinearLayout contentLl = holder.getView(R.id.content_ll);
        TextView zhContentTv = holder.getView(R.id.zh_content_tv);
        TextView enContentTv = holder.getView(R.id.en_content_tv);
        ImageView imgTv = holder.getView(R.id.img_content_tv);
        zhContentTv.setText(model.getZhContent());
        enContentTv.setText(model.getEnContent());
        if (!TextUtils.isEmpty(model.getImgUrl())) {
            imgTv.setVisibility(View.VISIBLE);
            Glide.with(context).load(model.getImgUrl()).into(imgTv);
        } else {
            imgTv.setVisibility(View.GONE);
        }
        contentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SentenceDetailActivity.class);
                intent.putExtra("sentence", model);
                context.startActivity(intent);
            }
        });
    }
}
