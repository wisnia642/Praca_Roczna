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
    private final String[] Dodatki;
    private final String[] Dodatkowe_Zyczenia;
    private final String[] Ilosc;


    public CustomAdapter5(Activity context,
                          String[] nazwa, String[] dodatki, String[] dodatkowe_zyczenia, String[] ilosc) {
        super(context, R.layout.custom_row5, nazwa);
        this.context = context;
        this.Nazwa = nazwa;
        this.Dodatki = dodatki;
        this.Dodatkowe_Zyczenia = dodatkowe_zyczenia;
        this.Ilosc = ilosc;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView5 = inflater.inflate(R.layout.custom_row5, null, true);

        if (Nazwa[position] != null) {

            TextView Text1 = (TextView) rowView5.findViewById(R.id.textView82);
            TextView Text2 = (TextView) rowView5.findViewById(R.id.textView83);
            TextView Text3 = (TextView) rowView5.findViewById(R.id.textView84);
            TextView Text4 = (TextView) rowView5.findViewById(R.id.textView85);

            Text1.setText(Nazwa[position]);
            Text2.setText(Dodatki[position]);
            Text3.setText(Dodatkowe_Zyczenia[position]);
            Text4.setText(Ilosc[position]);

        }
        return rowView5;


    }
}