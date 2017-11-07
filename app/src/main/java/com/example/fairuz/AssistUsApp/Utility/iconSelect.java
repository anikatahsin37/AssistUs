package com.example.fairuz.AssistUsApp.Utility;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fairuz.AssistUsApp.R;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;

/**
 * Created by anika on 11/5/17.
 */





public class iconSelect extends AppCompatActivity {






   public static String selectedIcon ="calender";
    RadioGroup rg;
    LinearLayout iconmainLayout;

    RadioButton rb;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_selector);

        RadioButton rButton ;

        Button done = (Button) findViewById(R.id.doneButton);


        iconmainLayout = (LinearLayout) findViewById(R.id.iconmainlayout);
        if(theme==0 ) iconmainLayout.setBackgroundResource(R.color.backgroundColor);
        rg = (RadioGroup) findViewById(R.id.RG);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                rb = (RadioButton) findViewById(i);
                switch(i)
                {
                    case R.id.call:

                        selectedIcon = "call";

                        break;

                    case R.id.cake:
                       selectedIcon = "cake";
                        break;

                    case R.id.travel:
                        selectedIcon = "travel";
                        break;

                    case R.id.medical:
                        selectedIcon = "medical";
                        break;

                    case R.id.calender:
                        selectedIcon = "calender";
                        break;

                    case R.id.gift:
                        selectedIcon = "gift";
                        break;

                    case R.id.shopping:
                        selectedIcon = "shopping";
                        break;



                }
            }
        });






        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }


    public void onStart()
    {
        super.onStart();

        if(theme==0 ) iconmainLayout.setBackgroundResource(R.color.backgroundColor);
    }



}
