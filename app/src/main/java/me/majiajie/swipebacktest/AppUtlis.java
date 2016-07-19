package me.majiajie.swipebacktest;


import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.util.List;

public class AppUtlis
{
    private AppUtlis(){}

    /**
     * 获取栈顶Activity类名,一般只能获取自己应用的，5.0之后对这一权限做了极大的限制
     * <p>
     *     需要添加权限: android.permission.GET_TASKS
     * </p>
     */
    public static String getTopActivityName(Context context)
    {
        String name = "";

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            List<ActivityManager.AppTask> appTasks = manager.getAppTasks();
            if(appTasks != null && appTasks.size() > 0)
            {
                name = appTasks.get(0).getTaskInfo().topActivity.getClassName();
            }
        }
        else
        {
            List<ActivityManager.RunningTaskInfo> taskInfos = manager.getRunningTasks(1);
            if(taskInfos != null && taskInfos.size() > 0)
            {
                name = taskInfos.get(0).topActivity.getClassName();
            }
        }

        return name;
    }
}
