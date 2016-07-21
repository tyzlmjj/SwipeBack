package me.majiajie.swipeback;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.majiajie.swipeback.utils.ActivityStack;

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
    public View findViewById(int id)
    {
        View v = super.findViewById(id);
        if (v == null && mSwipeBackHelper != null)
            return mSwipeBackHelper.findViewById(id);
        return v;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options)
    {
        Activity activity = ActivityStack.getInstance().getTopActivity();
        if(activity instanceof SwipeBackActivity)
        {
            View view = ((SwipeBackActivity) activity).getSwipeBackLayout();

            if(view != null)
            {
                ObjectAnimator objectAnimator =
                        ObjectAnimator.ofFloat(view,"TranslationX",-view.getWidth()*0.3f);
                objectAnimator.setDuration(200);
                objectAnimator.setCurrentPlayTime(100);
                objectAnimator.start();
            }
        }
        super.startActivityForResult(intent, requestCode, options);
    }

    public SwipeBackLayout getSwipeBackLayout()
    {
        return mSwipeBackHelper.getSwipeLayout();
    }

    /**
     * 设置是否可以边缘滑动返回
     * @param enable
     */
    public void setSwipeBackEnable(boolean enable)
    {
        getSwipeBackLayout().setSwipeBackEnable(enable);
    }

    /**
     * 右滑结束Activity
     */
    public void scrollToFinishActivity()
    {
        getSwipeBackLayout().scrollToFinishActivity();
    }

}
