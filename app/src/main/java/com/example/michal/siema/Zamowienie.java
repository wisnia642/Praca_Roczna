package com.example.michal.siema;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


public class Zamowienie extends ActionBarActivity {

    Bundle applesData;
    String sala,cena,zdjecie,dodatki,dodatkowe_zyczenia,sql,nazwa,ilosc;
    Double wartosc=0.0;
    Double suma=0.0;

    Connection connection = null;
    int baza=0;
    Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;

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
            connection = DriverManager.getConnection("jdbc:mysql://54.217.215.74/sql481900", "sql481900", "qF9!gX2*");
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
                sql = "INSERT INTO Zamowienie (Klient,Danie,Ilosc,Dodatki,Dodatkowe_Zyczenia,Zdjecie,Suma) VALUES (?,?,?,?,?,?,?) ";


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
                    ps.setString(7, String.valueOf(suma));
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
                showToast("brak po³aczenia z internetem");
            }
    }

    private void ToDataBase()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
           sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Zamowienie (Klient VARCHAR,Danie VARCHAR,Ilosc VARCHAR,Dodatki VARCHAR," +
                   "Dodatkowe_Zyczenia VARCHAR,Zdjecie VARCHAR,Suma INT);");

        }
        catch (Exception e){}

    }

    public void ZapisSqlLight()
    {
             ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            suma=0.0;
            sampleDB.execSQL("INSERT INTO Zamowienie (Klient,Danie,Ilosc,Dodatki,Dodatkowe_Zyczenia,Zdjecie,Suma) VALUES ('"+sala+"','"+nazwa+"','"+ilosc+"','"+dodatki+"','"+dodatkowe_zyczenia+"','"+zdjecie+"','"+suma+"') ");

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
        EditText Nazwa = (EditText) findViewById(R.id.editText4);
        TextView Kasa = (TextView) findViewById(R.id.textView29);

        applesData = getIntent().getExtras();
        sala = applesData.getString("Sala");
        cena = applesData.getString("cena");
        zdjecie = applesData.getString("zdjecie");
        nazwa = applesData.getString("nazwa");

        //wyswietlanie danych
        Klient.setText(sala);
        Bitmap bmImg = BitmapFactory.decodeFile(zdjecie);
        Zdjecie.setImageBitmap(bmImg);
        if(bmImg==null&zdjecie!=null)
        {
           Zdjecie.setImageDrawable(Zdjecie.getResources().getDrawable(R.drawable.brak));

        }
        Nazwa.setText(nazwa);
        Suma.setText(cena);


        Kasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ilosc = Ilosc.getText().toString();

                    if(ilosc!="0")

                        suma = Double.parseDouble(ilosc);
                    wartosc = Double.parseDouble(cena);
                    suma = suma * wartosc;
                    cena = String.valueOf(suma);
                    Suma.setText(String.valueOf(cena));
                }
                catch(Exception e)
                {}
            }
        });



                //dodanie zamowienia do stolika
                dodawanie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dodatki = Dodatki.getText().toString();
                        dodatkowe_zyczenia = Dodatkowe_Zyczenia.getText().toString();
                        ZapisSqlLight();
                        ZapisMySql();
                        Intent i = new Intent(Zamowienie.this, MainActivity.class);
                        startActivity(i);
                    }
                });

        anulacja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Zamowienie.this, MainActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zamowienie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
