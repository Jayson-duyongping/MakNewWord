package com.mak.newword.show.adapter;

import android.content.Context;

import com.mak.newword.R;
import com.mak.newword.base.BaseRecyclerAdapter;
import com.mak.newword.widget.SmartViewHolder;

/**
 * 创建人：jayson
 * 创建时间：2019/4/7
 * 创建内容：
 */

public class WordItemAdapter extends BaseRecyclerAdapter<String> {

    private Context context;

    public WordItemAdapter(Context context) {
        super(R.layout.item_word);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
        onHandle(holder, model, position);
    }

    private void onHandle(SmartViewHolder holder, String model, int position) {

    }
}
