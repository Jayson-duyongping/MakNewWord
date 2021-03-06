package com.mak.eword.show.fragment;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mak.eword.HomeActivity;
import com.mak.eword.R;
import com.mak.eword.application.WordApp;
import com.mak.eword.base.BaseFragment;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.mvp.inface.IWordPlanView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.UserBean;
import com.mak.eword.mvp.model.WordPlanBean;
import com.mak.eword.mvp.model.common.CommonBean;
import com.mak.eword.mvp.model.request.WordPlanReq;
import com.mak.eword.mvp.presenter.WordPlanPresenter;
import com.mak.eword.show.activity.AboutActivity;
import com.mak.eword.show.activity.CommentActivity;
import com.mak.eword.show.activity.SettingActivity;
import com.mak.eword.utils.PopupWindowUtil;
import com.mak.eword.utils.ToastUtils;
import com.mak.eword.utils.manager.StorageDayManager;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的Fragment
 */
public class MineFragment extends BaseFragment implements IWordPlanView {
    @BindView(R.id.mine_parent)
    LinearLayout mineParent;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.day_record_tv)
    TextView dayRecordTv;
    @BindView(R.id.day_record_card)
    CardView dayRecordCard;
    @BindView(R.id.day_remember_tv)
    TextView dayRememberTv;
    @BindView(R.id.day_remember_card)
    CardView dayRememberCard;

    private PopupWindow planPop;
    private View planPopView;

    private UserBean userBean;

    private int recordDayNum = 30;
    private int rememberDayNum = 30;

    private WordPlanPresenter wordPlanPresenter = new WordPlanPresenter(this, getActivity());

    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        //padding状态栏高度
        HomeActivity activity = (HomeActivity) getContext();
        mineParent.setPadding(0, activity.statusBarHeight, 0, 0);
    }

    @Override
    protected void initData() {
        //获取用户信息
        userBean = WordApp.instance.getUser();
        //设置
        userNameTv.setText(userBean.getUsername());
        //更新计划卡
        updateDayNumber();
    }

    @OnClick({R.id.day_record_card, R.id.day_remember_card, R.id.comment_ll, R.id.setting_ll, R.id.about_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.day_record_card:
                showPlanPop(0);
                break;
            case R.id.day_remember_card:
                showPlanPop(1);
                break;
            case R.id.comment_ll:
                //意见建议
                Intent commentIntent = new Intent(getContext(), CommentActivity.class);
                getContext().startActivity(commentIntent);
                break;
            case R.id.setting_ll:
                //设置
                Intent settingIntent = new Intent(getContext(), SettingActivity.class);
                getContext().startActivity(settingIntent);
                break;
            case R.id.about_ll:
                //关于我们
                Intent aboutIntent = new Intent(getContext(), AboutActivity.class);
                getContext().startActivity(aboutIntent);
                break;
        }
    }

    /**
     * 刷新计划卡
     */
    public void updateDayNumber() {
        //请求获取计划卡
        wordPlanPresenter.getWordPlan(getContext());
    }

    /**
     * 弹出计划框
     *
     * @param type
     */
    public void showPlanPop(final int type) {
        planPopView = getActivity().getLayoutInflater()
                .inflate(R.layout.pop_plan, null);
        final TextView planTipTv = planPopView.findViewById(R.id.plan_tip_etv);
        final EditText planNumEt = planPopView.findViewById(R.id.plan_num_et);
        TextView planConfirmTv = planPopView.findViewById(R.id.confirm_tv);
        if (type == 0) {
            planTipTv.setText("计划每日记录单词数");
        } else {
            planTipTv.setText("计划每日记忆单词数");
        }
        planConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planPop.dismiss();
                if (type == 0) {
                    if (!TextUtils.isEmpty(planNumEt.getText().toString())) {
                        recordDayNum = Integer.parseInt(planNumEt.getText().toString());
                    }
                } else {
                    if (!TextUtils.isEmpty(planNumEt.getText().toString())) {
                        rememberDayNum = Integer.parseInt(planNumEt.getText().toString());
                    }
                }
                //请求修改
                WordPlanReq req = new WordPlanReq();
                req.setRecordDayNumber(recordDayNum);
                req.setRememberDayNumber(rememberDayNum);
                wordPlanPresenter.alterWordPlan(getContext(), setRequestBody(req));
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

    @Override
    public void showWordPlanResult(WordPlanBean bean) {
        if (bean != null) {
            recordDayNum = bean.getRecordDayNumber();
            rememberDayNum = bean.getRememberDayNumber();

            int recordNum = StorageDayManager.getInstance(getContext())
                    .getRememberNum(StringConstant.Share_Record_Count);
            int rememberNum = StorageDayManager.getInstance(getContext())
                    .getRememberNum(StringConstant.Share_Remember_Count);
            dayRecordTv.setText(recordNum + "/" + recordDayNum);
            dayRememberTv.setText(rememberNum + "/" + rememberDayNum);
            if (recordNum >= recordDayNum) {
                //记录计划已经满了
                dayRecordCard.setCardBackgroundColor(getContext().getResources().getColor(R.color.color_orange));
            } else {
                dayRecordCard.setCardBackgroundColor(getContext().getResources().getColor(R.color.color_lan1));
            }
            if (rememberNum >= rememberDayNum) {
                //记忆计划已经满了
                dayRememberCard.setCardBackgroundColor(getResources().getColor(R.color.color_orange));
            } else {
                dayRememberCard.setCardBackgroundColor(getResources().getColor(R.color.color_lan1));
            }
        }
    }

    @Override
    public void showAlterWordPlanResult(CommonBean bean) {
        if (bean != null) {
            ToastUtils.show(bean.getMsg());
            //修改了刷新计划卡
            updateDayNumber();
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
