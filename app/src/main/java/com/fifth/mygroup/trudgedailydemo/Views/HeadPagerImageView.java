package com.fifth.mygroup.trudgedailydemo.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lenovo on 2016/5/19.
 */
public class HeadPagerImageView extends ImageView{
    private Paint paint;
    public HeadPagerImageView(Context context) {
        this(context, null);
    }

    public HeadPagerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadPagerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
