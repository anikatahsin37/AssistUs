package com.example.fairuz.AssistUsApp.Utility;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.Activities.MainOption;
import com.example.fairuz.AssistUsApp.R;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.ThemeFlagDir;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.ThemeFlagParentDir;
import static com.example.fairuz.AssistUsApp.Activities.SignupActivity.themeFlag;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Save;


/**
 * Created by anika on 11/4/17.
 */

public class settings extends AppCompatActivity {


    RadioGroup rg;

    RadioButton rb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button done = (Button) findViewById(R.id.done) ;

        rg = (RadioGroup) findViewById(R.id.rg);

        RadioButton rbLight =(RadioButton) findViewById(R.id.light);

        RadioButton rbDark =(RadioButton) findViewById(R.id.dark);

        if(theme==1) rbLight.setChecked(true);
        else rbDark.setChecked(true);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                rb = (RadioButton) findViewById(i);

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

                Intent intent = new Intent(settings.this, MainOption.class);
                startActivity(intent);
                finish();
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


}