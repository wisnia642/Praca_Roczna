package com.example.michal.siema;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter9 extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] Data;
    private final String[] Czas;
    private final String[] Nazwa;
    private final String[] Ilosc;

    private final String[] Kto_wykonal;
    int i=0;


    public CustomAdapter9(Activity context,String[] data,String[] czas,
                          String[] nazwa, String[] ilosc,String[] kto_wykonal) {
        super(context, R.layout.custom_row9, nazwa);
        this.context = context;
        this.Data = data;
        this.Czas = czas;
        this.Nazwa = nazwa;
        this.Ilosc = ilosc;
        this.Kto_wykonal = kto_wykonal;


    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView5 = inflater.inflate(R.layout.custom_row9, null, true);

        TextView Text1 = (TextView) rowView5.findViewById(R.id.textView104);
        TextView Text2 = (TextView) rowView5.findViewById(R.id.textView105);
        TextView Text3 = (TextView) rowView5.findViewById(R.id.textView109);
        TextView Text4 = (TextView) rowView5.findViewById(R.id.textView106);
        TextView Text6 = (TextView) rowView5.findViewById(R.id.textView108);

        if(Data!=null) {
            Text1.setText(Data[position]);
            Text2.setText(Czas[position]);
            Text3.setText(Nazwa[position]);
            Text4.setText(Ilosc[position]);
            Text6.setText(Kto_wykonal[position]);
        }
        return rowView5;


    }
}