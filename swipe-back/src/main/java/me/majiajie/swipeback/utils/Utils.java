package me.majiajie.swipeback.utils;


import android.app.Activity;
import android.app.ActivityOptions;

import java.lang.reflect.Method;

public class Utils
{
    public static void convertActivityToTranslucent(Activity activity)
    {
        try
        {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;

            for (Class clazz : classes)
            {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }

            Method method = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz, ActivityOptions.class);
            method.setAccessible(true);
            method.invoke(activity,null,null);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    public static void convertActivotyFromTranslucent(Activity activity)
    {
        try
        {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
