package com.zl.everydayff.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zl.everydayff.R;
import com.zl.everydayff.UserLoginActivity;
import com.zl.everydayff.model.UserLoginResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

/**
 * Created by Boom on 2017/6/21.
 */

public class CenterFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    TextView user_login_tv,user_exit_login;
    private Context mContext;
    LinearLayout user_logined_ll;
    ImageView user_head_iv;
    TextView user_name_tv;
    TextView user_location_tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_center,null);
        mContext=getActivity();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user_login_tv = (TextView) mRootView.findViewById(R.id.user_login_tv);
        user_exit_login = (TextView) mRootView.findViewById(R.id.user_exit_login);
        user_logined_ll = (LinearLayout) mRootView.findViewById(R.id.user_logined_ll);
        user_head_iv =(ImageView)mRootView.findViewById(R.id.user_head_iv);
        user_name_tv = (TextView) mRootView.findViewById(R.id.user_name_tv);
        user_location_tv = (TextView) mRootView.findViewById(R.id.user_location_tv);
        user_login_tv.setOnClickListener(this);
        user_exit_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.user_login_tv:
                Intent intent=new Intent(mContext,UserLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.user_exit_login:
                //跳转登陆状态设为false
                SharedPreferences sp = mContext.getSharedPreferences("info",Context.MODE_PRIVATE);
                sp.edit().putBoolean("is_login",false).commit();
                break;

        }


    }

    /**
     *  Main -->login(true)--->MainActivity onResume --->CenterFragment onResume
     */
    @Override
    public void onResume() {
        super.onResume();
        //判断用户是否登录 ，如果登录了显示登录的中心头部，否则显示未登录的中心头部
        SharedPreferences sp = mContext.getSharedPreferences("info",Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("is_login",false);
        if (isLogin){
            user_logined_ll.setVisibility(View.VISIBLE);
            user_login_tv.setVisibility(View.GONE);
            //设置用户信息
           String userInfoStr = mContext.getSharedPreferences("info",Context.MODE_PRIVATE).getString("userinfo",null);
            if (!TextUtils.isEmpty(userInfoStr)){
                //把用户信息json转换为对象
                Gson gson = new Gson();
                UserLoginResult.DataBean userInfo = gson.fromJson(userInfoStr, UserLoginResult.DataBean.class);
                //设置图片
                Glide.with(mContext)
                        .load(userInfo.getMember_info().getMember_avatar())
                        .into(user_head_iv);
                //设置名称和地区
                user_name_tv.setText(userInfo.getMember_info().getMember_name());
                user_location_tv.setText(userInfo.getMember_info().getMember_location_text());
            }
        }else {
            //没有登录
            user_login_tv.setVisibility(View.VISIBLE);
            user_logined_ll.setVisibility(View.GONE);
        }
    }
}
