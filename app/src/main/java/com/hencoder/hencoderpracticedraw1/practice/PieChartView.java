package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by dumingwei on 2017/12/7 0007.
 */

public class PieChartView extends View {

    private List<PieEntry> pieEntries;
    private Paint paint;
    private float centerX;
    private float centerY;
    private float radius;
    private float sRadius;
    private RectF rectF;
    private Path path;
    private DecimalFormat format;
    private OnItemClickListener itemClickListener;
    private float offset;
    //当在第三象限和第四象限绘制文字时候，距离横线的间距
    private float upTextSpace;
    private float smallCircleRadius;

    private static final String TAG = "PieChartView";

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(sp2px(16));
        paint.setStrokeWidth(dp2px(1));
        rectF = new RectF();
        format = new DecimalFormat("00.00");
        path = new Path();
        offset = dp2px(10);
        upTextSpace = sp2px(3);
        smallCircleRadius = sp2px(2);
    }

    public void setsRadius(float sRadius) {
        this.sRadius = sRadius;
    }

    public void setPieEntries(List<PieEntry> pieEntries) {
        this.pieEntries = pieEntries;
        invalidate();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int total = 0;
        for (PieEntry pieEntry : pieEntries) {
            total += pieEntry.getNumber();
        }
        centerX = getPivotX();
        centerY = getPivotY();
        if (sRadius == 0) {
            sRadius = (Math.min(getWidth(), getHeight()) / 2);
        }
        radius = sRadius - dp2px(5);
        //起始角度设为0，即X轴正方向
        float startC = 0;
        //遍历List<PieEntry> 开始画扇形
        for (int i = 0; i < pieEntries.size(); i++) {
            //计算当前扇形扫过的角度
            float sweep;
            if (total > 0) {
                sweep = 360 * (pieEntries.get(i).getNumber() / total);
            } else {
                sweep = 360 / pieEntries.size();
            }
            paint.setColor(getResources().getColor(pieEntries.get(i).getColorRes()));
            float radiusT;
            if (pieEntries.get(i).isSelected()) {
                radiusT = sRadius;
            } else {
                radiusT = radius;
            }
            rectF.set(centerX - radiusT, centerY - radiusT, centerX + radiusT, centerY + radiusT);
            canvas.drawArc(rectF, startC, sweep, true, paint);
            //绘制画扇形外围的短线和百分数值。
            float arcCenterC = startC + sweep / 2;//当前扇形弧线的中间点和圆心的连线 与 起始角度的夹角
            //当前扇形弧线的中间点 的坐标 x,y  以此点作为短线的起始点
            float arcCenterX;
            float arcCenterY;
            //这两个点作为短线的结束点
            float arcCenterX2;
            float arcCenterY2;

            String text;
            if (total > 0) {
                text = format.format(pieEntries.get(i).getNumber() / total * 100) + "%";
            } else {
                text = "0.00%";
            }
            offset = paint.measureText(text);
            path.reset();

            //分象限 利用三角函数 来求出每个短线的起始点和结束点，并画出短线和百分比。
            if (arcCenterC >= 0 && arcCenterC < 90) {
                arcCenterX = (float) (centerX + radiusT * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterY = (float) (centerY + radiusT * Math.sin(Math.PI * arcCenterC / 180));
                arcCenterX2 = (float) (arcCenterX + dp2px(10) * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterY2 = (float) (arcCenterY + dp2px(10) * Math.sin(Math.PI * arcCenterC / 180));

                path.moveTo(arcCenterX, arcCenterY);
                path.lineTo(arcCenterX2, arcCenterY2);
                path.lineTo(arcCenterX2 + offset, arcCenterY2);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(arcCenterX2 + offset+smallCircleRadius, arcCenterY2,smallCircleRadius,paint);
                canvas.drawText(text, arcCenterX2, arcCenterY2 + paint.getTextSize(), paint);

            } else if (arcCenterC >= 90 && arcCenterC < 180) {
                arcCenterC = 180 - arcCenterC;
                arcCenterX = (float) (centerX - radiusT * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterY = (float) (centerY + radiusT * Math.sin(Math.PI * arcCenterC / 180));
                arcCenterX2 = (float) (arcCenterX - dp2px(10) * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterY2 = (float) (arcCenterY + dp2px(10) * Math.sin(Math.PI * arcCenterC / 180));

                path.moveTo(arcCenterX, arcCenterY);
                path.lineTo(arcCenterX2, arcCenterY2);
                path.lineTo(arcCenterX2 - offset, arcCenterY2);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(arcCenterX2 - offset-smallCircleRadius, arcCenterY2,smallCircleRadius,paint);
                canvas.drawText(text, arcCenterX2 - offset, arcCenterY2 + paint.getTextSize(), paint);

            } else if (arcCenterC >= 180 && arcCenterC < 270) {
                arcCenterC = 270 - arcCenterC;
                arcCenterX = (float) (centerX - radiusT * Math.sin(Math.PI * arcCenterC / 180));
                arcCenterY = (float) (centerY - radiusT * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterX2 = (float) (arcCenterX - dp2px(10) * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterY2 = (float) (arcCenterY - dp2px(10) * Math.sin(Math.PI * arcCenterC / 180));

                path.moveTo(arcCenterX, arcCenterY);
                path.lineTo(arcCenterX2, arcCenterY2);
                path.lineTo(arcCenterX2 - offset, arcCenterY2);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(arcCenterX2 - offset-smallCircleRadius, arcCenterY2, smallCircleRadius, paint);
                canvas.drawText(text, arcCenterX2 - offset, arcCenterY2 - upTextSpace, paint);

            } else if (arcCenterC >= 270 && arcCenterC < 360) {
                arcCenterC = 360 - arcCenterC;
                arcCenterX = (float) (centerX + radiusT * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterY = (float) (centerY - radiusT * Math.sin(Math.PI * arcCenterC / 180));
                arcCenterX2 = (float) (arcCenterX + dp2px(10) * Math.cos(Math.PI * arcCenterC / 180));
                arcCenterY2 = (float) (arcCenterY - dp2px(10) * Math.sin(Math.PI * arcCenterC / 180));

                path.moveTo(arcCenterX, arcCenterY);
                path.lineTo(arcCenterX2, arcCenterY2);
                path.lineTo(arcCenterX2 + offset, arcCenterY2);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(arcCenterX2 + offset+smallCircleRadius, arcCenterY2, smallCircleRadius, paint);
                canvas.drawText(text, arcCenterX2, arcCenterY2 - upTextSpace, paint);

            }
            pieEntries.get(i).setStartC(startC);
            pieEntries.get(i).setEndC(startC + sweep);
            startC += sweep;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX;
        float touchY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                //判断touch点到圆心的距离 是否小于半径
                if (Math.pow(touchX - centerX, 2) + Math.pow(touchY - centerY, 2) <= Math.pow(radius, 2)) {
                    //计算 touch点和圆心的连线 与 x轴正方向的夹角
                    float touchC = getSweep(touchX, touchY);
                    //遍历 List<PieEntry> 判断touch点在哪个扇形中
                    for (int i = 0; i < pieEntries.size(); i++) {
                        if (touchC >= pieEntries.get(i).getStartC() && touchC < pieEntries.get(i).getEndC()) {
                            pieEntries.get(i).setSelected(true);
                            if (itemClickListener != null) {
                                itemClickListener.onItemClick(i);
                            }
                        } else {
                            pieEntries.get(i).setSelected(false);
                        }
                    }
                }
                break;

        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取  touch点/圆心连线  与  x轴正方向 的夹角
     *
     * @param touchX
     * @param touchY
     */
    private float getSweep(float touchX, float touchY) {
        float xZ = touchX - centerX;
        float yZ = touchY - centerY;
        float a = Math.abs(xZ);
        float b = Math.abs(yZ);
        double c = Math.toDegrees(Math.atan(b / a));
        if (xZ >= 0 && yZ >= 0) {//第一象限
            return (float) c;
        } else if (xZ <= 0 && yZ >= 0) {//第二象限
            return 180 - (float) c;
        } else if (xZ <= 0 && yZ <= 0) {//第三象限
            return (float) c + 180;
        } else {//第四象限
            return 360 - (float) c;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private float dp2px(float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getResources().getDisplayMetrics());
    }

    private float sp2px(float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                getResources().getDisplayMetrics());
    }
}
