package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Practice10HistogramView extends View {

    private static final String TAG = "Practice10HistogramView";
    private Paint paint;
    private int gap = 10;
    private String[] texts = {"Froyo", "CB", "ICS", "JB", "KitKat", "L", "M"};

    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        paint.setColor(Color.WHITE);
        canvas.drawLine(50, 50, 50, 400, paint);
        for (int i = 0; i < 7; i++) {
            if (i > 0) {
                paint.setColor(Color.GREEN);
            }
            canvas.drawRect(10 + 90 * i + 50, 100, 10 + 90 * i + 80 + 50, 400, paint);
        }
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < 7; i++) {
            canvas.drawText(texts[i], 10 + 90 * i + 50 + 40, 440, paint);
        }
        canvas.drawLine(50, 400, 800, 400, paint);
        paint.setTextSize(32);

        canvas.drawText("直方图", 300, 480, paint);
    }

    /**
     * 居中绘制文字
     *
     * @param canvas
     */
    private void testDrawTextCneter(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, 600, 400, paint);
        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, 200, 600, 200, paint);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        Log.e(TAG, "top=" + metrics.top + ",bottom=" + metrics.bottom);
        int textY = (int) (200 - (metrics.top + metrics.bottom) / 2);
        canvas.drawText("Hello beautiful girl", 300, textY, paint);
    }
}
