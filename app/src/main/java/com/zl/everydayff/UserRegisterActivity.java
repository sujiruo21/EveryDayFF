package com.zl.everydayff;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Editable;
        import android.text.Html;
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
        import com.zl.everydayff.model.UserRequestCodeResult;
        import com.zl.everydayff.ui.VerificationCodeButton;
        import com.zl.everydayff.util.ActivityManagerUtil;
        import com.google.gson.Gson;
        import com.squareup.okhttp.Callback;
        import com.squareup.okhttp.FormEncodingBuilder;
        import com.squareup.okhttp.OkHttpClient;
        import com.squareup.okhttp.Request;
        import com.squareup.okhttp.RequestBody;
        import com.squareup.okhttp.Response;

        import java.io.IOException;


/**
 * Created by hui on 2016/8/24.
 */
public class UserRegisterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private CheckBox mCheckPasswordCb;
    private EditText mUserPhoneEt,mUserPasswordEt,mUserCodeEt;
    private Button mUserRegisterBt;
    private TextView mUserAgreementTv;

    private VerificationCodeButton mSendCodeBt;

    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自己单独统一管理activity
        ActivityManagerUtil.getInstance().addActivity(this);

        setContentView(R.layout.activity_user_register);

        mCheckPasswordCb = (CheckBox) findViewById(R.id.check_password_cb);
        mUserPhoneEt = (EditText) findViewById(R.id.user_phone_et);
        mUserPasswordEt = (EditText) findViewById(R.id.user_password_et);
        mUserRegisterBt = (Button) findViewById(R.id.user_register_bt);
        mUserAgreementTv = (TextView) findViewById(R.id.user_agreement_tv);
        mSendCodeBt = (VerificationCodeButton) findViewById(R.id.send_code_bt);
        mUserCodeEt = (EditText) findViewById(R.id.user_code_et);

        // 1.完成基本功能   显示和隐藏密码
        // 监听CheckBox状态改变
        mCheckPasswordCb.setOnCheckedChangeListener(this);

        // 2.处理点击提交数据
        mUserRegisterBt.setOnClickListener(this);
        mSendCodeBt.setOnClickListener(this);

        // html方式设置文字样式不一
        mUserAgreementTv.setText(Html.fromHtml("我已阅读并同意<font color='#24cfa2'>《天天防腐》用户协议</font>"));

        // mSendCodeBt 验证码和输入框绑定
        mSendCodeBt.bindPhoneEditText(mUserPhoneEt);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        // compoundButton 代表的是当前  CheckBox    checked 代表当前是否选中
        if(checked){
            // 显示密码
            mUserPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            // 隐藏密码
            mUserPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        // 把光标移动到最后
        Editable etext = mUserPasswordEt.getText();
        Selection.setSelection(etext, etext.length());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_register_bt:
                dealUserRegister();
                break;
            case R.id.send_code_bt:
                // 按钮点击，向后台发送请求
                // 1.把按钮置为请稍后状态
                mSendCodeBt.startLoad();
                // 2.请求
                requestUserCode();
                // 3.获取后台返回值之后判断后台反馈，如果成功倒计时，否则你要把按钮的状态置又可以点
                break;
        }
    }


    private void requestUserCode() {
        // 向后台发送请求
        // 1.本地验证
        String userPhone = mUserPhoneEt.getText().toString().trim();

        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_LONG).show();
            return;
        }

        // 2.往后台提交数据
        // OKhttp
        // 1.创建一个OkhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("appid","1")
                .add("sms_type","3")
                .add("cell_phone",userPhone)
                .build();
        // 3. 构建一个请求  post 提交里面是参数的builder   url()请求路径
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=Util&a=sendVerifyCode")
                .post(requestBody).build();

        // 4.发送一个请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // 失败
            }

            @Override
            public void onResponse(Response response) throws IOException {
                // 成功  数据在response里面  获取后台给我们的JSON 字符串
                String result = response.body().string();
                Log.e("TAG", result);
                Gson gson = new Gson();
                UserRequestCodeResult codeResult = gson.fromJson(result, UserRequestCodeResult.class);
                dealCodeResult(codeResult);
            }
        });
    }

    /**
     * 处理请求验证码的返回接口
     * @param codeResult
     */
    private void dealCodeResult(final UserRequestCodeResult codeResult) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 3.获取后台返回值之后判断后台反馈，如果成功倒计时，否则你要把按钮的状态置又可以点
                if(codeResult.errcode == 1){
                    mSendCodeBt.aginAfterTime(60);
                }else{
                    Toast.makeText(UserRegisterActivity.this,codeResult.errmsg,Toast.LENGTH_LONG).show();
                    mSendCodeBt.setNoraml();// 按钮恢复默认
                }
            }
        });
    }

    private void dealUserRegister() {
        // 1.本地验证
        String userPhone = mUserPhoneEt.getText().toString().trim();
        String password = mUserPasswordEt.getText().toString().trim();
        String userCode = mUserCodeEt.getText().toString().trim();

        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(userCode)){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
            return;
        }

        // 2.往后台提交数据
        // OKhttp
        // 1.创建一个OkhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("appid","1")
                .add("verify_code",userCode)
                .add("cell_phone",userPhone)//  添加多个参数
                .add("password", password)// MD5 AES
                .build();
        // 3. 构建一个请求  post 提交里面是参数的builder   url()请求路径
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=Member&a=register")
                .post(requestBody).build();

        // 4.发送一个请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // 失败
            }

            @Override
            public void onResponse(Response response) throws IOException {
                // 成功  数据在response里面  获取后台给我们的JSON 字符串
                // 注册完之后不需要用户再次登录  返回的结果就是登录的结果
                String result = response.body().string();
                Log.e("TAG", result);
                Gson gson = new Gson();
                final UserLoginResult loginResult = gson.fromJson(result, UserLoginResult.class);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dealRegisterResult(loginResult);
                    }
                });
            }
        });
    }
    // 3.处理返回的数据
    private void dealRegisterResult(UserLoginResult loginResult) {
        // 首先判断有没有成功
        if(loginResult.getErrcode() == 1){
            // 成功处理
            // 1.需要保存登录状态   当前设置为已登录
            SharedPreferences sp =  getSharedPreferences("info",MODE_PRIVATE);
            sp.edit().putBoolean("is_login",true).commit();

            // 2.需要保存用户信息
            UserLoginResult.DataBean userData =  loginResult.getData();
            // SharedPreferences 怎么保存对象   把对象转为JSON String --> SharedPreferences
            Gson gson = new Gson();
            String uesrInfoStr =  gson.toJson(userData);
            // 保存的用户信息为Json格式的字符串
            sp.edit().putString("user_info",uesrInfoStr).commit();

            // 3.关掉这个页面  关闭登录界面
            // 模拟注册成功，先关闭当前页面，然后关闭登录页面
            ActivityManagerUtil.getInstance().finishActivity(this);
            // 调用自己的activity管理类关闭其他页面
            ActivityManagerUtil.getInstance().finishActivity(UserLoginActivity.class);
        }else{
            // 登录失败
            Toast.makeText(this,loginResult.getErrmsg(),Toast.LENGTH_LONG).show();
        }
    }
}
