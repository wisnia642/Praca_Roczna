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

    private static final String url="jdbc:mysql://192.168.1.103:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    CustomAdapter5 adapter2;
    customAdapter1 adapter1;

    Double wynik1,wynik2,wynik3,wynik4,cena1,cena2,cena3;
    Integer procent1,procent2;

    String[] produkty = {"Magazyn","Lodowka","Brak_kategori","Mroznia"};
    String[] Kategorie = {"Zupy","Makarony","Przystawki","Ryba","Salatki","Fast_Food","Pizza","Suszi","Wina","Piwo","Desery","Dodatki","Napoje_Gazownane","Napoje_Zimne","Napoje_Gorace","Soki"};
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

    String cena_d,kat;
    int x,q,y,c,z,w;

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
            connection = DriverManager.getConnection(url ,user, pass);
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
                        String zm = rs.getString("Nazwa");
                        if (zm != null) {
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
                        InputStream is = rs.getBinaryStream("Zdjecie");
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

                        x++;

                    }}
                } catch (SQLException e1) {
                    e1.printStackTrace();
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
                }
                i++;
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

        readsqlLigtData();
        if(Nazwa[0]==null)
       try{
         wczytywanie();
       }
        catch(Exception e){}


        adapter1=new customAdapter1(this, Nazwa,ilosc,Zdjęcie,q,stan1);
        dania.setAdapter(adapter1);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Koszt_potrawy.this,Magzyn.class);
                startActivity(i);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (narzut.getText().toString() != null) {
                        procent1 = Integer.valueOf(narzut.getText().toString());

                        wynik1 = wynik2 * procent1 / 100;
                        wynik3 = wynik1;
                    }
                    if (vat.getText().toString() != null) {
                        procent2 = Integer.valueOf(vat.getText().toString());

                        wynik1 = wynik2 * procent2 / 100;
                        wynik4 = wynik1;
                    }
                    wynik1 = wynik2 + wynik3 + wynik4;
                    wynik1 *= 100;

                    wynik1 = Double.valueOf(Math.round(wynik1));
                    wynik1 /= 100;
                    cena_z.setText(String.valueOf(wynik1));
                    wynik2 *= 100;
                    wynik2 = Double.valueOf(Math.round(wynik2));
                    wynik2 /= 100;
                    cena_bez.setText(String.valueOf(wynik2));
                }catch (Exception e){showToast("Błąd w obliczeniach");}

            }
        });

        dania.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(int b=0;b<c;b=b+0)
                {
                    wyswietlanie[b]="";
                    b++;
                }
                c=0;
                wynik1=0.0;
                wynik2=0.0;

                for(int j=0;j<x;j=j+0)
                {
                    if(Nazwa[i]==Nazwa[j])
                    {
                        Produkt = (Skladniki[j].split("[-,0-99999+]+"));;
                        String filtered = Skladniki[j].replaceAll("[^0-9,]", "");
                        miarka = filtered.split(",");

                        for(int a=0;a<Produkt.length;a=a+0)
                        {
                            if(Produkt[a]!=null) {
                                for(w=0;w<Nazwa_produktu.length;w=w+0)
                                {

                                    if(Nazwa_produktu[w].equals(Produkt[a]))
                                    {

                                        cena_d = cena_detaliczna[w];
                                        kat = kategoria[w];
                                        cena1 = Double.valueOf(cena_detaliczna[w]);
                                        cena3 = Double.valueOf(miarka[a]);
                                        wynik1 = cena1*cena3;
                                    }
                                    w++;
                                }
                                    wyswietlanie[c] = Produkt[a]+" " +miarka[a]+" "+kat+" "+cena_d;
                                    c++;
                                    wynik2=wynik1+wynik2;
                            }
                            a++;
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
