package com.zl.everydayff.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Boom on 2017/7/2.
 */

public class TimerCodeButton extends Button {
    //当前时间
    private int mCurrentCount = 0;
    //不断更新处理消息
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mCurrentCount>0){
                --mCurrentCount;
                countDown(mCurrentCount);
            }else {
                ableStatus();
            }
        }
    };

    public TimerCodeButton(Context context) {
        this(context,null);
    }

    public TimerCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimerCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ableStatus();
    }

    /**
     * 分为三种状态
     *  1.能够点击的状态，默认状态
     *  2.不能点击稍后状态
     *  3.倒计时状态
     */

    //1.能够点击状态，默认的状态
    private void ableStatus() {
        this.setEnabled(true);
        this.setText("获取验证码");
        laterOnStatus(Color.GREEN);
    }

    //2.不能点击，稍后
    private void laterOnStatus(int color){
        this.setEnabled(false);
        this.setText("请稍后。。。");
        this.setTextColor(color);
    }

    //3.多少后   重新获取的效果
    public void countDown(int count){
        mCurrentCount = count;
        this.setEnabled(false);
        this.setText(count+"秒后重获");
        //不断去更新
        mHandler.sendEmptyMessageDelayed(0 ,1000);

    }
}
