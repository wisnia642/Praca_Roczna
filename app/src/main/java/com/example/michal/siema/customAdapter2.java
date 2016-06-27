package com.example.michal.siema;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class customAdapter2 extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] Stolik;

    public customAdapter2(Activity context,
                          String[] zm) {
        super(context, R.layout.custom_row2, zm);
        this.context = context;
        this.Stolik = zm;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView2 = inflater.inflate(R.layout.custom_row2, null, true);
        TextView txtTitle = (TextView) rowView2.findViewById(R.id.Stolik);

        txtTitle.setText(Stolik[position]);

        return rowView2;
    }
}


