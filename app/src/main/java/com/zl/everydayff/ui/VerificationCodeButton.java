package com.zl.everydayff.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import com.zl.everydayff.R;
import com.example.boom.baseutil.GeneralUtil;


/**
 * 
 * ============================================================
 * 
 * project name :DiDi professor
 * 
 * copyright ZENG HUI (c) 2014
 * 
 * author : HUI
 * 
 * version : 1.0
 * 
 * date created : On December 24, 2014 9:37:31
 * 
 * description : 获取验证码的计时按钮
 * 
 * revision history :
 * 
 * ============================================================
 * 
 */
@SuppressLint("HandlerLeak")
public class VerificationCodeButton extends Button {
	private int mTime;
	private String mNormalStr;
	private ColorStateList mNormalColorList;
	private Drawable mNormalDrawable;
	private int mTimerBackground;
	private int mTimerColor;
	// Bind input box
	private EditText mBindPhoneEt;
	// 是否开始加载
	private boolean mStartLoad = false;

	/**
	 * 利用handler做计时器
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mTime > 0) {
				mTime -= 1;
				timer();
			} else {
				stopRequest();
			}
		};
	};

	public VerificationCodeButton(Context context) {
		this(context, null);
	}

	public VerificationCodeButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VerificationCodeButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// 获取之前的属性（方便将它设置为正常状态）
		mNormalStr = getText().toString();
		mNormalColorList = getTextColors();
		mNormalDrawable = getBackground();

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.VerificationCodeButton);
		// 计时的时候背景
		mTimerBackground = array.getResourceId(
				R.styleable.VerificationCodeButton_timing_background, 0);
		mTimerColor = array.getColor(
				R.styleable.VerificationCodeButton_timing_textColor, 0);
		array.recycle();
	}

	/**
	 * 设置一个正常状态
	 */
	@SuppressLint("NewApi")
	public void setNoraml() {
		this.setEnabled(true);
		this.setText(mNormalStr);
		this.setTextColor(mNormalColorList);
		this.setBackground(mNormalDrawable);
	}

	/**
	 * 向后台请求数据呈现的样子
	 */
	public void startLoad() {
		mStartLoad = true;
		this.setEnabled(false);
		setAttribute();
		this.setText("  请稍后...   ");
	}

	private void setAttribute() {
		if (mTimerBackground != 0) {
			this.setBackgroundResource(mTimerBackground);
		}
		if (mTimerColor != 0) {
			this.setTextColor(mTimerColor);
		}
	}

	/**
	 * 多久之后可以再次获取
	 * 
	 * @param time
	 */
	public void aginAfterTime(int time) {
		setAttribute();
		this.mTime = time;
		// 一旦开始计时，这个按钮就不能点击了
		this.setEnabled(false);
		timer();
	}

	/**
	 * 停止获取验证码
	 */
	private void stopRequest() {
		mStartLoad = false;
		setNoraml();
	}

	/**
	 * 计时开始获取
	 */
	private void timer() {
		this.setText(formatDuring(mTime));
		mHandler.sendEmptyMessageDelayed(0, 1000);
	}

	private CharSequence formatDuring(int mTime) {
		if (mTime < 10) {
			return "0" + mTime + "秒后重获";
		}
		return mTime + "秒后重获";
	}

	/**
	 * Binding a telephone box
	 */
	public void bindPhoneEditText(EditText editText) {
		this.mBindPhoneEt = editText;
		this.setEnabled(false);
		setAttribute();

		// 监听输入框的状态改变
		this.mBindPhoneEt
				.addTextChangedListener(phoneNumbersAddSpacesTextWatcher);
	}

	/**
	 * 自动添加空格的TextWatcher
	 */
	private TextWatcher phoneNumbersAddSpacesTextWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s == null || s.length() == 0)
				return;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				if (i != 3 && i != 8 && s.charAt(i) == ' ') {
					continue;
				} else {
					sb.append(s.charAt(i));
					if ((sb.length() == 4 || sb.length() == 9)
							&& sb.charAt(sb.length() - 1) != ' ') {
						sb.insert(sb.length() - 1, ' ');
					}
				}
			}
			if (!sb.toString().equals(s.toString())) {
				int index = start + 1;
				if (sb.charAt(start) == ' ') {
					if (before == 0) {
						index++;
					} else {
						index--;
					}
				} else {
					if (before == 1) {
						index--;
					}
				}
				mBindPhoneEt.setText(sb.toString());
				mBindPhoneEt.setSelection(index);
			}

			// 上面就是加空格的逻辑
			
			// Number is correct first, and then control VerificationCodeButton this is available
			String trimStr = removeAllSpace(sb.toString());
			
			if(GeneralUtil.judgePhoneQual(trimStr) && !mStartLoad){
				// If is phone VerificationCodeButton is able
				setNoraml();
			}else{
				// Else VerificationCodeButton is enable
				VerificationCodeButton.this.setEnabled(false);
				setAttribute();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	public static String removeAllSpace(String str) {
		String tmpstr = str.replace(" ", "");
		return tmpstr;
	}
}