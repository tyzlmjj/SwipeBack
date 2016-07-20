package me.majiajie.swipebacktest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import me.majiajie.swipeback.SwipeBackActivity;
import me.majiajie.swipebacktest.R;

public class SecondActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                SecondActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
