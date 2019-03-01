package com.example.lunar.edumanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivityData extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView31day;
    TextView textView62day;

    TextView tvMsat3;
    TextView tvMsat4;
    TextView tvMsat5;
    TextView tvMsun3;
    TextView tvMsun4;
    TextView tvMsun5;
    TextView tvMmon4;
    TextView tvMmon5;
    TextView tvMmon6;
    TextView tvMtue4;
    TextView tvMtue5;
    TextView tvMtue6;

    TextView tvEsat3;
    TextView tvEsat4;
    TextView tvEsat5;
    TextView tvEsun3;
    TextView tvEsun4;
    TextView tvEsun5;
    TextView tvEmon4;
    TextView tvEmon5;
    TextView tvEmon6;
    TextView tvEtue4;
    TextView tvEtue5;
    TextView tvEtue6;


    DatabaseReference databaseMath;
    DatabaseReference databaseEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_data);

        textView1 = (TextView) findViewById(R.id.textViewMcount);
        textView2 = (TextView) findViewById(R.id.textViewEcount);
        textView31day = (TextView) findViewById(R.id.textView31day);
        textView62day = (TextView) findViewById(R.id.textView62day);

        tvMsat3 = (TextView) findViewById(R.id.textViewMsat3);
        tvMsat4 = (TextView) findViewById(R.id.textViewMsat4);
        tvMsat5 = (TextView) findViewById(R.id.textViewMsat5);
        tvMsun3 = (TextView) findViewById(R.id.textViewMsun3);
        tvMsun4 = (TextView) findViewById(R.id.textViewMsun4);
        tvMsun5 = (TextView) findViewById(R.id.textViewMsun5);
        tvMmon4 = (TextView) findViewById(R.id.textViewMmon4);
        tvMmon5 = (TextView) findViewById(R.id.textViewMmon5);
        tvMmon6 = (TextView) findViewById(R.id.textViewMmon6);
        tvMtue4 = (TextView) findViewById(R.id.textViewMtue4);
        tvMtue5 = (TextView) findViewById(R.id.textViewMtue5);
        tvMtue6 = (TextView) findViewById(R.id.textViewMtue6);

        tvEsat3 = (TextView) findViewById(R.id.textViewEsat3);
        tvEsat4 = (TextView) findViewById(R.id.textViewEsat4);
        tvEsat5 = (TextView) findViewById(R.id.textViewEsat5);
        tvEsun3 = (TextView) findViewById(R.id.textViewEsun3);
        tvEsun4 = (TextView) findViewById(R.id.textViewEsun4);
        tvEsun5 = (TextView) findViewById(R.id.textViewEsun5);
        tvEmon4 = (TextView) findViewById(R.id.textViewEmon4);
        tvEmon5 = (TextView) findViewById(R.id.textViewEmon5);
        tvEmon6 = (TextView) findViewById(R.id.textViewEmon6);
        tvEtue4 = (TextView) findViewById(R.id.textViewEtue4);
        tvEtue5 = (TextView) findViewById(R.id.textViewEtue5);
        tvEtue6 = (TextView) findViewById(R.id.textViewEtue6);


        databaseMath = FirebaseDatabase.getInstance().getReference("math");
        databaseEnglish = FirebaseDatabase.getInstance().getReference("english");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseMath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long num;
                String str = "Total Math Students: ";
                num = dataSnapshot.getChildrenCount();
                textView1.setText(str + String.valueOf(num));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseEnglish.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long num;
                String str = "Total English Students: ";
                num = dataSnapshot.getChildrenCount();
                textView2.setText(str + String.valueOf(num));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("timestamps");

        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(31, TimeUnit.DAYS);
        Query oldItems = mDatabase.orderByChild("creationDate").endAt(cutoff);

        long cutoff1 = new Date().getTime() - TimeUnit.MILLISECONDS.convert(62, TimeUnit.DAYS);
        Query oldItems1 = mDatabase.orderByChild("creationDate").endAt(cutoff1);

        long start = new Date().getTime() - TimeUnit.MILLISECONDS.convert(31, TimeUnit.DAYS);
        Query newItems = mDatabase.orderByChild("creationDate").startAt(start);

        oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long sum = 0;
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    sum++;

                    textView62day.setText("Last Month Total Attendance: " + String.valueOf(sum));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        oldItems1.addListenerForSingleValueEvent(new ValueEventListener() {
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

        newItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long sum = 0;

                for(DataSnapshot tsSnapshot : dataSnapshot.getChildren()){
                    sum++;

                    textView31day.setText("31-day Total Attendance: " + String.valueOf(sum));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("math");

        Query query = ref.orderByChild("mathDay").equalTo("Saturday");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum3 = 0;
                int sum4 = 0;
                int sum5 = 0;
                for (DataSnapshot mathSnapshot : dataSnapshot.getChildren()) {
                    Math math = mathSnapshot.getValue(Math.class);
                    //String str = math.getMathHour();
                    if (math.getMathHour().equals("3PM")) {
                        sum3++;
                        tvMsat3.setText("Sat 3PM: " + String.valueOf(sum3));
                    }

                    if (math.getMathHour().equals("4PM")) {
                        sum4++;
                        tvMsat4.setText("4PM: " + String.valueOf(sum4));
                    }

                    if (math.getMathHour().equals("5PM")) {
                        sum5++;
                        tvMsat5.setText("5PM: " + String.valueOf(sum5));
                    }
                    //tvMsat3.setText("Sat 3PM: " + String.valueOf(str));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query = ref.orderByChild("mathDay").equalTo("Sunday");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum3 = 0;
                int sum4 = 0;
                int sum5 = 0;
                for (DataSnapshot mathSnapshot : dataSnapshot.getChildren()) {
                    Math math = mathSnapshot.getValue(Math.class);
                    if (math.getMathHour().equals("3PM")) {
                        sum3++;
                        tvMsun3.setText("Sun 3PM: " + String.valueOf(sum3));
                    }

                    if (math.getMathHour().equals("4PM")) {
                        sum4++;
                        tvMsun4.setText("4PM: " + String.valueOf(sum4));
                    }

                    if (math.getMathHour().equals("5PM")) {
                        sum5++;
                        tvMsun5.setText("5PM: " + String.valueOf(sum5));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query = ref.orderByChild("mathDay").equalTo("Monday");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum4 = 0;
                int sum5 = 0;
                int sum6 = 0;
                for (DataSnapshot mathSnapshot : dataSnapshot.getChildren()) {
                    Math math = mathSnapshot.getValue(Math.class);
                    if (math.getMathHour().equals("4PM")) {
                        sum4++;
                        tvMmon4.setText("Mon 4PM: " + String.valueOf(sum4));
                    }

                    if (math.getMathHour().equals("5PM")) {
                        sum5++;
                        tvMmon5.setText("5PM: " + String.valueOf(sum5));
                    }

                    if (math.getMathHour().equals("6PM")) {
                        sum6++;
                        tvMmon6.setText("6PM: " + String.valueOf(sum6));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query = ref.orderByChild("mathDay").equalTo("Tuesday");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum4 = 0;
                int sum5 = 0;
                int sum6 = 0;
                for (DataSnapshot mathSnapshot : dataSnapshot.getChildren()) {
                    Math math = mathSnapshot.getValue(Math.class);
                    if (math.getMathHour().equals("4PM")) {
                        sum4++;
                        tvMtue4.setText("Tue 4PM: " + String.valueOf(sum4));
                    }

                    if (math.getMathHour().equals("5PM")) {
                        sum5++;
                        tvMtue5.setText("5PM: " + String.valueOf(sum5));
                    }

                    if (math.getMathHour().equals("6PM")) {
                        sum6++;
                        tvMtue6.setText("6PM: " + String.valueOf(sum6));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("english");

        Query query1 = ref1.orderByChild("englishDay").equalTo("Saturday");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum3 = 0;
                int sum4 = 0;
                int sum5 = 0;
                for (DataSnapshot englishSnapshot : dataSnapshot.getChildren()) {
                    English english = englishSnapshot.getValue(English.class);
                    if (english.getEnglishHour().equals("3PM")) {
                        sum3++;
                        tvEsat3.setText("Sat 3PM: " + String.valueOf(sum3));
                    }

                    if (english.getEnglishHour().equals("4PM")) {
                        sum4++;
                        tvEsat4.setText("4PM: " + String.valueOf(sum4));
                    }

                    if (english.getEnglishHour().equals("5PM")) {
                        sum5++;
                        tvEsat5.setText("5PM: " + String.valueOf(sum5));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query1 = ref1.orderByChild("englishDay").equalTo("Sunday");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum3 = 0;
                int sum4 = 0;
                int sum5 = 0;
                for (DataSnapshot englishSnapshot : dataSnapshot.getChildren()) {
                    English english = englishSnapshot.getValue(English.class);
                    if (english.getEnglishHour().equals("3PM")) {
                        sum3++;
                        tvEsun3.setText("Sun 3PM: " + String.valueOf(sum3));
                    }

                    if (english.getEnglishHour().equals("4PM")) {
                        sum4++;
                        tvEsun4.setText("4PM: " + String.valueOf(sum4));
                    }

                    if (english.getEnglishHour().equals("5PM")) {
                        sum5++;
                        tvEsun5.setText("5PM: " + String.valueOf(sum5));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query1 = ref1.orderByChild("englishDay").equalTo("Monday");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum3 = 0;
                int sum4 = 0;
                int sum5 = 0;
                for (DataSnapshot englishSnapshot : dataSnapshot.getChildren()) {
                    English english = englishSnapshot.getValue(English.class);
                    if (english.getEnglishHour().equals("4PM")) {
                        sum3++;
                        tvEmon4.setText("Mon 4PM: " + String.valueOf(sum3));
                    }

                    if (english.getEnglishHour().equals("5PM")) {
                        sum4++;
                        tvEmon5.setText("5PM: " + String.valueOf(sum4));
                    }

                    if (english.getEnglishHour().equals("6PM")) {
                        sum5++;
                        tvEmon6.setText("6PM: " + String.valueOf(sum5));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query1 = ref1.orderByChild("englishDay").equalTo("Tuesday");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum3 = 0;
                int sum4 = 0;
                int sum5 = 0;
                for (DataSnapshot englishSnapshot : dataSnapshot.getChildren()) {
                    English english = englishSnapshot.getValue(English.class);
                    if (english.getEnglishHour().equals("4PM")) {
                        sum3++;
                        tvEtue4.setText("Tue 4PM: " + String.valueOf(sum3));
                    }

                    if (english.getEnglishHour().equals("5PM")) {
                        sum4++;
                        tvEtue5.setText("5PM: " + String.valueOf(sum4));
                    }

                    if (english.getEnglishHour().equals("6PM")) {
                        sum5++;
                        tvEtue6.setText("6PM: " + String.valueOf(sum5));
                    }
                }
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

}