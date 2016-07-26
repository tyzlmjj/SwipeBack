package me.majiajie.swipebacktest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;
import me.majiajie.swipebacktest.R;
import me.majiajie.swipebacktest.adapter.FragmentViewPagerAdapter;
import me.majiajie.swipebacktest.fragment.AFragment;

public class MainActivity extends BaseActivity
{
    private Controller mController;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(R.id.toolbar);

        initBottomTab();

        initViewPager();

        initEvent();

        setSwipeBackEnable(false);//设置不能边缘滑动返回
    }

    private void initBottomTab()
    {
        PagerBottomTabLayout bottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        mController = bottomTabLayout.builder()
                .addTabItem(android.R.drawable.ic_menu_camera, "相机")
                .addTabItem(android.R.drawable.ic_menu_compass, "位置")
                .addTabItem(android.R.drawable.ic_menu_search, "搜索")
                .addTabItem(android.R.drawable.ic_menu_help, "帮助")
                .build();
    }

    private void initViewPager()
    {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(AFragment.newInstance("1"));
        fragmentList.add(AFragment.newInstance("2"));
        fragmentList.add(AFragment.newInstance("3"));
        fragmentList.add(AFragment.newInstance("4"));

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        if(mViewPager != null)
        {
            mViewPager.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        }
    }

    private void initEvent()
    {
        mController.addTabItemClickListener(new OnTabItemSelectListener()
        {
            @Override
            public void onSelected(int index, Object tag)
            {
                mViewPager.setCurrentItem(index,false);
            }

            @Override
            public void onRepeatClick(int index, Object tag)
            {
                //重复选中
            }
        });

    }

    public void next(View v)
    {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }
}
