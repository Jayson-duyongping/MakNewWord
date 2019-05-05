package com.mak.eword.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.mak.eword.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duyongping on 2018/4/25.
 * Content: 自定义几种空页面
 */

public class LoadingLayout extends FrameLayout {

    /**
     * 5种空页面
     */
    public static final int EmptyData = 1;
    public static final int EmptyCommons = 2;
    public static final int EmptyMsg = 3;
    public static final int EmptyOrder = 4;
    public static final int EmptySearch = 5;

    public interface OnInflateListener {
        void onInflate(View inflated);
    }

    public static LoadingLayout wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static LoadingLayout wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }

    public static LoadingLayout wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (view == null) {
            throw new RuntimeException("parent view can not be null");
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        LoadingLayout layout = new LoadingLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }

    OnClickListener mRetryButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mRetryListener != null) {
                mRetryListener.onClick(v);
            }
        }
    };
    OnClickListener mGoButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mGoListener != null) {
                mGoListener.onClick(v);
            }
        }
    };
    OnClickListener mRetryListener;
    OnClickListener mGoListener;

    LoadingLayout.OnInflateListener mOnEmptyInflateListener;
    LoadingLayout.OnInflateListener mOnErrorInflateListener;

    int mEmptyResId = NO_ID, mLoadingResId = NO_ID, mErrorResId = NO_ID;
    int mEmptyCommonsResId = NO_ID, mEmptyMsgResId = NO_ID, mEmptyOrderResId = NO_ID, mEmptySearchResId = NO_ID;
    int mContentId = NO_ID;

    Map<Integer, View> mLayouts = new HashMap<>();


    public LoadingLayout(Context context) {
        this(context, null, R.attr.styleLoadingLayout);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.styleLoadingLayout);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, defStyleAttr, R.style.LoadingLayout_Style);

        mEmptyResId = a.getResourceId(R.styleable.LoadingLayout_llEmptyResId, R.layout._loading_empty);
        mLoadingResId = a.getResourceId(R.styleable.LoadingLayout_llLoadingResId, R.layout._loading_empty);
        mErrorResId = a.getResourceId(R.styleable.LoadingLayout_llErrorResId, R.layout._loading_empty);

        mEmptyCommonsResId = a.getResourceId(R.styleable.LoadingLayout_llEmptyCommonsResId, R.layout._loading_empty);
        mEmptyMsgResId = a.getResourceId(R.styleable.LoadingLayout_llEmptyMsgResId, R.layout._loading_empty);
        mEmptyOrderResId = a.getResourceId(R.styleable.LoadingLayout_llEmptyOrderResId, R.layout._loading_empty);
        mEmptySearchResId = a.getResourceId(R.styleable.LoadingLayout_llEmptySearchResId, R.layout._loading_empty);
        a.recycle();
    }

    LayoutInflater mInflater;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
    }

    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }

    public LoadingLayout setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }

    public LoadingLayout setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }

    public LoadingLayout setRetryListener(OnClickListener listener) {
        mRetryListener = listener;
        return this;
    }

    public LoadingLayout setGoListener(OnClickListener listener) {
        mGoListener = listener;
        return this;
    }

    /**
     * 显示加载页面
     */
    public void showLoading() {
        show(mLoadingResId);
    }

    //空页面类型，一般就用1，2、3、4、5为扩展
    private int EmptyType = EmptyData;

    /**
     * 显示空页面
     */
    public void showEmpty(int type) {
        EmptyType = type;
        switch (EmptyType) {
            case EmptyData:
                show(mEmptyResId);
                break;
            case EmptyCommons:
                show(mEmptyCommonsResId);
                break;
            case EmptyMsg:
                show(mEmptyMsgResId);
                break;
            case EmptyOrder:
                show(mEmptyOrderResId);
                break;
            case EmptySearch:
                show(mEmptySearchResId);
                break;
        }
    }

    public void showError() {
        show(mErrorResId);
    }

    public void showContent() {
        show(mContentId);
    }

    private void show(int layoutId) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(VISIBLE);
    }

    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    ImageView animView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private View layout(int layoutId) {
        //包含的布局
        if (mLayouts.containsKey(layoutId)) {
            if (layoutId == mEmptyResId) {
                emptyTypePage(mLayouts.get(layoutId));
            } else if (layoutId == mLoadingResId) {
            }
            return mLayouts.get(layoutId);
        }
        //不包含的布局
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);
        if (layoutId == mEmptyResId) {
            emptyTypePage(layout);
        } else if (layoutId == mErrorResId) {
            TextView btn = layout.findViewById(R.id.retry_button);
            if (btn != null) {
                btn.setOnClickListener(mRetryButtonClickListener);
            }
            if (mOnErrorInflateListener != null) {
                mOnErrorInflateListener.onInflate(layout);
            }
        } else if (layoutId == mLoadingResId) {
        }
        return layout;
    }

    /**
     * 空页面的几种情况
     *
     * @param layout
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void emptyTypePage(View layout) {
        if (mOnEmptyInflateListener != null) {
            mOnEmptyInflateListener.onInflate(layout);
        }
    }

    /**
     * 获取需要播放的动画资源
     */
    private int[] getRes(int res) {
        TypedArray typedArray = getResources().obtainTypedArray(res);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }

}
