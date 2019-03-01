package com.example.lunar.edumanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AttendEnglish extends AppCompatActivity {

    public static final String ENGLISH_NAME1 = "englishname";
    public static final String ENGLISH_ID1 = "englishid";

    DatabaseReference databaseEnglish;

    ListView listViewEnglish;
    TextView textViewName;
    List<English> englishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_list2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseEnglish = FirebaseDatabase.getInstance().getReference("english");

        listViewEnglish = (ListView) findViewById(R.id.listView1);

        englishList = new ArrayList<>();

        listViewEnglish.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                English english = englishList.get(position);

                Intent intent = new Intent(getApplicationContext(), EditAttendActivity1.class);

                intent.putExtra(ENGLISH_ID1, english.getEnglishId());
                intent.putExtra(ENGLISH_NAME1, english.getEnglishName());

                startActivity(intent);
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

                EnglishList adapter = new EnglishList(AttendEnglish.this, englishList);
                Collections.sort(englishList, new MyComparator0());
                Collections.sort(englishList, new MyComparator());

                listViewEnglish.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
