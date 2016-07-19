package me.majiajie.swipeback;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class SwipeBackHelper
{
    private Activity mActivity;

    private SwipeLayout mSwipeLayout;

    public SwipeBackHelper(Activity activity)
    {
        mActivity = activity;
    }

    public void onCreate()
    {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        mSwipeLayout = new SwipeLayout(mActivity);
        mSwipeLayout.addSwipeListener(new SwipeLayout.SwipeListener()
        {
            @Override
            public void onEdgeTouch(int edge)
            {
//                Utils.convertActivityToTranslucent(mActivity);
            }
        });
    }

    public void onPostCreate()
    {
        mSwipeLayout.attachToActivity(mActivity);
    }

    protected SwipeLayout getSwipeLayout()
    {
        return mSwipeLayout;
    }


}
