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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
            przyjete,koniec,odswiez;
    ImageView obraz;
    TextView nazwa,skladniki,sposob_przyrzadzenia,czas;
    ListView lista;
    int[] Klik = new int[20];
    String[] Klient = new String[20];
    String[] Klient1 = new String[20];
    String[] Danie = new String[20];
    String[] Danie1 = new String[20];
    String[] Ilosc = new String[20];
    String[] Ilosc1 = new String[20];
    String[] Zdjecie = new String[20];
    String[] Zdjecie1 = new String[20];
    String[] Sposb = new String[20];
    String[] Sposb1 = new String[20];
    String[] Skladniki = new String[20];
    String[] Skladniki1 = new String[20];
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
    String[] Brak_kategori = new String[20];

    long[] startTime = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    private Handler customHandler = new Handler();
    long[] timeInMilliseconds = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    long[] timeSwapBuff = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    long[] updatedTime = {0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L};
    int mins,secs,milliseconds;

    String message,zegarek,data,czas1,gdzie_idzie,jak;
    Boolean stan1=false;
    Boolean stan2=false;
    Boolean stan3=false;
    Boolean stan4=false;
    Boolean stan5=false;
    Boolean stan6=false;

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    int x,q,poz,A,B,C,w,p;
    FileOutputStream fos;
    customAdapter4 adapter;
    Double zm;

    Bundle applesData;
    String s,m,k,W,login;
    String hash1,textboks,haslo;
    private PopupWindow mpopup;

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
                    "Stan_krytyczny VARCHAR,Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

        }
        catch (Exception e){}

    }

    private void SqlLigtKoniec() {
        ToDataBase();
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("UPDATE Zamowienie SET Stan=('true') WHERE Klient=('" + Klient1[poz] + "') AND Danie=('" + Danie1[poz] + "') ");
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

            sampleDB.execSQL("INSERT INTO Wykonane ('Data','Czas','Nazwa','Ilosc','Czas_wykonania','Kto_wykonal') VALUES ('" + data + "','" + czas1 + "','" + Danie1[poz] + "','" + Ilosc1[poz] + "','" + zegarek + "','"+login+"') ");

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

            String sql = "UPDATE Zamowienie SET Stan=('true') WHERE Klient=('" + Klient1[poz] + "') AND Danie=('" + Danie1[poz] + "') ";

            try {
                st.executeUpdate(sql);
            } catch (SQLException e1) {
                // e1.printStackTrace();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            data = sdf.format(new Date());
            SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm:ss");
            czas1 = sdf1.format(new Date());

            String sql1 = "INSERT INTO Wykonane (Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal) VALUES" +
                    " (?,?,?,?,?,?)";

            try {
                ps = connection.prepareStatement(sql1);
                ps.setString(1, data);
                ps.setString(2, czas1);
                ps.setString(3, Danie1[poz]);
                ps.setString(4, Ilosc1[poz]);
                ps.setString(5, zegarek);
                ps.setString(6, login);
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
                String zm = String.valueOf(c.getString(0));
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

            int D=0;
            Cursor e  = sampleDB.rawQuery("SELECT * FROM Brak_kategori",null);

            while (e.moveToNext()) {
                String zm = String.valueOf(e.getString(0));
                if(zm!=null){
                    Brak_kategori[D] = String.valueOf(e.getString(0));
                    D++;}
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
            connection = DriverManager.getConnection(url,user,pass);
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
                sampleDB.execSQL("UPDATE " + gdzie_idzie + " SET Ilosc=('" + zm + "'),Przynaleznosc=('" + gdzie_idzie + "')  WHERE Nazwa=('" + Skladniki_produkty[p] + "') ");
            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w magazyn");
        }

        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
            String sql1 = "UPDATE " + gdzie_idzie + " SET Ilosc=('" + zm + "'),Przynaleznosc=('" + gdzie_idzie + "')  WHERE Nazwa=('" + Skladniki_produkty[p] + "')";

            try {
                st.executeUpdate(sql1);
            } catch (SQLException e1) {
                // e1.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                showToast("brak połączenia z internetem");
            }
        }
    }

    public void UpdateSql1()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                sampleDB.execSQL("INSERT INTO Brak_kategori (Nazwa,Przynaleznosc) VALUES ('" + Skladniki_produkty[p] + "','Brak kategori') ");
            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w brak kategori");
        }

        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
        String sql1 = "INSERT INTO Brak_kategori (Nazwa,Przynaleznosc)  VALUES ('" + Skladniki_produkty[p] + "','Brak kategori') ";

            try {
                st.executeUpdate(sql1);
            } catch (SQLException e1) {
                // e1.printStackTrace();
            }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            showToast("brak połączenia z internetem");
        }
    }
    }

    private void Hash()
    {
        try {
            hash1 = "%032x440472108104"+String.valueOf(textboks.hashCode());

        }
        catch (Exception e){showToast(""+e);}
    }

    private void readsqlLigt2()
    {

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from logowanie WHERE Stan=('true')", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    login = String.valueOf(c.getString(1));
                    haslo = String.valueOf(c.getString(2));
                }
            }
            sampleDB.close();
        }
        catch (Exception e)
        {

        }

    }

    public void wczytywanie2() {
        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sql = ("select * from logowanie WHERE Stan=('true')");

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
                    String  zm = rs.getString("Uzytkownik");
                    if(zm!=null){
                        login = rs.getString("Uzytkownik");
                        haslo = rs.getString("Haslo");
                    }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuchnia);

        wylacz = (Button) findViewById(R.id.button5);
        przerwa = (Button) findViewById(R.id.button34);
        odswiez = (Button) findViewById(R.id.button35);
        dod_przepis = (Button) findViewById(R.id.button38);
        edy_przepis = (Button) findViewById(R.id.button49);
        usu_przepis = (Button) findViewById(R.id.button39);
        mroznia = (Button) findViewById(R.id.button48);
        lodowka = (Button) findViewById(R.id.button47);
        magazyn = (Button) findViewById(R.id.button50);
        brak_kategori = (Button) findViewById(R.id.button66);
        przyjete = (Button) findViewById(R.id.button40);
        koniec = (Button) findViewById(R.id.button41);

        nazwa = (TextView) findViewById(R.id.textView68);
        skladniki = (TextView) findViewById(R.id.textView47);
        sposob_przyrzadzenia = (TextView) findViewById(R.id.textView70);
        czas = (TextView) findViewById(R.id.textView76);

        lista = (ListView) findViewById(R.id.listView);
        obraz = (ImageView) findViewById(R.id.imageView5);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");

        readsqlLigt2();
        if(login==null)
        {
            wczytywanie2();
        }

        readsqlLight();
        if(Klient[0]==null) {
            wczytywanie();
        }

        int j=0;
        for(int i=0;i<Klient.length;i=i+0) {
            if (Wykonane[i]=="null") {
                Klient1[j] = Klient[i];
                Dodatki1[j] = Dodatki[i];
                Dodatkowe_zyczenia1[j]=Dodatkowe_zyczenia[i];
                Ilosc1[j]=Ilosc[i];
                Danie1[j]=Danie[i];
                Zdjecie1[j]=Zdjecie[i];
                Sposb1[j]=Sposb[i];
                Skladniki1[j]=Skladniki[i];
                j++;
            }i++;
        }
        adapter = new customAdapter4(this, Klient1, Dodatki1, Dodatkowe_zyczenia1, Ilosc1);
        lista.setAdapter(adapter);
        //lista.invalidateViews();

        przerwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popUpView = getLayoutInflater().inflate(R.layout.blokada, null);
                // inflating popup layout
                mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //Creation of popup
                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
                mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

                Button btnOk = (Button) popUpView.findViewById(R.id.button60);
                final EditText editT = (EditText) popUpView.findViewById(R.id.editText5);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            textboks = editT.getText().toString();
                            Hash();
                                if (hash1.equals(haslo)) {
                                    mpopup.dismiss();
                                } else { showToast("błędne hasło");}
                        } catch (Exception e) {
                        }

                    }
                });
            }
        });

        wylacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Kuchnia.this, Glowne_menu.class);
                x.putExtra("sala_sprzedazy", s);
                x.putExtra("wszystko", W);
                x.putExtra("magazyn", m);
                x.putExtra("kuchnia", k);
                startActivity(x);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Danie1[i] != null) {
                    stan6 = true;
                    poz = i;
                    q = i;
                    if (Klik[poz] == 2) {
                        czas.setVisibility(view.VISIBLE);
                    } else {
                        czas.setVisibility(view.INVISIBLE);
                    }
                    //wyswietlanie danych
                    Bitmap bmImg = BitmapFactory.decodeFile(Zdjecie1[i]);
                    obraz.setImageBitmap(bmImg);
                    if (bmImg == null & Zdjecie[i] != null) {
                        obraz.setImageDrawable(obraz.getResources().getDrawable(R.drawable.brak));
                    }
                    nazwa.setText(Danie1[i]);
                    skladniki.setText(Skladniki1[i]);
                    sposob_przyrzadzenia.setText(Sposb1[i]);
                }
            }
        });

        odswiez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

        dod_przepis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent w = new Intent(Kuchnia.this, Dodawanie.class);
               w.putExtra("sala_sprzedazy", s);
                w.putExtra("wszystko", W);
                w.putExtra("magazyn", m);
                w.putExtra("kuchnia", k);
                message = "false";
                jak="true";
                w.putExtra("jak",jak);
                w.putExtra("warunek", message);
                startActivity(w);
            }
        });

        edy_przepis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Kuchnia.this, Karta_dodawanie.class);
                i.putExtra("sala_sprzedazy", s);
                i.putExtra("wszystko", W);
                i.putExtra("magazyn", m);
                i.putExtra("kuchnia", k);
                message = "ttrue";
                i.putExtra("Sala", message);
                startActivity(i);
            }
        });

        usu_przepis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Karta_dodawanie.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                message = "true";
                c.putExtra("Sala", message);
                startActivity(c);
            }
        });

        mroznia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                message = "Mroznia";
                c.putExtra("wartosc", message);
                jak="false";
                c.putExtra("jak",jak);
                startActivity(c);
            }
        });
        lodowka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                message = "Lodowka";
                c.putExtra("wartosc", message);
                jak="false";
                c.putExtra("jak",jak);
                startActivity(c);
            }
        });
        magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                message = "Magazyn";
                c.putExtra("wartosc", message);
                jak="false";
                c.putExtra("jak", jak);
                startActivity(c);
            }
        });
        brak_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Kuchnia.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                message = "Brak_kategori";
                c.putExtra("wartosc", message);
                jak="false";
                c.putExtra("jak",jak);
                startActivity(c);
            }
        });

        przyjete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stan6==true) {
                    Klik[poz] = 2;
                    czas.setVisibility(view.VISIBLE);
                    startTime[q] = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    Wykonane[q] = "false";
                    stan5 = true;
                    stan6 = false;
                }
            }
        });


        koniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stan5==true) {
                    zegarek = ("" + mins + ":"

                            + String.format("%02d", secs) + ":"

                            + String.format("%03d", milliseconds));

                    Wykonane[q] = "true";
                    Skladniki_produkty = (Skladniki1[q].split("[-,0-99999+]+"));
                    String filtered = Skladniki1[q].replaceAll("[^0-9,]", "");
                    Skladniki_porcje = filtered.split(",");

                    for (p = 0; p < Skladniki_produkty.length; p = p + 0) {
                        if (Skladniki_produkty[p] != null) {
                            for (int j = 0; j < Lodowka.length; j = j + 0) {
                                if (Skladniki_produkty[p].equals(Lodowka[j])) {
                                    Double zm1;
                                    Double zm2;
                                    zm1 = Double.parseDouble(Skladniki_porcje[p]);
                                    zm2 = Double.parseDouble(Lodówka_ilosc[j]);
                                    zm = zm2 - zm1;
                                    gdzie_idzie = "Lodowka";
                                    stan1 = true;
                                    zm1 = Double.valueOf(Lodówka_stan_krytyczny[j]);
                                    if (zm1 < zm) {
                                        showToast("Stan krytyczny " + Skladniki_produkty[p]);
                                    }
                                    UpdateSql();
                                }

                                if (Skladniki_produkty[p].equals(Magazyn[j])) {
                                    Double zm1;
                                    Double zm2;
                                    zm1 = Double.parseDouble(Skladniki_porcje[p]);
                                    zm2 = Double.parseDouble(Magazyn_ilosc[j]);
                                    zm = zm2 - zm1;
                                    gdzie_idzie = "Magazyn";
                                    stan2 = true;
                                    zm1 = Double.valueOf(Magazyn_stan_krytyczny[j]);
                                    if (zm1 < zm) {
                                        showToast("Stan krytyczny " + Skladniki_produkty[p]);
                                    }
                                    UpdateSql();

                                }

                                if (Skladniki_produkty[p].equals(Mroznia[j])) {
                                    Double zm1;
                                    Double zm2;
                                    zm1 = Double.parseDouble(Skladniki_porcje[p]);
                                    zm2 = Double.parseDouble(Mroznia_ilosc[j]);
                                    zm = zm2 - zm1;
                                    stan3 = true;
                                    gdzie_idzie = "Mroznia";
                                    zm1 = Double.valueOf(Mroznia_stan_krytyczny[j]);
                                    if (zm1 < zm) {
                                        showToast("Stan krytyczny " + Skladniki_produkty[p]);
                                    }
                                    UpdateSql();
                                }

                                j++;
                            }
                            if (stan1 == false & stan2 == false & stan3 == false & stan4 == false) {
                                UpdateSql1();
                            }
                        }
                        p++;
                    }
                    stan5=false;
                    SqlLigtKoniec();
                    finish();
                    startActivity(getIntent());

                }

            }

        });

    }
}
