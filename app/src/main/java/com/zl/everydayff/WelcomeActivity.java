package com.zl.everydayff;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.zl.everydayff.util.ActivityManagerUtil;

public class WelcomeActivity extends Activity {
    ImageView img_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自己单独统一管理activity
        ActivityManagerUtil.getInstance().addActivity(this);
        setContentView(R.layout.activity_welcome);
        img_iv = (ImageView) findViewById(R.id.img_iv);
        //停顿几秒，然后跳转到主界面，如果你刚开始打开界面，黑一块 主界面加载很多资源
        //为了更好的让主界面加载

        //1.停顿几秒 2.handler发送延迟消息 3.Timer类 4.用动画  0.7f--1.0f
        // AlphaAnimation     img_iv  target "alpha"需要的动画效果   0.7f,1.0f  从0.7f到1.0f
        ObjectAnimator animator = ObjectAnimator.ofFloat(img_iv,"alpha",0.7f,1.0f);
        //设置动画执行时间
        animator.setDuration(5000);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                //在主页面按返回又回到Welcome  你一进主页面就应该从Task里面移除
                // 在主页面按返回又回到了Welcome  你一进主页面就应该从Task 里面移除
                ActivityManagerUtil.getInstance().finishActivity(WelcomeActivity.this);
            }
        });

    }
}
