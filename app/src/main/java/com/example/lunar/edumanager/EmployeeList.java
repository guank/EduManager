package com.example.lunar.edumanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EmployeeList extends ArrayAdapter<Employee> {
    private Activity context;
    List<Employee> mathList;

    public EmployeeList(Activity context, List<Employee> mathList) {
        super(context, R.layout.list_layout1, mathList);
        this.context = context;
        this.mathList = mathList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout1, null, true);

        //TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewLname = (TextView) listViewItem.findViewById(R.id.textViewLname);

        Employee employee = mathList.get(position);
        //textViewName.setText(employee.getEmployeeName());
        textViewLname.setText(employee.getEmployeeLname()+ ", " + employee.getEmployeeName());

        return listViewItem;
    }
}
