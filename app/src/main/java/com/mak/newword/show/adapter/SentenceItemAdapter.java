package com.mak.newword.show.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mak.newword.R;
import com.mak.newword.base.BaseRecyclerAdapter;
import com.mak.newword.mvp.model.SentenceBean;
import com.mak.newword.show.activity.SentenceDetailActivity;
import com.mak.newword.utils.banner.GlideImageLoader;
import com.mak.newword.widget.SmartViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.Arrays;

/**
 * 创建人：jayson
 * 创建时间：2019/4/7
 * 创建内容：
 */

public class SentenceItemAdapter extends BaseRecyclerAdapter<SentenceBean> {

    private Context context;
    private String[] imgUrl = {
            "https://cdn.pixabay.com/photo/2019/04/25/16/01/jetty-4155214_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/04/15/15/08/landscape-4129533_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/04/16/20/01/architecture-4132660_960_720.jpg"};
    private String[] imgTitle = {"绝美如画", "雄壮的火山爆发", "暗夜城堡"};

    public SentenceItemAdapter(Context context) {
        super(R.layout.item_sentence);
        this.context = context;
    }

    /**
     * 设置轮播banner
     *
     * @param sentenceBanner 轮播控件
     */
    private void setBanner(Banner sentenceBanner) {
        //设置banner样式
        sentenceBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        sentenceBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        sentenceBanner.setImages(Arrays.asList(imgUrl));
        //设置banner动画效果-DepthPage景深空白Accordion翻页效果
        sentenceBanner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
        sentenceBanner.setBannerTitles(Arrays.asList(imgTitle));
        //设置自动轮播，默认为true
        sentenceBanner.isAutoPlay(true);
        //设置轮播时间
        sentenceBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        sentenceBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        sentenceBanner.start();
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, SentenceBean model, int position) {
        onHandle(holder, model, position);
    }

    private void onHandle(SmartViewHolder holder, final SentenceBean model, int position) {
        Banner sentenceBanner = holder.getView(R.id.sentence_banner);
        LinearLayout contentLl = holder.getView(R.id.content_ll);
        if (position == 0) {
            //设置轮播banner
            setBanner(sentenceBanner);
            sentenceBanner.setVisibility(View.VISIBLE);
            contentLl.setVisibility(View.GONE);
            sentenceBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                }
            });
            return;
        }
        sentenceBanner.setVisibility(View.GONE);
        contentLl.setVisibility(View.VISIBLE);
        TextView zhContentTv = holder.getView(R.id.zh_content_tv);
        TextView enContentTv = holder.getView(R.id.en_content_tv);
        ImageView imgTv = holder.getView(R.id.img_content_tv);
        enContentTv.setText(model.getZhContent());
        zhContentTv.setText(model.getEnContent());
        if (!TextUtils.isEmpty(model.getImgUrl())) {
            imgTv.setVisibility(View.VISIBLE);
            Glide.with(context).load(model.getImgUrl()).placeholder(R.mipmap.place46).into(imgTv);
        } else {
            imgTv.setVisibility(View.GONE);
        }
        contentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SentenceDetailActivity.class);
                intent.putExtra("sentence", model);
                context.startActivity(intent);
            }
        });
    }
}
