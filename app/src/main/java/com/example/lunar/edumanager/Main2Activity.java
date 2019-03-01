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

public class Main2Activity extends AppCompatActivity {

    public static final String MATH_ID = "mathid";
    public static final String MATH_NAME = "mathname";
    public static final String MATH_LNAME = "mathlname";
    public static final String MATH_LEVEL = "mathlevel";
    public static final String MATH_TEST = "mathtest";
    public static final String MATH_HW = "mathhw";
    public static final String MATH_DAY = "mathday";
    public static final String MATH_HOUR = "mathhour";
    public static final String MATH_NOTES = "mathnotes";

    TextView editTextViewName;
    EditText editTextName;
    Button buttonNewsub;

    DatabaseReference databaseMath;

    ListView listViewMath;
    List<Math> mathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2s);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseMath = FirebaseDatabase.getInstance().getReference("math");

        editTextViewName = (TextView) findViewById(R.id.editTextViewName);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonNewsub = (Button) findViewById(R.id.buttonNewsub);

        listViewMath = (ListView) findViewById(R.id.listViewMath);

        mathList = new ArrayList<>();

        buttonNewsub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showNewmathDialog();
            }
        });
/*
        listViewMath.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Math math = mathList.get(position);

                Intent intent = new Intent(getApplicationContext(), UpdateStudentActivity.class);

                intent.putExtra(MATH_ID, math.getMathId());
                intent.putExtra(MATH_NAME, math.getMathName());
                intent.putExtra(MATH_LNAME, math.getMathLname());
                intent.putExtra(MATH_LEVEL, math.getMathLevel());
                intent.putExtra(MATH_TEST, math.getMathTest());
                intent.putExtra(MATH_HW, math.getMathHW());
                intent.putExtra(MATH_DAY, math.getMathDay());
                intent.putExtra(MATH_HOUR, math.getMathHour());
                intent.putExtra(MATH_NOTES, math.getMathNotes());

                startActivity(intent);
            }
        });*/

        listViewMath.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Math math = mathList.get(position);
                showMathUpdateDialog(position, math.getMathId(), math.getMathName(), math.getMathLname(), math.getMathLevel(), math.getMathTest(), math.getMathHW(), math.getMathDay(), math.getMathHour(), math.getMathNotes());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseMath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mathList.clear();

                for(DataSnapshot mathSnapshot : dataSnapshot.getChildren()){
                    Math math = mathSnapshot.getValue(Math.class);

                    mathList.add(math);
                }

                MathList adapter = new MathList(Main2Activity.this, mathList);
                Collections.sort(mathList, new MyComparator0());
                Collections.sort(mathList, new MyComparator());

                listViewMath.setAdapter(adapter);



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

    private class MyComparator0 implements Comparator<Math> {
        @Override
        public int compare(Math p1, Math p2) {
            return p1.getMathName().compareTo(p2.getMathName());
        }
    }

    private class MyComparator implements Comparator<Math> {
        @Override
        public int compare(Math p1, Math p2) {
            return p1.getMathLname().compareTo(p2.getMathLname());
        }
    }

    private void showNewmathDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.newmath_dialog, null);

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

                    String id = databaseMath.push().getKey();

                    Math math = new Math(id, name, lname, "","0",
                            "0", "", "" , "", "");

                    databaseMath.child(id).setValue(math);

                }

                alertDialog.dismiss();

            }
        });

    }

    private void showMathUpdateDialog(final int position, final String mathId, String mathName, String mathLname, String mathLevel, String mathTest,
                                      String mathHW, String mathDay, String mathHour, String mathNotes){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.math_update_dialog, null);

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

        //dialogBuilder.setTitle("Updating "+mathName);

        final AlertDialog alertDialog = dialogBuilder.create();

        studentTextView.setText(mathName);
        editTextName.getText().insert(editTextName.getSelectionStart(), mathName);
        editTextLname.getText().insert(editTextLname.getSelectionStart(), mathLname);
        editTextLevel.getText().insert(editTextLevel.getSelectionStart(), mathLevel);
        editTextTest.getText().insert(editTextTest.getSelectionStart(), mathTest);
        editTextHW.getText().insert(editTextHW.getSelectionStart(), mathHW);
        editTextNotes.getText().insert(editTextNotes.getSelectionStart(), mathNotes);

        spinnerDay.setSelection(((ArrayAdapter)spinnerDay.getAdapter()).getPosition(mathDay));
        spinnerHour.setSelection(((ArrayAdapter)spinnerHour.getAdapter()).getPosition(mathHour));

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

                Math math = mathList.get(position);
                String history = math.getMathHistory();

                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name required");
                    return;
                }

                updateMath(mathId, name, lname, level, test, hw, day, hour, history, notes);

                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteMath(mathId);
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

    private void deleteMath(final String mathId){
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
                DatabaseReference drArtist = FirebaseDatabase.getInstance().getReference("math").child(mathId);
                DatabaseReference drAttend = FirebaseDatabase.getInstance().getReference("attends").child(mathId);

                drArtist.removeValue();
                drAttend.removeValue();

                alertDialog.dismiss();
            }
        });
    }

    private boolean updateMath(String id, String name, String lname, String level, String test,
                                 String hw, String day, String hour, String history, String notes){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("math").child(id);

        Math math = new Math(id, name, lname, level, test, hw, day, hour, history, notes);

        databaseReference.setValue(math);

        Toast.makeText(this, "Student Updated Successfully!", Toast.LENGTH_LONG).show();

        return true;
    }

}
