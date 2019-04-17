package com.mak.newword.show.fragment;

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
import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.application.WordApp;
import com.mak.newword.base.BaseFragment;
import com.mak.newword.constant.StringConstant;
import com.mak.newword.greendao.service.UserService;
import com.mak.newword.mvp.model.UserBean;
import com.mak.newword.show.activity.CommentActivity;
import com.mak.newword.utils.PopupWindowUtil;
import com.mak.newword.utils.manager.StorageDayManager;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的Fragment
 */
public class MineFragment extends BaseFragment {
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

    private int recordNum = 50;
    private int rememberNum = 30;

    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        //pading状态栏高度
        HomeActivity activity = (HomeActivity) getContext();
        mineParent.setPadding(0, activity.statusBarHeight, 0, 0);
    }

    @Override
    protected void initData() {
        //获取用户信息
        userBean = WordApp.instance.getUser();
        //设置
        userNameTv.setText(userBean.getName());
        updateDayNumber();
    }

    @OnClick({R.id.day_record_card, R.id.day_remember_card, R.id.comment_ll, R.id.setting_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.day_record_card:
                showPlanPop(0);
                break;
            case R.id.day_remember_card:
                showPlanPop(1);
                break;
            case R.id.comment_ll:
                Intent intent = new Intent(getContext(), CommentActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.setting_ll:
                break;
        }
    }

    /**
     * 刷新计划卡
     */
    public void updateDayNumber() {
        int recordNum = StorageDayManager.getInstance(getContext())
                .getRememberNum(StringConstant.Share_Record_Count);
        int rememberNum = StorageDayManager.getInstance(getContext())
                .getRememberNum(StringConstant.Share_Remember_Count);
        dayRecordTv.setText(recordNum + "/" + userBean.getRecordTotalNum());
        dayRememberTv.setText(rememberNum + "/" + userBean.getRememberTotalNum());
        if (recordNum >= userBean.getRecordTotalNum()) {
            //记录计划已经满了
            dayRecordCard.setCardBackgroundColor(getContext().getResources().getColor(R.color.color_orange));
        } else {
            dayRecordCard.setCardBackgroundColor(getContext().getResources().getColor(R.color.color_lan1));
        }
        if (rememberNum >= userBean.getRememberTotalNum()) {
            //记忆计划已经满了
            dayRememberCard.setCardBackgroundColor(getResources().getColor(R.color.color_orange));
        } else {
            dayRememberCard.setCardBackgroundColor(getResources().getColor(R.color.color_lan1));
        }
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
                        recordNum = Integer.parseInt(planNumEt.getText().toString());
                        userBean.setRecordTotalNum(recordNum);
                        UserService.getInstance(getContext()).updateUser(userBean);
                        updateDayNumber();
                    }
                } else {
                    if (!TextUtils.isEmpty(planNumEt.getText().toString())) {
                        rememberNum = Integer.parseInt(planNumEt.getText().toString());
                        userBean.setRememberTotalNum(rememberNum);
                        UserService.getInstance(getContext()).updateUser(userBean);
                        updateDayNumber();
                    }
                }
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
}
