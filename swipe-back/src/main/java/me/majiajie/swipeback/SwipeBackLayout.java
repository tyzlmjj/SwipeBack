package me.majiajie.swipeback;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import me.majiajie.swipeback.utils.ActivityStack;
import me.majiajie.swipeback.utils.Utils;
import me.majiajie.swipeback.utils.ViewDragHelper;

public class SwipeBackLayout extends FrameLayout
{
    /**
     * 滑动销毁距离界限
     */
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.5f;

    /**
     * 滑动销毁速度界限
     */
    private static final float DEFAULT_VELOCITY_THRESHOLD = 500f;

    /**
     * 最大透明度
     */
    private static final int FULL_ALPHA = 255;

    /**
     * 最小滑动速度
     */
    private static final int MIN_FLING_VELOCITY = 200;


    private ViewDragHelper mViewDragHelper;

    private Activity mActivity;

    private View mContentView;


    /**
     * 记录左边移动的像素值
     */
    private int mContentLeft;

    /**
     * 当前滑动范围 [0,1)
     */
    private float mScrollPercent;

    /**
     * 阴影
     */
    private Drawable mShadowLeft;

    /**
     * 记录阴影透明比例 [0,1]
     */
    private float mScrimOpacity;

    private Rect mTmpRect = new Rect();

    /**
     * 判断是否正在执行onLayout方法
     */
    private boolean mInLayout;

    /**
     * 设置是否可滑动
     */
    private boolean CanSwipeBack = true;

    /**
     * 判断背景Activity是否启动进入动画
     */
    private boolean EnterAnimRunning = false;

    /**
     * 进入动画(只在释放手指时使用)
     */
    private ObjectAnimator mEnterAnim;

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
        mViewDragHelper = ViewDragHelper.create(SwipeBackLayout.this, new ViewDragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        mViewDragHelper.setMinVelocity(minVel);
        mViewDragHelper.setMaxVelocity(minVel * 2f);

        mShadowLeft = ContextCompat.getDrawable(context,R.drawable.swipeback_shadow_left);
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
    public void computeScroll()
    {
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

    /**
     * 设置是否可以滑动返回
     */
    public void setSwipeBackEnable(boolean enable)
    {
        CanSwipeBack = enable;
    }

    /**
     * 启动进入动画
     */
    public void startEnterAnim()
    {
        if (mContentView != null)
        {
            ObjectAnimator anim = ObjectAnimator
                    .ofFloat(mContentView,"TranslationX",mContentView.getTranslationX(),0f);

            anim.setDuration((long) (125*mScrimOpacity));

            mEnterAnim = anim;
            mEnterAnim.start();
        }
    }

    /**
     * 回复界面的平移到初始位置
     */
    public void recovery()
    {
        if(mEnterAnim != null && mEnterAnim.isRunning())
        {
            mEnterAnim.end();
        }
        else
        {
            mContentView.setTranslationX(0);
        }
    }

    protected View getContentView()
    {
        return mContentView;
    }

    private void setContentView(ViewGroup decorChild)
    {
        mContentView = decorChild;
    }

    private class ViewDragCallback extends ViewDragHelper.Callback
    {
        @Override
        public boolean tryCaptureView(View child, int pointerId)
        {
            boolean ret = mViewDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, pointerId);
            if (CanSwipeBack && ret)
            {
                Utils.convertActivityToTranslucent(mActivity);
                return true;
            }
            return false;
        }

        @Override
        public int getViewHorizontalDragRange(View child)
        {
            return CanSwipeBack?ViewDragHelper.EDGE_LEFT:0;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy)
        {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            if(changedView == mContentView)
            {
                mScrollPercent = Math.abs((float) left
                        / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));

                mContentLeft = left;

                //未执行动画就平移
                if(!EnterAnimRunning)
                {
                    moveBackgroundActivity();
                }

                invalidate();

                if (mScrollPercent >= 1 && !mActivity.isFinishing())
                {
                    mActivity.finish();
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel)
        {
            final int childWidth = releasedChild.getWidth();

            int left = 0, top = 0;

            if(xvel > DEFAULT_VELOCITY_THRESHOLD || mScrollPercent > DEFAULT_SCROLL_THRESHOLD)
            {
                left = childWidth + mShadowLeft.getIntrinsicWidth();
                mViewDragHelper.settleCapturedViewAt(left, top);

                if(mScrimOpacity < 0.85f)
                {
                    startAnimOfBackgroundActivity();
                }
            }
            else
            {
                left = 0;
                mViewDragHelper.settleCapturedViewAt(left, top);
            }
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx)
        {
            int ret = Math.min(child.getWidth(), Math.max(left, 0));
            return ret;
        }

        @Override
        public void onViewDragStateChanged(int state)
        {
            super.onViewDragStateChanged(state);

            if(state == ViewDragHelper.STATE_IDLE && mScrollPercent < 1f)
            {
                Utils.convertActivotyFromTranslucent(mActivity);
            }
        }
    }


    /**
     * 背景Activity开始进入动画
     */
    private void startAnimOfBackgroundActivity()
    {
        Activity activity = ActivityStack.getInstance().getBackActivity();
        if(activity instanceof SwipeBackActivity)
        {
            EnterAnimRunning = true;
            SwipeBackLayout swipeBackLayout = ((SwipeBackActivity) activity).getSwipeBackLayout();
            swipeBackLayout.startEnterAnim();
        }
    }

    /**
     * 移动背景Activity
     */
    private void moveBackgroundActivity()
    {
        Activity activity = ActivityStack.getInstance().getBackActivity();
        if(activity instanceof SwipeBackActivity)
        {
            View view = ((SwipeBackActivity) activity).getSwipeBackLayout().getContentView();

            if(view != null)
            {
                int width = view.getWidth();
                view.setTranslationX(-width*0.3f*Math.max(0f,mScrimOpacity-0.15f));
            }
        }
    }
}
