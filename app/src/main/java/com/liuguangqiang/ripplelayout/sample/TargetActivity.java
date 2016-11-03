package com.liuguangqiang.ripplelayout.sample;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.liuguangqiang.ripplelayout.Point;
import com.liuguangqiang.ripplelayout.Ripple;
import com.liuguangqiang.ripplelayout.RippleLayout;

public class TargetActivity extends AppCompatActivity {

    private String tag = "RippleLayout";

    private RippleLayout rippleLayout;
    private LinearLayout layoutTop;
    private LinearLayout layoutBottom;

    private Point point;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        initViews();
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_target_activity);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.ActionBar_Title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white);
    }

    private void initViews() {
        rippleLayout = (RippleLayout) findViewById(R.id.ripple);
        rippleLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.window_color));

        Bundle bundle = getIntent().getExtras();
        point = bundle.getParcelable(Ripple.ARG_START_LOCATION);
        rippleLayout.setOnStateChangedListener(new RippleLayout.OnStateChangedListener() {
            @Override
            public void onOpened() {
                startIntoAnimation();
            }

            @Override
            public void onClosed() {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        rippleLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rippleLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                rippleLayout.start(point);
                return true;
            }
        });

        layoutTop = (LinearLayout) findViewById(R.id.layout_top);
        layoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);

        layoutTop.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                layoutTop.getViewTreeObserver().removeOnPreDrawListener(this);
                layoutTop.setTranslationY(-layoutTop.getHeight());

                layoutBottom.setTranslationY(layoutBottom.getHeight());
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (rippleLayout.canBack()) {
            if (rippleLayout.isAnimationEnd()) {
                startOutAnimation();
                rippleLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rippleLayout.back();
                    }
                }, 300);
            }
        } else {
            super.onBackPressed();
        }
    }

    private void startIntoAnimation() {
        layoutTop.animate().translationY(0).setDuration(400).setInterpolator(new DecelerateInterpolator());
        layoutBottom.animate().translationY(0).setDuration(400).setInterpolator(new DecelerateInterpolator());
    }

    private void startOutAnimation() {
        layoutTop.animate()
                .translationY(-layoutTop.getHeight())
                .alpha(0.0f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator());

        layoutBottom.animate()
                .translationY(layoutBottom.getHeight())
                .alpha(0.0f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator());
    }

}
