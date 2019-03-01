package com.example.lunar.edumanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AttendanceMenu extends AppCompatActivity {

    Button buttonMath;
    Button buttonEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonMath = (Button) findViewById(R.id.buttonMath);
        buttonEnglish = (Button) findViewById(R.id.buttonEnglish);

        buttonMath.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AttendanceMenu.this, AttendMath.class));
            }
        });

        buttonEnglish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AttendanceMenu.this, AttendEnglish.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
