package me.majiajie.swipebacktest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.swipebacktest.R;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        setSwipeBackEnable(false);

        initTab();
    }

    private void initTab()
    {
        PagerBottomTabLayout bottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        bottomTabLayout.builder()
                .addTabItem(android.R.drawable.ic_menu_camera, "相机")
                .addTabItem(android.R.drawable.ic_menu_compass, "位置")
                .addTabItem(android.R.drawable.ic_menu_search, "搜索")
                .addTabItem(android.R.drawable.ic_menu_help, "帮助")
                .build();
    }

    public void next(View v)
    {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

}
