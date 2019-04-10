package com.mak.newword.show.activity;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jayson.commonlib.widget.smartrefresh.util.DensityUtil;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.constant.StringConstant;
import com.mak.newword.greendao.service.WordService;
import com.mak.newword.mvp.model.MeanBean;
import com.mak.newword.mvp.model.WordBean;
import com.mak.newword.utils.ToastUtils;
import com.mak.newword.utils.manager.StorageDayManager;
import com.mak.newword.widget.HeaderView;
import com.mak.newword.widget.SpinerPopWindow;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增单词页面
 */
public class AddWordActivity extends BaseFragmentActivity {

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
        tipTv.setVisibility(View.VISIBLE);
        performVisibleAnim(tipTv, 0, 30);
    }

    /**
     * 执行保存
     */
    private void saveWord() {
        //是否是修改
        boolean idEdit;
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
            ToastUtils.showToast(mContext, "请填写一个生词");
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
            WordService.getInstance(mContext).updateWord(wordBean);
        } else {
            //新增
            WordService.getInstance(mContext).insertWord(wordBean);
        }
        //保存完成
        ToastUtils.showToast(mContext, "保存成功");
        //记录本地一个
        StorageDayManager.getInstance(mContext)
                .handlerDayNumber(StringConstant.Share_Record_Count);
        //刷新界面和更新记录个数
        EventBus.getDefault().post(StringConstant.Event_RefreshWordList);
        EventBus.getDefault().post(StringConstant.Event_UpdateDayNumber);
        this.finish();
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
            ToastUtils.showToast(mContext, "别贪心哦，最多只能记3个");
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
    protected void onDestroy() {
        super.onDestroy();
        //释放，否者内容溢出
        if (animatorSetsuofang != null) {
            animatorSetsuofang.cancel();
            animatorSetsuofang = null;
        }
    }

    /**
     * 控件 显示和隐藏动画效果
     *
     * @param layout
     * @param start
     * @param end
     */
    AnimatorSet animatorSetsuofang;

    private void performVisibleAnim(final TextView layout, int start, int end) {
        //组合动画
        animatorSetsuofang = new AnimatorSet();
        //属性动画对象
        ValueAnimator vaH;
        //只是高度变化
        vaH = ValueAnimator.ofInt(start, DensityUtil.dp2px(end));
        vaH.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前的height值
                int h = (Integer) valueAnimator.getAnimatedValue();
                //动态更新view的高度
                layout.getLayoutParams().height = h;
                layout.requestLayout();
            }
        });
        animatorSetsuofang.play(vaH);//两个动画同时开始
        animatorSetsuofang.setDuration(500);
        //开始动画
        animatorSetsuofang.start();
    }
}
