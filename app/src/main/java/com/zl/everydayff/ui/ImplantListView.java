package com.zl.everydayff.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Boom on 2017/6/24.
 */

public class ImplantListView extends ListView {
    public ImplantListView(Context context) {
        this(context,null);
    }

    public ImplantListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {// 测量方法   计算
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
