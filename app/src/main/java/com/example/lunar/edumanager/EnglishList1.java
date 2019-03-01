package com.example.lunar.edumanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EnglishList1 extends ArrayAdapter<English> {
    private Activity context;
    List<English> mathList;

    public EnglishList1(Activity context, List<English> mathList) {
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

        English math = mathList.get(position);
        //textViewName.setText(math.getEnglishName());
        textViewLname.setText(math.getEnglishLname() + ", " + math.getEnglishName());
        textViewDay.setText(math.getEnglishDay());
        textViewHour.setText(math.getEnglishHour());
        textViewHistory.setText("Last session: " + math.getEnglishHistory());
        textViewNotes.setText("Notes: " + math.getEnglishNotes());

        return listViewItem;
    }

}