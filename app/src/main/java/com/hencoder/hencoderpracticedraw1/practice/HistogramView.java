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
import android.view.MotionEvent;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.ScreenUtil;

import java.util.List;

/**
 * Created by dumingwei on 2017/12/11 0011.
 * 假设数据最大是1000
 */

public class HistogramView extends View {

    private static final String TAG = "HistogramView";
    private List<HistogramEntry> data;
    private Paint paint;
    private int offsetLeft;
    private int offsetTop;
    private int offsetRight;
    private int offsetBottom;
    private int strokeWidth;
    private int gapWidth;
    private RectF rectF;
    private int textSize;
    private Path path;
    private OnItemClickListener itemClickListener;

    public HistogramView(Context context) {
        this(context, null);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokeWidth = ScreenUtil.sp2px(1, getContext());
        offsetLeft = ScreenUtil.dp2px(24, getContext());
        offsetTop = ScreenUtil.dp2px(24, getContext());
        offsetRight = ScreenUtil.dp2px(24, getContext());
        offsetBottom = ScreenUtil.dp2px(24, getContext());
        gapWidth = ScreenUtil.dp2px(10, getContext());
        textSize = ScreenUtil.sp2px(14, getContext());
        rectF = new RectF();
        path = new Path();
    }

    public void setData(List<HistogramEntry> data) {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#B09EFF"));
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        path.moveTo(offsetLeft, offsetTop - 20);
        path.lineTo(offsetLeft - 4, offsetTop - 10);
        path.lineTo(offsetLeft + 4, offsetTop - 10);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawLine(offsetLeft, offsetTop - 10, offsetLeft, getHeight() - offsetBottom, paint);
        canvas.drawLine(offsetLeft, getHeight() - offsetBottom, getWidth() - offsetRight, getHeight() - offsetBottom, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(16);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        for (int i = 1; i <= 10; i++) {
            canvas.drawLine(offsetLeft, getHeight() - offsetBottom - i * (getHeight() - offsetBottom - offsetTop) / 10,
                    offsetLeft + 10, getHeight() - offsetBottom - i * (getHeight() - offsetBottom - offsetTop) / 10, paint);
            canvas.drawText(String.valueOf(i * 100), offsetLeft - 10,
                    getHeight() - offsetBottom - i * (getHeight() - offsetBottom - offsetTop) / 10 - (metrics.top + metrics.bottom) / 2, paint);
        }
        path.reset();
        paint.setStyle(Paint.Style.FILL);
        path.moveTo(getWidth() - offsetRight + 10, getHeight() - offsetBottom);
        path.lineTo(getWidth() - offsetRight, getHeight() - offsetBottom + 4);
        path.lineTo(getWidth() - offsetRight, getHeight() - offsetBottom - 4);
        canvas.drawPath(path, paint);
        //每个矩形的宽度
        int rectWidth = (getWidth() - offsetLeft - offsetRight - gapWidth * (data.size() + 1)) / data.size();
        for (int i = 0; i < data.size(); i++) {
            int left = offsetLeft + (i + 1) * gapWidth + i * rectWidth;
            int right = offsetLeft + (i + 1) * gapWidth + (i + 1) * rectWidth;
            float top = getHeight() - offsetBottom - data.get(i).getNumber() * (getHeight() - offsetTop - offsetBottom) / 1000;
            int bottom = getHeight() - offsetBottom - strokeWidth;
            rectF.set(left, top, right, bottom);
            paint.setColor(getResources().getColor(data.get(i).getColor()));
            canvas.drawRect(rectF, paint);
            data.get(i).setLeft(left);
            data.get(i).setTop(top);
            data.get(i).setRight(right);
            data.get(i).setBottom(bottom);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(textSize);
            canvas.drawText(data.get(i).getText(), left + rectWidth / 2, getHeight() - offsetBottom + strokeWidth + textSize, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x;
        float y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                for (int i = 0; i < data.size(); i++) {
                    if (x >= data.get(i).getLeft() && x <= data.get(i).getRight() &&
                            y >= data.get(i).getTop() && y <= data.get(i).getBottom()) {
                        if (null != itemClickListener) {
                            itemClickListener.onItemClick(i);
                            break;
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);

    }

    private float getMax() {
        float max = 0;
        for (HistogramEntry datum : data) {
            if (max < datum.getNumber()) {
                max = datum.getNumber();
            }
        }
        Log.e(TAG, "max=" + max);
        return max;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
