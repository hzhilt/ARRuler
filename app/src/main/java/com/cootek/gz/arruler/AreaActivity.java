package com.cootek.gz.arruler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AreaActivity extends AppCompatActivity {

    private AreaView mAreaView;

    List<CustomPoint> points = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.area_layout);

        mAreaView = findViewById(R.id.area_layout);

        points = (List<CustomPoint>)getIntent().getSerializableExtra("points");


        CustomPoint p1 = new CustomPoint(0.1f , 0.2f,  0.3f);


        mAreaView.setPoints(points);

    }
}
