package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Practice9DrawPathView extends View {

    private static final String TAG = "Practice9DrawPathView";

    private Paint paint;
    private Path path;
    private RectF rectF1, rectF2;

    private Point point;

    public Practice9DrawPathView(Context context) {
        super(context);
        init();
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
        point = new Point(200, 200);
        rectF1 = new RectF(point.x, point.y, point.x + 200, point.y + 200);
        rectF2 = new RectF(point.x + 200, point.y, point.x + 400, point.y + 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "canvas.isHardwareAccelerated()?" + canvas.isHardwareAccelerated());
//        练习内容：使用 canvas.drawPath() 方法画心形
        path.moveTo(point.x, point.y);
        path.arcTo(rectF1, -225, 225, true);
        path.arcTo(rectF2, -180, 225, false);
        path.lineTo(point.x+200,point.y+300);
        canvas.drawPath(path, paint);

        //        练习内容：使用 canvas.drawPath() 方法画心形
        /*path.addArc(50,50,200,200,-225,225);
        path.arcTo(200, 50, 350, 200, -180, 225, false);
        path.lineTo(200,300);
        canvas.drawPath(path,paint);*/
        /*paint.setStyle(Paint.Style.STROKE);
        path.addCircle(400,400,100, Path.Direction.CW);
        //path.arcTo(200, 50, 350, 200, -180, 225, false);
        path.lineTo(200,300);
        canvas.drawPath(path,paint);*/
    }
}
