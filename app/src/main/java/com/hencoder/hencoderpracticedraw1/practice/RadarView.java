package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.R;
import com.hencoder.hencoderpracticedraw1.ScreenUtil;

import java.util.List;

/**
 * Created by dumingwei on 2017/12/14 0014.
 */

public class RadarView extends View {

    private static final String TAG = "RadarView";

    //数据个数
    private int count;
    //根据数据个数计算的弧度
    private float angle;
    //网格最大半径
    private float radius;
    private int centerX;
    private int centerY;
    //数据最大值
    private double maxValue;

    private Paint paint;

    //雷达网线的颜色
    private int lineColor;
    private int lineWidth;

    private int textColor;
    private int textSize;

    //雷达扫过区域的颜色
    private int regionColor;
    private int regionAlpha;

    //雷达扫过区域边界圆点的颜色
    private int pointColor;
    private int pointRadius;

    private Path path;

    private List<RadarEntry> data;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
        textColor = ta.getColor(R.styleable.RadarView_textColor, Color.BLACK);
        textSize = ta.getDimensionPixelSize(R.styleable.RadarView_textSize, ScreenUtil.sp2px(14, getContext()));
        regionColor = ta.getColor(R.styleable.RadarView_regionColor, Color.GREEN);
        lineColor = ta.getColor(R.styleable.RadarView_lineColor, Color.BLACK);
        lineWidth = ta.getDimensionPixelSize(R.styleable.RadarView_lineWidth, ScreenUtil.dp2px(1, getContext()));
        regionAlpha = ta.getInteger(R.styleable.RadarView_regionAlpha, 127);
        pointColor = ta.getColor(R.styleable.RadarView_pointColor, Color.GREEN);
        pointRadius = ta.getDimensionPixelOffset(R.styleable.RadarView_pointRadius, ScreenUtil.dp2px(4, getContext()));
        ta.recycle();
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        angle = (float) (Math.PI * 2 / 6);
    }

    public void setData(List<RadarEntry> data) {
        this.data = data;
        count = data.size();
        angle = (float) (Math.PI * 2 / count);
        maxValue = getMaxValue();
        invalidate();
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setRegionAlpha(int regionAlpha) {
        this.regionAlpha = regionAlpha;
    }

    public void setRegionColor(int regionColor) {
        this.regionColor = regionColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    public void setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
    }

    /**
     * 默认是从数据里面取最大值
     *
     * @return
     */
    private double getMaxValue() {
        double maxValue = 0;
        for (RadarEntry datum : data) {
            if (datum.getValue() > maxValue) {
                maxValue = datum.getValue();
            }
        }
        return maxValue;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2 * 0.9f;
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    /**
     * 绘制多边形 从X轴正方向开始绘制
     *
     * @param canvas
     */

    private void drawPolygon(Canvas canvas) {
        //r 是蜘蛛丝之间的间距
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(lineColor);
        float r = radius / (count - 1);
        for (int i = 0; i < count; i++) {
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    private void drawLines(Canvas canvas) {
        paint.setColor(lineColor);
        for (int i = 0; i < count; i++) {
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            canvas.drawLine(centerX, centerY, x, y, paint);
        }
    }

    private void drawText(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //字体最大高度
        float fontHeight = (fontMetrics.descent - fontMetrics.ascent) / 2;
        //文字中心到文字基线的距离
        float baseLineHeight = -(fontMetrics.top + fontMetrics.bottom) / 2;
        for (int i = 0; i < count; i++) {
            String title = data.get(i).getTitle();
            float x = (float) (centerX + (radius + fontHeight) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight) * Math.sin(angle * i));
            float tempRadian = angle * i;
            if (tempRadian >= 0 && tempRadian < 0.5 * Math.PI) {
                //第一象限
                canvas.drawText(title, x, y + baseLineHeight, paint);
            } else if (tempRadian >= 0.5 * Math.PI && tempRadian < Math.PI) {
                //第二象限
                float textWidth = paint.measureText(title);
                canvas.drawText(title, x - textWidth, y + baseLineHeight, paint);
            } else if (tempRadian >= Math.PI && tempRadian < Math.PI * 1.5) {
                //第三象限
                float textWidth = paint.measureText(title);
                canvas.drawText(title, x - textWidth, y + baseLineHeight, paint);
            } else if (tempRadian >= Math.PI / 2 && tempRadian < Math.PI * 2) {
                //第四象限
                canvas.drawText(title, x, y + baseLineHeight, paint);
            }
        }
    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
        path.reset();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(pointColor);
        for (int i = 0; i < count; i++) {
            double percent = data.get(i).getValue() / maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, pointRadius, paint);
        }
        paint.setColor(regionColor);
        paint.setAlpha(regionAlpha);
        canvas.drawPath(path, paint);
    }

}
