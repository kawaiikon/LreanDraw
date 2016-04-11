package com.example.learndraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

//    Button btnSingleThread, btnDoubleThread;
//    SurfaceView sfv;
//    SurfaceHolder sfh;
//    ArrayList<Integer> imgList = new ArrayList<Integer>();
//    int imgWidth, imgHeight;
//    Bitmap bitmap;//独立线程读取，独立线程绘图


    /** Called when the activity is first created. */
    Button btnSimpleDraw, btnTimerDraw;
    SurfaceView sfv;
    SurfaceHolder sfh;

    private Timer mTimer;
    private MyTimerTask mTimerTask;
    int Y_axis[],//保存正弦波的Y轴上的点
            centerY,//中心线
            oldX,oldY,//上一个XY点
            currentX;//当前绘制到的X轴上的点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSimpleDraw = (Button) this.findViewById(R.id.Button01);
        btnTimerDraw = (Button) this.findViewById(R.id.Button02);
        btnSimpleDraw.setOnClickListener(new ClickEvent());
        btnTimerDraw.setOnClickListener(new ClickEvent());
        sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);
        sfh = sfv.getHolder();

        //动态绘制正弦波的定时器
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();

        // 初始化y轴数据
        centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv.getTop()) / 2;
        Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
        for (int i = 1; i < Y_axis.length; i++) {// 计算正弦波
            Y_axis[i - 1] = centerY - (int) (100 * Math.sin(i * 2 * Math.PI / 180));
        }

//        btnSingleThread = (Button) this.findViewById(R.id.Button01);
//        btnDoubleThread = (Button) this.findViewById(R.id.Button02);
//        btnSingleThread.setOnClickListener(new ClickEvent());
//        btnDoubleThread.setOnClickListener(new ClickEvent());
//        sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);
//        sfh = sfv.getHolder();
//        sfh.addCallback(new MyCallBack());// 自动运行surfaceCreated以及surfaceChanged
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (v == btnSimpleDraw) {

                oldY = centerY;
                SimpleDraw(Y_axis.length-1);//直接绘制正弦波

            } else if (v == btnTimerDraw) {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = new Timer();
                    ClearDraw();
                }
                oldY = centerY;
                mTimer.schedule(mTimerTask, 0, 5);//动态绘制正弦波
            }

//            if (v == btnSingleThread) {
//                new Load_DrawImage(0, 0).start();//开一条线程读取并绘图
//            } else if (v == btnDoubleThread) {
//                new LoadImage().start();//开一条线程读取
//                new DrawImage(imgWidth + 10, 0).start();//开一条线程绘图
//            }

        }

    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            SimpleDraw(currentX);
            currentX++;//往前进
            if (currentX == Y_axis.length - 1) {//如果到了终点，则清屏重来
                ClearDraw();
                currentX = 0;
                oldY = centerY;
            }
        }

    }

    /*
     * 绘制指定区域
     */
    void SimpleDraw(int length) {
        if (length == 0)
            oldX = 0;
        Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + length,
                getWindowManager().getDefaultDisplay().getHeight()));// 关键:获取画布
        Log.i("Canvas:",
                String.valueOf(oldX) + "," + String.valueOf(oldX + length));

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
        sfh.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
    }

    void ClearDraw() {
        Canvas canvas = sfh.lockCanvas(null);
        canvas.drawColor(Color.BLACK);// 清除画布
        sfh.unlockCanvasAndPost(canvas);

    }

//    class MyCallBack implements SurfaceHolder.Callback {
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width,
//                                   int height) {
//            Log.i("Surface:", "Change");
//
//        }
//
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            Log.i("Surface:", "Create");
//
//            // 用反射机制来获取资源中的图片ID和尺寸
//            Field[] fields = R.drawable.class.getDeclaredFields();
//            for (Field field : fields) {
//                if (!"icon".equals(field.getName()))// 除了icon之外的图片
//                {
//                    int index = 0;
//                    try {
//                        index = field.getInt(R.drawable.class);
//                    } catch (IllegalArgumentException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    // 保存图片ID
//                    imgList.add(index);
//                }
//            }
//            // 取得图像大小
//            Bitmap bmImg = BitmapFactory.decodeResource(getResources(),
//                    imgList.get(0));
//            imgWidth = bmImg.getWidth();
//            imgHeight = bmImg.getHeight();
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            Log.i("Surface:", "Destroy");
//
//        }
//
//    }
//
//    /*
//     * 读取并显示图片的线程
//     */
//    class Load_DrawImage extends Thread {
//        int x, y;
//        int imgIndex = 0;
//
//        public Load_DrawImage(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        public void run() {
//            while (true) {
//                Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x
//                        + imgWidth, this.y + imgHeight));
//                Bitmap bmImg = BitmapFactory.decodeResource(getResources(),
//                        imgList.get(imgIndex));
//                c.drawBitmap(bmImg, this.x, this.y, new Paint());
//                imgIndex++;
//                if (imgIndex == imgList.size())
//                    imgIndex = 0;
//
//                sfh.unlockCanvasAndPost(c);// 更新屏幕显示内容
//            }
//        }
//    };
//
//    /*
//     * 只负责绘图的线程
//     */
//    class DrawImage extends Thread {
//        int x, y;
//
//        public DrawImage(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        public void run() {
//            while (true) {
//                if (bitmap != null) {//如果图像有效
//                    Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x
//                            + imgWidth, this.y + imgHeight));
//
//                    c.drawBitmap(bitmap, this.x, this.y, new Paint());
//
//                    sfh.unlockCanvasAndPost(c);// 更新屏幕显示内容
//                }
//            }
//        }
//    };
//
//    /*
//     * 只负责读取图片的线程
//     */
//    class LoadImage extends Thread {
//        int imgIndex = 0;
//
//        public void run() {
//            while (true) {
//                bitmap = BitmapFactory.decodeResource(getResources(),
//                        imgList.get(imgIndex));
//                imgIndex++;
//                if (imgIndex == imgList.size())//如果到尽头则重新读取
//                    imgIndex = 0;
//            }
//        }
//    };
}
