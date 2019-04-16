package com.mak.newword.show.fragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mak.newword.HomeActivity;
import com.mak.newword.R;
import com.mak.newword.base.BaseFragment;
import com.mak.newword.mvp.inface.ICibaWordView;
import com.mak.newword.mvp.model.BaseErrorBean;
import com.mak.newword.mvp.model.CibaWordEnBean;
import com.mak.newword.mvp.model.CibaWordZhBean;
import com.mak.newword.mvp.presenter.CibaWordPresenter;
import com.mak.newword.utils.StringUtil;
import com.mak.newword.utils.ToastUtils;

import butterknife.BindView;

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

    CibaWordPresenter cibaWordPresenter = new CibaWordPresenter(this, getActivity());

    @Override
    protected int getLayout() {
        return R.layout.fragment_ciba;
    }

    @Override
    protected void initView() {
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
                        ToastUtils.showToast(getContext(), "请输入需要搜索的单词");
                        //return false;
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
    }

    /**
     * 加载一个新SymbolsBean
     *
     * @param symbolsBean
     */
    public void addNewMean(CibaWordEnBean.SymbolsBean symbolsBean) {
        final View symView = LayoutInflater.from(getContext()).inflate(R.layout.item_ciba_symbol, null);
        final TextView empronTv = symView.findViewById(R.id.em_pron_tv);
        final TextView amPronTv = symView.findViewById(R.id.am_pron_tv);
        final ImageView pronEmMp3Iv = symView.findViewById(R.id.pron_em_mp3_iv);
        final ImageView pronAmMp3Iv = symView.findViewById(R.id.pron_am_mp3_iv);
        final LinearLayout meanContainerLl = symView.findViewById(R.id.mean_container_ll);
        //音标
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
            meanTv.setText(StringUtil.getBodySym(mean.getMeans().toString()));
            meanContainerLl.addView(meanView);
        }
        symbolsContainerLl.addView(symView);
    }

    @Override
    public void showCibaWordEnResult(CibaWordEnBean bean) {
        if (bean != null) {
            //名字
            wordNameTv.setText(bean.getWord_name());
            symbolsContainerLl.removeAllViews();
            for (int i = 0; i < bean.getSymbols().size(); i++) {
                addNewMean(bean.getSymbols().get(i));
            }
        }
    }

    @Override
    public void showCibaWordZhResult(CibaWordZhBean bean) {

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
