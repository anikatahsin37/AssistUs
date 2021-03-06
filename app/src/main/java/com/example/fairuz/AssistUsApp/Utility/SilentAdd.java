package com.example.fairuz.AssistUsApp.Utility;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.Dialog.TimeDialog;
import com.example.fairuz.AssistUsApp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;

/**
 * Created by anika on 9/6/17.
 */

public class SilentAdd extends AppCompatActivity {

    String TitleSilentToBeSent="",TimeSilentToBeSentLayoutFormat="",TimeSilentToBeSentFileFormat="";
    CheckBox sun,mon,tue,thu,wed,all,none,sat,fri;

    EditText title,StartTimeText,EndTimeText;
    Button StartTime,EndTime,save;
    RelativeLayout mainlayout ;

    TextView addtitle,addtime,repeat;


    public void onStart()
    {
        super.onStart();
        if(theme==1) setColor();
    }


    public void selectDays(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();



        switch (view.getId())
        {
            case R.id.all:

                sun.setChecked(true);
                mon.setChecked(true);
                tue.setChecked(true);
                wed.setChecked(true);
                thu.setChecked(true);
                fri.setChecked(true);
                sat.setChecked(true);
                none.setChecked(false);
                break;

            case R.id.none:

                sun.setChecked(false);
                mon.setChecked(false);
                tue.setChecked(false);
                wed.setChecked(false);
                thu.setChecked(false);
                fri.setChecked(false);
                sat.setChecked(false);
                all.setChecked(false);

                break;

            case R.id.sun:
                none.setChecked(false);
                break;

            case R.id.mon:
                none.setChecked(false);
                break;
            case R.id.tue:
                none.setChecked(false);
                break;
            case R.id.wed:
                none.setChecked(false);
                break;
            case R.id.thu:
                none.setChecked(false);
                break;
            case R.id.fri:
                none.setChecked(false);
                break;
            case R.id.sat:
                none.setChecked(false);
                break;

        }

    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silent_edit_add);


        title= (EditText) findViewById(R.id.title);
        StartTime = (Button) findViewById(R.id.chooseTimeFrom);
        StartTimeText= (EditText) findViewById(R.id.timeFrom);
        EndTime = (Button) findViewById(R.id.chooseTimeTo);
        EndTimeText= (EditText) findViewById(R.id.timeTo);
        save = (Button) findViewById(R.id.save);
        addtime = (TextView) findViewById(R.id.addTime) ;
        addtitle= (TextView) findViewById(R.id.addTitle) ;
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayoutsilent) ;
        repeat = (TextView) findViewById(R.id.repeat) ;

        sun = (CheckBox) findViewById(R.id.sun);
        mon = (CheckBox) findViewById(R.id.mon);
        tue = (CheckBox) findViewById(R.id.tue);
        wed = (CheckBox) findViewById(R.id.wed);
        thu = (CheckBox) findViewById(R.id.thu);
        fri = (CheckBox) findViewById(R.id.fri);
        sat = (CheckBox) findViewById(R.id.sat);
        none = (CheckBox) findViewById(R.id.none);
        all = (CheckBox) findViewById(R.id.all);

        if(theme==1) setColor();

        StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog(StartTimeText);
                dialog.show(getFragmentManager().beginTransaction(), "TimePicker");
            }
        });

        EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog(EndTimeText);
                dialog.show(getFragmentManager().beginTransaction(), "TimePicker");
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String t1= String.valueOf(title.getText());
                String t2= String.valueOf(StartTimeText.getText());
                String t3= String.valueOf(EndTimeText.getText());


                if(t1.equals("") ||t2.equals("") || t3.equals("") )
                {
                    Toast.makeText(getApplicationContext(),
                            "Fill up all the required fields! ", Toast.LENGTH_SHORT).show();
                }


                else {

                    TitleSilentToBeSent=""+title.getText();
                    TitleSilentToBeSent =  TitleSilentToBeSent.replaceAll("\\r\\n|\\r|\\n", " ");
                    TimeSilentToBeSentLayoutFormat=""+StartTimeText.getText() + " to "+EndTimeText.getText();


                    SimpleDateFormat date12Format = new SimpleDateFormat("h:mm a",Locale.ENGLISH);
                    SimpleDateFormat date24Format = new SimpleDateFormat("H m",Locale.ENGLISH);


                    try {
                        TimeSilentToBeSentFileFormat= ""+(date24Format.format(date12Format.parse(""+StartTimeText.getText())));
                        TimeSilentToBeSentFileFormat += " "+(date24Format.format(date12Format.parse(""+EndTimeText.getText())));





                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    onBackPressed();
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("key",TitleSilentToBeSent);
        i.putExtra("key2",TimeSilentToBeSentLayoutFormat);
        i.putExtra("key3",TimeSilentToBeSentFileFormat);


        setResult(RESULT_OK,i);
        finish();
    }


    private void setColor()
    {



        addtitle.setTextColor(Color.parseColor("#384248"));
        addtime.setTextColor(Color.parseColor("#384248"));
        repeat.setTextColor(Color.parseColor("#384248"));
        StartTime.setBackground( getResources().getDrawable(R.drawable.button_light));
        EndTime.setBackground( getResources().getDrawable(R.drawable.button_light));
        save.setBackground( getResources().getDrawable(R.drawable.button_light));
        title.setBackground( getResources().getDrawable(R.drawable.text_input_border_light));
        StartTimeText.setTextColor(Color.parseColor("#384248"));
        EndTimeText.setTextColor(Color.parseColor("#384248"));
        title.setTextColor(Color.parseColor("#384248"));
        sat.setTextColor(Color.parseColor("#384248"));
        sun.setTextColor(Color.parseColor("#384248"));
        mon.setTextColor(Color.parseColor("#384248"));
        tue.setTextColor(Color.parseColor("#384248"));
        wed.setTextColor(Color.parseColor("#384248"));
        thu.setTextColor(Color.parseColor("#384248"));
        fri.setTextColor(Color.parseColor("#384248"));
        none.setTextColor(Color.parseColor("#384248"));
        all.setTextColor(Color.parseColor("#384248"));
        mainlayout.setBackgroundResource(R.color.lightAsh);

    }
}
