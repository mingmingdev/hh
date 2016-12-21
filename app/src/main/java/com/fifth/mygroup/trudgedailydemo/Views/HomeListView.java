package com.fifth.mygroup.trudgedailydemo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lenovo on 2016/5/20.
 */
public class HomeListView extends ListView{
    public HomeListView(Context context) {
        this(context, null);
    }

    public HomeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
