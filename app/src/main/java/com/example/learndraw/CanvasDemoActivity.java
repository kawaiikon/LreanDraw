package com.example.learndraw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.view.View;

/**
 * Created on 2016/4/8.
 */
public class CanvasDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView1extends(this));
    }

    class CustomView1extends extends View {

        Paint paint;

        public CustomView1extends(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setAntiAlias(true);//无锯齿
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setXfermode(new Xfermode());
            paint.setStrokeWidth(3);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //圆形
            canvas.drawCircle(100, 100, 90, paint);

//            弧线
            RectF rectF = new RectF(0,0,100,100);
            canvas.drawArc(rectF,//弧线所使用的矩形区域大小
                    360,//开始角度;顺时针旋转，0度在右边水平位置
                    30,//弧线扫过的角度
                    true,//是否是扇形
                    paint);

//            椭圆
            RectF rect = new RectF(0,0,200,300);
            canvas.drawOval(rect, paint);

//            按照既定点 绘制文本内容        
            canvas.drawPosText("Android777", new float[]{
                10, 10,//第一个字母在坐标10,10                
                        20, 20,//第二个字母在坐标20,20                
                        30, 30,//....                
                        40, 40,
                        50, 50,
                        60, 60,
                        70, 70,
                        80, 80,
                        90, 90,
                        100, 100
            },paint);


//            圆角矩形
            canvas.drawRoundRect(rectF,30,//X轴半径
                    30,//Y轴半径
                    paint);

            Path path = new Path();//定义一条路径
            path.moveTo(100, 100);//移动到 坐标10,10
            path.lineTo(500,600);
            path.lineTo(1000, 800);
            path.lineTo(100, 100);
            canvas.drawPath(path,paint);
            canvas.drawTextOnPath("Android777开发者博客1123154651654987561364984165411841216545613216544Android777开发者博客1123154651654987561364984165411841216545613216544Android777开发者博客1123154651654987561364984165411841216545613216544Android777开发者博客1123154651654987561364984165411841216545613216544", path, 100, 100, paint);//数字表示转折点间隔

        }
    }
}
