package com.mak.newword.receiver;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 创建人：jayson
 * 创建时间：2019/4/27
 * 创建内容：闹钟广播
 */
public class AlarmReceiver extends BroadcastReceiver {
    private String TAG = this.getClass().getSimpleName();
    public static final String BC_ACTION = "com.mak.newword.BC_ACTION";
    private AlertDialog.Builder builder;
    CountDownTimer timer;
    //创建震动服务对象
    private Vibrator mVibrator;

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        Log.i(TAG, "get Receiver msg :" + msg);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        //获取手机震动服务
        mVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        //弹窗提示
        showConfirmDialog(context);
    }

    private void showConfirmDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle("简记生词本提示")
                .setMessage("你的单词计划还没有完成哦(将在10秒后关闭)")
                .setCancelable(false)
                .setPositiveButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (timer != null) timer.cancel();
                                //取消震动
                                mVibrator.cancel();
                            }
                        });
        setShowDialogType(context, builder.create());
    }

    private void setShowDialogType(Context context, AlertDialog alertDialog) {
        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        alertDialog.getWindow().setType(type);
        alertDialog.show();
        //开启倒计时，并设置倒计时时间（秒）
        startCountDownTimer(context, alertDialog, 10);
        //设置震动周期，数组表示时间：等待+执行，单位是毫秒，下面操作代表:等待100，执行100，等待100，执行1000，
        //后面的数字如果为-1代表不重复，只执行一次，其他代表会重复，0代表从数组的第0个位置开始
        mVibrator.vibrate(new long[]{1000,50,1000,50,1000}, 0);
    }

    private void startCountDownTimer(final Context context, final AlertDialog alertDialog, int time) {
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //倒计时提示文字
                Log.i(TAG, "onTick time :" + millisUntilFinished);
                alertDialog.setMessage("你的单词计划还没有完成哦(将在" + (millisUntilFinished / 1000) + "秒后关闭)");
            }

            @Override
            public void onFinish() {
                //倒计时结束
                Log.i(TAG, "倒计时结束！");
                alertDialog.dismiss();
                //取消震动
                mVibrator.cancel();
            }
        };
        timer.start();
    }
}
