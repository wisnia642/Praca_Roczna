package com.example.michal.siema;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Zamowienie extends ActionBarActivity  {

    Bundle applesData;
    String cena,cena1,zdjecie,dodatki,dodatkowe_zyczenia,sql,nazwa,ilosc,sposob,skladniki;
    String sala=null;
    Double wartosc=0.0;
    Double suma=0.0;
    boolean klikniete = false;

    String s,m,k,W;

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    int x,w,c,t=1;
    String tab[] = new String[20];
    Connection connection = null;
    int i;

    List<String> listaStringow = new ArrayList<String>();

    Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;

    Spinner spinnerOsversions;

    private static final String SAMPLE_DB_NAME = "Restalracja";

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    public void connect() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            return;
        }

        try {
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            showToast("Brak polaczenia z internetem");
            return;
        }
    }

    public void ZapisMySql()
    {
        connect();

        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {

            }
                sql = "INSERT INTO Zamowienie (Klient,Danie,Ilosc,Dodatki,Dodatkowe_Zyczenia,Zdjecie,Suma,Sposob_przygotowania,Skladniki) VALUES (?,?,?,?,?,?,?,?,?) ";


                try {
                    connection.setAutoCommit(false);
                    File file =new File(zdjecie);
                    try {
                        fis = new FileInputStream(file);
                    } catch (FileNotFoundException e) {

                    }
                    ps = connection.prepareStatement(sql);
                    ps.setString(1,sala);
                    ps.setString(2,nazwa);
                    ps.setString(3,ilosc);
                    ps.setString(4,dodatki);
                    ps.setString(5,dodatkowe_zyczenia);
                    ps.setBinaryStream(6, fis, (int) file.length());
                    ps.setString(7, cena1);
                    ps.setString(8, sposob);
                    ps.setString(9, skladniki);
                    ps.executeUpdate();
                    connection.commit();

                } catch (SQLException e) {

                }
                finally {
                    try {
                        ps.close();

                    } catch (SQLException e) {

                    }
                    try {
                        fis.close();
                    } catch (IOException e) {

                    }

                }
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                showToast("brak polaczenia z internetem");
            }
    }

    private void ToDataBase()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
           sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Zamowienie (Klient VARCHAR,Danie VARCHAR,Ilosc VARCHAR,Dodatki VARCHAR," +
                   "Dodatkowe_Zyczenia VARCHAR,Zdjecie VARCHAR,Suma DOUBLE,Sposob_przygotowania VARCHAR,Skladniki VARCHAR,Stan VARCHAR,Faktura VARCHAR);");

        }
        catch (Exception e){}

    }

    private void readFromDataBase()
    {
        try{
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

                x=0;
                Cursor c=sampleDB.rawQuery("SELECT * FROM ZAMOWIENIE",null);
                  while (c.moveToNext())
                {
                    if(tab[x]!="") {
                        tab[x] = String.valueOf(c.getString(0));

                    }
                    x++;

                }
            sampleDB.close();

        }catch (Exception a){}
    }

    public void ZapisSqlLight()
    {
             ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            suma=0.0;
            sampleDB.execSQL("INSERT INTO Zamowienie (Klient,Danie,Ilosc,Dodatki,Dodatkowe_Zyczenia,Zdjecie,Suma,Sposob_przygotowania,Skladniki) VALUES ('"+sala+"','"+nazwa+"','"+ilosc+"','"+dodatki+"','"+dodatkowe_zyczenia+"','"+zdjecie+"','"+cena1+"','"+sposob+"','"+skladniki+"') ");

            sampleDB.close();

        }catch (Exception e)
        {showToast(e+"");}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zamowienie);

        TextView Klient =(TextView) findViewById(R.id.textView27);
        final EditText Ilosc =(EditText) findViewById(R.id.editText3);
        final EditText Dodatki =(EditText) findViewById(R.id.editText2);
        final EditText Dodatkowe_Zyczenia = (EditText) findViewById(R.id.editText);
        final TextView Suma = (TextView) findViewById(R.id.textView30);
        ImageView Zdjecie = (ImageView) findViewById(R.id.imageView3);
        Button dodawanie = (Button) findViewById(R.id.button17);
        Button anulacja = (Button) findViewById(R.id.button);
        TextView Nazwa = (TextView) findViewById(R.id.textView32);
        TextView Kasa = (TextView) findViewById(R.id.textView29);

        applesData = getIntent().getExtras();
        sala = applesData.getString("Sala");
        cena = applesData.getString("cena");
        zdjecie = applesData.getString("zdjecie");
        nazwa = applesData.getString("nazwa");
        sposob = applesData.getString("sposob");
        skladniki = applesData.getString("skladniki");
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");

        try{readFromDataBase();}catch (Exception e){showToast("blad :(");}

        for (int i=0; i < x; i = i+ 0) {
            for (int j = 0; j < x; j = j+ 0) {
                if(j==0)
                {
                    j=j+i;
                }
                if (tab[j].equals(tab[i])) {
                    w = w + 1;
                }
                j = j + 1;
            }
            if (w == 1) {
                tab[c]=tab[i];
                listaStringow.add(tab[i]);
                //showToast(String.valueOf(tab[i]));
                c=c+1;
            }
            w = 0;
            i=i+1;
        }
        i=c;
        c=0;
        i=i+1;
        spinnerOsversions = (Spinner) findViewById(R.id.spinner);
        spinnerOsversions.setAdapter(new MyAdapter(this, R.layout.custom_spiner, listaStringow));
      //  spinnerOsversions.setPrompt("siema stary");
       // spinnerOsversions.

        spinnerOsversions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    klikniete = true;
                    t++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //wyswietlanie danych
        Bitmap bmImg = BitmapFactory.decodeFile(zdjecie);
        Zdjecie.setImageBitmap(bmImg);
        if(bmImg==null&zdjecie!=null)
        {
           Zdjecie.setImageDrawable(Zdjecie.getResources().getDrawable(R.drawable.brak));
        }
        Nazwa.setText(nazwa);
        Suma.setText(cena);

        //liczenie wszystkich zanmowien
        Kasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ilosc = Ilosc.getText().toString();

                    if (ilosc != null)

                        suma = Double.parseDouble(ilosc);
                    wartosc = Double.parseDouble(cena);
                    suma = suma * wartosc;
                    cena1 = String.valueOf(suma);
                    Suma.setText(String.valueOf(cena1));


                } catch (Exception e) {
                }
            }
        });

                //dodanie zamowienia do stolika
                dodawanie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ilosc = Ilosc.getText().toString();
                        if(ilosc==null){
                            showToast("UZUPELNIJ ILOSC");
                        }
                        else
                        {
                        dodatki = Dodatki.getText().toString();
                        dodatkowe_zyczenia = Dodatkowe_Zyczenia.getText().toString();
                        try {

                            suma = Double.parseDouble(ilosc);
                            wartosc = Double.parseDouble(cena);
                            suma = suma * wartosc;
                            cena1 = String.valueOf(suma);
                            Suma.setText(String.valueOf(cena1));


                        }
                        catch(Exception e)
                        {}


                            sala = String.valueOf(i);

                            if (klikniete==true & t>=3)
                            {
                                showToast("tak");
                                sala = (tab[w]);
                            }




                        ZapisSqlLight();
                        ZapisMySql();
                        Intent i = new Intent(Zamowienie.this, MainActivity.class);
                            i.putExtra("sala_sprzedazy", s);
                            i.putExtra("wszystko", W);
                            i.putExtra("magazyn", m);
                            i.putExtra("kuchnia", k);
                        startActivity(i);
                    }}
                });

        anulacja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Zamowienie.this, MainActivity.class);
                i.putExtra("sala_sprzedazy", s);
                i.putExtra("wszystko", W);
                i.putExtra("magazyn", m);
                i.putExtra("kuchnia", k);
                startActivity(i);
            }
        });
    }

    public class MyAdapter extends ArrayAdapter<String>
    {
        public MyAdapter(Context ctx, int txtViewResourceId, List<String> objects)
        {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt)
        {
            return getCustomView(position, cnvtView, prnt);
        }
        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt)
        {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent)
        {
            w=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text1);
            main_text.setText(tab[position]);
            return mySpinner;
        }}
}
