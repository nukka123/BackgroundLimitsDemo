package com.example.demo.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.demo.utils.BuildUtils;

public class NotificationHelper {

    final Context mContext;

    static final String CHANNEL_1 = "channel_1";

    public static NotificationHelper of(Context context) {
        return new NotificationHelper(context);
    }

    NotificationHelper(Context context) {
        mContext = context;
    }

    NotificationManager getManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.O)
    NotificationChannel newChannel1() {
        String name = "Category1 Title";
        String description = "Category1 Description.";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(CHANNEL_1, name, importance);
        channel.setDescription(description);
        return channel;
    }

    public void setupChannels() {
        if (BuildUtils.isAtLeastO()) {
            NotificationChannel channel1 = newChannel1();
            getManager().createNotificationChannel(channel1);
        }
    }


    public Notification newForegroundNoti(Service service) {
        String message = service.getClass().getSimpleName();
        return new NotificationCompat.Builder(mContext, CHANNEL_1)
                .setContentTitle("フォアグラウンドで起動中")
                .setContentText("By " + message)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .build();
    }

    public int getReqestId(){
        return 100;
    }
}
