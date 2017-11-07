package com.example.fairuz.AssistUsApp.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.Adapters.MyAdapterSilent;
import com.example.fairuz.AssistUsApp.BackgroundServices.DisableSilentService;
import com.example.fairuz.AssistUsApp.BackgroundServices.SilentService;
import com.example.fairuz.AssistUsApp.R;
import com.example.fairuz.AssistUsApp.Receivers.Disable_Silent_Receiver;
import com.example.fairuz.AssistUsApp.Receivers.Silent_Receiver;
import com.example.fairuz.AssistUsApp.Utility.RecyclerItem;
import com.example.fairuz.AssistUsApp.Utility.SilentAdd;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.firebaseAuth;
import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.ThemeFlagDir;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.ThemeFlagParentDir;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.themeFlag;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Load;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Save;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.generateCode;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.updateCalendarFromTo;

/**
 * Created by anika on 9/6/17.
 */

public class SilentMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static int SilentFlag = 0, dltSilentFlag = 0;
    private RecyclerView recyclerView;
    private MyAdapterSilent adapter;
    private ImageButton floatButton;
    private RelativeLayout mainback;

    DrawerLayout drawerLayout;
    NavigationView navigation;

    ActionBarDrawerToggle actionBarDrawerToggle;

    public static List<RecyclerItem> listItemsSilent = new ArrayList<>();


    public static File SilentTitleDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/SilentTitles.txt");
    File SilentTitleParentDir = SilentTitleDir.getParentFile();
    public static File SilentTimeDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/SilentTimes.txt");
    File SilentTimeParentDir  = SilentTimeDir.getParentFile();


    public static Map<Integer, Calendar> silentMap = new HashMap<Integer, Calendar>();
    public static ArrayList<String> savedDataTitleSilent = new ArrayList<String>();
    public static ArrayList<String> savedDataSilentTime = new ArrayList<String>();

    public void initializer() {
        isStoragePermissionGranted();
        if (SilentTitleParentDir != null) SilentTitleParentDir.mkdirs();
        if (SilentTimeParentDir != null) SilentTimeParentDir.mkdirs();
        try {
            if (isStoragePermissionGranted() == true) {
                savedDataTitleSilent = Load(SilentTitleDir);
                savedDataSilentTime = Load(SilentTimeDir);
                listItemsSilent.clear();
                for (int j = 0; j < savedDataTitleSilent.size(); j++) {
                    String[] splited = savedDataSilentTime.get(j).trim().split("\\s+");
                    int sp1 = Integer.parseInt(splited[0]);
                    int sp2 = Integer.parseInt(splited[1]);
                    int sp3 = Integer.parseInt(splited[2]);
                    int sp4 = Integer.parseInt(splited[3]);



                    SimpleDateFormat date12Format = new SimpleDateFormat("h:mm a",Locale.ENGLISH);

                    SimpleDateFormat date24Format = new SimpleDateFormat("H m", Locale.ENGLISH);

                    String TimeStart = date12Format.format(date24Format.parse(sp1+" "+sp2));
                    String TimeEnd = date12Format.format(date24Format.parse(sp3+" "+sp4));

                    listItemsSilent.add(new RecyclerItem(savedDataTitleSilent.get(j), TimeStart + " to " + TimeEnd));
                }
                adapter = new MyAdapterSilent(listItemsSilent, this);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            String silentTitle = data.getExtras().getString("key");
            String sitentTimeLayoutFormat = data.getExtras().getString("key2");
            String sitentTimeFileFormat = data.getExtras().getString("key3");

            try {
                ArrayList<Calendar> list = updateCalendarFromTo(sitentTimeFileFormat);
                Calendar calFrom = list.get(0);
                Calendar calTo = list.get(1);
                int codeFrom = generateCode(calFrom);
                int codeTo = generateCode(calTo);
                SilentAt(calFrom, codeFrom);
                DisableSilentAt(calTo, codeTo);
                savedDataTitleSilent.add(silentTitle);
                savedDataSilentTime.add(sitentTimeFileFormat);
                Save(SilentTitleDir, savedDataTitleSilent);
                Save(SilentTimeDir, savedDataSilentTime);
                if (!silentTitle.equals("") || !sitentTimeLayoutFormat.equals("")) {
                    listItemsSilent.add(new RecyclerItem(silentTitle, sitentTimeLayoutFormat));
                }
            } catch (Exception e) {
                Toast.makeText(this, "Add: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onDestroy() {
        super.onDestroy();
        if (isStoragePermissionGranted() == true) {
            Save(SilentTitleDir, savedDataTitleSilent);
            Save(SilentTimeDir, savedDataSilentTime);
        } else {
            Toast.makeText(getApplicationContext(), "permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart() {
        super.onStart();
        initializer();

        if( theme ==1 ) setColor();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigation = (NavigationView) findViewById(R.id.nav);
        navigation.setNavigationItemSelectedListener(this);
        mainback = (RelativeLayout) findViewById(R.id.mainback);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        TextView txtProfileName = (TextView) navigation.getHeaderView(0).findViewById(R.id.gmail);
        txtProfileName.setText(""+user.getEmail());

        if( theme ==1 ) setColor();

        setUpToolBar();



        if (SilentFlag == 1) {
            try {
                Intent intent = getIntent();
                String EditedTitle = intent.getExtras().getString("EditedTitle");
                String EditedTimeFileFormat = intent.getExtras().getString("EditedTimeFileFormat");
                String PreviousTime = intent.getExtras().getString("PreviousTime");


                ArrayList<Calendar> prevList = updateCalendarFromTo(PreviousTime);
                Calendar prevCalFrom = prevList.get(0);
                Calendar prevCalTo = prevList.get(1);
                int prevCodeFrom = generateCode(prevCalFrom);
                int prevCodeTo = generateCode(prevCalTo);
                cancelSilent(prevCodeFrom);
                cancelDisableSilent(prevCodeTo);
                ArrayList<Calendar> list = updateCalendarFromTo(EditedTimeFileFormat);
                Calendar calFrom = list.get(0);
                Calendar calTo = list.get(1);
                int codeFrom = generateCode(calFrom);
                int codeTo = generateCode(calTo);
                SilentAt(calFrom, codeFrom);
                DisableSilentAt(calTo, codeTo);
                SilentFlag = 0;
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "edit: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        if (dltSilentFlag == 1) {
            try {
                Intent intent = getIntent();
                String deletedTime = intent.getExtras().getString("deletedTime");

                ArrayList<Calendar> list = updateCalendarFromTo(deletedTime);
                Calendar calFrom = list.get(0);
                Calendar calTo = list.get(1);
                int codeFrom = generateCode(calFrom);
                int codeTo = generateCode(calTo);
                cancelSilent(codeFrom);
                cancelDisableSilent(codeTo);
                dltSilentFlag = 0;
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "dlt: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializer();
        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SilentMainActivity.this, SilentAdd.class);
                startActivityForResult(intent, 200);
            }

        });


    }


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
                return true;
            } else {
                // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    public void SilentAt(Calendar cal, int notificationID) {
        Intent silentServiceIntent = new Intent(getApplicationContext(), SilentService.class);
        silentServiceIntent.putExtra("cal", cal);
        silentServiceIntent.putExtra("notificationID", notificationID);
        startService(silentServiceIntent);
    }


    public void cancelSilent(int notificationID) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Silent_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    public void DisableSilentAt(Calendar cal, int notificationID) {
        Intent disableSilentServiceIntent = new Intent(getApplicationContext(), DisableSilentService.class);
        disableSilentServiceIntent.putExtra("cal", cal);
        disableSilentServiceIntent.putExtra("notificationID", notificationID);
        startService(disableSilentServiceIntent);
    }

    public void cancelDisableSilent(int notificationID) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Disable_Silent_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }



    private void setUpToolBar()
    {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch(id)
        {
            case R.id.task:
                Intent intent = new Intent(SilentMainActivity.this,TaskMainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.Ssilent:
                //Intent intent2 = new Intent(TaskMainActivity.this,SilentMainActivity.class);
               // startActivity(intent2);
                break;

            case R.id.location:
                Intent intent3 = new Intent(SilentMainActivity.this,MapsActivity.class);
                startActivity(intent3);
                finish();
                break;

            case R.id.logout:


                firebaseAuth.signOut();
                finish();
                Intent intent4 = new Intent(SilentMainActivity.this, LoginActivity.class);
                startActivity(intent4);
                break;

            case R.id.setting:
               // Intent intent5 = new Intent(SilentMainActivity.this, settings.class);
               // startActivity(intent5);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SilentMainActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.theme_selector, null);
                RadioGroup rg = (RadioGroup) mView.findViewById(R.id.rg);
                Button done = (Button) mView.findViewById(R.id.done);
                RadioButton light = (RadioButton) mView.findViewById(R.id.light);
                RadioButton dark = (RadioButton) mView.findViewById(R.id.dark);



                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                if(theme==1) light.setChecked(true);
                else dark.setChecked(true);



                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        //  rb = (RadioButton) findViewById(i);

                        switch(i)
                        {
                            case R.id.light:
                                theme = 1;

                                if (ThemeFlagParentDir != null)
                                {
                                    ThemeFlagParentDir.mkdirs();
                                    if(themeFlag.size()==0) themeFlag.add("1");

                                }


                                themeFlag.set(0,""+theme);

                                if (isStoragePermissionGranted() == true) {

                                    Save(ThemeFlagDir,themeFlag);
                                    Toast.makeText(getApplicationContext(), theme+"", Toast.LENGTH_SHORT).show();
                                }

                                break;

                            case R.id.dark:
                                theme =0;
                                if (ThemeFlagParentDir != null)
                                {
                                    ThemeFlagParentDir.mkdirs();
                                    if(themeFlag.size()==0) themeFlag.add("1");

                                }


                                themeFlag.set(0,""+theme);

                                if (isStoragePermissionGranted() == true) {

                                    Save(ThemeFlagDir,themeFlag);
                                    Toast.makeText(getApplicationContext(), theme+"", Toast.LENGTH_SHORT).show();
                                }

                                break;



                        }
                    }
                });

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(SilentMainActivity.this, SilentMainActivity.class);
                        startActivity(intent);
                        finish();

                        dialog.dismiss();
                    }
                });
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setColor()
    {


        mainback.setBackgroundResource(R.color.lightAsh);




    }

}
