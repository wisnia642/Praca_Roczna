package com.example.michal.siema;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class customAdapter1 extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] Danie;
    private final String[] Ilosc;
    private final String[] Zdjecie;
    private final String[] Stan;
    private final int poz;


    public customAdapter1(Activity context,
                         String[] zm,String[] zm1, String[] zm2,int zm3,String[] zm4) {
        super(context, R.layout.custom_row1, zm);
        this.context = context;
        this.Danie = zm;
        this.Ilosc = zm1;
        this.Zdjecie = zm2;
        this.poz = zm3;
        this.Stan = zm4;
    }



    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView1=inflater.inflate(R.layout.custom_row1, null,true);

        TextView txtTitle = (TextView) rowView1.findViewById(R.id.Text);
        TextView txtTitle1 = (TextView) rowView1.findViewById(R.id.Text2);
        ImageView imageView = (ImageView) rowView1.findViewById(R.id.Image);

            txtTitle.setText(Danie[position]); //Danie zmiana
            txtTitle1.setText(Ilosc[position]);
            String zdjencie = (Zdjecie[position]);


        if(Stan[position]!="null")
        {
            txtTitle.setTextColor(Color.parseColor("#33CC33"));
        }

            //dodawanie obrazu jesli go nie ma
            Bitmap bmImg = BitmapFactory.decodeFile(zdjencie);
            imageView.setImageBitmap(bmImg);
            if (bmImg == null & Danie[position] != null) {
                imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.brak));

        }
        return rowView1;

    }

}