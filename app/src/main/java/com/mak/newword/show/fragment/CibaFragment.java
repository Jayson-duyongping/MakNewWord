package com.mak.newword.show.fragment;

import android.animation.Animator;
import android.content.Intent;
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
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragment;
import com.mak.newword.constant.StringConstant;
import com.mak.newword.greendao.service.WordService;
import com.mak.newword.mvp.inface.ICibaWordView;
import com.mak.newword.mvp.model.BaseErrorBean;
import com.mak.newword.mvp.model.CibaWordEnBean;
import com.mak.newword.mvp.model.CibaWordZhBean;
import com.mak.newword.mvp.model.MeanBean;
import com.mak.newword.mvp.model.WordBean;
import com.mak.newword.mvp.presenter.CibaWordPresenter;
import com.mak.newword.show.activity.AboutActivity;
import com.mak.newword.show.activity.CommentActivity;
import com.mak.newword.utils.KeybordUtil;
import com.mak.newword.utils.StringUtil;
import com.mak.newword.utils.ToastUtils;
import com.mak.newword.utils.manager.StorageDayManager;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 记单词fragment
 */
public class CibaFragment extends BaseFragment implements ICibaWordView {
    @BindView(R.id.ciba_parent)
    LinearLayout cibaParent;
    @BindView(R.id.search_et)
    EditText searchEt;

    @BindView(R.id.symbols_container_ll)
    LinearLayout symbolsContainerLl;

    @BindView(R.id.word_name_tv)
    TextView wordNameTv;
    @BindView(R.id.add_record_iv)
    ImageView addRecordIv;
    //用作记录到生词本
    private CibaWordEnBean cibaWordEnBean;

    CibaWordPresenter cibaWordPresenter = new CibaWordPresenter(this, getActivity());
    //音频播放器
    private MediaPlayer mediaPlayer;

    @Override
    protected int getLayout() {
        return R.layout.fragment_ciba;
    }

    @Override
    protected void initView() {
        //防止软键盘自动弹出，点击的时候才弹出，很管用
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //pading状态栏高度
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
    }

    /**
     * 播放mp3
     *
     * @param url
     */
    private void playMp3(String url) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        try {
            mediaPlayer.setDataSource(" http://www.ytmp3.cn/down/60548.mp3");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
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
            List<WordBean> existList = WordService.getInstance(getContext())
                    .queryWordList(cibaWordEnBean.getWord_name());
            if (existList.size() > 0) {
                ToastUtils.show("生词本中已存在此单词");
                return;
            }
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
            //保存数据库
            WordService.getInstance(getContext()).insertWord(wordBean);
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
        final LinearLayout meanContainerLl = symView.findViewById(R.id.mean_container_ll);
        //音标（英语有英式和美式两种）
        empronTv.setText(TextUtils.isEmpty(symbolsBean.getPh_en()) ? "--" : "[" + symbolsBean.getPh_en() + "]");
        amPronTv.setText(TextUtils.isEmpty(symbolsBean.getPh_am()) ? "--" : "[" + symbolsBean.getPh_am() + "]");
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
        pronEmMp3Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMp3(symbolsBean.getPh_en_mp3());
            }
        });
        pronAmMp3Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMp3(symbolsBean.getPh_am_mp3());
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
        final LinearLayout secondIgnoreLl = symView.findViewById(R.id.second_ignore_ll);
        final LinearLayout meanContainerLl = symView.findViewById(R.id.mean_container_ll);
        //音标(中文只有一个)
        convertZeTv.setText("中");
        empronTv.setText(TextUtils.isEmpty(symbolsBean.getWord_symbol()) ? "--" : "[" + symbolsBean.getWord_symbol() + "]");
        secondIgnoreLl.setVisibility(View.GONE);
        //意思
        meanContainerLl.removeAllViews();
        for (int i = 0; i < symbolsBean.getParts().size(); i++) {
            View meanView = LayoutInflater.from(getContext()).inflate(R.layout.item_ciba_mean, null);
            TextView classTv = meanView.findViewById(R.id.class_tv);
            TextView meanTv = meanView.findViewById(R.id.mean_tv);
            CibaWordZhBean.SymbolsBean.PartsBean mean = symbolsBean.getParts().get(i);
            classTv.setText(mean.getPart_name());
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < mean.getMeans().size(); j++) {
                sb.append(mean.getMeans().get(j).getWord_mean() + "; ");
            }
            meanTv.setText(sb.toString());
            //动画一下
            YoYo.with(Techniques.BounceInLeft).duration(500).repeat(0).playOn(meanView);
            meanContainerLl.addView(meanView);
        }
        pronEmMp3Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMp3(symbolsBean.getSymbol_mp3());
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
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(BaseErrorBean bean) {

    }
}
