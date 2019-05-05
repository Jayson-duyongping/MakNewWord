package com.mak.eword.show.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mak.eword.HomeActivity;
import com.mak.eword.R;
import com.mak.eword.application.AppManager;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.mvp.inface.IUserLoginView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.UserBean;
import com.mak.eword.mvp.model.request.LoginReq;
import com.mak.eword.mvp.presenter.UserLoginPresenter;
import com.mak.eword.utils.SharedPreHelper;
import com.mak.eword.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseFragmentActivity implements IUserLoginView {

    @BindView(R.id.login_iv)
    ImageView loginIv;
    @BindView(R.id.username_et)
    EditText usernameEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.login_btn)
    Button loginBtn;

    private UserLoginPresenter userLoginPresenter = new UserLoginPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        YoYo.with(Techniques.BounceIn).duration(2000).repeat(0).playOn(loginIv);
        //这个页面关闭所有页面，作为退出登录效果更平滑一些
        AppManager.getAppManager().finishAllActivity();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.login_btn, R.id.register_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                LoginReq loginReq = new LoginReq();
                if (TextUtils.isEmpty(usernameEt.getText().toString())) {
                    ToastUtils.show("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(passwordEt.getText().toString())) {
                    ToastUtils.show("请输入密码");
                    return;
                }
                loginReq.setUsername(usernameEt.getText().toString());
                loginReq.setPassword(passwordEt.getText().toString());
                userLoginPresenter.getUserLoginResult(mContext, setRequestBody(loginReq));
                break;
            case R.id.register_tv:
                //跳转到注册
                Intent registerIntent = new Intent(mContext, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }

    @Override
    public void showUserLoginResult(UserBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getUsername())) {
            //保存用户
            SharedPreHelper.getInstance(mContext).saveBeanByFastJson(StringConstant.Share_User, bean);
            //登录成功
            Intent intent = new Intent(mContext, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showToast(BaseErrorBean bean) {
        if (bean != null) {
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
