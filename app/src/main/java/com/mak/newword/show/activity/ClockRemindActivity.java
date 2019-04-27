package com.mak.newword.show.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.mak.newword.R;
import com.mak.newword.base.BaseFragmentActivity;
import com.mak.newword.constant.StringConstant;
import com.mak.newword.receiver.AlarmReceiver;
import com.mak.newword.utils.SharedPreHelper;
import com.mak.newword.widget.HeaderView;

import java.util.Calendar;

import butterknife.BindView;

/**
 * 闹钟设置
 * 参考https://blog.csdn.net/zdc9023/article/details/78861724
 */
@SuppressWarnings("deprecation")
public class ClockRemindActivity extends BaseFragmentActivity {
    @BindView(R.id.headerView)
    HeaderView headerView;
    @BindView(R.id.timer_picker)
    TimePicker timePicker;
    @BindView(R.id.clock_switch)
    Switch clockSwich;
    //闹铃相关
    AlarmManager alarmManager;
    PendingIntent piIntent;
    long clockTime;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_clock_remind;
    }

    @Override
    protected void initView() {
        mStatusBar(R.color.head_back);
        headerView.setCenterText("闹钟提醒");
        headerView.setBackLnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        clockSwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //开
                    SharedPreHelper.getInstance(mContext).put(StringConstant.Share_Clock_On, true);
                    SharedPreHelper.getInstance(mContext).put(StringConstant.Share_Clock_Time, getTimeDiff());
                    setAlarm();
                } else {
                    //关
                    SharedPreHelper.getInstance(mContext).put(StringConstant.Share_Clock_On, false);
                    cancelAlarm();
                }
            }
        });
    }

    @Override
    protected void initData() {
        //初始化Alarm
        initAlarm();
        //闹铃状态
        Boolean clockOn = (Boolean) SharedPreHelper.getInstance(mContext)
                .getSharedPreference(StringConstant.Share_Clock_On, false);
        clockSwich.setChecked(clockOn);
        Long time = (Long) SharedPreHelper.getInstance(mContext)
                .getSharedPreference(StringConstant.Share_Clock_Time, 0L);
        if (time != 0) {

        }
    }

    /**
     * 初始化Alarm
     */
    private void initAlarm() {
        piIntent = PendingIntent.getBroadcast(mContext, 0, getMsgIntent(), 0);
        clockTime = System.currentTimeMillis();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    /**
     * 闹铃消息的广播
     *
     * @return
     */
    private Intent getMsgIntent() {
        //AlarmReceiver 为广播在下面代码中
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction(AlarmReceiver.BC_ACTION);
        intent.putExtra("msg", "闹钟开启");
        return intent;
    }

    /**
     * 设置Alarm(设置定时执行的任务)
     */
    private void setAlarm() {
        //android Api的改变不同版本中设置有所不同
        if (Build.VERSION.SDK_INT < 19) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, getTimeDiff(), piIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeDiff(), piIntent);
        }
    }

    /**
     * 取消Alarm
     */
    private void cancelAlarm() {
        alarmManager.cancel(piIntent);
    }

    /**
     * 设置当天的时间
     *
     * @return
     */
    public long getTimeDiff() {
        //这里设置的是当天的HH：mm分，注意精确到秒，时间可以自由设置
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        ca.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        ca.set(Calendar.SECOND, 0);
        return ca.getTimeInMillis();
    }
}
