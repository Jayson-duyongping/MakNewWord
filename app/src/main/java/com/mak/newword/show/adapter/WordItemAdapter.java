package com.mak.newword.show.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mak.newword.R;
import com.mak.newword.base.BaseRecyclerAdapter;
import com.mak.newword.greendao.service.WordService;
import com.mak.newword.mvp.model.MeanBean;
import com.mak.newword.mvp.model.WordBean;
import com.mak.newword.show.activity.AddWordActivity;
import com.mak.newword.show.activity.WordDetailActivity;
import com.mak.newword.utils.ToastUtils;
import com.mak.newword.widget.SmartViewHolder;
import com.mak.newword.widget.SwipeMenuLayout;

import java.io.Serializable;
import java.util.List;

/**
 * 创建人：jayson
 * 创建时间：2019/4/7
 * 创建内容：
 */

public class WordItemAdapter extends BaseRecyclerAdapter<WordBean> {

    private Context context;

    public WordItemAdapter(Context context) {
        super(R.layout.item_word);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, WordBean model, int position) {
        onHandle(holder, model, position);
    }

    private void onHandle(SmartViewHolder holder, final WordBean model, final int position) {
        final SwipeMenuLayout swipeMenuLayout = holder.getView(R.id.swipemenu_view);
        swipeMenuLayout.setSwipeEnable(true);
        swipeMenuLayout.setIos(false);
        ImageView finishIv = holder.getView(R.id.finish_iv);
        LinearLayout contentLl = holder.getView(R.id.content_ll);
        TextView wordNameTv = holder.getView(R.id.word_name_tv);
        TextView wordMeanTv = holder.getView(R.id.word_mean_tv);
        TextView editTv = holder.getView(R.id.edit_tv);
        TextView deleteTv = holder.getView(R.id.delete_tv);
        //-----
        wordNameTv.setText(model.getContent());
        List<MeanBean> means = model.getMeans();
        if (means == null || means.size() == 0) {
            wordMeanTv.setText("");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < means.size(); i++) {
                sb.append(means.get(i).getClass_() + " " + means.get(i).getMean_() + "； ");
            }
            wordMeanTv.setText(sb.toString());
        }
        if (model.getIsRemember()) {
            finishIv.setVisibility(View.VISIBLE);
        } else {
            finishIv.setVisibility(View.GONE);
        }
        //点击内容
        contentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WordDetailActivity.class);
                intent.putExtra("word", model);
                context.startActivity(intent);
            }
        });
        //编辑内容
        editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeMenuLayout.smoothClose();
                Intent intent = new Intent(context, AddWordActivity.class);
                intent.putExtra("word", model);
                context.startActivity(intent);
            }
        });
        //删除内容
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordService.getInstance(context).deleteWord(model);
                swipeMenuLayout.smoothClose();
                removePosition(position);
            }
        });
    }
}
