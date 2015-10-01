package com.example.michal.siema;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomAdapter6 extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] Nazwa;
    private final String[] Ilosc;
    private final String[] Czas_wykonania;
    private final String[] Kto_wykonal;
    private final String[] Cena_detalicza;
    private final String[] Ilosc_detaliczna;


    public CustomAdapter6(Activity context,
                           String[] nazwa, String[] ilosc, String[] czas_wykonania,String[] kto_wykonal,
                          String[] cena_detaliczna,String[] ilosc_detaliczna) {
        super(context, R.layout.custom_row6, nazwa);
        this.context = context;
        this.Nazwa = nazwa;
        this.Ilosc = ilosc;
        this.Czas_wykonania = czas_wykonania;
        this.Kto_wykonal = kto_wykonal;
        this.Cena_detalicza = cena_detaliczna;
        this.Ilosc_detaliczna = ilosc_detaliczna;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView5 = inflater.inflate(R.layout.custom_row6, null, true);


        if (Nazwa[position] != null) {
            TextView Text2 = (TextView) rowView5.findViewById(R.id.textView93);
            TextView Text6 = (TextView) rowView5.findViewById(R.id.textView94);
            TextView Text3 = (TextView) rowView5.findViewById(R.id.textView102);
            TextView Text7 = (TextView) rowView5.findViewById(R.id.textView95);
            TextView Text4 = (TextView) rowView5.findViewById(R.id.textView103);
            TextView Text5 = (TextView) rowView5.findViewById(R.id.textView96);

            Text2.setText(Nazwa[position]);
            Text6.setText(Ilosc[position]);
            Text3.setText(Czas_wykonania[position]);
            Text7.setText(Kto_wykonal[position]);
            Text4.setText(Cena_detalicza[position]);
            Text5.setText(Ilosc_detaliczna[position]);

        }
        return rowView5;


    }
}