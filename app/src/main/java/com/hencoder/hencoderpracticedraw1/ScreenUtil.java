package com.hencoder.hencoderpracticedraw1;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by dumingwei on 2017/12/1 0001.
 */

public class ScreenUtil {

    public static int dp2px(int dpVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(int dpVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
