package com.example.fairuz.AssistUsApp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.ThemeFlagDir;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.ThemeFlagParentDir;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.themeFlag;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Load;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Save;

public class MainOption extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static int theme = 1;
    LinearLayout llayout;
    ImageView Applogo;

    DrawerLayout drawerLayout;
    NavigationView navigation;

    ActionBarDrawerToggle actionBarDrawerToggle;

    public void onStart()
    {
        super.onStart();

        isStoragePermissionGranted();
        if (ThemeFlagParentDir != null) {

            ThemeFlagParentDir.mkdirs();
            if (themeFlag.size() == 0) {
                themeFlag.add("1");
                if (isStoragePermissionGranted()) {
                    Save(ThemeFlagDir, themeFlag);
                }
            }

        }

        try {
            if (isStoragePermissionGranted()) {
                themeFlag = Load(ThemeFlagDir);
                if (themeFlag.get(0).toString().equals("0")) theme = 0;
                else theme = 1;
            }



        }
        catch (Exception e) {
            Toast.makeText(this, "signupActivity: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        if(theme==1) llayout.setBackgroundResource(R.color.lightAsh);

        if(theme==0) llayout.setBackgroundResource(R.color.backgroundColor);


        if(theme==1)
        {
            Applogo.setImageResource(R.drawable.applogo_light);
        }
        else Applogo.setImageResource(R.drawable.applogo_dark);
    }


    static FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main_option);
        llayout = (LinearLayout) findViewById(R.id.llayout);
        Applogo = (ImageView) findViewById(R.id.applogo);
        if(theme==1)
        {
            Applogo.setImageResource(R.drawable.applogo_light);
        }
        else Applogo.setImageResource(R.drawable.applogo_dark);
        setUpToolBar();

        if(theme==0) llayout.setBackgroundResource(R.color.backgroundColor);



        navigation = (NavigationView) findViewById(R.id.nav);
        navigation.setNavigationItemSelectedListener(this);










        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        TextView txtProfileName = (TextView) navigation.getHeaderView(0).findViewById(R.id.gmail);
        txtProfileName.setText(""+user.getEmail());

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        //FirebaseUser user = firebaseAuth.getCurrentUser();





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
                Intent intent = new Intent(MainOption.this,TaskMainActivity.class);
                startActivity(intent);
                break;

            case R.id.Ssilent:
                Intent intent2 = new Intent(MainOption.this,SilentMainActivity.class);
                startActivity(intent2);
                break;

            case R.id.location:
                Intent intent3 = new Intent(MainOption.this,MapsActivity.class);
                startActivity(intent3);
                break;

            case R.id.logout:


                firebaseAuth.signOut();
                finish();
                Intent intent4 = new Intent(MainOption.this, LoginActivity.class);
                startActivity(intent4);
                break;

            case R.id.setting:
               // Intent intent5 = new Intent(MainOption.this, settings.class);
               // startActivity(intent5);


             //   finish();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainOption.this);
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

                        Intent intent = new Intent(MainOption.this, MainOption.class);
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
}