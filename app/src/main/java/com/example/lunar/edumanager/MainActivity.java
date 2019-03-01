package com.example.lunar.edumanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton buttonSchedule1;
    ImageButton buttonSchedule2;
    ImageButton buttonAttendance;
    ImageButton buttonClock;
    ImageButton buttonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainss);

        configureDirButton();

        buttonSchedule1 = (ImageButton) findViewById(R.id.buttonSchedule1);
        buttonSchedule2 = (ImageButton) findViewById(R.id.buttonSchedule2);
        buttonAttendance = (ImageButton) findViewById(R.id.buttonAttendance);
        buttonData = (ImageButton) findViewById(R.id.buttonData);
        buttonClock = (ImageButton) findViewById(R.id.buttonClock);

        buttonSchedule1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivitySchedule1.class));
            }
        });

        buttonSchedule2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivitySchedule2.class));
            }
        });

        buttonData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivityData.class));
            }
        });

        buttonAttendance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AttendanceMenu.class));
            }
        });

        buttonClock.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimeClockActivity.class));
            }
        });

    }

    private void configureDirButton(){
        ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DirectoryMenu.class));
                //Intent start = new Intent(MainActivity.this, start1.class);
                //startActivity(start);
            }
        });
    }

}
