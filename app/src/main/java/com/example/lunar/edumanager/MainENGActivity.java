package com.example.lunar.edumanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainENGActivity extends AppCompatActivity {

    public static final String ENGLISH_ID = "englishid";
    public static final String ENGLISH_NAME = "englishname";
    public static final String ENGLISH_LNAME = "englishlname";
    public static final String ENGLISH_LEVEL = "englishlevel";
    public static final String ENGLISH_TEST = "englishtest";
    public static final String ENGLISH_HW = "englishhw";
    public static final String ENGLISH_DAY = "englishday";
    public static final String ENGLISH_HOUR = "englishhour";
    public static final String ENGLISH_NOTES = "englishnotes";

    TextView editTextViewName;
    EditText editTextName;
    Spinner spinnerGenres;
    Button buttonNewsub;
    Button buttonEnglishView;

    DatabaseReference databaseEnglish;

    ListView listViewEnglish;
    List<English> englishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_eng);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseEnglish = FirebaseDatabase.getInstance().getReference("english");

        editTextViewName = (TextView) findViewById(R.id.editTextViewName);
        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerGenres = (Spinner) findViewById(R.id.spinnerGenres);
        buttonNewsub = (Button) findViewById(R.id.buttonNewsub);
        buttonEnglishView = (Button) findViewById(R.id.buttonEnglishView);

        listViewEnglish = (ListView) findViewById(R.id.listViewEnglish);

        englishList = new ArrayList<>();

        buttonNewsub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showNewenglishDialog();
            }
        });
/*
        listViewEnglish.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                English english = englishList.get(position);

                Intent intent = new Intent(getApplicationContext(), UpdateStudentActivity.class);

                intent.putExtra(MATH_ID, english.getEnglishId());
                intent.putExtra(MATH_NAME, english.getEnglishName());
                intent.putExtra(MATH_LNAME, english.getEnglishLname());
                intent.putExtra(MATH_LEVEL, english.getEnglishLevel());
                intent.putExtra(MATH_TEST, english.getEnglishTest());
                intent.putExtra(MATH_HW, english.getEnglishHW());
                intent.putExtra(MATH_DAY, english.getEnglishDay());
                intent.putExtra(MATH_HOUR, english.getEnglishHour());
                intent.putExtra(MATH_NOTES, english.getEnglishNotes());

                startActivity(intent);
            }
        });*/

        listViewEnglish.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                English english = englishList.get(position);
                showEnglishUpdateDialog(position, english.getEnglishId(), english.getEnglishName(), english.getEnglishLname(),
                        english.getEnglishLevel(), english.getEnglishTest(), english.getEnglishHW(), english.getEnglishDay(),
                        english.getEnglishHour(), english.getEnglishNotes());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEnglish.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                englishList.clear();

                for(DataSnapshot englishSnapshot : dataSnapshot.getChildren()){
                    English english = englishSnapshot.getValue(English.class);

                    englishList.add(english);
                }

                EnglishList adapter = new EnglishList(MainENGActivity.this, englishList);
                Collections.sort(englishList, new MyComparator0());
                Collections.sort(englishList, new MyComparator());

                listViewEnglish.setAdapter(adapter);



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

    private class MyComparator0 implements Comparator<English> {
        @Override
        public int compare(English p1, English p2) {
            return p1.getEnglishName().compareTo(p2.getEnglishName());
        }
    }

    private class MyComparator implements Comparator<English> {
        @Override
        public int compare(English p1, English p2) {
            return p1.getEnglishLname().compareTo(p2.getEnglishLname());
        }
    }

    private void showNewenglishDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.newenglish_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextLname = (EditText) dialogView.findViewById(R.id.editTextLname);
        final Button buttonSubmit = (Button) dialogView.findViewById(R.id.buttonSubmit);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String lname = editTextLname.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(lname)){
                    editTextName.setError("Name required");
                    return;
                }

                if (!TextUtils.isEmpty(name)) {

                    String id = databaseEnglish.push().getKey();

                    English english = new English(id, name, lname, "","0",
                            "0", "", "" , "", "");

                    databaseEnglish.child(id).setValue(english);

                }

                alertDialog.dismiss();

            }
        });

    }

    private void showEnglishUpdateDialog(final int position, final String englishId, String englishName, String englishLname, String englishLevel, String englishTest,
                                      String englishHW, String englishDay, String englishHour, String englishNotes){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.english_update_dialog, null);

        dialogBuilder.setView(dialogView);

        final TextView studentTextView = (TextView) dialogView.findViewById(R.id.studentTextName);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextLname = (EditText) dialogView.findViewById(R.id.editTextLname);
        final EditText editTextLevel = (EditText) dialogView.findViewById(R.id.editTextLevel);
        final EditText editTextTest = (EditText) dialogView.findViewById(R.id.editTextTest);
        final EditText editTextHW = (EditText) dialogView.findViewById(R.id.editTextHW);
        final Spinner spinnerDay = (Spinner) dialogView.findViewById(R.id.spinnerDay);
        final Spinner spinnerHour = (Spinner) dialogView.findViewById(R.id.spinnerHour);
        final EditText editTextNotes = (EditText) dialogView.findViewById(R.id.editTextNotes);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        final Button buttonClose = (Button) dialogView.findViewById(R.id.buttonClose);

        //dialogBuilder.setTitle("Updating "+englishName);

        final AlertDialog alertDialog = dialogBuilder.create();

        studentTextView.setText(englishName);
        editTextName.getText().insert(editTextName.getSelectionStart(), englishName);
        editTextLname.getText().insert(editTextLname.getSelectionStart(), englishLname);
        editTextLevel.getText().insert(editTextLevel.getSelectionStart(), englishLevel);
        editTextTest.getText().insert(editTextTest.getSelectionStart(), englishTest);
        editTextHW.getText().insert(editTextHW.getSelectionStart(), englishHW);
        //editTextDay.getText().insert(editTextDay.getSelectionStart(), englishDay);
        //editTextHour.getText().insert(editTextHour.getSelectionStart(), englishHour);
        editTextNotes.getText().insert(editTextNotes.getSelectionStart(), englishNotes);

        spinnerDay.setSelection(((ArrayAdapter)spinnerDay.getAdapter()).getPosition(englishDay));
        spinnerHour.setSelection(((ArrayAdapter)spinnerHour.getAdapter()).getPosition(englishHour));

        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String lname = editTextLname.getText().toString().trim();
                String level = editTextLevel.getText().toString().trim();
                String test = editTextTest.getText().toString().trim();
                String hw = editTextHW.getText().toString().trim();
                String day = spinnerDay.getSelectedItem().toString();
                String hour = spinnerHour.getSelectedItem().toString();
                String notes = editTextNotes.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(lname)){
                    editTextName.setError("Name required");
                    return;
                }

                English english = englishList.get(position);
                String history = english.getEnglishHistory();

                updateEnglish(englishId, name, lname, level, test, hw, day, hour, history, notes);

                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteEnglish(englishId);
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

    private void deleteEnglish(final String englishId){
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
                DatabaseReference drArtist = FirebaseDatabase.getInstance().getReference("english").child(englishId);
                DatabaseReference drAttend = FirebaseDatabase.getInstance().getReference("attends").child(englishId);

                drArtist.removeValue();
                drAttend.removeValue();

                alertDialog.dismiss();
            }
        });

    }

    private boolean updateEnglish(String id, String name, String lname, String level, String test,
                               String hw, String day, String hour, String history, String notes){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("english").child(id);

        English english = new English(id, name, lname, level, test, hw, day, hour, history, notes);

        databaseReference.setValue(english);

        Toast.makeText(this, "Student Updated Successfully!", Toast.LENGTH_LONG).show();

        return true;
    }

}
