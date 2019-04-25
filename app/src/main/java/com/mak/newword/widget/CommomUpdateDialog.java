package com.mak.newword.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mak.newword.R;

/**
 * Created by jayson on 2019/4/24.
 * Content:通用更新对话框
 */
public class CommomUpdateDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private LinearLayout buttonLl;
    private RelativeLayout progressRl;
    private ProgressBar updateProgress;
    private TextView percentTv;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    public CommomUpdateDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomUpdateDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    protected CommomUpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomUpdateDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    /**
     * 更新提示框需要调用的
     */
    public CommomUpdateDialog(Context context, int themeResId, String title, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.title = title;
        this.listener = listener;
        //更新不允许取消
        setCancelable(false);
    }

    public CommomUpdateDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommomUpdateDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CommomUpdateDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    /**
     * 设置进度
     *
     * @param percent
     */
    public void setProgress(int percent) {
        updateProgress.setProgress(percent);
        percentTv.setText(percent + "%");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentTxt = findViewById(R.id.content);
        titleTxt = findViewById(R.id.title);
        submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);
        buttonLl = findViewById(R.id.button_ll);
        progressRl = findViewById(R.id.progress_rl);
        updateProgress = findViewById(R.id.update_progress);
        percentTv = findViewById(R.id.percent_tv);

        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                buttonLl.setVisibility(View.GONE);
                progressRl.setVisibility(View.VISIBLE);
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }

}
