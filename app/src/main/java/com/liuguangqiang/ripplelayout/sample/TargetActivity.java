/*
 *  Copyright 2016 Eric Liu
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

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
