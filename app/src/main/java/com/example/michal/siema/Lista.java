package com.example.michal.siema;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
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


public class Lista extends ActionBarActivity {

    ListView list;
    String[] pi=null;
    String[] pi2 =null;
    String[] pi3 =null;
    String[] pi4=null;
    int userSelectedIndex;

    public static final int PIERWSZY_ELEMENT = 1;
    public static final int DRUGI_ELEMENT = 2;
    public static final int TRZECI_ELEMENT = 3;
    public static final int CZWARTY_ELEMENT = 4;
    public static final int PIATY_ELEMENT = 5;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    int i =0;
    static Statement st;
    Connection connection = null;
    String posilek;
    String pozycja;
    String message13="";

    final String[] zm = new String[50];
    final String[] zm1 = new String[50];
    final String[] zm2 = new String[50];
    final String[] zm3 = new String[50];

    ResultSet resultSet;
    FileOutputStream fos;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    //tworzenie polaczenia z baza danych
    public void connect()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            showToast("" + e);
            return;
        }


        try {
            connection = DriverManager.getConnection("jdbc:mysql://54.217.215.74/sql481900", "sql481900", "qF9!gX2*");
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
             String sql = "SELECT Nazwa,Skladniki,Cena,Zdjecie FROM " + posilek + " ";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                resultSet = stmt.executeQuery();

                while (resultSet.next()){
                    zm[i] = resultSet.getString(1);
                    zm1[i] = resultSet.getString(2);
                    zm2[i] = resultSet.getString(3);
                    File image = new File("/mnt/sdcard/"+zm[i]+".jpg");
                    zm3[i] = "/mnt/sdcard/"+zm[i]+".jpg";
                    try {
                        fos = new FileOutputStream(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    byte[] buffer = new byte[1];
                    InputStream is = resultSet.getBinaryStream(4);
                    try {
                        while (is.read(buffer) > 0)
                        {
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
            }}

            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                showToast("brak połączenia z internetem");
            }
        }



    public void readFromSqlLight()
    {
        try{
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    posilek + "(Nazwa VARCHAR, Skladniki VARCHAR,Zdjecie VARCHAR,Cena DOUBLE);");

            Cursor c=sampleDB.rawQuery("SELECT * FROM '"+posilek+"'",null);
                int j=0;
                while (c.moveToNext())
                {
                    zm[j]=  c.getString(0);
                    zm1[j]= c.getString(1);
                    zm3[j]= c.getString(2);
                    zm2[j]= c.getString(3);
                    j++;
                }
            showToast(zm3[1]);
            sampleDB.close();
        }catch (Exception a){}
    }

    public void saveDataSqlLight()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    posilek + "(Nazwa VARCHAR, Skladniki VARCHAR,Zdjecie VARCHAR ,Cena DOUBLE);");

            sampleDB.execSQL("DELETE FROM "+posilek+"");

            i=i-1;
            for (int w = 0; w <= i; w = w + 0) {
                sampleDB.execSQL("INSERT INTO "+posilek+" ('Nazwa','Skladniki','Zdjecie','Cena') VALUES ('"+zm[w]+"','"+zm1[w]+"','"+zm3[w]+"','"+zm2[w]+"')");
                w++;
            }
            sampleDB.close();
        }catch (Exception e){}
    }

    public void saveDataSqlLigtUpdate()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    posilek + "(Nazwa VARCHAR, Skladniki VARCHAR,Zdjecie VARCHAR ,Cena DOUBLE);");
            i=i-1;
            for (int w = 0; w <= i; w = w + 0) {
                sampleDB.execSQL("INSERT INTO "+posilek+" ('Nazwa','Skladniki','Zdjecie','Cena') VALUES ('"+zm[w]+"','"+zm1[w]+"','"+zm3[w]+"','"+zm2[w]+"')");
                w++;
            }
            sampleDB.close();
        }catch (Exception e){}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        Bundle applesData = getIntent().getExtras();
        if(applesData == null)
        {
            return;
        }
         posilek = applesData.getString("applesMessage");
        final String Sala = applesData.getString("Sala");

        readFromSqlLight();

            pi=zm;
            pi2=zm1;
            pi3=zm2;
            pi4=zm3;

            customAdapter adapter=new customAdapter(this, pi,pi2,pi3,pi4);
            list=(ListView)findViewById(R.id.listView3);
            list.setAdapter(adapter);

            list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //przejscie do zamowienia
                            Intent i = new Intent(Lista.this, Zamowienie.class);
                            String Message1 = zm[userSelectedIndex];
                            i.putExtra("nazwa",Message1);
                            String Massage3 = zm2[userSelectedIndex];
                            i.putExtra("cena", Massage3);
                            String Message4 = String.valueOf(zm3[userSelectedIndex]);
                            i.putExtra("zdjecie", Message4);
                            i.putExtra("Sala", Sala);
                            startActivity(i);
                        }

                    });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //usuwanie z menu
                if (message13.equals("true")) {
                    userSelectedIndex = position;
                    Intent i = new Intent(Lista.this, Dodawanie.class);
                    String Message1 = String.valueOf(zm[userSelectedIndex]);
                    i.putExtra("nazwa", Message1);
                    String Massage2 = zm1[userSelectedIndex];
                    i.putExtra("skladniki", Massage2);
                    String Massage3 = zm2[userSelectedIndex];
                    i.putExtra("cena", Massage3);
                    String Message4 = String.valueOf(zm3[userSelectedIndex]);
                    i.putExtra("zdjecie", Message4);
                    i.putExtra("warunek", message13);
                    i.putExtra("kategoria", posilek);
                    startActivity(i);
                }

                //poprawianie blendow
                if (message13.equals("ttrue")) {
                    userSelectedIndex = position;
                    Intent i = new Intent(Lista.this, Dodawanie.class);
                    String Message1 = String.valueOf(zm[userSelectedIndex]);
                    i.putExtra("nazwa", Message1);
                    String Massage2 = zm1[userSelectedIndex];
                    i.putExtra("skladniki", Massage2);
                    String Massage3 = zm2[userSelectedIndex];
                    i.putExtra("cena", Massage3);
                    String Message4 = String.valueOf(zm3[userSelectedIndex]);
                    i.putExtra("zdjecie", Message4);
                    i.putExtra("warunek", message13);
                    i.putExtra("kategoria", posilek);
                    pozycja = String.valueOf(userSelectedIndex + 1);
                    i.putExtra("pozycja", pozycja);
                    startActivity(i);
                }
                return true;
            }
        });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, PIERWSZY_ELEMENT, 0, "Dodaj Przepis");
        menu.add(1, DRUGI_ELEMENT, 0, "Usun Przepis");
        menu.add(2, TRZECI_ELEMENT, 0, "Poprawianie Przepisu");
        menu.add(3, CZWARTY_ELEMENT, 0, "Update Data");
        menu.add(4, PIATY_ELEMENT, 0, "Import Data");

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case PIERWSZY_ELEMENT:
                Intent w = new Intent(Lista.this,Dodawanie.class);
                w.putExtra("kategoria", posilek);
                message13 = "false";
                w.putExtra("warunek", message13);
                startActivity(w);
                break;
            case DRUGI_ELEMENT:
                Intent i = new Intent(Lista.this,Dodawanie.class);
                message13 = "true";
                i.putExtra("kategoria", posilek);
                showToast(" przytrzymaj pozycje aby ja usunac");

                break;
            case TRZECI_ELEMENT:
                Intent c = new Intent(Lista.this,Dodawanie.class);
                message13="ttrue";
                c.putExtra("kategoria", posilek);
                showToast(" przytrzymaj pozycje aby ja poprawic");

                break;
            case CZWARTY_ELEMENT:
                wczytywanie();
                saveDataSqlLight();

                break;
            case PIATY_ELEMENT:
                wczytywanie();
                saveDataSqlLigtUpdate();
                break;

            default:


        }
        return true;
    }
//TODO dopracowac spiner oraz jego wyglad

}
