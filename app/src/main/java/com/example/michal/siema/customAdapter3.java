package com.example.michal.siema;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class customAdapter3 extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] Ssztuki;
    private final String[] Ssala;
    private final String[] Sklient;
    private final String[] Sczas;
    private final String[] Sdata;

    public customAdapter3(Rezerwacja context,
                         String[] sztuki, String[] sala, String[] klient, String[] czas,String[] data) {
        super(context, R.layout.custom_row3, sztuki);
        this.context = context;
        this.Ssztuki = sztuki;
        this.Ssala = sala;
        this.Sklient = klient;
        this.Sczas = czas;
        this.Sdata = data;
    }
         public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView3 = inflater.inflate(R.layout.custom_row3, null, true);

        TextView Text1 = (TextView) rowView3.findViewById(R.id.textView54);
        TextView Text2 = (TextView) rowView3.findViewById(R.id.textView50);
       TextView Text3 = (TextView) rowView3.findViewById(R.id.textView51);
       TextView Text4 = (TextView) rowView3.findViewById(R.id.textView52);
             TextView Text5 = (TextView) rowView3.findViewById(R.id.textView53);

             Text1.setText(Ssztuki[position]);
            Text2.setText(Ssala[position]);
            Text3.setText(Sklient[position]);
             Text4.setText(Sczas[position]);
             Text5.setText(Sdata[position]);


             return rowView3;


    }
}
