package com.example.fairuz.AssistUsApp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.ArrayList;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Load;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Save;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    public static File ThemeFlagDir = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/ThemeFlag.txt");
    public static  File ThemeFlagParentDir = ThemeFlagDir.getParentFile();
    public static ArrayList<String> themeFlag = new ArrayList<String>();


    //defining view objects
     EditText editTextEmail;
     EditText editTextPassword;
     Button buttonSignup;

    TextView textViewSignin;
    TextView userReg;
    RelativeLayout signMainLayout;

    private ProgressDialog progressDialog;


    public void onStart() {
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
           // Toast.makeText(this, "signupActivity: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        if (theme == 0) setColor();
    }
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        isStoragePermissionGranted();

        if (ThemeFlagParentDir != null)
        {

            ThemeFlagParentDir.mkdirs();
            if(themeFlag.size()==0) themeFlag.add("1");

        }

        else
        {
            if(isStoragePermissionGranted())
            {
                themeFlag=Load(ThemeFlagDir);
                if(themeFlag.get(0).toString().equals("0")) theme=0;
                else theme=1;
            }
        }

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), MainOption.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        userReg = (TextView)findViewById(R.id.textView);
        signMainLayout = (RelativeLayout) findViewById(R.id.signmainlayout);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        if(theme==0) setColor();

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainOption.class));
                        }else{
                            //display some message here
                            Toast.makeText(SignupActivity.this,"Registration Failed!",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, LoginActivity.class));
        }

    }


    public void setColor()
    {

        signMainLayout.setBackgroundResource(R.color.backgroundColor);
        buttonSignup.setBackground( getResources().getDrawable(R.drawable.rectangular_button));
        editTextEmail.setTextColor(Color.parseColor("#ffffff"));
        editTextPassword.setTextColor(Color.parseColor("#ffffff"));
        userReg.setTextColor(Color.parseColor("#ffffff"));
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