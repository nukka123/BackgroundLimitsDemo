package com.example.demo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.demo.preferences.ConfigPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    ConfigPreferences mConfig;

    @BindView(R.id.switch1)
    Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mConfig = ConfigPreferences.from(getApplicationContext());
        mSwitch.setChecked(mConfig.getRebootDemoEnable());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mConfig.setRebootDemoEnable(b);
            }
        });
    }

    @Override
    protected void onDestroy() {
        Timber.d("onDestroy");
        super.onDestroy();
    }

    @OnClick(R.id.button_a00)
    void onClickButtonA00() {
        Intent intent = MyServiceA.newIntent(this, 0);
        ContextCompat.startForegroundService(this, intent);
    }

    @OnClick(R.id.button_a01)
    void onClickButtonA01() {
        Intent intent = MyServiceA.newIntent(this, 1);
        ContextCompat.startForegroundService(this, intent);
    }

    @OnClick(R.id.button_a02)
    void onClickButtonA02() {
        Intent intent = MyServiceA.newIntent(this, 2);
        ContextCompat.startForegroundService(this, intent);
    }

    @OnClick(R.id.button_a03_bg)
    void onClickButtonA03Background() {
        Intent intent = MyServiceA.newIntent(this, 3);
        startService(intent);
        finish();
    }


    @OnClick(R.id.button_j00)
    void onClickButtonJ00() {
        ComponentName component = new ComponentName(getApplicationContext(), MyJobService.class);
        JobInfo info = new JobInfo.Builder(100, component).setMinimumLatency(60 * 1000).build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
        finish();
    }
}
