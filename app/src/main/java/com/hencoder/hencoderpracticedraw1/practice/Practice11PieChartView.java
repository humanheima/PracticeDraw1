package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Practice11PieChartView extends View {

    private static final String TAG = "Practice11PieChartView";
    private Paint paint;
    private RectF rectFirst, rectSecond;
    private float x, y;
    private Path path;

    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        rectFirst = new RectF(100, 100, 300, 300);
        rectSecond = new RectF(120, 120, 320, 320);
        x = (float) (200 - Math.cos(135 * Math.PI / 360) * 100);
        y = (float) (200 - Math.sin(135 * Math.PI / 360) * 100);
        Log.e(TAG, "x=" + x + ",y=" + y);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //综合练习
        //练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        //参考
       /* Paint paint = new Paint();
        paint.setColor(Color.parseColor("#f12923"));

        RectF rect = new RectF(200, 30, 700, 530);
        canvas.drawArc(rect, -180, 120, true, paint);

        paint.setColor(Color.parseColor("#ffb700"));
        canvas.drawArc(rect, -60, 60, true, paint);

        paint.setColor(Color.parseColor("#8a00a3"));
        canvas.drawArc(rect, 0, 10, true, paint);

        paint.setColor(Color.parseColor("#8c8c8c"));
        canvas.drawArc(rect, 10, 10, true, paint);


        paint.setColor(Color.parseColor("#008675"));
        canvas.drawArc(rect, 20, 70, true, paint);


        paint.setColor(Color.parseColor("#157ef4"));
        canvas.drawArc(rect, 90, 90, true, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setStrokeWidth(10);
        canvas.drawText("Lollipop", 20, 50, paint);


        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        Path path = new Path();
        path.moveTo(150, 45);
        path.lineTo(290, 45);
        path.rLineTo(22, 22);
        canvas.drawPath(path, paint);*/
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectFirst, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawArc(rectFirst, -180, 135, true, paint);
        //canvas.drawRect(rectSecond, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawArc(rectSecond, -45, 45, true, paint);
        paint.setColor(Color.DKGRAY);
        canvas.drawArc(rectSecond, 5, 15, true, paint);
        paint.setColor(Color.GRAY);
        canvas.drawArc(rectSecond, 25, 15, true, paint);
        paint.setColor(Color.LTGRAY);
        canvas.drawArc(rectSecond, 45, 30, true, paint);
        paint.setColor(Color.CYAN);
        canvas.drawArc(rectSecond, 80, 100, true, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        path.lineTo(x - 10, y - 10);
        path.lineTo(x-100, y-10);
        canvas.drawPath(path, paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(32);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("hello",x-140,y-10,paint);

    }
}
