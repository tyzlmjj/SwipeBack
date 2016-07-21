package me.majiajie.swipebacktest.activity;

import android.os.Build;
import android.support.v7.widget.Toolbar;

import me.majiajie.swipeback.SwipeBackActivity;
import me.majiajie.swipebacktest.R;


public class BaseActivity extends SwipeBackActivity
{

    protected void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                toolbar.getLayoutParams().height = getAppBarHeight();
                toolbar.setPadding(toolbar.getPaddingLeft(),
                        getStatusBarHeight(),
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
            }

            setSupportActionBar(toolbar);
        }
    }

    private int getAppBarHeight()
    {
        return dp2px(56)+getStatusBarHeight();
    }

    private int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0)
        {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private int dp2px(float dipValue)
    {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
