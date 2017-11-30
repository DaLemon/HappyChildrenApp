package com.example.vrml.happychildapp.HYC_Code.WritingPractice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by AU on 2017/10/20.
 */


public class CanvasView extends View {

    public int width;
    public int height;
    private static final float TOLERANCE = 5;
    private float mX , mY;
    private Path mpath;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    Context context;


    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mpath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //抗鋸齒 讓邊邊不要有齒
        mPaint.setDither(true); //防抖動

        mPaint.setColor(Color.BLACK);

        mPaint.setStyle(Paint.Style.STROKE);
        //paint 默認畫筆為矩形，如果要改成圓形畫筆，就要加上這兩行
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(20f);
        //-------------------------------------
        //mPaint.setXfermode(null); //還原混合模式
        //mPaint.setAlpha(0xff); //設定畫筆的透明度
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mpath,mPaint);

    }

    private void touchStart(float x,float y){

        mpath.moveTo(x,y);
        mX = x; mY = y;
    }

    private void touchMove(float x , float y ){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx>=TOLERANCE || dy >=TOLERANCE){
            mpath.quadTo(mX, mY, (x+mX)/2 , (y+mY)/2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas(){
        mpath.reset();
        invalidate();
    }

    private void touchUp(){
        mpath.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}
