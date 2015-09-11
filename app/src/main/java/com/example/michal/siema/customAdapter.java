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
    private final String[] zm;
    private final String[] zm1;
    private final String[] zm2;
    private final String[] zm3;
    public customAdapter(Activity context,
                         String[] zma,String[] zma1, String[] zma2,String[] zma3) {
        super(context, R.layout.custom_row, zma);
        this.context = context;
        this.zm = zma;
        this.zm1 = zma1;
        this.zm2 = zma2;
        this.zm3 = zma3;
    }
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_row, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.customText);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.customText1);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.customText2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.customImage);

        txtTitle.setText(zm[position]);
        txtTitle1.setText(zm1[position]);
        txtTitle2.setText(zm2[position]);
        String zdjencie = (zm3[position]);

        //dodawanie obrazu jesli go nie ma
        Bitmap bmImg = BitmapFactory.decodeFile(zdjencie);
        imageView.setImageBitmap(bmImg);
        if(bmImg==null&zm[position]!=null)
        {
            imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.brak));

        }

        return rowView;


    }



}
