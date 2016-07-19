package me.majiajie.swipeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

    public SwipeLayout getSwipeLayout()
    {
        return mSwipeBackHelper.getSwipeLayout();
    }
}
