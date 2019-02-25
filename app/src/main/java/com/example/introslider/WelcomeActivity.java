package com.example.introslider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] layouts = {R.layout.first_slide, R.layout.second_slid, R.layout.third_slide, R.layout.fourth_slide};
    private PageAdapter pageAdapter;
    private ViewPager viewPager;
    private LinearLayout dots_layout;
    private ImageView[] dot;
    private Button bn_next, bn_skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new PreferencesConfig(this).checkPreference()) {
            skipHome();
        }

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_welcome);


        viewPager = findViewById(R.id.container_view);
        pageAdapter = new PageAdapter(this, layouts);
        viewPager.setAdapter(pageAdapter);


        dots_layout = findViewById(R.id.liner_layout);
        createDots(0);
        bn_next = findViewById(R.id.next_bn);
        bn_skip = findViewById(R.id.skip_bn);
        bn_skip.setOnClickListener(this);
        bn_next.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);

                if (position == layouts.length - 1) {
                    bn_next.setText("Start");
                    bn_skip.setVisibility(View.INVISIBLE);
                } else {
                    bn_next.setText("Next");
                    bn_next.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void createDots(int current) {

        if (dots_layout != null)
            dots_layout.removeAllViews();
        dot = new ImageView[layouts.length];

        for (int i = 0; i < layouts.length; i++) {
            dot[i] = new ImageView(this);

            if (i == current) {
                dot[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            } else {
                dot[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.unactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            dots_layout.addView(dot[i], params);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.skip_bn:
                skipHome();
                new PreferencesConfig(this).writePreference();
                break;

            case R.id.next_bn:
                nextHome();
                break;
        }

    }

    private void skipHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void nextHome() {

        int position = viewPager.getCurrentItem() + 1;
        if (position < layouts.length) {
            viewPager.setCurrentItem(position);
        } else {
            skipHome();
            new PreferencesConfig(this).writePreference();
        }
    }
}
