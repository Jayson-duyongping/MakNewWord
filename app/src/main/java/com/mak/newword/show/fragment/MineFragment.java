package com.mak.newword.show.fragment;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragment;
import com.mak.newword.greendao.service.UserService;
import com.mak.newword.mvp.model.UserBean;
import com.mak.newword.utils.PopupWindowUtil;

import java.util.List;

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
        long count = UserService.getInstance(getContext()).queryUserListCount();
        if (count == 0) {
            //新建一个用户
            userBean = new UserBean();
            userBean.setName("英语学习者");
            userBean.setRecordDayNum(0);
            userBean.setRememberDayNum(0);
            userBean.setRecordTotalNum(50);
            userBean.setRememberTotalNum(30);
            UserService.getInstance(getContext()).insertUser(userBean);
        } else {
            //获取数据库的用户
            List<UserBean> users = UserService.getInstance(getContext()).queryUserList();
            userBean = users.get(0);
        }
        userNameTv.setText(userBean.getName());
        dayRecordTv.setText("0/" + userBean.getRecordTotalNum());
        dayRememberTv.setText("0/" + userBean.getRememberTotalNum());
    }

    @OnClick({R.id.day_record_card, R.id.day_remember_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.day_record_card:
                showPlanPop(0);
                break;
            case R.id.day_remember_card:
                showPlanPop(1);
                break;
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
                        dayRecordTv.setText("0/" + recordNum);
                    }
                } else {
                    if (!TextUtils.isEmpty(planNumEt.getText().toString())) {
                        rememberNum = Integer.parseInt(planNumEt.getText().toString());
                        userBean.setRememberTotalNum(rememberNum);
                        UserService.getInstance(getContext()).updateUser(userBean);
                        dayRememberTv.setText("0/" + rememberNum);
                    }
                }
            }
        });
        if (planPop == null) {
            planPop = PopupWindowUtil.getPopWindowForCenter(getActivity(), planPopView);
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
