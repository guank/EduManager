package com.example.lunar.edumanager;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeClockActivity2 extends AppCompatActivity {
    TextView textViewName;
    ListView listViewClock;

    Button buttonNewsub;
    Button buttonClear;
    Button buttonCalc;

    DatabaseReference databaseClock;
    List<Clock> clocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_clock2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonNewsub = (Button) findViewById(R.id.buttonNewsub);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);

        listViewClock = (ListView) findViewById(R.id.listViewClock);

        Intent intent = getIntent();

        clocks = new ArrayList<>();

        String id = intent.getStringExtra(TimeClockActivity.EMPLOYEE_ID);
        String name = intent.getStringExtra(TimeClockActivity.EMPLOYEE_NAME);

        databaseClock = FirebaseDatabase.getInstance().getReference("clock").child(id);

        buttonNewsub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newClock();
            }
        });

        buttonCalc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calcHours();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearHours();
            }
        });

        listViewClock.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Clock clock = clocks.get(position);
                showAttendUpdateDialog(clock.getClockId(), clock.getClockDate(), clock.getClockIn());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseClock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clocks.clear();

                for(DataSnapshot clockSnapshot : dataSnapshot.getChildren()){
                    Clock clock = clockSnapshot.getValue(Clock.class);
                    clocks.add(clock);
                }

                ClockList clockListAdapter = new ClockList(TimeClockActivity2.this, clocks);
                Collections.reverse(clocks);


                listViewClock.setAdapter(clockListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    private void calcHours(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.hour_sum_dialog, null);

        dialogBuilder.setView(dialogView);

        final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewName);
        final TextView textViewName1 = (TextView) dialogView.findViewById(R.id.textViewName1);
        final Button buttonClose = (Button) dialogView.findViewById(R.id.buttonClose);
        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();


        Intent intent = getIntent();
        String id = intent.getStringExtra(TimeClockActivity.EMPLOYEE_ID);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clock").child(id);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long sum = 0;

                for(DataSnapshot clockSnapshot : dataSnapshot.getChildren()){
                    Clock clock = clockSnapshot.getValue(Clock.class);
                    try {
                        long difference = 0;

                        String clockIn = clock.getClockIn();
                        String clockOut = clock.getClockOut();

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        Date date1 = sdf.parse(clockIn);
                        Date date2 = sdf.parse(clockOut);

                        difference = (int)((date2.getTime() - date1.getTime())/60000);


                        //Log.i("log_tag","Hours: "+hours+", Mins: "+min);

                        sum += difference;

                        textViewName1.setText(String.valueOf(sum%60));
                        textViewName.setText(String.valueOf(sum/60));

                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void clearHours(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.warning_dialog, null);

        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        final Button buttonClose = (Button) dialogView.findViewById(R.id.buttonClose);
        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        buttonClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra(TimeClockActivity.EMPLOYEE_ID);

                DatabaseReference drAttend = FirebaseDatabase.getInstance().getReference("clock").child(id);

                drAttend.removeValue();

                alertDialog.dismiss();
            }
        });

    }

    private void clearHours1(final String clockId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.warning_dialog, null);

        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        final Button buttonClose = (Button) dialogView.findViewById(R.id.buttonClose);
        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        buttonClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra(TimeClockActivity.EMPLOYEE_ID);

                DatabaseReference drAttend = FirebaseDatabase.getInstance().getReference("clock").child(id).child(clockId);

                drAttend.removeValue();

                alertDialog.dismiss();
            }
        });

    }

    private void showAttendUpdateDialog(final String clockId, final String clockDate, final String clockIn){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.clock_update_dialog, null);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        final String clockOut = sdf.format(c);


        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextName1 = (EditText) dialogView.findViewById(R.id.editTextName1);

        editTextName.setText(clockIn);
        editTextName1.setText(clockOut);

        editTextName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeClockActivity2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = "";

                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        datetime.set(Calendar.MINUTE, selectedMinute);

                        String myFormat = "hh:mm a"; // your own format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        String clockOut = sdf.format(datetime.getTime());

                        editTextName1.setText(clockOut);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        editTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeClockActivity2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = "";

                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        datetime.set(Calendar.MINUTE, selectedMinute);

                        String myFormat = "hh:mm a"; // your own format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        String clockOut = sdf.format(datetime.getTime());

                        editTextName.setText(clockOut);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        final Button buttonClose = (Button) dialogView.findViewById(R.id.buttonClose);

        final AlertDialog alertDialog = dialogBuilder.create();

        //editTextName.getText().insert(editTextName.getSelectionStart(), attendDate);

        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String clockIn = editTextName.getText().toString().trim();
                String clockOut = editTextName1.getText().toString().trim();

                updateAttend(clockId, clockDate, clockIn, clockOut);

                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearHours1(clockId);
                alertDialog.dismiss();
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private boolean updateAttend(String clockId, String clockDate, String clockIn, String clockOut){
        Intent intent = getIntent();
        String id = intent.getStringExtra(TimeClockActivity.EMPLOYEE_ID);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("clock").child(id).child(clockId);

        Clock clock = new Clock(clockId, clockDate, clockIn, clockOut);

        databaseReference.setValue(clock);

        Toast.makeText(this, "Entry Updated Successfully!", Toast.LENGTH_LONG).show();

        return true;
    }

    private void newClock(){
        String id = databaseClock.push().getKey();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String clockIn = sdf.format(c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        Clock clock = new Clock(id, formattedDate, clockIn, "");

        databaseClock.child(id).setValue(clock);

    }
}

