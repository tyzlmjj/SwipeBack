package me.majiajie.swipeback;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

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
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onEdgeTouch(int edge) {

            }
        });
    }

    public void onPostCreate()
    {
        mSwipeBackLayout.attachToActivity(mActivity);
    }

    protected SwipeBackLayout getSwipeLayout()
    {
        return mSwipeBackLayout;
    }


}
