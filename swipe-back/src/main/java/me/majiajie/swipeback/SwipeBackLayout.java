package me.majiajie.swipeback;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import me.majiajie.swipeback.utils.ActivityStack;

public class SwipeBackLayout extends FrameLayout
{
    private static final int FULL_ALPHA = 255;

    private ViewDragHelper mViewDragHelper;

    private SwipeListener mSwipeListener;

    private Activity mActivity;

    private View mContentView;

    /**
     * 记录左边移动的像素值
     */
    private int mContentLeft;

    /**
     * 判断滑动是否有效
     */
    private boolean mIsScrollOverValid;

    /**
     * 当前滑动范围 [0,1)
     */
    private float mScrollPercent;

    /**
     * 阴影
     */
    private Drawable mShadowLeft;

    /**
     * 记录阴影透明比例 0~1
     */
    private float mScrimOpacity;

    private Rect mTmpRect = new Rect();

    private boolean mInLayout;

    public SwipeBackLayout(Context context) {
        super(context);
        init(context);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        mViewDragHelper = ViewDragHelper.create(SwipeBackLayout.this,new ViewDragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

        mShadowLeft = ContextCompat.getDrawable(context,R.drawable.shadow_left);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        mInLayout = true;
        if (mContentView != null)
        {
            mContentView.layout(mContentLeft, top,
                    mContentLeft + mContentView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        }
        mInLayout = false;
    }

    @Override
    public void requestLayout()
    {
        if (!mInLayout)
        {
            super.requestLayout();
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime)
    {
        final boolean drawContent = child == mContentView;

        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (mScrimOpacity > 0 && drawContent
                && mViewDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE)
        {
            drawShadow(canvas, child);
        }
        return ret;
    }

    /**
     * 画阴影
     */
    private void drawShadow(Canvas canvas, View child)
    {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);

        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top,
                childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        mShadowLeft.draw(canvas);
    }

    @Override
    public void computeScroll() {
        mScrimOpacity = 1 - mScrollPercent;
        if (mViewDragHelper.continueSettling(true))
        {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        try
        {
            return mViewDragHelper.shouldInterceptTouchEvent(event);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 将View添加到Activity
     */
    public void attachToActivity(Activity activity)
    {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(ViewGroup decorChild)
    {
        mContentView = decorChild;
    }

    /**
     * 添加滑动监听
     * @param listener {@link SwipeListener}
     */
    public void addSwipeListener(SwipeListener listener)
    {
        mSwipeListener = listener;
    }

    public interface SwipeListener
    {
        /**
         * 边缘触发时调用
         * @param edge
         */
        void onEdgeTouch(int edge);
    }

    private class ViewDragCallback extends ViewDragHelper.Callback
    {
        @Override
        public boolean tryCaptureView(View child, int pointerId)
        {
            boolean ret = mViewDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, pointerId);
            if (ret && mSwipeListener != null)
            {
                mSwipeListener.onEdgeTouch(ViewDragHelper.EDGE_LEFT);

                mIsScrollOverValid = true;
            }
            return ret;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return ViewDragHelper.EDGE_LEFT;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mScrollPercent = Math.abs((float) left
                    / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));

            mContentLeft = left;

            moveBackgroundActivity(mScrollPercent);

            invalidate();

//            if (mScrollPercent < mScrollThreshold && !mIsScrollOverValid)
//            {
//                mIsScrollOverValid = true;
//            }

            if (mScrollPercent >= 1 && !mActivity.isFinishing())
            {
                mActivity.finish();
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel)
        {
            final int childWidth = releasedChild.getWidth();

            int left = 0, top = 0;

            left = xvel > 100 ? childWidth + mShadowLeft.getIntrinsicWidth() : 0;

            mViewDragHelper.settleCapturedViewAt(left, top);

            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx)
        {
            int ret = Math.min(child.getWidth(), Math.max(left, 0));

            return ret;
        }
    }

    /**
     * 移动背景Activity
     * @param scrollPercent
     */
    private void moveBackgroundActivity(float scrollPercent)
    {
        Activity activity = ActivityStack.getInstance().getBackActivity();
        if(activity instanceof SwipeBackActivity)
        {
            SwipeBackLayout swipeBackLayout = ((SwipeBackActivity) activity).getSwipeLayout();

            int width = swipeBackLayout.getWidth();

            swipeBackLayout.setTranslationX(-width*0.3f*Math.min(1f,1f-scrollPercent));
        }
    }


}
