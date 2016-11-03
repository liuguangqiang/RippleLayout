package com.liuguangqiang.ripplelayout.sample;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.liuguangqiang.ripplelayout.Ripple;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = (FloatingActionButton) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                Ripple.startActivity(MainActivity.this, TargetActivity.class, btnTest);
                break;
        }
    }

}
