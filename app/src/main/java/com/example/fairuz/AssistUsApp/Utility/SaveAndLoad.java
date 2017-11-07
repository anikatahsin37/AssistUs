package com.example.fairuz.AssistUsApp.Utility;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

public class SaveAndLoad extends Activity {
    public static void Save(File file, ArrayList<String> data) {//copyright stackoverflow.com
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.size(); i++) {
                    fos.write(data.get(i).getBytes());
                    if (i < data.size() - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> Load(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        ArrayList<String> array = new ArrayList<String>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                array.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void updateMapFromList(Map<Integer, Calendar> map, ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String time = list.get(i);
            Calendar cal = updateCalendarFromText(time);
            map.put(generateCode(cal), cal);
        }
    }

    public static Calendar updateCalendarFromText(String dateTimeString) {
        Scanner sc = new Scanner(dateTimeString);
        int date = Integer.parseInt(sc.next());
        int month = Integer.parseInt(sc.next());
        int year = Integer.parseInt(sc.next());
        int hour = Integer.parseInt(sc.next());
        int minute = Integer.parseInt(sc.next());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static ArrayList<Calendar> updateCalendarFromTo(String fromToString) {
        ArrayList<Calendar> list = new ArrayList<Calendar>();
        Scanner sc = new Scanner(fromToString);
        int hourFrom = Integer.parseInt(sc.next());
        int minuteFrom = Integer.parseInt(sc.next());
        int hourTo = Integer.parseInt(sc.next());
        int minuteTo = Integer.parseInt(sc.next());
        Calendar from = Calendar.getInstance(), to = Calendar.getInstance();
        from.set(Calendar.HOUR_OF_DAY, hourFrom);
        from.set(Calendar.MINUTE, minuteFrom);
        from.set(Calendar.SECOND, 0);
        to.set(Calendar.HOUR_OF_DAY, hourTo);
        to.set(Calendar.MINUTE, minuteTo);
        to.set(Calendar.SECOND, 0);
        if (from.getTimeInMillis() > to.getTimeInMillis())
            to.set(Calendar.DAY_OF_MONTH, from.get(Calendar.DAY_OF_MONTH) + 1);
        list.add(from);
        list.add(to);
        return list;
    }

    public static int generateCode(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
        String TimeStop_Str = sdf.format(cal.getTime());
        return Integer.parseInt(TimeStop_Str);
    }
}