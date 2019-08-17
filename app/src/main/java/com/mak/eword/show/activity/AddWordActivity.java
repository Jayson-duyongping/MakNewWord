package com.mak.eword.show.activity;

import android.animation.Animator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import com.mak.eword.widget.SpinerPopWindow;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增单词页面
 */
public class AddWordActivity extends BaseFragmentActivity implements IWordEditView {

    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.tip_tv)
    TextView tipTv;
    @BindView(R.id.mean_container_ll)
    LinearLayout meanContainerLl;
    @BindView(R.id.other_ll)
    LinearLayout otherLl;

    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.plus_iv)
    ImageView plusIv;
    @BindView(R.id.ex_en_et)
    EditText exEnEt;
    @BindView(R.id.ex_zh_et)
    EditText exZhEt;
    @BindView(R.id.method_et)
    EditText methodEt;

    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<String> classList;
    private List<MeanBean> meanList;
    //本类全局单词实体
    private WordBean wordBean;

    private WordEditPresenter wordEditPresenter = new WordEditPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_word;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("新增单词");
        headerView.setRightText("完成");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        headerView.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行保存
                saveWord();
            }
        });
        //属性动画有点消耗性能
        //performVisibleAnim(tipTv, 0, 30);
        YoYo.with(Techniques.FlipInX).duration(1000).repeat(0)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        contentEt.setVisibility(View.VISIBLE);
                        plusIv.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FlipInX).duration(1000).repeat(0).playOn(contentEt);
                        YoYo.with(Techniques.FlipInX).duration(1000).repeat(0).playOn(plusIv);
                    }
                }).playOn(tipTv);
    }

    /**
     * 执行保存
     */
    //是否是修改
    boolean idEdit;

    private void saveWord() {
        if (wordBean == null) {
            //新增
            idEdit = false;
            wordBean = new WordBean();
        } else {
            //修改
            idEdit = true;
        }
        String content = contentEt.getText().toString();
        //主内容必须填
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show("请填写一个生词");
            return;
        }
        wordBean.setContent(content);
        //多项词义
        for (int i = 0; i < meanContainerLl.getChildCount(); i++) {
            LinearLayout view = (LinearLayout) meanContainerLl.getChildAt(i);
            TextView classTv = view.findViewById(R.id.class_tv);
            EditText meanEt = view.findViewById(R.id.mean_et);
            MeanBean mean = new MeanBean();
            mean.setClass_(classTv.getText().toString());
            mean.setMean_(meanEt.getText().toString());
            meanList.add(mean);
        }
        wordBean.setMeans(meanList);
        //例句跟方法
        wordBean.setExampleEn(exEnEt.getText().toString());
        wordBean.setExampleZh(exZhEt.getText().toString());
        wordBean.setMethod(methodEt.getText().toString());
        if (idEdit) {
            //修改
            wordEditPresenter.alterWord(mContext, setRequestBody(wordBean));
        } else {
            //新增
            wordEditPresenter.addWord(mContext, setRequestBody(wordBean));
        }
    }

    @Override
    protected void initData() {
        //获取英语所有词性
        classList = Arrays.asList(getResources().getStringArray(R.array.word_class));
        //初始化词义集合
        meanList = new ArrayList<>();
        //编辑页面
        wordBean = (WordBean) getIntent().getSerializableExtra("word");
        if (wordBean != null) {
            contentEt.setVisibility(View.VISIBLE);
            plusIv.setVisibility(View.VISIBLE);
            setEditView(wordBean);
        }
    }

    /**
     * 设置编辑页面
     */
    private void setEditView(WordBean wordBean) {
        contentEt.setText(wordBean.getContent());
        List<MeanBean> means = wordBean.getMeans();
        if (means != null) {
            if (means.size() >= 1) {
                otherLl.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < means.size(); i++) {
                addNewMean(means.get(i));
            }
        }
        exEnEt.setText(wordBean.getExampleEn());
        exZhEt.setText(wordBean.getExampleZh());
        methodEt.setText(wordBean.getMethod());
    }

    @OnClick(R.id.plus_iv)
    public void onAddNewMean() {
        if (meanContainerLl.getChildCount() == 3) {
            ToastUtils.show("别贪心哦，最多只能记3个");
            return;
        }
        if (otherLl.getVisibility() == View.GONE) {
            otherLl.setVisibility(View.VISIBLE);
        }
        addNewMean(null);
    }

    /**
     * 添加一个新的词义
     */
    public void addNewMean(MeanBean mean) {
        final View gameView = LayoutInflater.from(mContext).inflate(R.layout.item_word_mean, null);
        final TextView classTv = gameView.findViewById(R.id.class_tv);
        final EditText meanEt = gameView.findViewById(R.id.mean_et);
        final ImageView meanDeleteIv = gameView.findViewById(R.id.mean_delete_iv);
        //设置词性值
        if (mean != null) {
            classTv.setText(mean.getClass_());
            meanEt.setText(mean.getMean_());
        }
        //选择词性
        classTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化下拉并设置监听
                mSpinerPopWindow = new SpinerPopWindow<>(mContext, classList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        mSpinerPopWindow.dismiss();
                        classTv.setText(classList.get(position));
                    }
                });
                //选择词性
                mSpinerPopWindow.setWidth(classTv.getWidth());
                mSpinerPopWindow.showAsDropDown(classTv);
            }
        });
        //删除此列
        meanDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meanContainerLl.removeView(gameView);
            }
        });
        meanContainerLl.addView(gameView);
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

    @Override
    public void showWordEditResult(CommonBean bean) {
        if (bean != null && "200".equals(bean.getCode())) {
            if (!idEdit) {
                //新增成功
                ToastUtils.show(bean.getMsg());
                //记录本地一个
                StorageDayManager.getInstance(mContext)
                        .handlerDayNumber(StringConstant.Share_Record_Count);
                //更新记录个数
                EventBus.getDefault().post(StringConstant.Event_UpdateDayNumber);
                //刷新界面
                EventBus.getDefault().post(StringConstant.Event_RefreshWordList);
                this.finish();
            } else {
                //修改成功
                ToastUtils.show(bean.getMsg());
                //刷新界面
                EventBus.getDefault().post(StringConstant.Event_RefreshWordList);
                this.finish();
            }
        }
    }
}
