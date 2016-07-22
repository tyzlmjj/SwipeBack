package me.majiajie.swipeback;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

public class SwipeBackHelper
{
    private Activity mActivity;

    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackHelper(Activity activity)
    {
        mActivity = activity;
    }

    public void onCreate()
    {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        mSwipeBackLayout = new SwipeBackLayout(mActivity);
    }

    public void onPostCreate()
    {
        mSwipeBackLayout.attachToActivity(mActivity);
    }

    protected View getContentView()
    {
        return mSwipeBackLayout.getContentView();
    }

    public View findViewById(int id)
    {
        if (mSwipeBackLayout != null)
        {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public void setSwipeBackEnable(boolean enable)
    {
        mSwipeBackLayout.setSwipeBackEnable(enable);
    }
}
