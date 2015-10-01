package com.example.michal.siema;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

    public class CustomAdapter7 extends ArrayAdapter<String> {
        private final Activity context;
        private final String[] Data;
        private final String[] Czas;
        private final String[] Nazwa;
        private final String[] Ilosc;
        private final String[] Czas_wykonania;
        private final String[] Kto_wykonal;
        int i=0;

        public CustomAdapter7(Activity context,String[] data,String[] czas,
                              String[] nazwa, String[] ilosc, String[] czas_wykonania,String[] kto_wykonal) {
            super(context, R.layout.custom_row7, nazwa);
            this.context = context;
            this.Data = data;
            this.Czas = czas;
            this.Nazwa = nazwa;
            this.Ilosc = ilosc;
            this.Czas_wykonania = czas_wykonania;
            this.Kto_wykonal = kto_wykonal;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView5 = inflater.inflate(R.layout.custom_row7, null, true);


                TextView Text1 = (TextView) rowView5.findViewById(R.id.textView104);
                TextView Text2 = (TextView) rowView5.findViewById(R.id.textView105);
                TextView Text3 = (TextView) rowView5.findViewById(R.id.textView109);
                TextView Text4 = (TextView) rowView5.findViewById(R.id.textView106);
                TextView Text5 = (TextView) rowView5.findViewById(R.id.textView107);
                TextView Text6 = (TextView) rowView5.findViewById(R.id.textView108);

                Text1.setText(Data[position]);
                Text2.setText(Czas[position]);
                Text3.setText(Nazwa[position]);
                Text4.setText(Ilosc[position]);
                Text5.setText(Czas_wykonania[position]);
                Text6.setText(Kto_wykonal[position]);
                i++;
            return rowView5;


        }
    }

