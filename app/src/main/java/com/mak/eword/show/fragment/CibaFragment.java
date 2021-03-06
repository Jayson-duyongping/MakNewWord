package com.mak.eword.show.fragment;

import android.animation.Animator;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mak.eword.HomeActivity;
import com.mak.eword.R;
import com.mak.eword.application.WordApp;
import com.mak.eword.base.BaseFragment;
import com.mak.eword.constant.StringConstant;
import com.mak.eword.mvp.inface.ICibaWordView;
import com.mak.eword.mvp.inface.IWordEditView;
import com.mak.eword.mvp.model.BaseErrorBean;
import com.mak.eword.mvp.model.CibaWordEnBean;
import com.mak.eword.mvp.model.CibaWordZhBean;
import com.mak.eword.mvp.model.MeanBean;
import com.mak.eword.mvp.model.UserBean;
import com.mak.eword.mvp.model.WordBean;
import com.mak.eword.mvp.model.common.CommonBean;
import com.mak.eword.mvp.presenter.CibaWordPresenter;
import com.mak.eword.mvp.presenter.WordEditPresenter;
import com.mak.eword.utils.KeybordUtil;
import com.mak.eword.utils.StringUtil;
import com.mak.eword.utils.ToastUtils;
import com.mak.eword.utils.manager.StorageDayManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 记单词fragment
 */
public class CibaFragment extends BaseFragment implements ICibaWordView, IWordEditView {
    @BindView(R.id.ciba_parent)
    LinearLayout cibaParent;
    @BindView(R.id.search_et)
    EditText searchEt;

    @BindView(R.id.search_kong_ll)
    LinearLayout searchKongLl;

    @BindView(R.id.symbols_container_ll)
    LinearLayout symbolsContainerLl;

    @BindView(R.id.word_name_tv)
    TextView wordNameTv;
    @BindView(R.id.add_record_iv)
    ImageView addRecordIv;
    //用作记录到生词本
    private CibaWordEnBean cibaWordEnBean;

    CibaWordPresenter cibaWordPresenter = new CibaWordPresenter(this, getActivity());
    WordEditPresenter wordEditPresenter = new WordEditPresenter(this, getActivity());
    //音频播放器
    private MediaPlayer mediaPlayer;
    private AnimationDrawable pronAnim;

    @Override
    protected int getLayout() {
        return R.layout.fragment_ciba;
    }

    @Override
    protected void initView() {
        //防止软键盘自动弹出，点击的时候才弹出，很管用
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //padding状态栏高度
        HomeActivity activity = (HomeActivity) getContext();
        cibaParent.setPadding(0, activity.statusBarHeight, 0, 0);
        //设置键盘按钮（搜索）监听
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //不能为空关键字
                    String keyword = searchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtils.show("请输入需要搜索的单词");
                        return false;
                    }
                    //开始搜索
                    cibaWordPresenter.getCibaWordResult(getContext(), keyword);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                releaseAnim();
            }
        });
    }

    /**
     * 播放mp3
     *
     * @param url
     * @param pronIv
     */
    private void playMp3(String url, ImageView pronIv) {
        try {
            releaseAnim();
            //播放声音
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            //播放动画
            pronAnim = (AnimationDrawable) pronIv.getBackground();
            pronAnim.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放动画
     */
    private void releaseAnim() {
        if (pronAnim != null) {
            pronAnim.selectDrawable(0);
            pronAnim.stop();
            pronAnim = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //回收mediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        //释放动画
        releaseAnim();
    }

    @OnClick({R.id.add_record_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_record_iv:
                //添加到生词本
                addNewRecord();
                break;
        }
    }

    /**
     * 添加到生词本
     */
    private void addNewRecord() {
        if (cibaWordEnBean != null) {
            WordBean wordBean = new WordBean();
            wordBean.setContent(cibaWordEnBean.getWord_name());
            List<MeanBean> means = new ArrayList<>();
            //外部循环标记
            lable:
            for (int i = 0; i < cibaWordEnBean.getSymbols().size(); i++) {
                for (int j = 0; j < cibaWordEnBean.getSymbols().get(i).getParts().size(); j++) {
                    if (means.size() == 3) {
                        //只添加三个，跳出外部循环
                        break lable;
                    }
                    MeanBean meanBean = new MeanBean();
                    CibaWordEnBean.SymbolsBean.PartsBean mean
                            = cibaWordEnBean.getSymbols().get(i).getParts().get(j);
                    meanBean.setClass_(mean.getPart());
                    meanBean.setMean_(StringUtil.getBodySym(
                            mean.getMeans().toString()).trim().replace(",", ";"));
                    means.add(meanBean);
                }
            }
            wordBean.setMeans(means);
            //新增单词
            wordEditPresenter.addWord(getContext(), setRequestBody(wordBean));
        }
    }

    /**
     * 加载一个新SymbolsBean
     *
     * @param symbolsBean
     */
    public void addNewMeanEn(CibaWordEnBean.SymbolsBean symbolsBean) {
        final View symView = LayoutInflater.from(getContext()).inflate(R.layout.item_ciba_symbol, null);
        final TextView empronTv = symView.findViewById(R.id.em_pron_tv);
        final TextView amPronTv = symView.findViewById(R.id.am_pron_tv);
        final ImageView pronEmMp3Iv = symView.findViewById(R.id.pron_em_mp3_iv);
        final ImageView pronAmMp3Iv = symView.findViewById(R.id.pron_am_mp3_iv);
        //点击发音的控件
        final RelativeLayout pronEmMp3Rl = symView.findViewById(R.id.pron_em_mp3_rl);
        final RelativeLayout pronAmMp3Rl = symView.findViewById(R.id.pron_am_mp3_rl);
        final LinearLayout meanContainerLl = symView.findViewById(R.id.mean_container_ll);
        //音标（英语有英式和美式两种）
        empronTv.setText(TextUtils.isEmpty(symbolsBean.getPh_en()) ? "--" : "[" + symbolsBean.getPh_en() + "]");
        amPronTv.setText(TextUtils.isEmpty(symbolsBean.getPh_am()) ? "--" : "[" + symbolsBean.getPh_am() + "]");
        //没有语音
        if (TextUtils.isEmpty(symbolsBean.getPh_en_mp3())) {
            pronEmMp3Rl.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(symbolsBean.getPh_am_mp3())) {
            pronAmMp3Iv.setVisibility(View.GONE);
        }
        //意思
        meanContainerLl.removeAllViews();
        for (int i = 0; i < symbolsBean.getParts().size(); i++) {
            View meanView = LayoutInflater.from(getContext()).inflate(R.layout.item_ciba_mean, null);
            TextView classTv = meanView.findViewById(R.id.class_tv);
            TextView meanTv = meanView.findViewById(R.id.mean_tv);
            CibaWordEnBean.SymbolsBean.PartsBean mean = symbolsBean.getParts().get(i);
            classTv.setText(mean.getPart());
            meanTv.setText(StringUtil.getBodySym(mean.getMeans().toString()).trim().replace(",", ";"));
            //动画一下
            YoYo.with(Techniques.BounceInLeft).duration(500).repeat(0).playOn(meanView);
            meanContainerLl.addView(meanView);
        }
        pronEmMp3Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMp3(symbolsBean.getPh_en_mp3(), pronEmMp3Iv);
            }
        });
        pronAmMp3Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMp3(symbolsBean.getPh_am_mp3(), pronAmMp3Iv);
            }
        });
        symbolsContainerLl.addView(symView);
    }

    /**
     * 加载一个新SymbolsBean
     *
     * @param symbolsBean
     */
    public void addNewMeanZh(CibaWordZhBean.SymbolsBean symbolsBean) {
        final View symView = LayoutInflater.from(getContext()).inflate(R.layout.item_ciba_symbol, null);
        final TextView convertZeTv = symView.findViewById(R.id.convert_ze_tv);
        final TextView empronTv = symView.findViewById(R.id.em_pron_tv);
        final ImageView pronEmMp3Iv = symView.findViewById(R.id.pron_em_mp3_iv);
        //点击发音的控件
        final RelativeLayout pronEmMp3Rl = symView.findViewById(R.id.pron_em_mp3_rl);
        final LinearLayout secondIgnoreLl = symView.findViewById(R.id.second_ignore_ll);
        final LinearLayout meanContainerLl = symView.findViewById(R.id.mean_container_ll);
        //音标(中文只有一个)
        convertZeTv.setText("中");
        empronTv.setText(TextUtils.isEmpty(symbolsBean.getWord_symbol()) ? "--" : "[" + symbolsBean.getWord_symbol() + "]");
        secondIgnoreLl.setVisibility(View.GONE);
        //没有语音
        if (TextUtils.isEmpty(symbolsBean.getSymbol_mp3())) {
            pronEmMp3Rl.setVisibility(View.GONE);
        }
        //意思
        meanContainerLl.removeAllViews();
        for (int i = 0; i < symbolsBean.getParts().size(); i++) {
            View meanView = LayoutInflater.from(getContext()).inflate(R.layout.item_ciba_mean, null);
            TextView classTv = meanView.findViewById(R.id.class_tv);
            TextView meanTv = meanView.findViewById(R.id.mean_tv);
            CibaWordZhBean.SymbolsBean.PartsBean mean = symbolsBean.getParts().get(i);
            classTv.setText(mean.getPart_name());
            StringBuilder sb = new StringBuilder();
            if (mean.getMeans() == null) {
                continue;
            }
            for (int j = 0; j < mean.getMeans().size(); j++) {
                sb.append(mean.getMeans().get(j).getWord_mean() + "; ");
            }
            meanTv.setText(sb.toString());
            //动画一下
            YoYo.with(Techniques.BounceInLeft).duration(500).repeat(0).playOn(meanView);
            meanContainerLl.addView(meanView);
        }
        pronEmMp3Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMp3(symbolsBean.getSymbol_mp3(), pronEmMp3Iv);
            }
        });
        symbolsContainerLl.addView(symView);
    }

    /**
     * 根据英文查询出来的结果
     *
     * @param bean
     */
    @Override
    public void showCibaWordEnResult(CibaWordEnBean bean) {
        if (bean != null) {
            if (TextUtils.isEmpty(bean.getWord_name())) {
                return;
            }
            searchKongLl.setVisibility(View.GONE);
            cibaWordEnBean = bean;
            //名字
            wordNameTv.setText(bean.getWord_name());
            //只有英文才能够加入生词本
            addRecordIv.setVisibility(View.VISIBLE);
            //移除all
            symbolsContainerLl.removeAllViews();
            //动画一下
            YoYo.with(Techniques.FadeIn).duration(500).repeat(0)
                    .onEnd(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            //动画结束
                            for (int i = 0; i < bean.getSymbols().size(); i++) {
                                addNewMeanEn(bean.getSymbols().get(i));
                            }
                        }
                    }).playOn(wordNameTv);
            //如果软键盘打开就让他关闭
            if (KeybordUtil.isSoftInputShow(getActivity())) {
                KeybordUtil.closeKeybord(searchEt, getContext());
            }
        }
    }

    /**
     * 根据中文查出来的结果
     *
     * @param bean
     */
    @Override
    public void showCibaWordZhResult(CibaWordZhBean bean) {
        if (bean != null) {
            if (TextUtils.isEmpty(bean.getWord_name())) {
                return;
            }
            searchKongLl.setVisibility(View.GONE);
            cibaWordEnBean = null;
            //名字
            wordNameTv.setText(bean.getWord_name());
            //只有英文才能够加入生词本
            addRecordIv.setVisibility(View.GONE);
            //移除all
            symbolsContainerLl.removeAllViews();
            //动画一下
            YoYo.with(Techniques.FadeIn).duration(500).repeat(0)
                    .onEnd(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            //动画结束
                            for (int i = 0; i < bean.getSymbols().size(); i++) {
                                addNewMeanZh(bean.getSymbols().get(i));
                            }
                        }
                    }).playOn(wordNameTv);
            //如果软键盘打开就让他关闭
            if (KeybordUtil.isSoftInputShow(getActivity())) {
                KeybordUtil.closeKeybord(searchEt, getContext());
            }
        }
    }

    @Override
    public void showWordEditResult(CommonBean bean) {
        if (bean != null && "200".equals(bean.getCode())) {
            ToastUtils.show("添加到生词本");
            //刷新界面
            EventBus.getDefault().post(StringConstant.Event_RefreshWordList);
            //记录本地一个
            StorageDayManager.getInstance(getContext())
                    .handlerDayNumber(StringConstant.Share_Record_Count);
            //更新记录个数
            EventBus.getDefault().post(StringConstant.Event_UpdateDayNumber);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(BaseErrorBean bean) {

    }
}
