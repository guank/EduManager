package com.example.lunar.edumanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClockList extends ArrayAdapter<Clock> {
    private Activity context;
    List<Clock> mathList;

    public ClockList(Activity context, List<Clock> mathList) {
        super(context, R.layout.list_layout3, mathList);
        this.context = context;
        this.mathList = mathList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout3, null, true);

        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        TextView textViewTimein = (TextView) listViewItem.findViewById(R.id.textViewTimein);
        TextView textViewTimeout = (TextView) listViewItem.findViewById(R.id.textViewTimeout);

        Clock clock = mathList.get(position);
        textViewDate.setText(clock.getClockDate());
        textViewTimein.setText(clock.getClockIn());
        textViewTimeout.setText(clock.getClockOut());

        return listViewItem;
    }

}