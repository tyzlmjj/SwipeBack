package me.majiajie.swipebacktest;


import android.app.Application;

import me.majiajie.swipeback.utils.ActivityStack;

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(ActivityStack.getInstance());
    }
}
