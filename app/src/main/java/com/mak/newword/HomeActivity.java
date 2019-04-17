package com.mak.newword;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.constant.StringConstant;
import com.mak.newword.show.fragment.CibaFragment;
import com.mak.newword.show.fragment.MineFragment;
import com.mak.newword.show.fragment.SentenceFragment;
import com.mak.newword.show.fragment.WordFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
        EventBus.getDefault().unregister(this);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            YoYo.with(Techniques.Bounce).duration(300).repeat(0).playOn(radioButton);
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
}
