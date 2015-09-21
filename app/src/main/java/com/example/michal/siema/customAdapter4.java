package com.example.michal.siema;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class customAdapter4 extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] Nazwa;
    private final String[] Dodatki;
    private final String[] Dodatkowe_Zyczenia;
    private final String[] Ilosc;


    public customAdapter4(Activity context,
                          String[] nazwa, String[] dodatki, String[] dodatkowe_zyczenia, String[] ilosc) {
        super(context, R.layout.custom_row4, nazwa);
        this.context = context;
        this.Nazwa = nazwa;
        this.Dodatki = dodatki;
        this.Dodatkowe_Zyczenia = dodatkowe_zyczenia;
        this.Ilosc = ilosc;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView4 = inflater.inflate(R.layout.custom_row4, null, true);



                TextView Text1 = (TextView) rowView4.findViewById(R.id.textView72);
                TextView Text2 = (TextView) rowView4.findViewById(R.id.textView73);
                TextView Text3 = (TextView) rowView4.findViewById(R.id.textView74);
                TextView Text4 = (TextView) rowView4.findViewById(R.id.textView75);

                 if (Nazwa[position] != null) {
                     Text1.setText("Klient: " + Nazwa[position]);
                     Text2.setText("Dodatki: " + Dodatki[position]);
                     Text3.setText("Zyczenia: " + Dodatkowe_Zyczenia[position]);
                     Text4.setText("Ilosc: " + Ilosc[position]);

                 }
        return rowView4;


    }
}
