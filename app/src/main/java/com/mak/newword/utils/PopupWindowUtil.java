package com.mak.newword.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mak.newword.utils.manager.DisplayCutoutManager;


/**
 * Created by duyongping on 2018/7/9.
 * Content:
 */

public class PopupWindowUtil {
    /**
     * 设置需要背景透明（从控件底部出现）
     *
     * @param view
     * @param button
     * @return
     */
    public static PopupWindow getPopWindowForView(Context context, View view, final View button) {
        PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, false
        );
        popupWindow.setOutsideTouchable(false);
        //popupWindow.setAnimationStyle(R.style.popStyle);
        //设置背景透明度
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //解决显示问题(7.0, 7.1, 8.0)
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            button.getGlobalVisibleRect(visibleFrame);
            int height = button.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            //判断小米全面屏手机的虚拟按键是否显示，也就是判断是否为全面屏
            boolean isFull = Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
            if (isFull) {
                //因为小米全面隐藏了虚拟按键，还是会获取高度
                height += DisplayCutoutManager.getNavigationBarHeight(context);
            }
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(button, 0, 0);
        } else {
            popupWindow.showAsDropDown(button, 0, 0);
        }
        popupWindow.showAsDropDown(button);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        return popupWindow;
    }

    /**
     * 设置popwindow显示在屏幕正中央
     *
     * @param mContext
     * @param view     @return
     */
    public static PopupWindow getPopWindowForCenter(final Activity mContext, View view) {
        final PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true
        );
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setAnimationStyle(R.style.popStyle);
        //设置背景透明度
        WindowManager.LayoutParams ll =
                mContext.getWindow().getAttributes();
        ll.alpha = 0.45f;
        mContext.getWindow().setAttributes(ll);

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams ll =
                        mContext.getWindow().getAttributes();
                ll.alpha = 1f;
                mContext.getWindow().setAttributes(ll);
                //设置View可见
            }
        });
        return popupWindow;
    }
}
