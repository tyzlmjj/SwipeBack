package me.majiajie.swipeback.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Vector;


public class ActivityStack implements Application.ActivityLifecycleCallbacks
{
    private static ActivityStack ourInstance = new ActivityStack();

    private Vector<Activity> activities = new Vector<>();

    public static ActivityStack getInstance()
    {
        return ourInstance;
    }

    private ActivityStack() {}

    public Activity getTopActivity()
    {
        if(activities.size() > 0)
        {
            return activities.get(activities.size()-1);
        }
        else
        {
            return null;
        }
    }

    public Activity getBackActivity()
    {
        if(activities.size() > 1)
        {
            return activities.get(activities.size()-2);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        int index = activities.indexOf(activity);

        if(index == -1)
        {
            activities.add(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity)
    {

    }

    @Override
    public void onActivityResumed(Activity activity)
    {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
    }


}
