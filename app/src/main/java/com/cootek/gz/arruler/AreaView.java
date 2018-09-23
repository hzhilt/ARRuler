package com.cootek.gz.arruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AreaView extends View {

    List<CustomPoint> points = new ArrayList<>();

    Paint paint;


    public AreaView(Context context) {
        super(context);

        initView();
    }

    public AreaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AreaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public AreaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(0x3F51B5);
        paint.setStrokeWidth(10);

    }


    public void setPoints(List<CustomPoint> points) {
        this.points = points;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < points.size(); i++) {
            if (i < points.size() - 2) {
                canvas.drawLine(points.get(i).getX() * 100,
                        points.get(i).getY() * 100,
                        points.get(i + 1).getX() * 100,
                        points.get(i = 1).getY() * 100, paint);
            } else {
                canvas.drawLine(points.get(i).getX() * 100,
                        points.get(i).getY() * 100,
                        points.get(0).getX() * 100,
                        points.get(0).getY() * 100, paint);
            }
        }
    }
}
