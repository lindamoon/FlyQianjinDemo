package com.feima.flyqianjindemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by lixb on 2016/3/26.
 */
public class FlyControlView extends RelativeLayout {

    private int mFlOuterWidth;   //外圈控件的宽度
    private int mFlOuterHeight;  //外圈控件的高度
    private int mFlInnerWidth;
    private int mFlInnerHeight;
    private FrameLayout mFlInner; //外圈控件
    private FrameLayout mFlOuter; //中心圆控件
    private int mRadius; //中心圆能移动的半径

    private int initX;//初始x坐标
    private int initY;//初始y坐标

    public FlyControlView(Context context) {
        super(context);
        initView(context);
    }



    public FlyControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public FlyControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public FlyControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView(Context context) {
        final View view = View.inflate(context, R.layout.view_flycontrol, null);

        mFlOuter = (FrameLayout) view.findViewById(R.id.fl_outer);
        mFlInner = (FrameLayout) view.findViewById(R.id.fl_inner);


        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                mFlOuterWidth = mFlOuter.getWidth();
                mFlOuterHeight = mFlOuter.getHeight();

                mFlInnerWidth = mFlInner.getWidth();
                mFlInnerHeight = mFlInner.getHeight();

                int[] innerLocation = new int[2];
                mFlInner.getLocationInWindow(innerLocation);

                initX = innerLocation[0];
                initY = innerLocation[1];

                int[] outerLocation = new int[2];
                mFlOuter.getLocationInWindow(outerLocation);

                mRadius =Math.abs(outerLocation[0] - innerLocation[0] + (mFlInnerWidth / 2));
                int tempRadius = Math.abs(outerLocation[1] - innerLocation[1] + (mFlInnerHeight / 2));
                Log.e("FlyControlView", "x:" + mRadius + "Y:" + tempRadius);
            }
        });

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startX = 0, startY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        setViewLocation(mFlInner, startX, startY);
                        setViewLocation(mFlOuter, startX, startY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float curX = event.getX();
                        float curY = event.getY();
                        float diffX = curX - startX;
                        float diffY = curY - startY;
                        int distance = (int) Math.sqrt(diffX * diffX + diffY * diffY);
                        if (distance <= mRadius) {
                            setViewLocation(mFlInner, curX,curY);
                        }

                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        setViewLocation(mFlInner, initX, initY);
                        setViewLocation(mFlOuter, initX, initY);
                        break;

                }

                return true;
            }
        });
        addView(view);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 日志输出 中心点位置坐标
     */
    public void logLocationInfo() {
        int[] outerCenterPoint = new int[2];
        mFlOuter.getLocationOnScreen(outerCenterPoint);
        Log.e("FlyControlView", "OnScreen X=" + (outerCenterPoint[0] + (mFlOuterWidth / 2)) + "Y=" + (outerCenterPoint[1] + (mFlOuterHeight / 2)));
        mFlOuter.getLocationInWindow(outerCenterPoint);
        Log.e("FlyControlView", "InWindow X=" + (outerCenterPoint[0]+(mFlOuterWidth /2)) + "Y=" + (outerCenterPoint[1]+(mFlOuterHeight /2)));

        int[] interCenterPoint = new int[2];
        mFlInner.getLocationOnScreen(interCenterPoint);
        Log.e("FlyControlView", "OnScreen X=" + (outerCenterPoint[0] + (mFlInnerWidth / 2)) + "Y=" + (outerCenterPoint[1] + (mFlInnerHeight / 2)));
        mFlInner.getLocationInWindow(interCenterPoint);
        Log.e("FlyControlView", "InWindow X=" + (outerCenterPoint[0] + (mFlInnerWidth / 2)) + "Y=" + (outerCenterPoint[1] + (mFlInnerHeight / 2)));

    }

    private void setViewLocation(View view, float x, float y) {
        view.setX(x-(view.getWidth()/2));
        view.setY(y-(view.getHeight()/2));
    }

}
