package com.example.fairuz.AssistUsApp.BackgroundServices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.Receivers.Disable_Silent_Receiver;

import java.util.Calendar;

/**
 * Created by Anik on 9/9/2017.
 */
public class DisableSilentService extends Service {
    Calendar cal = Calendar.getInstance();
    int notificationID = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Bundle b = intent.getExtras();
            if (b != null) {
                cal = (Calendar) b.get("cal");
                notificationID = (int) b.get("notificationID");
                DisableSilentAt(cal, notificationID);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // startService(new Intent(this, DisableSilentService.class));  //adding this line doesn't let the service to stop
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void DisableSilentAt(Calendar cal, int notificationID) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent disableSilentRecieverIntent = new Intent(getApplicationContext(), Disable_Silent_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, disableSilentRecieverIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
