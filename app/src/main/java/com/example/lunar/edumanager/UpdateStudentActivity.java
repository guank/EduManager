package com.example.lunar.edumanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateStudentActivity extends AppCompatActivity {

    DatabaseReference databaseEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextLname = (EditText) findViewById(R.id.editTextLname);
        //Spinner spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        EditText editTextTest = (EditText) findViewById(R.id.editTextTest);
        EditText editTextHW = (EditText) findViewById(R.id.editTextHW);

        Button buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);

        Intent intent = getIntent();

        String id = intent.getStringExtra(Main2Activity.MATH_ID);
        String name = intent.getStringExtra(Main2Activity.MATH_NAME);
        String lname = intent.getStringExtra(Main2Activity.MATH_LNAME);
        String level = intent.getStringExtra(Main2Activity.MATH_LEVEL);
        String test = intent.getStringExtra(Main2Activity.MATH_TEST);
        String hw = intent.getStringExtra(Main2Activity.MATH_HW);
        String day = intent.getStringExtra(Main2Activity.MATH_DAY);
        String hour = intent.getStringExtra(Main2Activity.MATH_HOUR);
        String notes = intent.getStringExtra(Main2Activity.MATH_NOTES);

        databaseEnglish = FirebaseDatabase.getInstance().getReference("english").child(id);

        editTextName.getText().insert(editTextName.getSelectionStart(), name);
    }
}
