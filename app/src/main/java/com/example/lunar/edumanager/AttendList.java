package com.example.lunar.edumanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AttendList extends ArrayAdapter<Attend> {

    private Activity context;
    List<Attend> attends;

    public AttendList(Activity context, List<Attend> attends) {
        super(context, R.layout.layout_attend_list, attends);
        this.context = context;
        this.attends = attends;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_attend_list, null, true);

        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        //TextView textViewSubject = (TextView) listViewItem.findViewById(R.id.textViewSubject);

        Attend attend = attends.get(position);
        textViewDate.setText(attend.getAttendDate());
        //textViewSubject.setText(attend.getAttendSubject());

        return listViewItem;
    }
}
