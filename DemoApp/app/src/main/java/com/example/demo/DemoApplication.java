package com.example.demo;

import android.app.Application;

import com.example.demo.notifications.NotificationHelper;

import timber.log.Timber;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        Timber.plant(new Timber.DebugTree());
        Timber.d("onCreate");
        super.onCreate();

        NotificationHelper.of(this).setupChannels();
    }
}
