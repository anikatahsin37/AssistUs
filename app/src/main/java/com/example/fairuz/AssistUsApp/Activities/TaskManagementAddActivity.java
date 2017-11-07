package com.example.fairuz.AssistUsApp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.Dialog.DateDialog;
import com.example.fairuz.AssistUsApp.Dialog.TimeDialog;
import com.example.fairuz.AssistUsApp.R;
import com.example.fairuz.AssistUsApp.Utility.TuneSelect;
import com.example.fairuz.AssistUsApp.Utility.iconSelect;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;
import static com.example.fairuz.AssistUsApp.Utility.iconSelect.selectedIcon;


public class TaskManagementAddActivity extends AppCompatActivity {
    String titleToBeSent = "", timeToBeSentLayoutFormat = "", timeToBeSentFileFormat = "", descriptionToBeSent = "";

    String vibrate = "off";
    public static String selectedTune = "";
    ImageView icon;
    EditText time, date, title, description;
    TextView tune,Addtitle,Adddes,alarmBefore,vibrateText;
    RelativeLayout mainlayout;
    Button chooseTune,chooseDate,chooseTime,save,chooseAlarmTime,chooseIcon;
    View view;
    Switch switch1 ;

    String alarmTime = "", type = "Minute(s)";
    int toBeMinus = 10, amnt = 10;


    protected void onStart() {

        super.onStart();
        if(theme==1)
        {
            setColor();
        }
        tune.setText(selectedTune);
        Toast.makeText(this, "onstart", Toast.LENGTH_SHORT).show();

        if(selectedIcon.equals("calender"))
        {
            icon.setImageResource(R.drawable.calender);
        }
        if(selectedIcon.equals("travel"))
        {
            icon.setImageResource(R.drawable.travel);
        }
        if(selectedIcon.equals("gift"))
        {
            icon.setImageResource(R.drawable.gift);
        }
        if(selectedIcon.equals("medical"))
        {
            icon.setImageResource(R.drawable.medical);
        }
        if(selectedIcon.equals("cake"))
        {
            icon.setImageResource(R.drawable.cake);
        }
        if(selectedIcon.equals("call"))
        {
            icon.setImageResource(R.drawable.call);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_add);
        title = (EditText) findViewById(R.id.title);
        Addtitle = (TextView) findViewById(R.id.addTitle);
        Adddes = (TextView) findViewById(R.id.addDescription);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);

        chooseDate = (Button) findViewById(R.id.chooseDate);
        chooseTime = (Button) findViewById(R.id.chooseTime);
        chooseTune = (Button) findViewById(R.id.chooseTune);
        tune = (TextView) findViewById(R.id.tune);
        time = (EditText) findViewById(R.id.time);
        date = (EditText) findViewById(R.id.date);
        description = (EditText) findViewById(R.id.description);
         save = (Button) findViewById(R.id.save);
         chooseAlarmTime = (Button) findViewById(R.id.chooseAlarmTime);
        alarmBefore = (TextView) findViewById(R.id.alarmbefore);
        chooseIcon = (Button) findViewById(R.id.chooseIcon);
        icon = (ImageView) findViewById(R.id.logo);
        switch1 = (Switch) findViewById(R.id.switch1);
        vibrateText = (TextView) findViewById(R.id.vibrationtext);
        view = (View) findViewById(R.id.view) ;

        icon.setImageResource(R.drawable.calender);

        selectedIcon = "calender";


        if(theme==1)
        {
            setColor();
        }

        tune.setText(selectedTune);
        alarmBefore.setText("10 Minute(s)");

        chooseIcon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Intent intent = new Intent(TaskManagementAddActivity.this,iconSelect.class);
            startActivity(intent);
        }
        });
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateDialog dialog = new DateDialog(date);
                dialog.show(getFragmentManager().beginTransaction(), "DatePicker");
            }
        });


        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog(time);
                dialog.show(getFragmentManager().beginTransaction(), "TimePicker");
            }
        });


        chooseTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskManagementAddActivity.this, TuneSelect.class);
                startActivity(intent);


            }
        });


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    vibrate="on";
                }
                else vibrate = "off";
            }
        });


        chooseAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskManagementAddActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.alarm_time_dialogue, null);

                Button done = (Button) mView.findViewById(R.id.done);
                final EditText amount = (EditText) mView.findViewById(R.id.amount);
                RadioGroup rg = (RadioGroup) mView.findViewById(R.id.radioGroup);
                LinearLayout preTimeBack = (LinearLayout) mView.findViewById(R.id.pretime_main_back);
                RadioButton rb1 = (RadioButton) mView.findViewById(R.id.rt1);
                RadioButton rb2 = (RadioButton) mView.findViewById(R.id.rt2);
                RadioButton rb3 = (RadioButton) mView.findViewById(R.id.rt3);


                if(theme==1)
                {
                    preTimeBack.setBackgroundResource(R.color.lightAsh);
                    done.setBackground( getResources().getDrawable(R.drawable.button_light));
                    amount.setTextColor(Color.parseColor("#384248"));
                    rb1.setTextColor(Color.parseColor("#384248"));
                    rb2.setTextColor(Color.parseColor("#384248"));
                    rb3.setTextColor(Color.parseColor("#384248"));


                }

                amount.setText(amnt + "");
                if (type.equals("Hour(s)")) {
                    RadioButton rb = (RadioButton) mView.findViewById(R.id.rt2);
                    rb.setChecked(true);
                }

                if (type.equals("Day(s)")) {
                    RadioButton rb = (RadioButton) mView.findViewById(R.id.rt3);
                    rb.setChecked(true);
                }


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton rb = (RadioButton) mView.findViewById(i);
                        switch (i) {
                            case R.id.rt1:

                                type = "Minute(s)";
                                // Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.rt2:

                                type = "Hour(s)";
                                // Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.rt3:

                                type = "Day(s)";
                                //Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                                break;


                        }
                    }
                });


                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (amount.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(),
                                    "Fill up the required fields", Toast.LENGTH_LONG).show();
                        } else {
                            String tmp = amount.getText().toString();
                            tmp = tmp.replaceAll("[\\D.]", "");
                            toBeMinus = Integer.parseInt(tmp);
                            alarmTime = toBeMinus + " " + type;
                            alarmBefore.clearComposingText();
                            alarmBefore.setText(alarmTime);


                            dialog.dismiss();
                        }

                    }
                });


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String t1 = String.valueOf(title.getText());
                String t2 = String.valueOf(time.getText());
                String t3 = String.valueOf(date.getText());
                String t4 = String.valueOf(description.getText());
                String t5 = String.valueOf(tune.getText());

                if (t1.equals("") || t2.equals("") || t3.equals("") || t4.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Fill up all the required fields! ", Toast.LENGTH_LONG).show();
                } else {

                    titleToBeSent = "" + title.getText();
                    titleToBeSent = titleToBeSent.replaceAll("\\r\\n|\\r|\\n", " ");
                    timeToBeSentLayoutFormat = "" + date.getText() + " " + time.getText();
                    //  timeToBeSentFileFormat = "" + sDay + " " + sMonth + " " + SYear + " " + sHour + " " + sMin;

                    DateFormat formatLayout = new SimpleDateFormat("MMM d,yyyy h:mm a", Locale.ENGLISH);
                    DateFormat formatFile = new SimpleDateFormat("d M yyyy H m", Locale.ENGLISH);

                    try {
                        timeToBeSentFileFormat = formatFile.format(formatLayout.parse(timeToBeSentLayoutFormat));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//

                    SimpleDateFormat df = new SimpleDateFormat("d M yyyy H m", Locale.ENGLISH);
                    Date d = null;
                    try {
                        d = df.parse(timeToBeSentFileFormat);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);

                    if (type.equals("Minute(s)")) {
                        cal.add(Calendar.MINUTE, -toBeMinus);
                    }

                    if (type.equals("Hour(s)")) {
                        cal.add(Calendar.HOUR, -toBeMinus);
                    }
                    if (type.equals("Day(s)")) {
                        cal.add(Calendar.DATE, -toBeMinus);
                    }
                    String newTimeAfterMinus = df.format(cal.getTime());


                    Toast.makeText(getApplicationContext(),
                            newTimeAfterMinus, Toast.LENGTH_LONG).show();


                    descriptionToBeSent = description.getText() + "";
                    descriptionToBeSent = descriptionToBeSent.replaceAll("\\r\\n|\\r|\\n", " ");
                    Intent i = new Intent(TaskManagementAddActivity.this, TaskMainActivity.class);
                    i.putExtra("key", titleToBeSent);
                    i.putExtra("key2", timeToBeSentLayoutFormat);
                    i.putExtra("key3", timeToBeSentFileFormat);
                    i.putExtra("key4", descriptionToBeSent);
                    i.putExtra("key5", selectedTune);
                    i.putExtra("key6", newTimeAfterMinus);
                    i.putExtra("key7", alarmBefore.getText().toString());
                    i.putExtra("key8", selectedIcon);
                    i.putExtra("vibrate", vibrate);


                    TaskMainActivity.TaskfirstFlag = 1;
                    startActivity(i);


                    finish();


                }
            }
        });


    }



    private void setColor()
    {


        mainlayout.setBackgroundResource(R.color.lightAsh);
        Addtitle.setTextColor(Color.parseColor("#384248"));
        Adddes.setTextColor(Color.parseColor("#384248"));
        chooseDate.setBackground( getResources().getDrawable(R.drawable.button_light));
        chooseTime.setBackground( getResources().getDrawable(R.drawable.button_light));
        chooseTune.setBackground( getResources().getDrawable(R.drawable.button_light));
        title.setTextColor(Color.parseColor("#384248"));
        title.setBackground( getResources().getDrawable(R.drawable.text_input_border_light));
        tune.setBackground( getResources().getDrawable(R.drawable.text_input_border_light));
        description.setBackground( getResources().getDrawable(R.drawable.text_input_border_light));
        alarmBefore.setBackground( getResources().getDrawable(R.drawable.text_input_border_light));
        tune.setTextColor(Color.parseColor("#384248"));
        time.setTextColor(Color.parseColor("#384248"));
        date.setTextColor(Color.parseColor("#384248"));
        description.setTextColor(Color.parseColor("#384248"));
        alarmBefore.setTextColor(Color.parseColor("#384248"));
        save.setBackground( getResources().getDrawable(R.drawable.button_light));
        chooseAlarmTime.setBackground( getResources().getDrawable(R.drawable.button_light));
        vibrateText.setTextColor(Color.parseColor("#384248"));
        chooseIcon.setBackground( getResources().getDrawable(R.drawable.button_light));
        view.setBackground( getResources().getDrawable(R.color.colorAccent));





    }


}