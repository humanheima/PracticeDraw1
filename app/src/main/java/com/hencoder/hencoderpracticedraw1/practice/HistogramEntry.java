package com.hencoder.hencoderpracticedraw1.practice;

import android.graphics.Paint;

/**
 * Created by dumingwei on 2017/12/11 0011.
 */

public class HistogramEntry {

    private float number;
    private int color;
    private String text;
    private float left, top, right, bottom;

    public HistogramEntry(int number, int color, String text) {
        this.number = number;
        this.color = color;
        this.text = text;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

}
