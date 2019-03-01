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

public class AttendMath extends AppCompatActivity {

    public static final String MATH_NAME1 = "mathname";
    public static final String MATH_ID1 = "mathid";

    DatabaseReference databaseMath;

    TextView textViewName;
    ListView listViewMath;
    List<Math> mathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseMath = FirebaseDatabase.getInstance().getReference("math");

        listViewMath = (ListView) findViewById(R.id.listView1);

        mathList = new ArrayList<>();

        listViewMath.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Math math = mathList.get(position);

                Intent intent = new Intent(getApplicationContext(), EditAttendActivity.class);

                intent.putExtra(MATH_ID1, math.getMathId());
                intent.putExtra(MATH_NAME1, math.getMathName());

                startActivity(intent);
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

                MathList adapter = new MathList(AttendMath.this, mathList);
                Collections.sort(mathList, new MyComparator0());
                Collections.sort(mathList, new MyComparator());

                listViewMath.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
