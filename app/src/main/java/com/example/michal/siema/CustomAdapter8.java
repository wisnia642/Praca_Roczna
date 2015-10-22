package com.example.michal.siema;

        import android.app.Activity;
        import android.provider.ContactsContract;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

public class CustomAdapter8 extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] Data1;
    private final String[] Czas;
    private final Double[] Nazwa;
    private final Double[] Ilosc;
    private final Double[] Czas_wykonania;
    private final String[] Kto_wykonal;
    int i=0;


    public CustomAdapter8(Activity context,String[] data,String[] czas,
                          Double[] nazwa, Double[] ilosc, Double[] czas_wykonania,String[] kto_wykonal) {
        super(context, R.layout.custom_row7,data);
        this.context = context;
        this.Data1 = data;
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

        if(Data1!=null) {
            Text1.setText(Data1[position]);
            Text2.setText(Czas[position]);
            Text3.setText(String.valueOf(Nazwa[position]));
            Text4.setText(String.valueOf(Ilosc[position]));
            Text5.setText(String.valueOf(Czas_wykonania[position]));
            Text6.setText(Kto_wykonal[position]);
        }
        return rowView5;


    }
}

