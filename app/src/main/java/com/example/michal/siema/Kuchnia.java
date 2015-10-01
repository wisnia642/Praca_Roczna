package com.example.michal.siema;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Kuchnia extends ActionBarActivity {

    Button wylacz,przerwa,dod_przepis,edy_przepis,usu_przepis,mroznia,lodowka,magazyn,brak_kategori,
            przyjete,koniec;
    ImageView obraz;
    TextView nazwa,skladniki,sposob_przyrzadzenia,czas;
    ListView lista;
    int[] Klik = new int[20];
    String[] Klient = new String[20];
    String[] Klient1 = new String[20];
    String[] Danie = new String[20];
    String[] Ilosc = new String[20];
    String[] Ilosc1 = new String[20];
    String[] Zdjecie = new String[20];
    String[] Sposb = new String[20];
    String[] Skladniki = new String[20];
    String[] Skladniki_produkty = new String[20];
    String[] Skladniki_porcje = new String[20];
    String[] Dodatki = new String[20];
    String[] Dodatki1 = new String[20];
    String[] Dodatkowe_zyczenia= new String[20];
    String[] Dodatkowe_zyczenia1= new String[20];
    String[] Lodowka= new String[40];
    String[] Lodówka_stan_krytyczny = new String[40];
    String[] Lodówka_ilosc = new String[40];
    String[] Mroznia= new String[40];
    String[] Mroznia_ilosc = new String[40];
    String[] Mroznia_stan_krytyczny = new String[40];
    String[] Magazyn= new String[40];
    String[] Magazyn_ilosc = new String[40];
    String[] Magazyn_stan_krytyczny = new String[40];
    String[] Wykonane = new String[20];

    long[] startTime = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    private Handler customHandler = new Handler();
    long[] timeInMilliseconds = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    long[] timeSwapBuff = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    long[] updatedTime = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    int mins,secs,milliseconds;

    String message,zegarek,data,czas1,gdzie_idzie;
    Boolean stan1=false;
    Boolean stan2=false;
    Boolean stan3=false;
    Boolean komunikat=false;

    int x,q,poz,A,B,C,w,p;
    FileOutputStream fos;
    customAdapter4 adapter;
    Double zm;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void ToDataBase()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Wykonane (Data VARCHAR,Czas VARCHAR,Nazwa VARCHAR," +
                    "Ilosc VARCHAR,Czas_wykonania VARCHAR,Kto_wykonal VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Lodowka (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR,Stan_krytyczny VARCHAR," +
                    "Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Mroznia (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR," +
                    "Stan_krytyczny VARCHAR,Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Magazyn (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR," +
                    "Stan_krytyczny VARCHAR,Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Brak_kategori (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR," +
                    "Stan_krytyczny VARCHAR,Przynaleznosc VARCHAR);");

        }
        catch (Exception e){}

    }

    private void SqlLigtKoniec() {
        ToDataBase();
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("UPDATE Zamowienie SET Stan=('" + Wykonane[q] + "') WHERE Klient=('" + Klient[q] + "') AND Danie=('" + Danie[q] + "') ");

            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            data = sdf.format(new Date());
            SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm:ss");
            czas1 = sdf1.format(new Date());

            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("INSERT INTO Wykonane (Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal) VALUES ('" + data + "','" + czas1 + "','" + Danie[q] + "','" + Ilosc[q] + "','" + zegarek + "','"+"Kucharz"+"') ");

            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }

        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }

            String sql = "UPDATE Zamowienie SET Stan=('" + Wykonane[q] + "') WHERE Klient=('" + Klient[q] + "') AND Danie=('" + Danie[q] + "') ";

            try {
                st.executeUpdate(sql);
            } catch (SQLException e1) {
                // e1.printStackTrace();
            }

            String sql1 = "INSERT INTO Wykonane (Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal) VALUES" +
                    " (?,?,?,?,?,?)";

            try {
                ps = connection.prepareStatement(sql1);
                ps.setString(1, data);
                ps.setString(2, czas1);
                ps.setString(3, Danie[q]);
                ps.setString(4, Ilosc[q]);
                ps.setString(5, zegarek);
                ps.setString(6, "Kucharz");
                ps.executeUpdate();
                connection.commit();

            } catch (SQLException e) {

            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                showToast("brak połączenia z internetem");
            }
        }
    }

    private void readsqlLight() {
            ToDataBase();
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            Cursor c  = sampleDB.rawQuery("SELECT * FROM ZAMOWIENIE", null);

            while (c.moveToNext()) {
                String zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    Klient[x] = String.valueOf(c.getString(0));
                    Danie[x] = String.valueOf(c.getString(1));
                    Ilosc[x] = String.valueOf(c.getString(2));
                    Dodatki[x] = String.valueOf(c.getString(3));
                    Dodatkowe_zyczenia[x] = String.valueOf(c.getString(4));
                    Zdjecie[x] = String.valueOf(c.getString(5));
                    Sposb[x] = String.valueOf(c.getString(7));
                    Skladniki[x] = String.valueOf(c.getString(8));
                    Wykonane[x] = String.valueOf(c.getString(9));
                    x++;}
            }

            A=0;
            Cursor a  = sampleDB.rawQuery("SELECT * FROM Lodowka",null);

            while (a.moveToNext()) {
                String zm = String.valueOf(a.getString(0));
                if(zm!=null){
                    Lodowka[A] = String.valueOf(a.getString(0));
                    Lodówka_ilosc[A] = String.valueOf(a.getString(1));
                    Lodówka_stan_krytyczny[A] = String.valueOf(a.getString(3));
                    A++;}
            }

             C=0;
            Cursor b  = sampleDB.rawQuery("SELECT * FROM Magazyn",null);

            while (b.moveToNext()) {
                String zm = String.valueOf(b.getString(0));
                if(zm!=null){
                    Magazyn[C] = String.valueOf(b.getString(0));
                    Magazyn_ilosc[C] = String.valueOf(b.getString(1));
                    Magazyn_stan_krytyczny[C] = String.valueOf(b.getString(3));
                    C++;}
            }

              B=0;
            Cursor d  = sampleDB.rawQuery("SELECT * FROM Mroznia",null);

            while (d.moveToNext()) {
                String zm = String.valueOf(d.getString(0));
                if(zm!=null){
                    Mroznia[B] = String.valueOf(d.getString(0));
                    Mroznia_ilosc[B] = String.valueOf(d.getString(1));
                    Mroznia_stan_krytyczny[B] = String.valueOf(d.getString(3));
                    B++;}
            }

            sampleDB.close();
        } catch (Exception a) {
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

            String sql = "SELECT * FROM Zamowienie";

            try {
                rs=st.executeQuery(sql);
            } catch (SQLException e1) {
                //  e1.printStackTrace();
            }
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                rs = stmt.executeQuery();

                while (rs.next())
                {
                    Klient[x] = rs.getString("Klient");
                    Danie[x] = rs.getString("Danie");
                    Ilosc[x] = rs.getString("Ilosc");
                    Sposb[x] = rs.getString("Sposob_przygotowania");
                    Skladniki[x] = rs.getString("Skladniki");
                    Dodatki[x] = rs.getString("Dodatki");
                    Wykonane[x] = rs.getString("Stan");
                    Dodatkowe_zyczenia[x] = rs.getString("Dodatkowe_Zyczenia");
                    File image = new File("/mnt/sdcard/"+Klient[x]+".jpg");
                    Zdjecie[x] = "/mnt/sdcard/"+Klient[x]+".jpg";
                    try {
                        fos = new FileOutputStream(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    byte[] buffer = new byte[1];
                    InputStream is = rs.getBinaryStream("Zdjecie");
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

                    x++;

                } }catch (SQLException e1)
            {
                e1.printStackTrace();
            }

            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                showToast("brak polaczenia z internetem");}

        }
    }

    Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds[q] = SystemClock.uptimeMillis() - startTime[q];

            updatedTime[q] = timeSwapBuff[q] + timeInMilliseconds[q];

            secs = (int) (updatedTime[q] / 1000);

            mins = secs / 60;

            secs = secs % 60;

            milliseconds = (int) (updatedTime[q] % 1000);

            czas.setText("" + mins + ":"

                    + String.format("%02d", secs) + ":"

                    + String.format("%03d", milliseconds));



            customHandler.postDelayed(this, 0);

        }

    };

    public void UpdateSql()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
//poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("UPDATE "+gdzie_idzie+" SET Ilosc=('" + zm + "'),Przynaleznosc=('" + gdzie_idzie + "')  WHERE Nazwa=('" + Skladniki_produkty[p] + "') ");

            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }
    }

    public void UpdateSql1()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO Brak_kategori (Nazwa,Ilosc,Kategoria,Stan_krytyczny,Przynaleznosc,Komunikat) VALUES ('" + Skladniki_produkty[p] + "','" + Skladniki_porcje[p] + "','" + "brak" + "','" + "0" + "','" + gdzie_idzie + "','" + komunikat + "') ");


            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuchnia);

        wylacz = (Button) findViewById(R.id.button5);
        przerwa = (Button) findViewById(R.id.button34);
        dod_przepis = (Button) findViewById(R.id.button35);
        edy_przepis = (Button) findViewById(R.id.button38);
        usu_przepis = (Button) findViewById(R.id.button49);
        mroznia = (Button) findViewById(R.id.button47);
        lodowka = (Button) findViewById(R.id.button39);
        magazyn = (Button) findViewById(R.id.button48);
        brak_kategori = (Button) findViewById(R.id.button50);
        przyjete = (Button) findViewById(R.id.button40);
        koniec = (Button) findViewById(R.id.button41);

        nazwa = (TextView) findViewById(R.id.textView68);
        skladniki = (TextView) findViewById(R.id.textView47);
        sposob_przyrzadzenia = (TextView) findViewById(R.id.textView70);
        czas = (TextView) findViewById(R.id.textView76);

        lista = (ListView) findViewById(R.id.listView);
        obraz = (ImageView) findViewById(R.id.imageView5);

        readsqlLight();

        if(Klient[0]==null){
            try {
               readsqlLight();
               wczytywanie();
           } catch (Exception e) {
            }}
        int j=0;
        for(int i=0;i<Klient.length;i=i+0) {
            if (Wykonane[i]=="null") {
                Klient1[j] = Klient[i];
                Dodatki1[j] = Dodatki[i];
                Dodatkowe_zyczenia1[j]=Dodatkowe_zyczenia[i];
                Ilosc1[j]=Ilosc[i];
                j++;
            }i++;
        }
        adapter = new customAdapter4(this, Klient1, Dodatki1, Dodatkowe_zyczenia1, Ilosc1);
        lista.setAdapter(adapter);
        //lista.invalidateViews();

        wylacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Kuchnia.this, Glowne_menu.class);
                startActivity(x);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Danie[i] != null) {
                    poz = i;
                    q = i;
                    if (Klik[poz] == 2) {
                        czas.setVisibility(view.VISIBLE);
                    } else {
                        czas.setVisibility(view.INVISIBLE);
                    }
                    //wyswietlanie danych
                    Bitmap bmImg = BitmapFactory.decodeFile(Zdjecie[i]);
                    obraz.setImageBitmap(bmImg);
                    if (bmImg == null & Zdjecie[i] != null) {
                        obraz.setImageDrawable(obraz.getResources().getDrawable(R.drawable.brak));
                    }
                    nazwa.setText(Danie[i]);
                    skladniki.setText(Skladniki[i]);
                    sposob_przyrzadzenia.setText(Sposb[i]);
                }
            }
        });

        dod_przepis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent w = new Intent(Kuchnia.this, Dodawanie.class);
                message = "false";
                w.putExtra("warunek", message);
                startActivity(w);
            }
        });

        edy_przepis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Kuchnia.this, Karta_dodawanie.class);
                message = "ttrue";
                i.putExtra("Sala", message);
                startActivity(i);
            }
        });

        usu_przepis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Karta_dodawanie.class);
                message = "true";
                c.putExtra("Sala", message);
                startActivity(c);
            }
        });

        mroznia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                message = "Mroznia";
                c.putExtra("wartosc", message);
                startActivity(c);
            }
        });
        lodowka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                message = "Lodowka";
                c.putExtra("wartosc", message);
                startActivity(c);
            }
        });
        magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                message = "Magazyn";
                c.putExtra("wartosc", message);
                startActivity(c);
            }
        });
        brak_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                message = "Brak_kategori";
                c.putExtra("wartosc", message);
                startActivity(c);
            }
        });

        przyjete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Klik[poz]=2;
                czas.setVisibility(view.VISIBLE);
                startTime[q] = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                Wykonane[q]="false";
            }
        });



        koniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zegarek=("" + mins + ":"

                        + String.format("%02d", secs) + ":"

                        + String.format("%03d", milliseconds));

                Wykonane[q]="true";
                Skladniki_produkty = (Skladniki[q].split("[-,0-99999+]+"));;
                String filtered = Skladniki[q].replaceAll("[^0-9,]", "");
                Skladniki_porcje = filtered.split(",");



               for(p=0;p<Skladniki_produkty.length;p=p+0) {

                   if(Lodowka[p]!=null){
                    for(int j=0;j<Lodowka.length;j=j+0)
                    {

                        if(Skladniki_produkty[p].equals(Lodowka[j])) {
                            Double zm1;
                            Double zm2;
                            zm1= Double.parseDouble(Skladniki_porcje[p]);
                            zm2= Double.parseDouble(Lodówka_ilosc[j]);
                            zm = zm2-zm1;
                            gdzie_idzie="Lodowka";
                            stan1=true;
                            zm1= Double.valueOf(Lodówka_stan_krytyczny[j]);
                            if(zm1<zm)
                            {
                                komunikat=true;
                            }
                           UpdateSql();
                        }
                            j++;
                    }}
                   if(Magazyn[p]!=null){
                    for(int j=0;j<Magazyn.length;j=j+0)
                    {

                        if(Skladniki_produkty[p].equals(Magazyn[j])) {
                            Double zm1;
                            Double zm2;
                            zm1= Double.parseDouble(Skladniki_porcje[p]);
                            zm2= Double.parseDouble(Magazyn_ilosc[j]);
                            zm = zm1-zm2;
                            gdzie_idzie="Magazyn";
                            stan2=true;
                            zm1= Double.valueOf(Magazyn_stan_krytyczny[j]);
                            if(zm1<zm)
                            {
                                komunikat=true;
                            }
                            UpdateSql();

                        }
                            j++;
                    }}
                   if(Mroznia[p]!=null){
                    for(int j=0;j<Mroznia.length;j=j+0)
                    {
                        if(Skladniki_produkty[p].equals(Mroznia[j])) {
                            Double zm1;
                            Double zm2;
                            zm1= Double.parseDouble(Skladniki_porcje[p]);
                            zm2= Double.parseDouble(Mroznia_ilosc[j]);
                            zm = zm1-zm2;
                            stan3=true;
                            gdzie_idzie="Mroznia";
                            zm1= Double.valueOf(Mroznia_stan_krytyczny[j]);
                            if(zm1<zm)
                            {
                                komunikat=true;
                            }
                            UpdateSql();
                        }
                        j++;
                    }}

                   if (stan1 == false & stan2 == false & stan3 == false) {
                       gdzie_idzie="Brak Kategori";
                       UpdateSql1();
                  }
                   p++;
               }
                SqlLigtKoniec();
                finish();
                startActivity(getIntent());
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kuchnia, menu);
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
