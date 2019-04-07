package com.mak.newword.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mak.newword.R;


public class HeaderView extends RelativeLayout {
	private TextView centerText;
	private TextView rightText;
	private TextView leftText;
	private ImageView rightImg;
	private ImageView leftImg;
	private ImageView centerImg;
	private RelativeLayout rl1;

	public HeaderView(Context context) {
		super(context);
		init(context);
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public HeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	protected void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.app_head, this);
		centerText = (TextView) findViewById(R.id.textcenter);
		rightImg = (ImageView) findViewById(R.id.rightImg);
		rightText = (TextView) findViewById(R.id.righttext);
		leftImg = (ImageView) findViewById(R.id.leftImg);
		leftText = (TextView) findViewById(R.id.leftText);
		centerImg = (ImageView) findViewById(R.id.centerimg);
		rl1 = (RelativeLayout) findViewById(R.id.rl1);
	}

	/**
	 * 设置背景
	 * @param resId
     */
	public void setBackground(int resId){
		rl1.setBackgroundColor(resId);
	}

	/**
	 * 设置左边图片
	 * 
	 * @param resId
	 */
	public void setLeftImg(int resId) {
		leftImg.setImageResource(resId);
	}

	/**
	 * 设置中间图片
	 *
	 * @param resId
	 */
	public void setCenterImg(int resId) {
		centerImg.setImageResource(resId);
	}

	/**
	 * 设置左边文字
	 * 
	 * @param
	 */
	public void setLeftText(String text) {
		leftText.setText(text);
	}

	/**
	 * 设置中间文字
	 * 
	 * @param content
	 * 
	 */
	public void setCenterText(String content) {
		centerText.setText(content);
	}

	/**
	 * 设置右边文字颜色
	 * @param color
	 */
	public void setRightTextColor(int color){
		rightText.setTextColor(color);
	}

	/**
	 * 设置右边文字
	 * 
	 * @param content
	 */
	public void setRightText(String content) {
		rightText.setText(content);
		rightText.setVisibility(View.VISIBLE);
	}

	public String getRightText() {
		if (null != rightText) {
			return rightText.getText().toString();
		}
		return "";
	}
	public String getLeftText() {
		if (null != leftText) {
			return leftText.getText().toString();
		}
		return "";
	}
	/**
	 * 设置右边图片
	 * 
	 * @param id
	 */
	public void setRightImgSrc(int id) {
		rightImg.setImageResource(id);
		rightImg.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏右边图片
	 * 
	 * @param
	 */
	public void hideRightImgSrc() {
		rightImg.setVisibility(View.GONE);
	}

	/**
	 * 设置返回点击事件
	 */
	public void setBackLnOnClickListener(OnClickListener onClickListener) {
		leftImg.setOnClickListener(onClickListener);
	}

	/**
	 * 设置左边文字点击事件
	 */
	public void setLeftTextOnClickListener(OnClickListener onClickListener) {
		leftText.setOnClickListener(onClickListener);
	}

	/**
	 * 设置右边文字点击事件
	 */
	public void setRightTextOnClickListener(OnClickListener onClickListener) {
		rightText.setOnClickListener(onClickListener);
	}
	/**
	 * 设置中间文字点击事件
	 */
	public void setCenterTextOnClickListener(OnClickListener onClickListener) {
		centerText.setOnClickListener(onClickListener);
	}

	/**
	 * 设置右边图片点击事件
	 */
	public void setRightImgOnClickListener(OnClickListener onClickListener) {
		rightImg.setOnClickListener(onClickListener);
	}

	/**
	 * 隐藏back
	 */
	public void SetHideBack() {
		leftImg.setVisibility(View.GONE);
	}

	/**
	 * 设置左边图片显示  隐藏
	 *
	 * @param
	 */
	public void setLeftImgVisibility(int visibility) {
		leftImg.setVisibility(visibility);
	}
	/**
	 * 设置右边文字显示  隐藏
	 *
	 * @param
	 */
	public void setRightTextVisibility(int visibility) {
		rightText.setVisibility(visibility);
	}

	/**
	 * 设置右边图片显示  隐藏
	 *
	 * @param
	 */
	public void setRightImgVisibility(int visibility) {
		rightImg.setVisibility(visibility);
	}
}
