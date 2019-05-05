package com.mak.eword.show.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mak.eword.R;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.mvp.inface.IUserRegisterView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.UserBean;
import com.mak.eword.mvp.model.common.CommonBean;
import com.mak.eword.mvp.presenter.UserRegisterPresenter;
import com.mak.eword.utils.ToastUtils;
import com.mak.eword.widget.HeaderView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseFragmentActivity implements IUserRegisterView {
    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.username_et)
    EditText usernameEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.password2_et)
    EditText password2Et;
    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.register_btn)
    Button registerBtn;
    //presenter
    private UserRegisterPresenter userRegisterPresenter
            = new UserRegisterPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("注册");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.login_tv, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_tv:
                finish();
                break;
            case R.id.register_btn:
                registerUser();
                break;
        }
    }

    /**
     * 注册用户
     */
    private void registerUser() {
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        String password2 = password2Et.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.show("请填写账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show("请填写密码");
            return;
        }
        if (TextUtils.isEmpty(password2)) {
            ToastUtils.show("请确认密码");
            return;
        }
        if (!password.equals(password2)) {
            ToastUtils.show("填写的密码不一致");
            return;
        }
        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password);
        //网络请求
        userRegisterPresenter.getUserRegisterResult(mContext, setRequestBody(user));
    }

    @Override
    public void showUserRegisterResult(CommonBean bean) {
        if (bean != null) {
            //返回注册结果
            ToastUtils.show(bean.getMsg());
            //处理
            if ("200".equals(bean.getCode())) {
                finish();
            }
        }
    }

    @Override
    public void showToast(BaseErrorBean bean) {
        if(bean!=null){
            ToastUtils.show("请求失败");
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }
}
