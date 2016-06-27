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

    Button ok, cancel;
    EditText narzut, vat, cena_bez, cena_z;
    ListView dania, skladniki;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    private static final String url = "jdbc:mysql://192.168.1.101:3306/restalracja1234";
    private static final String user = "michal";
    private static final String pass = "kaseta12";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    CustomAdapter5 adapter2;
    customAdapter1 adapter1;

    Double wynik1, wynik2, wynik3, wynik4, cena1, cena2, cena3;
    Integer procent1, procent2;

    String[] produkty = {"Magazyn", "Lodowka", "Brak_kategori", "Mroznia"};
    String[] Kategorie = {"Zupy", "Makarony", "Przystawki", "Ryba", "Salatki", "Fast_Food", "Pizza", "Suszi", "Wina", "Piwo", "Desery", "Dodatki", "Napoje_Gazowane", "Napoje_Zimne", "Napoje_Gorace", "Soki"};
    private static  String[] ilosc = new String[30];
    private static  String[] stan1 = new String[30];
    private static  String[] Nazwa = new String[30];
    private static  String[] Skladniki = new String[30];
    private static  String[] Zdjęcie = new String[30];
    private static  String[] Produkt = new String[30];
    private static  String[] miarka = new String[30];
    private static  String[] Nazwa_produktu = new String[30];
    private static  String[] kategoria = new String[30];
    private static  String[] cena_detaliczna = new String[30];
    private static  String[] ilosc_detaliczna = new String[30];
    private static  String[] wyswietlanie = new String[30];
    private static  String[] zm = new String[20];
    private static  String[] zm1 = new String[20];

    ResultSet resultSet;
    FileOutputStream fos;

    Bundle applesData;
    String s, m, k, W;

    String cena_d, kat;
    int x, q, y, c, z,p,n;
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();

    }

    private void readsqlLigtData() {
        x = 0;
        y = 0;

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        try {
            for (int i = 0; i < 16; i = i + 0) {
                Cursor c = sampleDB.rawQuery("SELECT * FROM '" + Kategorie[i] + "'", null);

                while (c.moveToNext()) {
                    String zm = String.valueOf(c.getString(0));
                    if (zm != null) {
                        Nazwa[x] = String.valueOf(c.getString(0));
                        Skladniki[x] = String.valueOf(c.getString(1));
                        Zdjęcie[x] = String.valueOf(c.getString(4));
                        x++;
                    }
                }
                i++;
            }
            sampleDB.close();
        } catch (Exception e) { showToast(""+e);

        }


        SQLiteDatabase sampleDB1 = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try {
            for (int i = 0; i < 4; i = i + 0) {
                Cursor d = sampleDB1.rawQuery("SELECT * FROM '" + produkty[i] + "'", null);

                while (d.moveToNext()) {
                    String zm = String.valueOf(d.getString(0));
                    if (zm != null) {
                        Nazwa_produktu[y] = String.valueOf(d.getString(0));
                        kategoria[y] = String.valueOf(d.getString(2));
                        cena_detaliczna[y] = String.valueOf(d.getString(6));
                        ilosc_detaliczna[y] = String.valueOf(d.getString(7));
                        y++;
                    }
                }
                i++;
            }

            sampleDB1.close();
        } catch (Exception e) {showToast(""+e);}

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
               // e1.printStackTrace();
            }

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
                               // e.printStackTrace();
                            }
                            byte[] buffer = new byte[1];
                            InputStream is = rs.getBinaryStream("Zdjecie");
                            try {
                                while (is.read(buffer) > 0) {
                                    try {
                                        fos.write(buffer);
                                    } catch (IOException e) {
                                       // e.printStackTrace();
                                    }
                                }
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                   // e.printStackTrace();
                                }
                            } catch (IOException e) {
                               // e.printStackTrace();
                            }

                            x++;

                        }
                    }
                } catch (SQLException e1) {
                  //  e1.printStackTrace();
                }
                i++;
            }


            for (int i = 0; i < 4; i = i + 0) {
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
                }
                i++;
            }

            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                showToast("brak połączenia z internetem");
            }

        }
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

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");

        readsqlLigtData();

       // showToast(Nazwa[0]);
        adapter1=new customAdapter1(this, Nazwa,ilosc,Zdjęcie,q,stan1);
        dania.setAdapter(adapter1);

        //showToast(Nazwa[0]+Nazwa_produktu[0]);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Koszt_potrawy.this,Magzyn.class);
                i.putExtra("sala_sprzedazy", s);
                i.putExtra("wszystko", W);
                i.putExtra("magazyn", m);
                i.putExtra("kuchnia", k);
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
                try {
                    //czyszczenie listy
                    for (int b = 0; b < c; b = b + 0) {
                        wyswietlanie[b] = "";
                        b++;
                    }
                    adapter2 = new CustomAdapter5(Koszt_potrawy.this, wyswietlanie);
                    skladniki.setAdapter(adapter2);
                  //  wyswietlanie[0]="Nazwa produktu/Ilość/Kategoria/Cena";
                    c = 0;
                    wynik1 = 0.0;
                    wynik2 = 0.0;

                    for (int j = 0; j < x; j = j + 0) {
                        if (Nazwa[i] == Nazwa[j]) {
                            Produkt = (Skladniki[i].split("[-,0-99999+]+"));
                            String filtered = Skladniki[i].replaceAll("[^0-9,]", "");
                            miarka = filtered.split(",");
                        }
                            j++;
                        }
                    p=0;
                    n=0;
                   for(int w=0;w<Produkt.length;w++)
                   {
                       if(Produkt[w]!=null){
                       zm[p]=Produkt[w];
                           p++;
                       }
                   }

                    for(int w=0;w<Nazwa_produktu.length;w++)
                    {
                        if(Nazwa_produktu[w]!=null){
                            zm1[n]=Nazwa_produktu[w];
                            n++;
                        }
                    }

                    for(int a=0;a<p;a++) {
                        for(int w=0;w<n;w++) {
                             if (zm1[w].equals(zm[a])) {
                                 cena_d = cena_detaliczna[w];
                                 kat = kategoria[w];
                                 cena1 = Double.valueOf(cena_detaliczna[w]);
                                 cena3 = Double.valueOf(miarka[a]);
                                 wynik1 = cena1 * cena3;
                                 wyswietlanie[c] = zm[a] + ",   " + miarka[a] + ",   " + kat + ",   " + cena_d;
                                 c++;
                                 wynik2 = wynik1 + wynik2;
                                 adapter2 = new CustomAdapter5(Koszt_potrawy.this, wyswietlanie);
                                 skladniki.setAdapter(adapter2);
                             }

                        }

                    }

                } catch (Exception e) {
                    showToast("Brak zdeklarowanych wszystkich składników w magazynach");
                }
            }
        });


    }
}
