package com.example.demo;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.demo.utils.ThreadUtils;

import timber.log.Timber;


public class MyJobService extends JobService {

    JobParameters mJobParams;

    @Override
    public void onCreate() {
        Timber.d("onCreate");
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Timber.d("onStartJob");
        mJobParams = jobParameters;
        new Wait10sTask().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Timber.d("onStopJob");
        return false;
    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy");
        super.onDestroy();
        jobFinished(mJobParams, false);
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
            Timber.d("onPostExecute: start service.");
            Intent intent = MyServiceA.newIntent(getApplicationContext(), 0);
            startService(intent);

            Timber.d("onPostExecute: call stopSelf.");
            jobFinished(mJobParams, false);
        }
    }
}
