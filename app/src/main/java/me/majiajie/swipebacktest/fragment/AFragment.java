package me.majiajie.swipebacktest.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AFragment extends Fragment
{
    private String mContent;

    public void AFragment() {}

    public static Fragment newInstance(String content)
    {
        Fragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = getArguments().getString("content");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        TextView textView = new TextView(getContext());
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setText("测试页面\n\n" +mContent);
        textView.setBackgroundColor(0xFFECECEC);
        return textView;
    }
}
