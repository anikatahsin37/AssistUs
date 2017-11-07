package com.example.fairuz.AssistUsApp.BackgroundServices;

import android.Manifest;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Double.parseDouble;

/**
 * Created by fairuz on 11/7/17.
 */

public class LocationAddService extends Service {



    DatabaseReference LocationReference;
    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private double latitude,longitude, added_latitude, added_longitude;
    Location lastLocation;
    Location location;
    LocationListener listener;
    boolean isGPSEnable, isNetworkEnable, getFromMap;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {
            getFromMap = true;

        } else {


            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //return;
                }

                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listener);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener(){
                    // @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        // TODO locationListenerGPS onStatusChanged

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }

                    // @Override
                    public void onLocationChanged(Location location) {


                    }
                });

                if (locationManager != null) {

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                }
            } else if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //return;
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);
                if (locationManager != null) {

                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                }
            }

        }
    }



    //Toast.makeText(LocationAddService.this, "AAAAA-", Toast.LENGTH_SHORT).show();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {

            LocationReference = FirebaseDatabase.getInstance().getReference().child(id).child("AddedLocationLatlng");


            LocationReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    try {
                        for (final DataSnapshot postDataSnapshot : dataSnapshot.getChildren())
                        {
                            if(postDataSnapshot.getValue()!=null)
                            {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                //Toast.makeText(LocationAddService.this, "AAAAA-"+latitude+":("+longitude, Toast.LENGTH_SHORT).show();

                                final String latlng = postDataSnapshot.getValue().toString();


                                String l[] = latlng.split(",");

                                String l1[] = l[0].split("=");
                                String l2[] = l[1].split("=");

                                String lat = l1[1].substring(0,9);
                                String lon = l2[1].substring(0,9);

                                LatLng latLng_add = new LatLng(parseDouble(lat), parseDouble(lon));

                                added_latitude = latLng_add.latitude;
                                added_longitude = latLng_add.longitude;

                                //Toast.makeText(LocationAddService.this, "AL:" +added_latitude+ " :/ "+ added_longitude, Toast.LENGTH_SHORT).show();
                                float results[] = new float[20];
                                Location.distanceBetween(latitude, longitude, added_latitude, added_longitude, results);

                                Toast.makeText(LocationAddService.this, "Results:"+results[0] , Toast.LENGTH_LONG).show();

                                if(results[0] < 500)
                                {

                                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                    //NotificationManager notificationManager  = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(LocationAddService.this)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle("Task Reminder")
                                            .setContentText("You have a pending task")
                                            .setAutoCancel(true)
                                            .setSound(defaultSoundUri);

                                    NotificationManager notificationManager =
                                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


                                }

                            }


                        }


                    }
                    catch (Exception e){

                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        /*NotificationCompat.Builder mBuilder =

                new NotificationCompat.Builder(this)
                        //.setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");*/
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, LocationAddService.class));
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


}
