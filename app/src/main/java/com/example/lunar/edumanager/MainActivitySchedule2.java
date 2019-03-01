package com.example.lunar.edumanager;

import android.graphics.drawable.ColorDrawable;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivitySchedule2 extends AppCompatActivity {

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
    Button buttonNewsub;
    Button buttonEnglishView;

    DatabaseReference databaseEnglish;
    DatabaseReference databaseAttend;
    DatabaseReference databaseTimestamps;

    ListView listViewEnglish;
    List<English> englishList;

    Map<String, String> timestampCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_schedule1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseEnglish = FirebaseDatabase.getInstance().getReference("english");
        databaseTimestamps = FirebaseDatabase.getInstance().getReference("timestamps");

        editTextViewName = (TextView) findViewById(R.id.editTextViewName);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonNewsub = (Button) findViewById(R.id.buttonNewsub);
        buttonEnglishView = (Button) findViewById(R.id.buttonEnglishView);

        listViewEnglish = (ListView) findViewById(R.id.listViewMath);

        editTextViewName.setText("English Schedule");

        englishList = new ArrayList<>();

        listViewEnglish.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                English english = englishList.get(position);
                showMathUpdateDialog(position, english.getEnglishId(), english.getEnglishName(), english.getEnglishLname(), english.getEnglishLevel(),
                        english.getEnglishTest(), english.getEnglishHW(), english.getEnglishDay(), english.getEnglishHour(), english.getEnglishNotes());
            }
        });

        listViewEnglish.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (((ColorDrawable)view.getBackground()).getColor() == getResources().getColor(R.color.violet)){
                    view.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }else {
                    view.setBackgroundColor(getResources().getColor(R.color.violet));
                }

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEnglish.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                englishList.clear();

                for(DataSnapshot mathSnapshot : dataSnapshot.getChildren()){
                    English math = mathSnapshot.getValue(English.class);

                    englishList.add(math);
                }

                EnglishList1 adapter = new EnglishList1(MainActivitySchedule2.this, englishList);
                Collections.sort(englishList, new MyComparator());
                //Collections.sort(mathList, new MyComparator1());

                if (hour >= 15 && hour < 16) {
                    Collections.sort(englishList, new hourComparator1());
                } else if(hour >= 16 && hour < 17){
                    Collections.sort(englishList, new hourComparator2());
                } else if(hour >= 17 && hour < 18){
                    Collections.sort(englishList, new hourComparator3());
                } else if(hour >= 18 && hour < 19){
                    Collections.sort(englishList, new hourComparator4());
                }

                switch (day) {
                    case Calendar.MONDAY:
                        Collections.sort(englishList, new dateComparator1());
                        break;
                    case Calendar.TUESDAY:
                        Collections.sort(englishList, new dateComparator2());
                        break;
                    case Calendar.WEDNESDAY:
                        Collections.sort(englishList, new dateComparator3());
                        break;
                    case Calendar.THURSDAY:
                        Collections.sort(englishList, new dateComparator4());
                        break;
                    case Calendar.FRIDAY:
                        Collections.sort(englishList, new dateComparator5());
                        break;
                    case Calendar.SATURDAY:
                        Collections.sort(englishList, new dateComparator6());
                        break;
                    case Calendar.SUNDAY:
                        Collections.sort(englishList, new dateComparator7());
                        break;
                }

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

    public class MyComparator implements Comparator<English> {
        @Override
        public int compare(English p1, English p2) {
            return p1.getEnglishHour().compareTo(p2.getEnglishHour());
        }
    }

    public final class hourComparator1 implements Comparator<English> {
        private String[] items ={
                "3PM"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishHour()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishHour()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class hourComparator2 implements Comparator<English> {
        private String[] items ={
                "4PM",
                "5PM",
                "6PM"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishHour()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishHour()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class hourComparator3 implements Comparator<English> {
        private String[] items ={
                "5PM",
                "6PM"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishHour()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishHour()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class hourComparator4 implements Comparator<English> {
        private String[] items ={
                "6PM"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishHour()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishHour()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class dateComparator1 implements Comparator<English> {
        private String[] items ={
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "saturday",
                "sunday",
                "inactive"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishDay()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishDay()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class dateComparator2 implements Comparator<English> {
        private String[] items ={
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "saturday",
                "sunday",
                "monday",
                "inactive"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishDay()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishDay()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class dateComparator3 implements Comparator<English> {
        private String[] items ={
                "wednesday",
                "thursday",
                "friday",
                "saturday",
                "sunday",
                "monday",
                "tuesday",
                "inactive"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishDay()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishDay()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class dateComparator4 implements Comparator<English> {
        private String[] items ={
                "thursday",
                "friday",
                "saturday",
                "sunday",
                "monday",
                "tuesday",
                "wednesday",
                "inactive"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishDay()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishDay()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class dateComparator5 implements Comparator<English> {
        private String[] items ={
                "friday",
                "saturday",
                "sunday",
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "inactive"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishDay()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishDay()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class dateComparator6 implements Comparator<English> {
        private String[] items ={
                "saturday",
                "sunday",
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "inactive"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishDay()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishDay()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    public final class dateComparator7 implements Comparator<English> {
        private String[] items ={
                "sunday",
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "saturday",
                "inactive"
        };
        @Override
        public int compare(English p1, English p2)
        {
            int ai = items.length, bi=items.length;
            for(int i = 0; i<items.length; i++)
            {
                if(items[i].equalsIgnoreCase(p1.getEnglishDay()))
                    ai=i;
                if(items[i].equalsIgnoreCase(p2.getEnglishDay()))
                    bi=i;
            }
            return ai-bi;
        }
    };

    private void showMathUpdateDialog(final int position, final String mathId, final String mathName, final String mathLname, String mathLevel, String mathTest,
                                      String mathHW, String mathDay, String mathHour, String mathNotes){

        databaseAttend = FirebaseDatabase.getInstance().getReference("attends").child(mathId);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.maths_update_dialog, null);

        dialogBuilder.setView(dialogView);

        final TextView studentTextView = (TextView) dialogView.findViewById(R.id.studentTextName);
        final EditText editTextLevel = (EditText) dialogView.findViewById(R.id.editTextLevel);
        final EditText editTextTest = (EditText) dialogView.findViewById(R.id.editTextTest);
        final EditText editTextHW = (EditText) dialogView.findViewById(R.id.editTextHW);
        final Spinner spinnerDay = (Spinner) dialogView.findViewById(R.id.spinnerDay);
        final Spinner spinnerHour = (Spinner) dialogView.findViewById(R.id.spinnerHour);
        final EditText editTextNotes = (EditText) dialogView.findViewById(R.id.editTextNotes);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonIncrement = (Button) dialogView.findViewById(R.id.buttonIncrement);
        final Button buttonClose = (Button) dialogView.findViewById(R.id.buttonClose);

        final Button buttonAtt = (Button) dialogView.findViewById(R.id.buttonAtt);

        //dialogBuilder.setTitle("Updating "+mathName);

        final AlertDialog alertDialog = dialogBuilder.create();

        studentTextView.setText(mathName);
        editTextLevel.getText().insert(editTextLevel.getSelectionStart(), mathLevel);
        editTextTest.getText().insert(editTextTest.getSelectionStart(), mathTest);
        editTextHW.getText().insert(editTextHW.getSelectionStart(), mathHW);
        editTextNotes.getText().insert(editTextNotes.getSelectionStart(), mathNotes);

        spinnerDay.setSelection(((ArrayAdapter)spinnerDay.getAdapter()).getPosition(mathDay));
        spinnerHour.setSelection(((ArrayAdapter)spinnerHour.getAdapter()).getPosition(mathHour));

        alertDialog.show();

        buttonIncrement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int t = Integer.parseInt(editTextHW.getText().toString());
                int t1 = Integer.parseInt(editTextTest.getText().toString());

                editTextHW.setText(String.valueOf(t+1));
                editTextTest.setText(String.valueOf(t1+1));


            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String level = editTextLevel.getText().toString().trim();
                String test = editTextTest.getText().toString().trim();
                String hw = editTextHW.getText().toString().trim();
                String day = spinnerDay.getSelectedItem().toString();
                String hour = spinnerHour.getSelectedItem().toString();
                String notes = editTextNotes.getText().toString().trim();

                English english = englishList.get(position);
                String history = english.getEnglishHistory();

                updateMath(mathId, mathName, mathLname, level, test, hw, day, hour, history, notes);

                alertDialog.dismiss();

            }
        });

        buttonAtt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveAttend(mathId);
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void saveAttend(String englishId){
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        String id = databaseAttend.push().getKey();
        String id2 = databaseTimestamps.push().getKey();

        Attend attend = new Attend(id, formattedDate, "English", timestampCreated);

        Timestamp timestamp = new Timestamp(id2);

        databaseAttend.child(id).setValue(attend);
        databaseTimestamps.child(id2).setValue(timestamp);

        databaseEnglish.child(englishId).child("englishHistory").setValue(formattedDate);

        Toast.makeText(this, "Updated Attendance", Toast.LENGTH_LONG).show();

    }

    private boolean updateMath(String id, String name, String lname, String level, String test,
                               String hw, String day, String hour, String history, String notes){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("english").child(id);

        English english = new English(id, name, lname, level, test, hw, day, hour, history, notes);

        databaseReference.setValue(english);

        Toast.makeText(this, "Student Updated Successfully!", Toast.LENGTH_LONG).show();

        return true;
    }


}
