package com.fifth.mygroup.trudgedailydemo.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lenovo on 2016/5/20.
 */
public class HeadListView extends ImageView{
    private Paint paint;
    private TextPaint textPaint;
    private Path path;
    public HeadListView(Context context) {
        this(context, null);
    }

    public HeadListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        textPaint=new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(8);
        textPaint.setStrokeWidth(2);
        path=new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(0, 0);
        path.moveTo(getWidth() / 2, 0);
        path.moveTo(0, getHeight() / 2);
        path.close();
        canvas.drawPath(path,paint);
        canvas.rotate(45);
        canvas.drawText("用户",5,10,paint);
    }
}
