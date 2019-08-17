package com.mak.eword.show.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mak.eword.R;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.mvp.inface.IWordEditView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.MeanBean;
import com.mak.eword.mvp.model.WordBean;
import com.mak.eword.mvp.model.common.CommonBean;
import com.mak.eword.mvp.presenter.WordEditPresenter;
import com.mak.eword.utils.ToastUtils;
import com.mak.eword.utils.manager.StorageDayManager;
import com.mak.eword.widget.HeaderView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class WordDetailActivity extends BaseFragmentActivity implements IWordEditView {
    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.means_Ll)
    LinearLayout meansContainerLl;
    @BindView(R.id.ex_en_tv)
    TextView exEnTv;
    @BindView(R.id.ex_zh_tv)
    TextView exZhTv;
    @BindView(R.id.method_tv)
    TextView methodTv;

    private WordBean wordBean;

    private WordEditPresenter wordEditPresenter = new WordEditPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_word_detail;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("单词详情");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        headerView.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //标记为已记
                wordBean.setIsRemember(1);
                wordEditPresenter.alterWord(mContext, setRequestBody(wordBean));
            }
        });
    }

    @Override
    protected void initData() {
        wordBean = (WordBean) getIntent().getSerializableExtra("word");
        if (wordBean.getIsRemember() != 1) {
            headerView.setRightText("已记");
        }
        contentTv.setText(wordBean.getContent());
        addNewMean(wordBean.getMeans());
        if (TextUtils.isEmpty(wordBean.getExampleEn())
                && TextUtils.isEmpty(wordBean.getExampleZh())) {
            exEnTv.setText("--");
        } else if (!TextUtils.isEmpty(wordBean.getExampleEn())
                && TextUtils.isEmpty(wordBean.getExampleZh())) {
            exEnTv.setText(wordBean.getExampleEn());
            exZhTv.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(wordBean.getExampleEn())
                && !TextUtils.isEmpty(wordBean.getExampleZh())) {
            exEnTv.setVisibility(View.GONE);
            exZhTv.setVisibility(View.VISIBLE);
            exZhTv.setText(wordBean.getExampleZh());
        } else {
            exEnTv.setText(wordBean.getExampleEn());
            exZhTv.setVisibility(View.VISIBLE);
            exZhTv.setText(wordBean.getExampleZh());
        }
        if (TextUtils.isEmpty(wordBean.getMethod())) {
            methodTv.setText("--");
        } else {
            methodTv.setText(wordBean.getMethod());
        }
    }

    /**
     * 添加一个新的词义
     */
    public void addNewMean(List<MeanBean> means) {
        if (means == null) {
            return;
        }
        for (int i = 0; i < means.size(); i++) {
            final View gameView = LayoutInflater.from(mContext).inflate(R.layout.item_ciba_mean, null);
            final TextView classTv = gameView.findViewById(R.id.class_tv);
            final TextView meanTv = gameView.findViewById(R.id.mean_tv);
            //设置词性值
            classTv.setText(means.get(i).getClass_());
            meanTv.setText(means.get(i).getMean_());
            meansContainerLl.addView(gameView);
        }
    }

    @Override
    public void showWordEditResult(CommonBean bean) {
        if (bean != null) {
            ToastUtils.show("已标记为已记");
            EventBus.getDefault().post(StringConstant.Event_RefreshWordList);
            //存一个当日记忆数到本地
            StorageDayManager.getInstance(mContext)
                    .handlerDayNumber(StringConstant.Share_Remember_Count);
            //刷新MineFragment中的计划卡
            EventBus.getDefault().post(StringConstant.Event_UpdateDayNumber);
            this.finish();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(BaseErrorBean bean) {

    }
}
