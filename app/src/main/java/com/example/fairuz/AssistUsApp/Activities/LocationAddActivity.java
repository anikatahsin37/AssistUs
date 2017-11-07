package com.example.fairuz.AssistUsApp.Activities;

/**
 * Created by anika on 11/7/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.fairuz.AssistUsApp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fairuz on 11/7/17.
 */


public class LocationAddActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    Map<String, EditText> task = new HashMap<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_add);
        final EditText title = (EditText) findViewById(R.id.task_title);
        Button save = (Button) findViewById(R.id.save);

        EditText description = (EditText) findViewById(R.id.task_description);

        DatabaseReference AddedLocationReference;
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        AddedLocationReference = FirebaseDatabase.getInstance().getReference().child(user_id).child("AddedLocationLatlng").child("Title");
        //FirebaseDatabase.getInstance().getReference().child(user_id).child("AddedLocationLatlng").child("Title").setValue(title);


        task.put(user_id, title);



    }
}
