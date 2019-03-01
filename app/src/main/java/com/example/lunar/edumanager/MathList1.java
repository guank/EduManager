package com.example.lunar.edumanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MathList1 extends ArrayAdapter<Math> {
    private Activity context;
    List<Math> mathList;

    public MathList1(Activity context, List<Math> mathList) {
        super(context, R.layout.list_layout1ss, mathList);
        this.context = context;
        this.mathList = mathList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout1ss, null, true);

        //TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewLname = (TextView) listViewItem.findViewById(R.id.textViewLname);
        TextView textViewDay = (TextView) listViewItem.findViewById(R.id.textViewDay);
        TextView textViewHour = (TextView) listViewItem.findViewById(R.id.textViewHour);
        TextView textViewHistory = (TextView) listViewItem.findViewById(R.id.textViewHistory);
        TextView textViewNotes = (TextView) listViewItem.findViewById(R.id.textViewNotes);

        Math math = mathList.get(position);
        //textViewName.setText(math.getMathName());
        textViewLname.setText(math.getMathLname() + ", " + math.getMathName());
        textViewDay.setText(math.getMathDay());
        textViewHour.setText(math.getMathHour());
        textViewHistory.setText("Last session: " + math.getMathHistory());
        textViewNotes.setText("Notes: " + math.getMathNotes());

        return listViewItem;
    }

}