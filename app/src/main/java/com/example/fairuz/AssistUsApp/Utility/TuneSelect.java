package com.example.fairuz.AssistUsApp.Utility;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fairuz.AssistUsApp.R;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;
import static com.example.fairuz.AssistUsApp.Activities.TaskManagementAddActivity.selectedTune;
import static com.example.fairuz.AssistUsApp.Utility.TaskManagementEdit.tuneEditFlag;


/**
 * Created by anika on 10/5/17.
 */

public class TuneSelect extends AppCompatActivity{

    RadioGroup rg;

    RadioButton rb;

    static int tuneflag=0;

    protected void onDestroy()
    {
        super.onDestroy();


        stopPlaying();


    }



    private MediaPlayer mp;









    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tune_selector);

        RadioButton rt1 = (RadioButton) findViewById(R.id.rt1);
        RadioButton rt2 = (RadioButton) findViewById(R.id.rt2);
        RadioButton rt3 = (RadioButton) findViewById(R.id.rt3);
        RadioButton rt4 = (RadioButton) findViewById(R.id.rt4);
        RadioButton rt5 = (RadioButton) findViewById(R.id.rt5);
        RadioButton rt6 = (RadioButton) findViewById(R.id.rt6);
        RadioButton rt7 = (RadioButton) findViewById(R.id.rt7);
        RadioButton rt8 = (RadioButton) findViewById(R.id.rt8);
        RadioButton rt9 = (RadioButton) findViewById(R.id.rt9);
        RadioButton rt10 = (RadioButton) findViewById(R.id.rt10);

        LinearLayout tuneMainBack = (LinearLayout) findViewById(R.id.tunemainback);






        if(tuneflag==1)
        {
            tuneEditFlag=1;
            Intent intent = getIntent();
            String ringtone=""+intent.getExtras().getString("ringtoneName");
            if(ringtone.equals("Ringtone1"))
            {

                rt1.setChecked(true);
            }
            if(ringtone.equals("Ringtone2"))
            {

                rt2.setChecked(true);
            }
            if(ringtone.equals("Ringtone3"))
            {

                rt3.setChecked(true);
            }
            if(ringtone.equals("Ringtone4"))
            {

                rt4.setChecked(true);
            }
            if(ringtone.equals("Ringtone5"))
            {

                rt5.setChecked(true);
            }
            if(ringtone.equals("Ringtone6"))
            {

                rt6.setChecked(true);
            }
            if(ringtone.equals("Ringtone7"))
            {

                rt7.setChecked(true);
            }
            if(ringtone.equals("Ringtone8"))
            {

                rt8.setChecked(true);
            }
            if(ringtone.equals("Ringtone9"))
            {

                rt9.setChecked(true);
            }
            if(ringtone.equals("Ringtone10"))
            {

                rt10.setChecked(true);
            }
            tuneflag=0;

        }


        if(theme==1)
        {



            rt1.setTextColor(Color.parseColor("#384248"));
            rt2.setTextColor(Color.parseColor("#384248"));
            rt3.setTextColor(Color.parseColor("#384248"));
            rt4.setTextColor(Color.parseColor("#384248"));
            rt5.setTextColor(Color.parseColor("#384248"));
            rt6.setTextColor(Color.parseColor("#384248"));
            rt7.setTextColor(Color.parseColor("#384248"));
            rt8.setTextColor(Color.parseColor("#384248"));
            rt9.setTextColor(Color.parseColor("#384248"));
            rt10.setTextColor(Color.parseColor("#384248"));
            tuneMainBack.setBackgroundResource(R.color.lightAsh);

        }



        rg = (RadioGroup) findViewById(R.id.radioGroup);


         rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                 rb = (RadioButton) findViewById(i);
                 switch(i)
                 {
                     case R.id.rt1:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone1);
                         mp.start();
                         selectedTune="Ringtone1";
                         break;

                     case R.id.rt2:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone2);
                         mp.start();
                         selectedTune="Ringtone2";
                         break;

                     case R.id.rt3:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone3);
                         mp.start();
                         selectedTune="Ringtone3";
                         break;

                     case R.id.rt4:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone4);
                         mp.start();
                         selectedTune="Ringtone4";
                         break;

                     case R.id.rt5:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone5);
                         mp.start();
                         selectedTune="Ringtone5";
                         break;

                     case R.id.rt6:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone6);
                         mp.start();
                         selectedTune="Ringtone6";
                         break;
                     case R.id.rt7:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone7);
                         mp.start();
                         selectedTune="Ringtone7";
                         break;
                     case R.id.rt8:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone8);
                         mp.start();
                         selectedTune="Ringtone8";
                         break;
                     case R.id.rt9:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone9);
                         mp.start();
                         selectedTune="Ringtone9";
                         break;
                     case R.id.rt10:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone10);
                         mp.start();
                         selectedTune="Ringtone10";
                         break;

                 }
             }
         });





    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }








}
