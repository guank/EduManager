package com.example.lunar.edumanager;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
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

public class TimeClockActivity extends AppCompatActivity {

    public static final String EMPLOYEE_NAME = "employeename";
    public static final String EMPLOYEE_ID = "employeeid";

    TextView editTextViewName;
    Button buttonNewsub;

    DatabaseReference databaseEmployee;

    ListView listViewEmployee;
    List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_clock);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseEmployee = FirebaseDatabase.getInstance().getReference("employee");

        buttonNewsub = (Button) findViewById(R.id.buttonNewsub);
        listViewEmployee = (ListView) findViewById(R.id.listViewEmployee);

        employeeList = new ArrayList<>();

        buttonNewsub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showNewmathDialog();
            }
        });

        listViewEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Employee employee = employeeList.get(position);

                Intent intent = new Intent(getApplicationContext(), TimeClockActivity2.class);

                intent.putExtra(EMPLOYEE_ID, employee.getEmployeeId());
                intent.putExtra(EMPLOYEE_NAME, employee.getEmployeeName());

                startActivity(intent);

            }
        });

        listViewEmployee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Employee employee = employeeList.get(position);
                showDeleteDialog(employee.getEmployeeId());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEmployee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                employeeList.clear();

                for(DataSnapshot employeeSnapshot : dataSnapshot.getChildren()){
                    Employee employee = employeeSnapshot.getValue(Employee.class);

                    employeeList.add(employee);
                }

                EmployeeList adapter = new EmployeeList(TimeClockActivity.this, employeeList);
                Collections.sort(employeeList, new TimeClockActivity.MyComparator());

                listViewEmployee.setAdapter(adapter);

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

    public class MyComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee p1, Employee p2) {
            return p1.getEmployeeLname().compareTo(p2.getEmployeeLname());
        }
    }

    private void showDeleteDialog(final String id){
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
                DatabaseReference drAttend = FirebaseDatabase.getInstance().getReference("employee").child(id);

                drAttend.removeValue();

                alertDialog.dismiss();
            }
        });
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

                    String id = databaseEmployee.push().getKey();

                    Employee employee = new Employee(id, name, lname);

                    databaseEmployee.child(id).setValue(employee);

                }

                alertDialog.dismiss();

            }
        });

    }

}
