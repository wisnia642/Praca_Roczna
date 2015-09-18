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

public class Kuchnia extends ActionBarActivity {

    Button wylacz,przerwa,dod_przepis,edy_przepis,usu_przepis,mroznia,lodowka,magazyn,
            przyjete,koniec;
    ImageView obraz;
    TextView nazwa,skladniki,sposob_przyrzadzenia,czas;
    ListView lista;
    int[] Klik = new int[20];
    String[] Klient = new String[20];
    String[] Danie = new String[20];
    String[] Ilosc = new String[20];
    String[] Zdjecie = new String[20];
    String[] Sposb = new String[20];
    String[] Skladniki = new String[20];
    String[] Dodatki = new String[20];
    String[] Dodatkowe_zyczenia= new String[20];
    Boolean[] Wykonane = new Boolean[20];

    long[] startTime = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    private Handler customHandler = new Handler();
    long[] timeInMilliseconds = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    long[] timeSwapBuff = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    long[] updatedTime = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    int mins,secs,milliseconds;

    String message,zegarek;
    int x,q,poz;
    FileOutputStream fos;
    customAdapter4 adapter;

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
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS WYKONANE (Data VARCHAR,Czas VARCHAR,Nazwa VARCHAR," +
                    "Ilosc VARCHAR,Czas_wykonania VARCHAR,Kto_wykonal VARCHAR);");

        }
        catch (Exception e){}

    }

    private void readsqlLight() {

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            Cursor c  = sampleDB.rawQuery("SELECT * FROM ZAMOWIENIE",null);

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
                    x++;}
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
        przyjete = (Button) findViewById(R.id.button40);
        koniec = (Button) findViewById(R.id.button41);

        nazwa = (TextView) findViewById(R.id.textView68);
        skladniki = (TextView) findViewById(R.id.textView47);
        sposob_przyrzadzenia = (TextView) findViewById(R.id.textView70);
        czas = (TextView) findViewById(R.id.textView76);

        lista = (ListView) findViewById(R.id.listView);
        obraz = (ImageView) findViewById(R.id.imageView5);


        //  wczytywanie();
        readsqlLight();

        adapter = new customAdapter4(this, Klient, Dodatki, Dodatkowe_zyczenia, Ilosc);
        lista.setAdapter(adapter);

        wylacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Kuchnia.this, MainActivity.class);
                startActivity(x);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(Danie[i]!=null) {
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

        przyjete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Klik[poz]=2;
                czas.setVisibility(view.VISIBLE);
                startTime[q] = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                Wykonane[q]=false;
            }
        });

        koniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zegarek=("" + mins + ":"

                        + String.format("%02d", secs) + ":"

                        + String.format("%03d", milliseconds));

                Wykonane[q]=true;
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
