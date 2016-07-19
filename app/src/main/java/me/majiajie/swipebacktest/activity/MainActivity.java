package me.majiajie.swipebacktest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import me.majiajie.swipebacktest.AppUtlis;
import me.majiajie.swipebacktest.R;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("asd","top: " + AppUtlis.getTopActivityName(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("asd","onStart");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("asd","onPostCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("asd","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("asd","onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i("asd","onPostResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("asd","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("asd","onDestroy");
    }

    public void next(View v)
    {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

}
