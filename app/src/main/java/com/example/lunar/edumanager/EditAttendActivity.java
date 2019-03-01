package com.example.lunar.edumanager;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class EditAttendActivity extends AppCompatActivity {

    TextView textViewName;
    ListView listViewAttend;

    DatabaseReference databaseAttend;
    List<Attend> attends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attend);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewName = (TextView) findViewById(R.id.textViewName);
        listViewAttend = (ListView) findViewById(R.id.listViewAttend);

        Intent intent = getIntent();

        attends = new ArrayList<>();

        String id = intent.getStringExtra(AttendMath.MATH_ID1);
        String name = intent.getStringExtra(AttendMath.MATH_NAME1);

        textViewName.setText(name + "'s Attendance");

        databaseAttend = FirebaseDatabase.getInstance().getReference("attends").child(id);

        listViewAttend.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Attend attend = attends.get(position);
                showAttendUpdateDialog(attend.getAttendId(), attend.getAttendDate(), attend.getAttendSubject());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(90, TimeUnit.DAYS);
        Query oldItems = databaseAttend.orderByChild("creationDate").endAt(cutoff);

        oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    itemSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        databaseAttend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                attends.clear();

                for(DataSnapshot attendSnapshot : dataSnapshot.getChildren()){
                    Attend attend = attendSnapshot.getValue(Attend.class);
                    attends.add(attend);
                }

                AttendList attendListAdapter = new AttendList(EditAttendActivity.this, attends);
                listViewAttend.setAdapter(attendListAdapter);
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

    private void showAttendUpdateDialog(final String attendId, String attendDate, final String attendSub){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.attend_update_dialog, null);

        final Calendar myCalendar = Calendar.getInstance();

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editTextName.setText(sdf.format(myCalendar.getTime()));
            }
        };

        editTextName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditAttendActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                String date = editTextName.getText().toString().trim();

                updateAttend(attendId, date, attendSub);

                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteAttend(attendId);
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

    private void deleteAttend(String attendId){
        Intent intent = getIntent();
        String id = intent.getStringExtra(AttendMath.MATH_ID1);

        DatabaseReference drAttend = FirebaseDatabase.getInstance().getReference("attends").child(id).child(attendId);

        drAttend.removeValue();

        Toast.makeText(this, "Entry Deleted", Toast.LENGTH_LONG).show();

    }

    private boolean updateAttend(String attendId, String date, String sub){
        Intent intent = getIntent();
        String id = intent.getStringExtra(AttendMath.MATH_ID1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("attends").child(id).child(attendId);

        databaseReference.child("attendDate").setValue(date);
        databaseReference.child("attendSubject").setValue(sub);

        Toast.makeText(this, "Entry Updated Successfully!", Toast.LENGTH_LONG).show();

        return true;
    }

}
