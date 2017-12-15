package com.hencoder.hencoderpracticedraw1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hencoder.hencoderpracticedraw1.practice.RadarEntry;
import com.hencoder.hencoderpracticedraw1.practice.RadarView;

import java.util.ArrayList;
import java.util.List;

public class RadarViewActivity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, RadarViewActivity.class);
        context.startActivity(intent);
    }

    private List<RadarEntry> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_view);
        RadarView radarView = (RadarView) findViewById(R.id.radarView);
        data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            RadarEntry entry = new RadarEntry();
            entry.setTitle("radarEntry" + i);
            entry.setValue((i + 1) * 100);
            data.add(entry);
        }
        radarView.setData(data);
    }
}
