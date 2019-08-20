package com.example.runningtrial.UI;

import android.content.Context;
import android.graphics.Point;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 *  ViewGroup/Layout must be a single public class,
 *  or will get ClassNotFoundException
 */
public class VDHLayout extends LinearLayout
{
    private ViewDragHelper mDragger;
    private View mDragView;
    // 演示除了移動後，鬆手自動返回到原本的位置 點擊Button，展開/關閉Drawerlayout
    private View mAutoBackView;
    // 邊界移動時對View進行捕獲, DrawerLayout
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public VDHLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            // return ture則表示可以捕獲該view，你可以根據傳入的第一個view參數決定哪些可以捕獲
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //mEdgeTrackerView禁止直接移动
                return child == mDragView || child == mAutoBackView;
            }

            // 對child移動的邊界進行控制，left , top 分別為即將移動到的位置
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel)
            {
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == mAutoBackView)
                {
                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId)
            {
                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }

            @Override
            public int getViewHorizontalDragRange(View child)
            {
                // for clickable View such as button
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child)
            {
                // for clickable View such as button
                return getMeasuredHeight()-child.getMeasuredHeight();
            }
        });
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll()
    {
        if(mDragger.continueSettling(true))
        {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}