package com.example.michal.siema;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CustomAdapter5 extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] Nazwa;



    public CustomAdapter5(Activity context,
                          String[] nazwa) {
        super(context, R.layout.custom_row5, nazwa);
        this.context = context;
        this.Nazwa = nazwa;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView5 = inflater.inflate(R.layout.custom_row5, null, true);

        if (Nazwa[position] != null) {

            TextView Text1 = (TextView) rowView5.findViewById(R.id.textView82);

            Text1.setText(Nazwa[position]);


        }
        return rowView5;


    }
}