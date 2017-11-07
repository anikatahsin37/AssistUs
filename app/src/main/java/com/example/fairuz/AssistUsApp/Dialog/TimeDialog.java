package com.example.fairuz.AssistUsApp.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    EditText txtdate;
    public static int sHour, sMin;

    public TimeDialog(View view) {
        txtdate = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {


// Use the current date as the default date in the dialog
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, false);


    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = timeformatting(hourOfDay, minute);
        txtdate.setText(time);
    }

    public static String timeformatting(int hourOfDay, int minute) {
        String amPm = "AM";
        String time = "";
        if (hourOfDay == 0) {
            amPm = "AM";
            hourOfDay = 12;
        } else if (hourOfDay > 12) {
            amPm = "PM";
            hourOfDay -= 12;
        } else if (hourOfDay == 12) {
            amPm = "PM";
        }
        if (minute < 10) {
            time = hourOfDay + ":0" + minute + " " + amPm;
        } else {
            time = hourOfDay + ":" + minute + " " + amPm;
        }
        return time;

    }


}
