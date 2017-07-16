package com.example.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.example.demo.preferences.ConfigPreferences;

import timber.log.Timber;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("onReceive: intent=%s", intent);

        ConfigPreferences prefs = ConfigPreferences.from(context);
        Intent serviceIntent = MyServiceA.newIntent(context, 0);

        if (prefs.getRebootDemoEnable()) {
            //Fail, IllegalStateException: Not allowed to start service.
            context.startService(serviceIntent);
        } else {
            ContextCompat.startForegroundService(context, serviceIntent);
        }
    }
}
