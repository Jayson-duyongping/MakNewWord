package com.mak.eword.base;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;


import com.jayson.commonlib.widget.smartrefresh.SmartRefreshLayout;
import com.jayson.commonlib.widget.smartrefresh.api.RefreshLayout;
import com.jayson.commonlib.widget.smartrefresh.listener.OnLoadMoreListener;
import com.jayson.commonlib.widget.smartrefresh.listener.OnRefreshListener;
import com.mak.eword.R;
import com.mak.eword.widget.LoadingLayout;

import butterknife.BindView;

/**
 * Created by duyongping on 2018/4/25.
 * Content: 所有的列表Fragment可以继承此类
 */
public abstract class BaseListFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.list_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.load_layout)
    LoadingLayout loadingLayout;

    //当前页
    protected int currentPage = 1;
    //
    protected Context mContext;
    //是否是刷新页面
    protected boolean isRefresh;
    //当前是否是列表状态
    protected boolean isListContent;
    //是否自动刷新，默认是true
    protected boolean isAutoFresh = true;
    //是否可以加载更多，默认是
    protected boolean isCanLoadMore = true;

    @Override
    protected int getLayout() {
        return R.layout.base_list_view;
    }

    @Override
    protected void initView() {
        mContext = getActivity();
        //配置刷新控件
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        //配置RecyclerView
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
    }

    /**
     * 设置列表背景颜色
     *
     * @param color
     */
    public void setListBackColor(int color) {
        if (mRecyclerView != null) {
            mRecyclerView.setBackgroundColor(color);
        }
    }


    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void initData() {
        initInstance();
        //防止notifyDataSetChanged（）刷新闪烁，需重写getItemId
        if (!getAdapter().hasObservers()) {
            getAdapter().setHasStableIds(true);
        }
        mRecyclerView.setAdapter(getAdapter());
        initListenner();
        //自动刷新
        if (isAutoFresh) {
            mSmartRefreshLayout.autoRefresh();
        }
    }

    /**
     * 去刷新列表
     */
    protected void autoRefreshList() {
        //自动刷新
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.autoRefresh();
        }
    }

    /**
     * 设置监听
     */
    private void initListenner() {
        //列表点击
        ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setItemClickListener(position);
            }
        });
    }

    /**
     * 创建实例
     */
    protected abstract void initInstance();

    /**
     * 获取adapter
     */
    protected abstract BaseRecyclerAdapter getAdapter();

    /**
     * 点击事件
     */
    protected abstract void setItemClickListener(int position);

    /**
     * 获取网络数据
     */
    protected abstract void getHttpData();


    /**
     * 刷新界面
     *
     * @param isSuc     是否请求成功
     * @param isRefresh 是否是刷新
     * @param emptyType 显示空页面类型
     */
    protected void notifyAdapterUi(boolean isRefresh, boolean isSuc, int emptyType) {
        if (mSmartRefreshLayout == null) {
            return;
        }
        if (isRefresh) {
            mSmartRefreshLayout.finishRefresh();
        } else {
            mSmartRefreshLayout.finishLoadMore();
        }
        if (mRecyclerView.getAdapter().getItemCount() == 0) {
            if (!isSuc) {
                //网络等原因失败，显示这个空页面
                showNetError();
            } else {
                //其余正常，显示这个空页面
                showEmpty(emptyType);
            }
        } else {
            showContent();
            //置顶
            //mRecyclerView.getLayoutManager().scrollToPosition(0);
        }
    }

    /**
     * 列表置顶
     */
    protected void scrollToTop() {
        mRecyclerView.getLayoutManager().scrollToPosition(0);
    }

    /**
     * 设置没有更多
     */
    protected void setNoMore() {
        //没有更多数据，设置后不可加载更多了，需要在刷新的时候再次设置可以加载
        if (mSmartRefreshLayout == null) {
            return;
        }
        mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //又可以加载更多了
        refreshLayout.setNoMoreData(false);
        currentPage = 1;
        //是刷新
        isRefresh = true;
        //网络请求
        getHttpData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage++;
                //不是刷新
                isRefresh = false;
                //网络请求
                getHttpData();
            }
        }, 500);
    }

    /**
     * 显示加载框
     */
    protected void showLoadView() {
        isListContent = false;
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
    }

    /**
     * 网络等原因失败，显示这个空页面
     */
    protected void showNetError() {
        isListContent = false;
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
    }

    /**
     * 其余正常，显示这个空页面
     *
     * @param emptyType
     */
    protected void showEmpty(int emptyType) {
        isListContent = false;
        loadingLayout.showEmpty(1);
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(false);
    }

    /**
     * 显示有数据
     */
    protected void showContent() {
        isListContent = true;
        loadingLayout.showContent();
        mSmartRefreshLayout.setEnableRefresh(true);
        if (isCanLoadMore) {
            mSmartRefreshLayout.setEnableLoadMore(true);
        } else {
            mSmartRefreshLayout.setEnableLoadMore(false);
        }
    }

    /**
     * 获取mSmartRefreshLayout
     */
    protected SmartRefreshLayout getRefreshLayout() {
        return mSmartRefreshLayout;
    }
}
