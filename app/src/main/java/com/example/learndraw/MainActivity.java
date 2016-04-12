package com.example.learndraw;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button btnSimpleDraw, btnTimerDraw;
    MySurfaceView sfv;
    private MagnificentChart chart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (MagnificentChart) this.findViewById(R.id.order_chart_id);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 0.6);
        int scrHeight = (int) (dm.widthPixels * 0.6);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                scrWidth, scrHeight);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        chart.setLayoutParams(layoutParams);
        chart.setMaxValue(100);
        chart.setChartBackgroundColor(Color.WHITE);
        List<MagnificentChartItem> chartItemsList = new ArrayList<MagnificentChartItem>();
        for (int i = 0;i<20;i++){
            chartItemsList.add(new MagnificentChartItem("", 0, Color.BLUE, "其他"));
            chartItemsList.add(new MagnificentChartItem("", 1, Color.RED, "hong"));
        }
        chart.setChartItemsList(chartItemsList,40);

//        btnSimpleDraw = (Button) this.findViewById(R.id.Button01);
//        btnTimerDraw = (Button) this.findViewById(R.id.Button02);
//        btnSimpleDraw.setOnClickListener(new ClickEvent());
//        btnTimerDraw.setOnClickListener(new ClickEvent());
//        sfv = (MySurfaceView) this.findViewById(R.id.SurfaceView01);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        sfv.isDraw = false;
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {

//            if (v == btnSimpleDraw) {
//
//                sfv.isStart = true;
//
//            } else if (v == btnTimerDraw) {
//                sfv.isStart = false;
//            }

        }

    }
}
