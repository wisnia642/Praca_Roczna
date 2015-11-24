package com.example.michal.siema;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lista_dodawanie extends ActionBarActivity {

    ListView list;
    int userSelectedIndex;

    public static final int PIERWSZY_ELEMENT = 1;
    public static final int DRUGI_ELEMENT = 2;
    public static final int TRZECI_ELEMENT = 3;
    public static final int CZWARTY_ELEMENT = 4;
    public static final int PIATY_ELEMENT = 5;

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    Bundle applesData;
    String s,m,k,W,jak;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    int i = 0;
    static Statement st;
    Connection connection = null;
    String posilek,gdzie;
    String pozycja;
    String message13 = "";

    final String[] zm = new String[50];
    final String[] zm1 = new String[50];
    final String[] zm2 = new String[50];
    final String[] zm3 = new String[50];
    final String[] zm4 = new String[50];
    final String[] zm5 = new String[50];
    PreparedStatement ps;
    FileInputStream fis = null;
    File file;

    ResultSet resultSet;
    FileOutputStream fos;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    //tworzenie polaczenia z baza danych
    public void connect() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            showToast("" + e);
            return;
        }


        try {
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            showToast("" + e);
            return;
        }

    }

    //dane z serwera
    public void wczytywanie() {

        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            String sql = "SELECT Nazwa,Skladniki,Skladniki1,Sposob_przygotowania,Cena,Zdjecie FROM " + posilek + " ";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                resultSet = stmt.executeQuery();

                while (resultSet.next()) {
                    zm[i] = resultSet.getString(1);
                    zm5[i] = resultSet.getString(2);
                    zm1[i] = resultSet.getString(3);
                    zm4[i] = resultSet.getString(4);
                    zm2[i] = resultSet.getString(5);
                    File image = new File("/mnt/sdcard/" + zm[i] + ".jpg");
                    zm3[i] = "/mnt/sdcard/" + zm[i] + ".jpg";
                    try {
                        fos = new FileOutputStream(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    byte[] buffer = new byte[1];
                    InputStream is = resultSet.getBinaryStream(6);
                    try {
                        while (is.read(buffer) > 0) {
                            try {
                                fos.write(buffer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    i++;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            showToast("brak połączenia z internetem");
        }
    }

    public void readFromSqlLight() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    posilek + "(Nazwa VARCHAR, Skladniki VARCHAR,Skladniki1 VARCHAR,Sposob_przygotowania VARCHAR,Zdjecie VARCHAR,Cena DOUBLE);");

            Cursor c = sampleDB.rawQuery("SELECT * FROM '" + posilek + "'", null);
            int j = 0;
            while (c.moveToNext()) {
                zm[j] = c.getString(0);
                zm5[j] = c.getString(1);
                zm1[j] = c.getString(2);
                zm3[j] = c.getString(4);
                zm2[j] = c.getString(5);
                zm4[j] = c.getString(3);
                j++;
            }
            sampleDB.close();
        } catch (Exception a) {
        }
    }

    public void saveDataSqlLight() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    posilek + "(Nazwa VARCHAR, Skladniki VARCHAR,Skladniki1 VARCHAR,Sposob_przygotowania VARCHAR,Zdjecie VARCHAR ,Cena DOUBLE);");

            sampleDB.execSQL("DELETE FROM " + posilek + "");

            i = i - 1;
            for (int w = 0; w <= i; w = w + 0) {
                sampleDB.execSQL("INSERT INTO " + posilek + " ('Nazwa','Skladniki','Skladniki1','Sposob_przygotowania','Zdjecie','Cena') VALUES ('" + zm[w] + "','" + zm5[w] + "','" + zm1[w] + "','" + zm4[w] + "','" + zm3[w] + "','" + zm2[w] + "')");
                w++;
            }
            sampleDB.close();
        } catch (Exception e) {
        }
    }

    public void saveDataSqlLigtUpdate() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    posilek + "(Nazwa VARCHAR, Skladniki VARCHAR,Skladniki1 VARCHAR,Sposob_przygotowania VARCHAR,Zdjecie VARCHAR ,Cena DOUBLE);");
            i = i - 1;
            for (int w = 0; w <= i; w = w + 0) {
                sampleDB.execSQL("INSERT INTO " + posilek + " ('Nazwa','Skladniki','Skladniki1','Sposob_przygotowania','Zdjecie','Cena') VALUES ('" + zm[w] + "','" + zm5[w] + "','" + zm1[w] + "','" + zm4[w] + "','" + zm3[w] + "','" + zm2[w] + "')");
                w++;
            }
            sampleDB.close();
        } catch (Exception e) {
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dodawanie);
        Bundle applesData = getIntent().getExtras();
        if (applesData == null) {
            return;
        }
        posilek = applesData.getString("applesMessage");
        message13 = applesData.getString("Sala");
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");

        readFromSqlLight();

        customAdapter adapter = new customAdapter(this, zm, zm1, zm2, zm3);
        list = (ListView) findViewById(R.id.listView3);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //przejscie do zamowienia
                        //jeżeli nazwa jest pusta to nie wyswietlaj pozycji
                        userSelectedIndex = position;
                        //dodawanie
                       if(message13.equals("false"))
                       {
                           Intent w = new Intent(Lista_dodawanie.this,Dodawanie.class);
                           w.putExtra("sala_sprzedazy", s);
                           w.putExtra("wszystko", W);
                           w.putExtra("magazyn", m);
                           w.putExtra("kuchnia", k);
                           w.putExtra("kategoria", posilek);
                           w.putExtra("warunek", message13);
                           gdzie="true";
                           w.putExtra("gdzie",gdzie);
                           jak="true";
                           w.putExtra("jak",jak);
                           startActivity(w);
                       }
                        //usuwanie z menu
                        if (message13.equals("true")) {
                            userSelectedIndex = position;
                            Intent i = new Intent(Lista_dodawanie.this, Dodawanie.class);
                            i.putExtra("sala_sprzedazy", s);
                            i.putExtra("wszystko", W);
                            i.putExtra("magazyn", m);
                            i.putExtra("kuchnia", k);
                            String Message1 = String.valueOf(zm[userSelectedIndex]);
                            i.putExtra("nazwa", Message1);
                            String Massage2 = zm1[userSelectedIndex];
                            i.putExtra("skladniki", Massage2);
                            String Massage3 = zm2[userSelectedIndex];
                            i.putExtra("cena", Massage3);
                            String Message4 = String.valueOf(zm3[userSelectedIndex]);
                            i.putExtra("zdjecie", Message4);
                            String Message5 = String.valueOf(zm4[userSelectedIndex]);
                            i.putExtra("Sposob_przygotowania", Message5);
                            i.putExtra("warunek", message13);
                            i.putExtra("kategoria", posilek);
                            gdzie="true";
                            i.putExtra("gdzie",gdzie);
                            jak="true";
                            i.putExtra("jak",jak);
                            startActivity(i);
                        }

                        //poprawianie blendow
                        if (message13.equals("ttrue")) {
                            userSelectedIndex = position;
                            Intent i = new Intent(Lista_dodawanie.this, Dodawanie.class);
                            i.putExtra("sala_sprzedazy", s);
                            i.putExtra("wszystko", W);
                            i.putExtra("magazyn", m);
                            i.putExtra("kuchnia", k);
                            String Message1 = String.valueOf(zm[userSelectedIndex]);
                            i.putExtra("nazwa", Message1);
                            String Massage2 = zm5[userSelectedIndex];
                            i.putExtra("skladniki", Massage2);
                            String Massage3 = zm2[userSelectedIndex];
                            i.putExtra("cena", Massage3);
                            String Message4 = String.valueOf(zm3[userSelectedIndex]);
                            i.putExtra("zdjecie", Message4);
                            String Message5 = String.valueOf(zm4[userSelectedIndex]);
                            i.putExtra("Sposob_przygotowania", Message5);
                            i.putExtra("warunek", message13);
                            i.putExtra("kategoria", posilek);
                            pozycja = String.valueOf(userSelectedIndex + 1);
                            i.putExtra("pozycja", pozycja);
                            gdzie="true";
                            i.putExtra("gdzie",gdzie);
                            jak="true";
                            i.putExtra("jak",jak);
                            startActivity(i);
                        }
                    }

                });
    }
}