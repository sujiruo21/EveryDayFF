package com.zl.everydayff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zl.everydayff.model.UserLoginResult;
import com.zl.everydayff.util.ActivityManagerUtil;
import com.zl.everydayff.util.MD5Util;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Boom on 2017/6/28.
 */
public class UserLoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    CheckBox check_password_cb;
    EditText user_phone_et,user_password_et;
    Button user_login_bt;
    TextView user_register_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自己单独统一管理activity
        ActivityManagerUtil.getInstance().addActivity(this);
        setContentView(R.layout.activity_user_login);
        check_password_cb = (CheckBox) findViewById(R.id.check_password_cb);
        user_phone_et = (EditText) findViewById(R.id.user_phone_et);
        user_password_et = (EditText) findViewById(R.id.user_password_et);
        user_login_bt = (Button) findViewById(R.id.user_login_bt);
        user_register_tv= (TextView) findViewById(R.id.user_register_tv);
        //1.完成基本功能  显示和隐藏密码
        //监听checkBox的状态改变
        check_password_cb.setOnCheckedChangeListener(this);
        user_login_bt.setOnClickListener(this);
        user_register_tv.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //CompoundButton  代表当前的 CheckBox  isChecked 代表当前是否选中
        if (isChecked){
            //显示密码
            user_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            //隐藏密码
            user_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        //把光标移到最后
        Editable phone = user_phone_et.getText();
        Selection.setSelection(phone,phone.length());
        Editable password = user_password_et.getText();
        Selection.setSelection(password,password.length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_login_bt:
                dealUserLogin();
                break;
            case R.id.user_register_tv:
                // 跳转到注册页面
                Intent intent = new Intent(this,UserRegisterActivity.class);
                // startActivity(intent);
                // 待会要获取注册的返回值
                startActivityForResult(intent,0);
            break;
        }
    }

    private void dealUserLogin() {
        //本地验证
        String userPhone = user_phone_et.getText().toString().trim();
        String password = user_password_et.getText().toString().trim();

        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }//
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        //2.后台提交数据
        //okhttp
        //创建一个OkhhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("appid","1")
                .add("call_phone",userPhone)
                .add("password", MD5Util.strToMd5(password))
                .build();
        //构建一个请求 post请求  提交 builder相当于垃圾桶一键处理
        Request request = new Request.Builder()
                .url("http://v2.ffu365.com/index.php?m=Api&c=Member&a=login")
                .post(requestBody).build();
        //发送一个请求
        okHttpClient.newCall(request).enqueue(new Callback() {  //请求的一个回调
            @Override
            public void onFailure(Request request, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Response response) throws IOException {  //  不是运行在主线程
                //成功 数据在response里面  获取后台给我们的json字符串
                String result = response.body().string();
                Log.e("TAG", "onResponse: "+result);
                Gson gson = new Gson();
                UserLoginResult userLoginResult=gson.fromJson(result, UserLoginResult.class);
                dealLoginResult(userLoginResult);
            }
            //3.处理返回的数据
            private void dealLoginResult(UserLoginResult userLoginResult) {
                //首先判断有没有成功
                if(userLoginResult.getErrcode()==1){
                    //成功处理  干掉这个页面
                    //1.需要保存登录状态   当前设置为以登录
                        SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
                        sp.edit().putBoolean("is_login",true).commit();
                    //2.需要保存用户信息
                        UserLoginResult.DataBean userData = userLoginResult.getData();
                    //SharedPreferences  怎么保存对象  把对象转换为Json String  SharedPreferences
                    Gson gson = new Gson();
                    String userInfoStr = gson.toJson(userData);
                    sp.edit().putString("user_info",userInfoStr).commit();
                    //3.关掉这个页面
                    finish();
                }else {
                    //登陆失败
                    Toast.makeText(UserLoginActivity.this,userLoginResult.getErrmsg(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
