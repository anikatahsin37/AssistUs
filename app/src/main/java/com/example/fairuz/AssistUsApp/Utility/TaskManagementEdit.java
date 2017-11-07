package com.example.fairuz.AssistUsApp.Utility;

/**
 * Created by anika on 9/4/17.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.Activities.TaskMainActivity;
import com.example.fairuz.AssistUsApp.Dialog.DateDialog;
import com.example.fairuz.AssistUsApp.Dialog.TimeDialog;
import com.example.fairuz.AssistUsApp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskAlarmBeforeDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskDescriptionDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskFlag;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskNewArlarmTimeDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskTimeDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskTitleDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskTuneDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.listItems;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.map;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskAlarmBefore;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskDescription;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskTime;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskTitle;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskTune;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedtaskNewAlarmTime;
import static com.example.fairuz.AssistUsApp.Activities.TaskManagementAddActivity.selectedTune;
import static com.example.fairuz.AssistUsApp.Dialog.DateDialog.dateFormatting;
import static com.example.fairuz.AssistUsApp.Dialog.TimeDialog.timeformatting;
import static com.example.fairuz.AssistUsApp.R.id.rt1;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Save;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.updateCalendarFromText;
import static com.example.fairuz.AssistUsApp.Utility.TuneSelect.tuneflag;


public class TaskManagementEdit extends AppCompatActivity  {

    String titleToBeSent="",timeToBeSentLayoutFormat="",timeToBeSentFileFormat="",shortDate="",time24="",descriptionToBeSent="",editTune;
    String alarmTime = "",type ="Minute(s)";
    int toBeMinus=10;



    EditText time, date, title, description;
    TextView tune,Addtitle,Adddes,alarmBefore;
    RelativeLayout mainlayout;
    Button chooseTune,chooseDate,chooseTime,save,chooseAlarmTime;
    static int tuneEditFlag =0;


    protected void onStart()
    {

        super.onStart();

        if(theme==1)
        {
            setColor();
        }

        if(tuneEditFlag==1)
        {tune.setText(selectedTune);


        }

         else if(tuneEditFlag==0){
            tune.setText(editTune);



         }



    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit);
        title = (EditText) findViewById(R.id.edittitle);
        Addtitle = (TextView) findViewById(R.id.editaddTitle);
        Adddes = (TextView) findViewById(R.id.editaddDescription);
        mainlayout = (RelativeLayout) findViewById(R.id.editmainlayout);

        chooseDate = (Button) findViewById(R.id.editchooseDate);
        chooseTime = (Button) findViewById(R.id.editchooseTime);
        chooseTune = (Button) findViewById(R.id.editchooseTune);
        tune = (TextView) findViewById(R.id.edittune);
        time = (EditText) findViewById(R.id.edittime);
        date = (EditText) findViewById(R.id.editdate);
        description = (EditText) findViewById(R.id.editdescription);
        save = (Button) findViewById(R.id.editsave);
        chooseAlarmTime = (Button) findViewById(R.id.editchooseAlarmTime);
        alarmBefore = (TextView) findViewById(R.id.editalarmbefore);
        if(theme==1)
        {
            setColor();
        }


        Intent intent = getIntent();
        String result=""+intent.getExtras().getString("position");
        final int position = (int)Integer.parseInt(result);

        String editTitle= ""+savedTaskTitle.get(position);
        String editDescription = ""+savedTaskDescription.get(position);
        editTune = ""+ savedTaskTune.get(position);
        String AlarmBeforeText = ""+savedTaskAlarmBefore.get(position);


        String[] splited= savedTaskTime.get(position).trim().split("\\s+");
        int sp1=Integer.parseInt(splited[0]);
        int sp2=Integer.parseInt(splited[1]);
        int sp3=Integer.parseInt(splited[2]);
        int sp4=Integer.parseInt(splited[3]);
        int sp5=Integer.parseInt(splited[4]);
        String Date = dateFormatting(sp1,sp2,sp3);
        String Time= timeformatting(sp4,sp5);


        // listItems.add(new RecyclerItem(savedDataLine1.get(j), Date+Time));
        title.setText(editTitle);
        date.setText(Date);
        time.setText(Time);
        description.setText(editDescription);

        alarmBefore.setText(AlarmBeforeText);




        chooseDate.setText("Edit Date");
        chooseTime.setText("Edit Time");






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
                Intent intent = new Intent(TaskManagementEdit.this, TuneSelect.class);


                if(tuneEditFlag==0)
                {
                    intent.putExtra("ringtoneName",editTune);
                }
                else {
                    intent.putExtra("ringtoneName",selectedTune);
                }
                tuneflag=1;
                startActivity(intent);
                // finish();


            }
        });

        chooseAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskManagementEdit.this);
                final View mView = getLayoutInflater().inflate(R.layout.alarm_time_dialogue, null);

                Button done = (Button) mView.findViewById(R.id.done);
                final EditText amount = (EditText) mView.findViewById(R.id.amount);
                RadioGroup rg = (RadioGroup) mView.findViewById(R.id.radioGroup);





                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton rb = (RadioButton) mView.findViewById(i);
                        switch(i)
                        {
                            case rt1:

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

                        if(amount.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Fill up the required fields", Toast.LENGTH_LONG).show();
                        }


                        else {
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {




                String t1= String.valueOf(title.getText());
                String t2= String.valueOf(time.getText());
                String t3= String.valueOf(date.getText());
                String t4 = String.valueOf(description.getText());
                String t5 = String.valueOf(tune.getText());


                if(t1.equals("") ||t2.equals("") || t3.equals("") || t4.equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Fill up all the required fields! ", Toast.LENGTH_LONG).show();
                }

                else{
                    titleToBeSent = ""+title.getText();;
                    timeToBeSentLayoutFormat=date.getText()+" "+time.getText();
                    titleToBeSent =  titleToBeSent.replaceAll("\\r\\n|\\r|\\n", " ");
                    descriptionToBeSent=description.getText()+"";



                    String gotTimeFromEditText = ""+time.getText();


                    SimpleDateFormat date12Format = new SimpleDateFormat("h:mm a", Locale.US);

                    SimpleDateFormat date24Format = new SimpleDateFormat("H m",Locale.US);


                    try {
                        time24= ""+(date24Format.format(date12Format.parse(gotTimeFromEditText)));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    DateFormat shortFormat = new SimpleDateFormat("d M yyyy",Locale.ENGLISH);
                    DateFormat mediumFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
                    String s = ""+date.getText();
                    try {
                        shortDate = shortFormat.format(mediumFormat.parse(s));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    timeToBeSentFileFormat=shortDate+" "+time24;

                    SimpleDateFormat df = new SimpleDateFormat("d M yyyy H m");
                    java.util.Date d = null;
                    try {
                        d = df.parse(timeToBeSentFileFormat);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar Cal = Calendar.getInstance();
                    Cal.setTime(d);

                    if (type.equals("Minute(s)"))
                    {
                        Cal.add(Calendar.MINUTE, -toBeMinus);
                    }

                    if (type.equals("Hour(s)"))
                    {
                        Cal.add(Calendar.HOUR, -toBeMinus);
                    }
                    if (type.equals("Day(s)"))
                    {
                        Cal.add(Calendar.DATE, -toBeMinus);
                    }
                    String newTimeAfterMinus = df.format(Cal.getTime());


                    Toast.makeText(getApplicationContext(),
                            newTimeAfterMinus, Toast.LENGTH_LONG).show();




                    String PreviousTime = savedTaskTime.get(position);
                    savedTaskTitle.set(position,titleToBeSent);
                    savedTaskTime.set(position,timeToBeSentFileFormat);
                    savedTaskTune.set(position, t5);

                    savedtaskNewAlarmTime.set(position, newTimeAfterMinus);
                    savedTaskAlarmBefore.set(position, alarmBefore.getText().toString());
                    descriptionToBeSent =  descriptionToBeSent.replaceAll("\\r\\n|\\r|\\n", " ");


                    Calendar cal = updateCalendarFromText(timeToBeSentFileFormat);
                    Calendar prevCal = updateCalendarFromText(PreviousTime);
                    int code = generateCode(cal);
                    int prevCode = generateCode(prevCal);
                    map.remove(prevCode);
                    if(!map.containsKey(code)) {
                        savedTaskDescription.set(position, descriptionToBeSent);
                        Save(TaskTitleDir,savedTaskTitle);
                        Save(TaskTimeDir,savedTaskTime);
                        Save(TaskDescriptionDir,savedTaskDescription);
                        Save(TaskTuneDir, savedTaskTune);
                        Save(TaskNewArlarmTimeDir, savedtaskNewAlarmTime);
                        Save(TaskAlarmBeforeDir, savedTaskAlarmBefore);

                        listItems.set(position, (new RecyclerItem(titleToBeSent, timeToBeSentLayoutFormat)));
                    }
                    //sending edited info into TaskMainActivity

                    Intent intent = new Intent(TaskManagementEdit.this,TaskMainActivity.class);
                    intent.putExtra("EditedTitle", titleToBeSent);
                    intent.putExtra("EditedTimeFileFormat",timeToBeSentFileFormat);
                    intent.putExtra("PreviousTime",PreviousTime);
                    intent.putExtra("EditedDescription",descriptionToBeSent);
                    intent.putExtra("EditedTune",t5);
                    intent.putExtra("EditedAlarmTime",newTimeAfterMinus);
                    intent.putExtra("EditedAlarmText",alarmBefore.getText().toString());
                     TaskFlag=1;
                    startActivity(intent);
                    finish();




                }


            }
        });


    }


    public int generateCode(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
        String TimeStop_Str = sdf.format(cal.getTime());
        return Integer.parseInt(TimeStop_Str);
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




    }

}