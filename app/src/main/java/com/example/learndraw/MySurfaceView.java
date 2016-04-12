package com.example.learndraw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created on 2016/4/11.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Activity mContext;
    private SurfaceHolder holder;
    private RenderThread renderThread;
    public boolean isDraw = true;// 控制绘制的开关
    public boolean isStart = false;//控制暂停

    int Y_axis[],//保存正弦波的Y轴上的点
            centerY,//中心线
            oldX, oldY,//上一个XY点
            currentX;//当前绘制到的X轴上的点

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = (Activity) context;
        holder = this.getHolder();
        holder.addCallback(this);
        renderThread = new RenderThread();//动态绘制正弦波的定时器

        // 初始化y轴数据
        centerY = (mContext.getWindowManager().getDefaultDisplay().getHeight() - getTop()) / 2;
        Y_axis = new int[mContext.getWindowManager().getDefaultDisplay().getWidth()];
        for (int i = 1; i < Y_axis.length; i++) {// 计算正弦波
            Y_axis[i - 1] = centerY - (int) (100 * Math.sin(i * 2 * Math.PI / 180));
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        oldY = centerY;
        renderThread.start();
        Log.e("SurfaceView", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("SurfaceView", "format===" + format + "width===" + width + "height===" + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;
        Log.e("SurfaceView", "surfaceDestroyed");
    }

    /**
     * 绘制界面的线程
     */
    private class RenderThread extends Thread {
        @Override
        public void run() {
            // 不停绘制界面
            while (isDraw) {
                if (isStart) {
                    oldY = centerY;
                    drawUI(currentX);
                    try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentX++;//往前进
                    if (currentX == Y_axis.length - 1) {//如果到了终点，则清屏重来
                        ClearDraw();
                        currentX = 0;
                        oldY = centerY;
                    }
                }
            }
            super.run();
        }
    }

    /**
     * 界面绘制
     */
    private void drawUI(int length) {
        Canvas canvas = holder.lockCanvas(new Rect(oldX, 0, oldX + length,
                mContext.getWindowManager().getDefaultDisplay().getHeight()));
        try {
            drawCanvas(canvas, length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /*
     * 绘制指定区域
     */
    private void drawCanvas(Canvas canvas, int length) {
        // 在 canvas 上绘制需要的图形
        Log.e("Surface", "drawCanvas");
        if (length == 0)
            oldX = 0;

        Paint mPaint = new Paint();
        mPaint.setColor(Color.GREEN);// 画笔为绿色
        mPaint.setStrokeWidth(2);// 设置画笔粗细

        int y;
        for (int i = oldX + 1; i < length; i++) {// 绘画正弦波
            y = Y_axis[i - 1];
            canvas.drawLine(oldX, oldY, i, y, mPaint);
            oldX = i;
            oldY = y;
        }
    }

    private void ClearDraw() {
        Canvas canvas = holder.lockCanvas(null);
        canvas.drawColor(Color.BLACK);// 清除画布
        holder.unlockCanvasAndPost(canvas);
    }
}
