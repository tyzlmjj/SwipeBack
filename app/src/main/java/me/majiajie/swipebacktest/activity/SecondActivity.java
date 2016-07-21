package me.majiajie.swipebacktest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import me.majiajie.swipebacktest.R;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initToolbar();

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void next(View v)
    {
        startActivity(new Intent(SecondActivity.this,SecondActivity.class));
    }

    @Override
    public void onBackPressed()
    {
        scrollToFinishActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                scrollToFinishActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
