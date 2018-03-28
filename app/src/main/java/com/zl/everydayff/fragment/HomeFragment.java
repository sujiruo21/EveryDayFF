package com.zl.everydayff.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.zl.everydayff.DetailLinkActivity;
import com.zl.everydayff.R;
import com.zl.everydayff.adapter.HomeInfoListAdapter;
import com.zl.everydayff.model.HomeDataResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Boom on 2017/6/21.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private View mRootView;
    private ImageView adbanner_iv;
    private ImageView recommended_company;
    private Handler mHandler = new Handler();
    private ListView mNewsLv;

    private HomeDataResult mHomeDataResult;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView =inflater.inflate(R.layout.fragment_home,null);
        mContext = getActivity();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // findViewById（）  你界面在哪里  就用谁去找findViewbyId()
        adbanner_iv = (ImageView) mRootView.findViewById(R.id.adbanner_iv);
        recommended_company =(ImageView) mRootView.findViewById(R.id.recommended_company);
        mNewsLv = (ListView) mRootView.findViewById(R.id.industry_information_lv);

        // 设置图片点击时间
        adbanner_iv.setOnClickListener(this);

        //请求后台数据
        requesthomeData();
    }

    /**
     * 请求后台数据
     */
    private void requesthomeData() {
        //okhttp
        //创建一个OkhhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder().add("appid","1").build();
        //构建一个请求 post请求  提交 builder相当于垃圾桶一键处理
        Request request = new Request.Builder()
                .url("http://v2.ffu365.com/index.php?m=Api&c=Index&a=home")
                .post(requestBody).build();
        //发送一个请求
        okHttpClient.newCall(request).enqueue(new Callback() {  //请求的一个回调
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {  //  不是运行在主线程
                //成功 数据在response里面  获取后台给我们的json字符串
                String result = response.body().string();
                Log.e("TAG", "onResponse: "+result);
                Gson gson = new Gson();
                mHomeDataResult = gson.fromJson(result, HomeDataResult.class);
                showHomeData(mHomeDataResult.getData());
            }
        });

     /*   try {
            String result =okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 显示首页数据
     * @param data
     */
    private void showHomeData(final HomeDataResult.DataBean data) {
        // runonUiThread();   在Activity中有
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //---------------显示首页图片--------------------
                //从后台返回的数据中获取广告位的图片路径
                String adStr = data.getAd_list().get(0).getImage();
                Glide.with(mContext)  //with(Context context)
                        .load(adStr)   //load(网络图片的路径)
                        .into(adbanner_iv); //设置给谁
                //可以一直点的是链表的方式，一般会出现在Builder模式中
                String reCommendStr = data.getCompany_list().get(0).getImage();
                Glide.with(mContext)
                        .load(reCommendStr)
                        .into(recommended_company);
                //但是如果直接使用 android:scaleType="fitXY" 会导致图片变形，不使用界面不美观
                //---------------显示首页列表--------------------
                mNewsLv.setAdapter(new HomeInfoListAdapter(mContext,data.getNews_list()));
                // ScrollView + ListView 嵌套  一般就是ListView显示不全
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adbanner_iv:
                Intent intent = new Intent(mContext, DetailLinkActivity.class);
                // 获取广告位的链接
                String bannerUrl = mHomeDataResult.getData().getAd_list().get(0).getLink();
                // 把链接传递给下一个activity
                intent.putExtra(DetailLinkActivity.URL_KEY,bannerUrl);
                startActivity(intent);
                break;
        }
    }
}
