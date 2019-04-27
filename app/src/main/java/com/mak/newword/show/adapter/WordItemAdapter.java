package com.mak.newword.show.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mak.newword.R;
import com.mak.newword.base.BaseRecyclerAdapter;
import com.mak.newword.greendao.service.UserService;
import com.mak.newword.greendao.service.WordService;
import com.mak.newword.mvp.model.MeanBean;
import com.mak.newword.mvp.model.WordBean;
import com.mak.newword.show.activity.AddWordActivity;
import com.mak.newword.show.activity.WordDetailActivity;
import com.mak.newword.utils.PopupWindowUtil;
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
    //长按弹出的pop
    private PopupWindow planPop;
    private View planPopView;

    public WordItemAdapter(Context context) {
        super(R.layout.item_word);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, WordBean model, int position) {
        onHandle(holder, model, position);
    }

    private void onHandle(SmartViewHolder holder, final WordBean model, final int position) {
        ImageView finishIv = holder.getView(R.id.finish_iv);
        LinearLayout contentLl = holder.getView(R.id.content_ll);
        TextView wordNameTv = holder.getView(R.id.word_name_tv);
        TextView wordMeanTv = holder.getView(R.id.word_mean_tv);
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
        //长按操作
        contentLl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPlanPop(model, position);
                return true;
            }
        });
    }

    /**
     * 弹出计划框
     */
    public void showPlanPop(WordBean model, int position) {
        planPopView = LayoutInflater.from(context)
                .inflate(R.layout.pop_word, null);
        final TextView rememberBtn = planPopView.findViewById(R.id.remember_word_btn);
        final TextView editBtn = planPopView.findViewById(R.id.edit_word_btn);
        final TextView deleteBtn = planPopView.findViewById(R.id.delete_word_btn);
        //标记为已记
        rememberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planPop.dismiss();
            }
        });
        //编辑
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planPop.dismiss();
                Intent intent = new Intent(context, AddWordActivity.class);
                intent.putExtra("word", model);
                context.startActivity(intent);
            }
        });
        //删除
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planPop.dismiss();
                WordService.getInstance(context).deleteWord(model);
                removePosition(position);
            }
        });
        if (planPop == null) {
            planPop = PopupWindowUtil.getPopWindowForCenter(getActivity(), planPopView);
            //动画一下
            YoYo.with(Techniques.BounceIn)
                    .duration(500)
                    .repeat(0)
                    .playOn(planPopView);
            planPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams ll =
                            getActivity().getWindow().getAttributes();
                    ll.alpha = 1f;
                    getActivity().getWindow().setAttributes(ll);
                    planPop = null;
                }
            });
        }
    }

    /**
     * 获取Activity
     *
     * @return
     */
    private Activity getActivity() {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }
}
