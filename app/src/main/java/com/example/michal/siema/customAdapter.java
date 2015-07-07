package com.example.michal.siema;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class customAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] pi;
    private final String[] pi2;
    private final String[] pi3;
    private final String[] pi4;
    public customAdapter(Activity context,
                         String[] zm,String[] zm1, String[] zm2,String[] zm3) {
        super(context, R.layout.custom_row, zm);
        this.context = context;
        this.pi = zm;
        this.pi2 = zm1;
        this.pi3 = zm2;
        this.pi4 = zm3;
    }
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_row, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.customText);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.customText1);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.customText2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.customImage);

        txtTitle.setText(pi[position]);
        txtTitle1.setText(pi2[position]);
        txtTitle2.setText(pi3[position]);
        String zdjencie = (pi4[position]);

        //dodawanie obrazu jesli go nie ma
           Bitmap bmImg = BitmapFactory.decodeFile(zdjencie);
            imageView.setImageBitmap(bmImg);
          if(bmImg==null&pi[position]!=null)
        {
            imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.brak));

        }

        return rowView;


    }



}