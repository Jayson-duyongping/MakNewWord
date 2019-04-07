package com.jayson.commonlib.widget.smartrefresh.listener;

import android.support.annotation.NonNull;

import com.jayson.commonlib.widget.smartrefresh.api.RefreshLayout;


/**
 * 刷新监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
