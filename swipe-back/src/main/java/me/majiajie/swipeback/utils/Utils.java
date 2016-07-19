package me.majiajie.swipeback.utils;


import android.app.Activity;
import android.content.Context;

import java.lang.reflect.Method;

public class Utils
{
    public static float dp2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale;
    }

    public static void convertActivityToTranslucent(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method method = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[] {
                    null
            });
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
