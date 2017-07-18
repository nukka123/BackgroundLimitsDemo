package com.example.demo;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.example.demo.notifications.NotificationHelper;
import com.example.demo.utils.ThreadUtils;

import timber.log.Timber;

public class MyServiceA extends Service {

    static final String EXTRA_MODE = "mode";

    public static Intent newIntent(Context context, int mode) {
        Intent intent = new Intent();
        intent.setClass(context, MyServiceA.class);
        intent.putExtra(EXTRA_MODE, mode);
        return intent;
    }

    Handler mHandler = new Handler(Looper.getMainLooper());
    AsyncTask mRunningTask;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("onStartCommand: intent=%s, flags=%d, startId=%d", intent, flags, startId);

        showToast("STARTED");

        int mode = (intent != null) ? intent.getIntExtra(EXTRA_MODE, 0) : 0;

        switch (mode) {
            case 0:
                return onStartCommand00(intent);
            case 1:
                return onStartCommand01(intent);
            case 2:
                return onStartCommand02(intent);
            case 3:
                return onStartCommand03(intent);
            default:
                throw new IllegalArgumentException("Bad Parameter.");
        }
    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy");
        super.onDestroy();
        showToast("DESTROYED");

        if (mRunningTask != null) {
            mRunningTask.cancel(true);
            mRunningTask = null;
        }
    }

    // Ok.
    int onStartCommand00(Intent intent) {
        Timber.d("onStartCommand00");
        startForeground();
        new Wait10sTask().execute();
        return START_NOT_STICKY;
    }

    // Fail, if started by startForegroundService().
    // ANR.
    int onStartCommand01(Intent intent) {
        Timber.d("onStartCommand01");
        new Wait10sTask().execute();
        return START_NOT_STICKY;
    }

    // Fail, if started by startForegroundService().
    // Bringing down service while still waiting for start foreground.
    int onStartCommand02(Intent intent) {
        Timber.d("onStartCommand02");
        new Wait00sTask().execute();
        return START_NOT_STICKY;
    }

    // Fail, if started by startService() from UI, and finished all Activities.
    int onStartCommand03(Intent intent) {
        Timber.d("onStartCommand03");
        Wait90sTask task = new Wait90sTask();
        task.execute();
        mRunningTask = task;
        return START_NOT_STICKY;
    }

    void showToast(String message) {
        final String textClass = this.getClass().getSimpleName();
        final String text = textClass + ": " + message;
        Timber.d("showToast: text=%s", text);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void startForeground() {
        Timber.d("startForeground");
        NotificationHelper helper = NotificationHelper.of(this);
        Notification noti = helper.newForegroundNoti(this);
        startForeground(helper.getRequestId(), noti);
    }

    class Wait00sTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Timber.d("doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Timber.d("onPostExecute: call stopSelf.");
            stopSelf();
        }
    }

    class Wait10sTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Timber.d("doInBackground");
            ThreadUtils.sleep(10 * 1000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Timber.d("onPostExecute: call stopSelf.");
            stopSelf();
        }
    }

    class Wait90sTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Timber.d("doInBackground");

            ThreadUtils.sleep(15 * 1000);
            showToast("15s elapsed.");

            ThreadUtils.sleep(15 * 1000);
            showToast("30s elapsed.");

            ThreadUtils.sleep(15 * 1000);
            showToast("45s elapsed.");

            ThreadUtils.sleep(15 * 1000);
            showToast("60s elapsed.");

            ThreadUtils.sleep(30 * 1000);

            if (isCancelled()) {
                showToast("cancelled.");
                return null;
            }
            showToast("90s elapsed.");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Timber.d("onPostExecute: call stopSelf.");
            stopSelf();
        }
    }
}
