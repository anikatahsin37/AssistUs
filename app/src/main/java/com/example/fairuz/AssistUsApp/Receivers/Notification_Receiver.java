package com.example.fairuz.AssistUsApp.Receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.fairuz.AssistUsApp.R;

/**
 * Created by Anik on 8/30/2017.
 */
public class Notification_Receiver extends BroadcastReceiver {
    String notificationTitle = "Title";
    String notificationDescription = "Description";
    String tune = "Ringtone1";
    int notificationID = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        if (b != null) {
            notificationTitle = (String) b.get("notiTitle");
            notificationDescription = (String) b.get("notiDes");
            tune = (String) b.get("tune");
            notificationID = (int) b.get("id");
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationDescription);
        long[] pattern = {500, 500, 500, 500};
        mBuilder.setVibrate(pattern);
        Uri alarmSound = getUriFromString(tune, context);
        mBuilder.setSound(alarmSound);
        notificationManager.notify(notificationID, mBuilder.build());
    }

    Uri getUriFromString(String ringtoneName, Context context) {
        Uri uri;
        ringtoneName = ringtoneName.replace('R', 'r');
        Resources res = context.getResources();
        int soundId = res.getIdentifier(ringtoneName, "raw", context.getPackageName());
        uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + soundId);
        return uri;
    }
}
