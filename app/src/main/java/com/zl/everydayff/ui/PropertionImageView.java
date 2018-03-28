package com.zl.everydayff.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.zl.everydayff.R;

/**
 * Created by Boom on 2017/6/24.
 * 1.基本控件 extends View  TextView Button
 * 2.高级控件 ListView 。。。
 * 3.自定义控件  就是官方给的View Api不能实现的效果
 * <p>
 * 自定义View都会 继承自系统的   View ViewGroup （根据情况来 ）
 */

@SuppressLint("AppCompatCustomView")
public class PropertionImageView extends ImageView {
    private float mWidthPropertion;
    private float mHeightPropertion;
    //直接在代码里面new的时候调用
    public PropertionImageView(Context context) {
        this(context,null);
    }

    //在我们布局文件layout里面声明的时候调用
    public PropertionImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    //在布局文件里面  但是你自己又定义了style
    public PropertionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context,attrs);
    }

    /**
     *  初始化
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        //获取属性的数组  PropertionImageView就是我们自定义attrs
        TypedArray typedArray =context.obtainStyledAttributes(attrs, R.styleable.PropertionImageView);
        mWidthPropertion = typedArray.getFloat(R.styleable.PropertionImageView_widthPropertion,0);
        mHeightPropertion = typedArray.getFloat(R.styleable.PropertionImageView_heightPropertion,0);
        Log.e("PropertionImageView", mWidthPropertion+":"+mHeightPropertion );
        typedArray.recycle();
    }

    /**
     * 用来测量和显示View的大小
     * MeasureSpec  测量类
     * 这个自定义View是用来按比例显示ImageView显示  避免失帧的效果
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //现在把我们之前的图片  改变高度显示
        //1.获取宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.计算高度  假设比例是   2/1
        int height = (int) (width * mHeightPropertion/mWidthPropertion);

        //3.设置控件的宽高
        setMeasuredDimension(width, height);
    }
}
