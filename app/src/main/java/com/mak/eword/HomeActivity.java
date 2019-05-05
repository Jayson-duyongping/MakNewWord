package com.mak.eword;

import android.Manifest;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mak.eword.application.AppDownloadManager;
import com.mak.eword.base.BaseFragmentActivity;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.show.fragment.CibaFragment;
import com.mak.eword.show.fragment.MineFragment;
import com.mak.eword.show.fragment.SentenceFragment;
import com.mak.eword.show.fragment.WordFragment;
import com.mak.eword.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class HomeActivity extends BaseFragmentActivity {

    @BindView(R.id.fragment_vp)
    ViewPager mViewPager;
    @BindView(R.id.tabs_rg)
    RadioGroup mTabRadioGroup;

    public int statusBarHeight;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    SentenceFragment sentenceFragment;
    WordFragment wordFragment;
    CibaFragment cibaFragment;
    MineFragment mineFragment;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //获取状态栏高度
        statusBarHeight = getStatusBarHeight(this);
        // init fragment
        mFragments = new ArrayList<>(3);
        sentenceFragment = new SentenceFragment();
        wordFragment = new WordFragment();
        cibaFragment = new CibaFragment();
        mineFragment = new MineFragment();
        mFragments.add(sentenceFragment);
        mFragments.add(wordFragment);
        mFragments.add(cibaFragment);
        mFragments.add(mineFragment);
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        //6.0以上动态获取权限(RxPermissions内部已经判断Build.VERSION.SDK_INT >= 23)
        getPermission();
        //不使用定制组件，直接调用则可初始化
        new AppDownloadManager(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(mPageChangeListener);
        }
        EventBus.getDefault().unregister(this);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            YoYo.with(Techniques.Pulse).duration(300).repeat(0).playOn(radioButton);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    //smoothScroll改成false，解决多个页面快速滑动的问题
                    mViewPager.setCurrentItem(i, false);
                    return;
                }
            }
        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }


    /**
     * 接收总线事件，注意：不能传入基础数据类型
     *
     * @param eventStr
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100) //在ui线程执行 优先级100
    public void onHomeBus(String eventStr) {
        switch (eventStr) {
            //刷新单词列表
            case StringConstant.Event_RefreshWordList:
                if (wordFragment != null) {
                    wordFragment.refreshList();
                }
                break;
            //刷新计划卡
            case StringConstant.Event_UpdateDayNumber:
                if (mineFragment != null) {
                    mineFragment.updateDayNumber();
                }
                break;
        }
    }


    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    /**
     * 处理返回键事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            //"再按一次退出应用"
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


    /**
     * 动态获取权限
     */
    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            //ToastUtils.show("已授权");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            ToastUtils.show("未授权权限，部分功能不能使用");
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                            ToastUtils.show("未授权权限，请手动开启权限");
                        }
                    }
                });
    }
}
