package com.hencoder.hencoderpracticedraw1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hencoder.hencoderpracticedraw1.practice.PieChartView;
import com.hencoder.hencoderpracticedraw1.practice.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PieChartViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_view);
        PieChartView pieChartView = (PieChartView) findViewById(R.id.pie_chart_view);
        pieChartView.setsRadius(ScreenUtil.dp2px(80, this));
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(1, R.color.chart_orange, true));
        pieEntries.add(new PieEntry(2, R.color.chart_green, false));
        pieEntries.add(new PieEntry(3, R.color.chart_blue, false));
        pieEntries.add(new PieEntry(4, R.color.chart_purple, false));
        pieEntries.add(new PieEntry(5, R.color.chart_mblue, false));
        pieEntries.add(new PieEntry(6, R.color.chart_turquoise, false));
        pieChartView.setPieEntries(pieEntries);
        pieChartView.setItemClickListener(new PieChartView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(PieChartViewActivity.this, "我点击了第" + position + "个区域", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
