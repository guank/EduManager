package com.example.lunar.edumanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EnglishList extends ArrayAdapter<English> {
    private Activity context;
    List<English> englishList;

    public EnglishList(Activity context, List<English> englishList) {
        super(context, R.layout.list_layout2, englishList);
        this.context = context;
        this.englishList = englishList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout2, null, true);

        //TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewLname = (TextView) listViewItem.findViewById(R.id.textViewLname);
        /*TextView textViewLevel = (TextView) listViewItem.findViewById(R.id.textViewLevel);
        TextView textViewTest = (TextView) listViewItem.findViewById(R.id.textViewTest);
        TextView textViewHW = (TextView) listViewItem.findViewById(R.id.textViewHW);
        TextView textViewDay = (TextView) listViewItem.findViewById(R.id.textViewDay);
        TextView textViewHour = (TextView) listViewItem.findViewById(R.id.textViewHour);*/

        English english = englishList.get(position);
        //textViewName.setText(english.getEnglishName());
        textViewLname.setText(english.getEnglishLname() + ", " + english.getEnglishName());
       /* textViewLevel.setText(english.getEnglishLevel());
        textViewTest.setText(english.getEnglishTest());
        textViewHW.setText(english.getEnglishHW());
        textViewDay.setText(english.getEnglishDay());
        textViewHour.setText(english.getEnglishHour());*/


        return listViewItem;
    }

}