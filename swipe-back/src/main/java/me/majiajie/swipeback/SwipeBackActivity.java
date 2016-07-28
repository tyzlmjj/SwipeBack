package me.majiajie.swipeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SwipeBackActivity extends AppCompatActivity
{
    private SwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSwipeBackHelper = new SwipeBackHelper(this);
        mSwipeBackHelper.onCreate();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mSwipeBackHelper.onPostCreate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus)
        {
            getSwipeBackLayout().recovery();
        }
    }

    @Override
    public View findViewById(int id)
    {
        View v = super.findViewById(id);
        if (v == null && mSwipeBackHelper != null)
            return mSwipeBackHelper.findViewById(id);
        return v;
    }

    public SwipeBackLayout getSwipeBackLayout()
    {
        return mSwipeBackHelper.getSwipeBackLayout();
    }

    /**
     * 设置是否可以边缘滑动返回
     * @param enable    true可以边缘滑动返回
     */
    public void setSwipeBackEnable(boolean enable)
    {
        getSwipeBackLayout().setSwipeBackEnable(enable);
    }
}
