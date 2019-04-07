package com.mak.newword.show.activity;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.jayson.commonlib.widget.smartrefresh.util.DensityUtil;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.widget.HeaderView;

import butterknife.BindView;

/**
 * 新增单词页面
 */
public class AddWordActivity extends BaseFragmentActivity {

    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.tip_tv)
    TextView tipTv;

    private Handler mHandler = new Handler();

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
                onBackPressed();
            }
        });
        tipTv.setVisibility(View.VISIBLE);
        performVisibleAnim(tipTv, 0, 30);
    }

    @Override
    protected void initData() {

    }

    private void showEditView() {

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
        //列表滑动到底部
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showEditView();
            }
        }, 500);
    }
}
