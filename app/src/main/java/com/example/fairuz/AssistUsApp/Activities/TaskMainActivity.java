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

import com.example.fairuz.AssistUsApp.Adapters.MyAdapter;
import com.example.fairuz.AssistUsApp.BackgroundServices.NotificationService;
import com.example.fairuz.AssistUsApp.R;
import com.example.fairuz.AssistUsApp.Receivers.Notification_Receiver;
import com.example.fairuz.AssistUsApp.Utility.RecyclerItem;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
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
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.updateCalendarFromText;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.updateMapFromList;

public class TaskMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ImageButton floatButton;
    private RelativeLayout mainback;

    public static List<RecyclerItem> listItems = new ArrayList<>();
    public static Map<Integer, Calendar> map = new HashMap<Integer, Calendar>();

    DrawerLayout drawerLayout;
    NavigationView navigation;

    ActionBarDrawerToggle actionBarDrawerToggle;

    public static int TaskFlag = 0,TaskfirstFlag=0;
    public static int dltFlag = 0;
    public static File TaskTitleDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskTitleLists.txt");
    File TaskTitleParentDir = TaskTitleDir.getParentFile();
    public static File TaskTimeDir= new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskTimeLists.txt");
    File TaskTimeParentDir = TaskTimeDir.getParentFile();
    public static File TaskDescriptionDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskDescriptionLists.txt");
    File TaskDescriptionParentDir= TaskDescriptionDir.getParentFile();
    public static File TaskTuneDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskTune.txt");
    File TaskTuneParentDir = TaskTuneDir.getParentFile();

    public static File TaskNewArlarmTimeDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/NewAlarmTime.txt");
    File TaskNewArlarmTimeParentDir = TaskNewArlarmTimeDir.getParentFile();
    public static File TaskAlarmBeforeDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/AlarmBefore.txt");
    File TaskAlarmBeforeParentDir = TaskAlarmBeforeDir.getParentFile();

    public static File IconDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/Icon.txt");
    File IconParentDir = TaskAlarmBeforeDir.getParentFile();

    public static ArrayList<String> savedTaskTitle = new ArrayList<String>();
    public static ArrayList<String> savedTaskTime = new ArrayList<String>();
    public static ArrayList<String> savedTaskDescription = new ArrayList<String>();
    public static ArrayList<String> savedTaskTune = new ArrayList<String>();
    public static ArrayList<String> savedtaskNewAlarmTime = new ArrayList<String>();
    public static ArrayList<String> savedTaskAlarmBefore = new ArrayList<String>();
    public static ArrayList<String> savedIcon = new ArrayList<String>();


    void AdapterSetting() {
        adapter = new MyAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
    }


    public void initializer() {
        isStoragePermissionGranted();
        if (TaskTitleParentDir != null) TaskTitleParentDir.mkdirs();
        if (TaskTimeParentDir != null) TaskTimeParentDir.mkdirs();
        if (TaskDescriptionParentDir != null) TaskDescriptionParentDir.mkdirs();
        if (TaskTuneParentDir != null) TaskTuneParentDir.mkdirs();
        if (TaskNewArlarmTimeParentDir != null) TaskNewArlarmTimeParentDir.mkdirs();
        if (TaskAlarmBeforeParentDir != null) TaskAlarmBeforeParentDir.mkdirs();
        if (IconParentDir != null) IconParentDir.mkdirs();

        try {
            if (isStoragePermissionGranted() == true) {
                savedTaskTitle = Load(TaskTitleDir);
                savedTaskTime = Load(TaskTimeDir);
                savedTaskDescription = Load(TaskDescriptionDir);
                savedTaskTune = Load(TaskTuneDir);
                savedtaskNewAlarmTime = Load(TaskNewArlarmTimeDir);
                savedIcon=Load(IconDir);
                savedTaskAlarmBefore = Load(TaskAlarmBeforeDir);
                updateMapFromList(map, savedTaskTime);
                listItems.clear();
                for (int j = 0; j < savedTaskTitle.size(); j++) {
                    Calendar cal = updateCalendarFromText(savedTaskTime.get(j));
                    map.put(generateCode(cal), cal);
                    String[] splited = savedTaskTime.get(j).trim().split("\\s+");
                    int sp1 = Integer.parseInt(splited[0]);
                    int sp2 = Integer.parseInt(splited[1]);
                    int sp3 = Integer.parseInt(splited[2]);
                    int sp4 = Integer.parseInt(splited[3]);
                    int sp5 = Integer.parseInt(splited[4]);


                    String Date = sp1+" "+sp2+" "+sp3+" ";
                    String Time = sp4+" "+sp5;

                    DateFormat formatLayout = new SimpleDateFormat("MMM d,yyyy h:mm a",Locale.ENGLISH);
                    DateFormat formatFile = new SimpleDateFormat("d M yyyy H m",Locale.ENGLISH);

                    String DateLayoutFormat = formatLayout.format(formatFile.parse(Date+Time));


                    listItems.add(new RecyclerItem(savedTaskTitle.get(j), DateLayoutFormat));
                }
                adapter = new MyAdapter(listItems, this);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, "initializer: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }




    public void onDestroy() {
        super.onDestroy();

        if (isStoragePermissionGranted() == true) {
            Save(TaskTitleDir, savedTaskTitle);
            Save(TaskTimeDir, savedTaskTime);
            Save(TaskDescriptionDir, savedTaskDescription);
            Save(TaskTuneDir, savedTaskTune);
            Save(TaskNewArlarmTimeDir, savedtaskNewAlarmTime);
            Save(TaskAlarmBeforeDir, savedTaskAlarmBefore);
            Save(IconDir,savedIcon);

        } else {
            Toast.makeText(getApplicationContext(), "permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart() {
        super.onStart();
        if( theme ==1 ) setColor();
        initializer();
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

        setUpToolBar();
        if( theme ==1 ) setColor();

        if(TaskfirstFlag==1)
        {

            Intent data = getIntent();
            String message = data.getExtras().getString("key");
            String message2 = data.getExtras().getString("key2");
            String realTime = data.getExtras().getString("key3");
            String timeForFile = realTime;

            DateFormat formatTo = new SimpleDateFormat("d MMM, yyyy h:mm a",Locale.ENGLISH);
            DateFormat formatFrom = new SimpleDateFormat("d M yyyy H m",Locale.ENGLISH);

            try {
                realTime = formatTo.format(formatFrom.parse(realTime));
            } catch (ParseException e) {
                //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            String description = data.getExtras().getString("key4");
            String tune = data.getExtras().getString("key5");
            String NewAlarmTimeAfterMinus = data.getExtras().getString("key6");
            String AlarmBeforeText = data.getExtras().getString("key7");
            String icon =  data.getExtras().getString("key8");
            String vibrate =  data.getExtras().getString("vibrate");
            Toast.makeText(this,vibrate, Toast.LENGTH_LONG).show();

            try {
                Calendar cal = updateCalendarFromText(NewAlarmTimeAfterMinus);
                int code = 0;
                code = generateCode(cal);

                if (!map.containsKey(code)) {
                    Toast.makeText(this,"Added: " + Integer.toString(code), Toast.LENGTH_LONG).show();
                    map.put(code, cal);
                    NotifyAt(cal, message, realTime, tune, code);
                    savedTaskTitle.add(message);
                    savedTaskTime.add(timeForFile);
                    savedTaskDescription.add(description);
                    savedTaskTune.add(tune);
                    savedtaskNewAlarmTime.add(NewAlarmTimeAfterMinus);
                    savedTaskAlarmBefore.add(AlarmBeforeText);
                    savedIcon.add(icon);
                    Save(TaskTitleDir, savedTaskTitle);
                    Save(TaskTimeDir, savedTaskTime);
                    Save(TaskDescriptionDir, savedTaskDescription);
                    Save(TaskTuneDir, savedTaskTune);
                    Save(TaskNewArlarmTimeDir, savedtaskNewAlarmTime);
                    Save(TaskAlarmBeforeDir, savedTaskAlarmBefore);
                    Save(IconDir,savedIcon);
                    if (!message.equals("") || !message2.equals("")) {
                        listItems.add(new RecyclerItem(message, message2));
                    }
                } else
                    Toast.makeText(this, "Oops! Another task already exists at selected time", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "Task Not Saved", Toast.LENGTH_SHORT).show();
            }

            TaskfirstFlag=0;

            finish();
        }



        if (TaskFlag == 1) {


            Intent intent = getIntent();
            String EditedTitle = intent.getExtras().getString("EditedTitle");
            String EditedTimeFileFormat = intent.getExtras().getString("EditedTimeFileFormat");
            String realTime = EditedTimeFileFormat;
            DateFormat formatTo = new SimpleDateFormat("d MMM, yyyy h:mm a",Locale.ENGLISH);
            DateFormat formatFrom = new SimpleDateFormat("d M yyyy H m",Locale.ENGLISH);

            try {
                realTime = formatTo.format(formatFrom.parse(realTime));
            } catch (ParseException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }

            String PreviousTime = intent.getExtras().getString("PreviousTime");
            String EditedDescription = intent.getExtras().getString("EditedDescription");
            String EditedTune = intent.getExtras().getString("EditedTune");
            String EditedAlarmTime = intent.getExtras().getString("EditedAlarmTime");
            String EditedAlarmText = intent.getExtras().getString("EditedAlarmText");


            try {
                Calendar prevCal = updateCalendarFromText(PreviousTime);
                Calendar cal = updateCalendarFromText(EditedTimeFileFormat);
                int code = 0;
                int prevCode = 0;
                code = generateCode(cal);
                prevCode= generateCode(prevCal);
                Toast.makeText(this, Integer.toString(code), Toast.LENGTH_SHORT).show();
                    map.remove(prevCode);
                    cancelAlarm(prevCode);

                    map.put(code, cal);
                    NotifyAt(cal, EditedTitle, realTime, EditedTune, code);
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Time not changed", Toast.LENGTH_LONG).show();
            }
            TaskFlag = 0;
            finish();
        }

        if(dltFlag==1){
            Intent intent = getIntent();
            String deletedTime = intent.getExtras().getString("deletedTime");
            Calendar cal = updateCalendarFromText(deletedTime);
            int code = generateCode(cal);
            cancelAlarm(code);
            if(map.containsKey(code)) map.remove(code);
            dltFlag = 0;
            finish();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializer();

        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TaskMainActivity.this, TaskManagementAddActivity.class);
                TaskManagementAddActivity.selectedTune ="Ringtone1";
                startActivity(intent);

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

    public void NotifyAt(Calendar cal, String notificationTitle, String notificationDescription, String tune, int notificationID) {
        Intent notificationServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
        notificationServiceIntent.putExtra("cal", cal);
        notificationServiceIntent.putExtra("notiTitle", notificationTitle);
        notificationServiceIntent.putExtra("notiDes", notificationDescription);
        notificationServiceIntent.putExtra("tune", tune);
        notificationServiceIntent.putExtra("id", notificationID);
        startService(notificationServiceIntent);
    }

    public void cancelAlarm(int notificationID){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationRecieverIntent = new Intent(getApplicationContext(), Notification_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, notificationRecieverIntent, 0);
        alarmManager.cancel(pendingIntent);
    }


    public int generateCode(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm",Locale.ENGLISH);
        String TimeStop_Str = sdf.format(cal.getTime());
        return Integer.parseInt(TimeStop_Str);
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
               // Intent intent = new Intent(TaskMainActivity.this,TaskMainActivity.class);
              //  startActivity(intent);
                break;

            case R.id.Ssilent:
                Intent intent2 = new Intent(TaskMainActivity.this,SilentMainActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.location:
                Intent intent3 = new Intent(TaskMainActivity.this,MapsActivity.class);
                startActivity(intent3);
                finish();
                break;

            case R.id.logout:


                firebaseAuth.signOut();

                Intent intent4 = new Intent(TaskMainActivity.this, LoginActivity.class);
                startActivity(intent4);
                finish();
                break;

            case R.id.setting:
              //  Intent intent5 = new Intent(TaskMainActivity.this, settings.class);
              //  startActivity(intent5);

                ///////////////////////////

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskMainActivity.this);
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

                        Intent intent = new Intent(TaskMainActivity.this, TaskMainActivity.class);
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
