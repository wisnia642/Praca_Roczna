package com.example.michal.siema;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class Koszt_potrawy extends ActionBarActivity {

    Button ok,cancel;
    EditText narzut,vat,cena_bez,cena_z;
    ListView dania,skladniki;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    CustomAdapter5 adapter2;
    customAdapter1 adapter1;


    String[] produkty = {"Magazyn","Lodowka","Brak_kategori","Mroznia"};
    String[] Kategorie = {"Zupy","Makarony","Przystawki","Ryba","Salatki","Fast_Food","Pizza","Suszi","Wina","Piwo","Desery","Dodatki, " +
                    "Napoje_Gazownane","Napoje_Zimne","Napoje_Gorace","Soki"};
    String[] ilosc = new String[30];
    String[] stan1 = new String[30];
    String[] Nazwa= new String[30];
    String[] Skladniki = new String[30];
    String[] Zdjęcie = new String[30];
    String[] Produkt = new String[30];
    String[] miarka = new String[30];
    String[] Nazwa_produktu = new String[30];
    String[] kategoria = new String[30];
    String[] cena_detaliczna = new String[30];
    String[] ilosc_detaliczna =new String[30];
    String[] wyswietlanie = new String[30];

    ResultSet resultSet;
    FileOutputStream fos;

    int x,q,y,c;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void readsqlLigtData()
    {
        for (int i=0;i<16;i=i+0) {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            try {
                Cursor c = sampleDB.rawQuery("SELECT * FROM "+Kategorie[i]+"", null);

                while (c.moveToNext()) {
                    String zm = String.valueOf(c.getString(0));
                    if (zm != null) {
                        Nazwa[x] = String.valueOf(c.getString(0));
                        Skladniki[x] = String.valueOf(c.getString(1));
                        Zdjęcie[x] = String.valueOf(c.getString(2));
                        x++;
                    }
                }
                sampleDB.close();
            } catch (Exception e) {
                showToast("dupa");
            }
            i++;
        }
       // Lodowka.Nazwa , Lodowka.Kategoria , Lodowka.Cena_detaliczna ,Lodowka.ilosc_detaliczna , Magazyn.Nazwa , Magazyn.Kategoria , Magazyn.Cena_detaliczna Magazyn.ilosc_detaliczna FROM Lodowka INNER JOIN Magazyn ON Lodowka.commonfield=Magazyn.commonfield

        for (int i=0;i<4;i=i+0) {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        try {
            Cursor c = sampleDB.rawQuery("SELECT * FROM "+produkty[i]+"", null);

            while (c.moveToNext()) {
                String zm = String.valueOf(c.getString(0));
                if (zm != null) {
                    Nazwa_produktu[y] = String.valueOf(c.getString(0));
                    kategoria[y] = String.valueOf(c.getString(2));
                    cena_detaliczna[y] = String.valueOf(c.getString(6));
                    ilosc_detaliczna[y] = String.valueOf(c.getString(7));
                    y++;
                }
            }
            sampleDB.close();
        } catch (Exception e) {
            showToast("dupa");
        }
        i++;
    }
    }

    //tworzenie polaczenia z baza danych
    public void connect()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            return;
        }


        try {
            connection = DriverManager.getConnection("jdbc:mysql://85.10.205.173/restalracja1234", "michal3898", "kaseta12");
        } catch (SQLException e) {
            showToast("brak polaczenia z internetem");
            return;
        }

    }

    //wczytywanie danych z tablicy do bazy danych
    public void wczytywanie() {

        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
/*
            String sql = "SELECT Zupy.Nazwa,Zupy.Skladniki,Zupy.Zdjecie,Makarony.Nazwa,Makarony.Skladniki,Makarony.Zdjecie,Przystawki.Nazwa,Przystwaki.Skladniki,Przystawki.Zdjecie,"+
                    "Ryba.Nazwa,Ryba.Skladniki,Ryba.Zdjecie,Fast_Food.Nazwa,Fast_Food.Skladniki,Fast_Food.Zdjecie,Pizza.Nazwa,Pizza.Skladniki,Pizza.Zdjecie," +
                    "Suszi.Nazwa,Suszi.Skladniki,Suszi.Zdjecie,Wina.Nazwa,Wina.Skladniki,Wina.Zdjecie,Piwo.Nazwa,Piwo.Skladniki,Piwo.Zdjecie,Desery.Nazwa,Desery.Skladniki,Desery.Zdjecie," +
                    "Dodatki.Nazwa,Dodatki.Skladniki,Dodatki.Zdjecie,Napoje_Gazownane.Nazwa,Napoje_Gazownane.Skladniki,Napoje_Gazownane.Zdjecie,Napoje_Zimne.Nazwa,Napoje_Zimne.Skladniki,Napoje_Zimne.Zdjecie," +
                    "Napoje_Gorace.Nazwa,Napoje_Gorace.Skladniki,Napoje_Gorace.Zdjecie,Soki.Nazwa,Soki.Skladniki,Soki.Zdjecie FROM Zupy inner join Makarony inner join Przystawki inner join Ryba inner join Fast_Food inner join " +
                    "Pizza inner join Suszi inner join Wina inner join Piwo inner join Desery inner join Dodatki inner join Napoje_Gazownane inner join Napoje_Zimne inner join Napoje_Gorace inner join Soki";
*/
            for (int i = 0; i < 16; i = i + 0) {
                String sql = "SELECT * FROM " + Kategorie[i] + "";

                try {
                    rs = st.executeQuery(sql);
                } catch (SQLException e1) {
                    //  e1.printStackTrace();
                }
                try {
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        if (Nazwa[x] != null) {
                            Nazwa[x] = rs.getString("Nazwa");
                            Skladniki[x] = rs.getString("Skladniki");
                            File image = new File("/mnt/sdcard/" + Nazwa[x] + ".jpg");
                            Zdjęcie[x] = "/mnt/sdcard/" + Nazwa[x] + ".jpg";
                            try {
                                fos = new FileOutputStream(image);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            byte[] buffer = new byte[1];
                            InputStream is = resultSet.getBinaryStream(5);
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
                        }
                        x++;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }i++;
            }
        }
        for (int i=0;i<4;i=i+0) {
            String sql = "SELECT * FROM " + produkty[i] + "";

            try {
                rs = st.executeQuery(sql);
            } catch (SQLException e1) {
                //  e1.printStackTrace();
            }
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    Nazwa_produktu[y] = rs.getString("Nazwa");
                    kategoria[y] = rs.getString("Kategoria");
                    cena_detaliczna[y] = rs.getString("Cena_detaliczna");
                    ilosc_detaliczna[y] = rs.getString("Ilosc_detaliczna");
                    y++;

                }
            } catch (SQLException e1) {
                e1.printStackTrace();


                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException se) {
                    showToast("brak połączenia z internetem");
                }i++;
            }
        }}

    public void lista()
    {
         adapter2 = new CustomAdapter5(this, wyswietlanie);
         skladniki.setAdapter(adapter2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koszt_potrawy);

        ok = (Button) findViewById(R.id.button64);
        cancel = (Button) findViewById(R.id.button65);
        narzut = (EditText) findViewById(R.id.editText18);
        vat = (EditText) findViewById(R.id.editText19);
        cena_bez = (EditText) findViewById(R.id.editText20);
        cena_z = (EditText) findViewById(R.id.editText21);
        dania = (ListView) findViewById(R.id.listView6);
        skladniki = (ListView) findViewById(R.id.listView7);

        //readsqlLigtData();
       // if(Nazwa[0]!=null)
       // {
         wczytywanie();
        //}


       // adapter1=new customAdapter1(this, Nazwa,ilosc,Zdjęcie,q,stan1);
       // skladniki.setAdapter(adapter1);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(String.valueOf(y));
                showToast(String.valueOf(x));

            }
        });

        dania.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                for(int j=0;j<x;j=j+0)
                {
                    if(Nazwa[i]==Nazwa[j])
                    {
                        Produkt = (Skladniki[j].split("[-,0-99999+]+"));;
                        String filtered = Skladniki[j].replaceAll("[^0-9,]", "");
                        miarka = filtered.split(",");

                        for(int z=0;Produkt[z]!=null;z=z+0)
                        {
                            for(int w=0;Nazwa_produktu!=null;w=w+0) {
                                if (Produkt[z] == Nazwa_produktu[w])
                                {
                                    wyswietlanie[c] = Nazwa_produktu[w]+" " +miarka[z]+" "+cena_detaliczna[w];
                                    c++;
                                }
                                w++;
                            }
                            z++;
                        }
                    }
                    j++;
                }

                lista();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_koszt_potrawy, menu);
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
