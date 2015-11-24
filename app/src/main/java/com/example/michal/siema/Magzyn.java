package com.example.michal.siema;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Magzyn extends ActionBarActivity {

    Button wylacz,przerwa,lodowka,mroznia,magazyn,brak_kategorii,przychody,koszty_potrawy,potrawy_wykonane,
    lista_zakupow,odswierz,pokaz;
    ListView lista;

    Bundle applesData;
    String s,m,k,W,u,jak;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    String[] Data = new String[40];
    String[] Czas = new String[40];
    String[] Nazwa = new String[40];
    String[] Ilosc = new String[40];
    String[] Czas_wykonania = new String[40];
    String[] Kto_wykonal = new String[40];
    String[] Data1 = new String[40];
    String[] czas1 = new String[40];
    String[] nazwa1 = new String[40];
    String[] ilosc1 = new String[40];
    String[] czas_wykonania1 = new String[40];
    String[] kto_wykonal1 = new String[40];

    String[] uzytkonkik = new String[15];
    String[] haslo = new String[15];
    String[] kuchnia = new String[15];
    String[] magazyn1 = new String[15];
    String[] sala_sprzedazy = new String[15];
    String[] wszystko = new String[15];

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    EditText data1,data2;
    Calendar myCalendar;
    private SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener date;

    String data,dat2,data11,data22;
    String hash1,textboks;
    int x=0,y=0,spr,has;
    boolean wartosc = false;
    private PopupWindow mpopup;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(y==1) {
            data1.setText(sdf.format(myCalendar.getTime()));
            data = (sdf.format(myCalendar.getTime()));
        }
        if (y==2)
        {
            data2.setText(sdf.format(myCalendar.getTime()));
            data = (sdf.format(myCalendar.getTime()));
        }
    }

    private void readsqlLigtData()
    {
        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select  Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal from Wykonane where Data BETWEEN '" + data11 + "' AND '" + data22 + "'", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(2));
                if(zm!=null){
                    Data1[x] = String.valueOf(c.getString(0));
                    czas1[x] = String.valueOf(c.getString(1));
                    nazwa1[x] = String.valueOf(c.getString(2));
                    ilosc1[x] = String.valueOf(c.getString(3));
                    czas_wykonania1[x] = String.valueOf(c.getString(4));
                    kto_wykonal1[x] = String.valueOf(c.getString(5));
                    x++;}
            }
            sampleDB.close();
        }
        catch (Exception e)
        {

        }
        CustomAdapter7 adapter1 = new CustomAdapter7(this,Data1,czas1,nazwa1,ilosc1,czas_wykonania1,kto_wykonal1);
        lista.setAdapter(adapter1);

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
            connection = DriverManager.getConnection(url ,user,pass);
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

            String sql = "SELECT * FROM Wykonane";

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
                    String zm = rs.getString("Data");
                    if (zm != null) {
                        Data[x] = rs.getString("Data");
                        Czas[x] = rs.getString("Czas");
                        Nazwa[x] = rs.getString("Nazwa");
                        Ilosc[x] = rs.getString("Ilosc");
                        Czas_wykonania[x] = rs.getString("Czas_wykonania");
                        Kto_wykonal[x] = rs.getString("Kto_wykonal");
                        x++;
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

    //wczytywanie danych z tablicy do bazy danych
    public void wczytywanie2() {

        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sql = ("select  Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal from Wykonane where Data BETWEEN '" + data11 + "' AND '" + data22 + "'");

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
                    String  zm = rs.getString("Data");
                    if(zm!=null){
                    Data1[x] = rs.getString("Data");
                    czas1[x] = rs.getString("Czas");
                    nazwa1[x] = rs.getString("Nazwa");
                    ilosc1[x] = rs.getString("Ilosc");
                    czas_wykonania1[x] = rs.getString("Czas_wykonania");
                    kto_wykonal1[x] = rs.getString("Kto_wykonal");
                    x++;}

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
        CustomAdapter7 adapter1 = new CustomAdapter7(this,Data1,czas1,nazwa1,ilosc1,czas_wykonania1,kto_wykonal1);
        lista.setAdapter(adapter1);

    }

    private void readsqlLight() {

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try {
            Cursor c  = sampleDB.rawQuery("SELECT * FROM Wykonane",null);

            while (c.moveToNext()) {
              String  zm = String.valueOf(c.getString(0));
                if(zm!=null){
                    Data[x] = String.valueOf(c.getString(0));
                    Czas[x] = String.valueOf(c.getString(1));
                    Nazwa[x] = String.valueOf(c.getString(2));
                    Ilosc[x] = String.valueOf(c.getString(3));
                    Czas_wykonania[x] = String.valueOf(c.getString(4));
                    Kto_wykonal[x] = String.valueOf(c.getString(5));
                    x++;}
            }
            sampleDB.close();

        } catch (Exception e){
        }
    }

    private void readsqlLigt()
    {spr=0;

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from logowanie", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    uzytkonkik[spr] = String.valueOf(c.getString(1));
                    haslo[spr] = String.valueOf(c.getString(2));
                    sala_sprzedazy[spr] = String.valueOf(c.getString(3));
                    magazyn1[spr] = String.valueOf(c.getString(4));
                    kuchnia[spr] = String.valueOf(c.getString(5));
                    wszystko[spr] = String.valueOf(c.getString(6));
                    spr++;}
            }
            sampleDB.close();
        }
        catch (Exception e)
        {

        }

    }

    public void wczytywanie1() {
        spr=0;
        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sql = ("select * from logowanie");

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
                        uzytkonkik[spr] = rs.getString("Uzytkownik");
                        haslo[spr] = rs.getString("Haslo");
                        sala_sprzedazy[spr] = rs.getString("Sala_sprzedazy");
                        magazyn1[spr] = rs.getString("Magazyn");
                        kuchnia[spr] = rs.getString("Kuchnia");
                        wszystko[spr] = rs.getString("Wszystko");
                        spr++;}

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

    private void Hash()
    {
        try {
            hash1 = "%032x440472108104"+String.valueOf(textboks.hashCode());

        }
        catch (Exception e){showToast(""+e);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magzyn);

        wylacz = (Button) findViewById(R.id.button5);
        przerwa = (Button) findViewById(R.id.button34);
        odswierz = (Button) findViewById(R.id.button35);
        lodowka = (Button) findViewById(R.id.button38);
        mroznia = (Button) findViewById(R.id.button39);
        magazyn = (Button) findViewById(R.id.button47);
        brak_kategorii = (Button) findViewById(R.id.button48);
        przychody = (Button) findViewById(R.id.button59);
        koszty_potrawy = (Button) findViewById(R.id.button49);
        potrawy_wykonane = (Button) findViewById(R.id.button50);
        lista_zakupow = (Button) findViewById(R.id.button62);
        lista = (ListView) findViewById(R.id.listView5);
        pokaz = (Button) findViewById(R.id.button63);
        myCalendar = Calendar.getInstance();
        data1 = (EditText) findViewById(R.id.editText14);
        data2 = (EditText) findViewById(R.id.editText15);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");
        if (applesData != null) {
            u = applesData.getString("uzytkownik");
        }

        readsqlLigt();
        if(uzytkonkik[0]==null)
        {
            wczytywanie1();
        }

        readsqlLight();
         if(Nazwa[0]==null) {
            wczytywanie();
            wartosc = true;
        }

        final CustomAdapter7  adapter1 = new CustomAdapter7(this,Data, Czas, Nazwa,Ilosc,Czas_wykonania,Kto_wykonal);
       lista.setAdapter(adapter1);

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
                            for (int i = 0; i < uzytkonkik.length; i = i + 0) {
                                if (hash1.equals(haslo[i])) {
                                    mpopup.dismiss();
                                    has = 1;
                                } else {

                                }
                                i++;
                            }
                            if (has != 1) {
                                showToast("błędne hasło");
                            }
                        } catch (Exception e) {
                        }

                    }
                });
            }
        });


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Magzyn.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                y=1;
            }
        });

        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Magzyn.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                y=2;
            }
        });

        pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<x;i=i+0)
                {
                    Data1[i]=null;
                    czas1[i]=null;
                    nazwa1[i]=null;
                    ilosc1[i]=null;
                    czas_wykonania1[i]=null;
                    kto_wykonal1[i]=null;
                    i++;
                }

                data11 = data1.getText().toString();
                data22 = data2.getText().toString();


                if(wartosc==false) {
                    readsqlLigtData();
                }
                if (wartosc==true){
                    wczytywanie2();
                }
            }
        });

        wylacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Glowne_menu.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });

        odswierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

        mroznia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                 String message = "Mroznia";
                c.putExtra("wartosc", message);
                jak="true";
                c.putExtra("jak", jak);
                startActivity(c);
            }
        });

        brak_kategorii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                String message = "Brak_kategori";
                c.putExtra("wartosc", message);
                jak="true";
                c.putExtra("jak", jak);
                startActivity(c);
            }
        });

        lodowka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                String message = "Lodowka";
                c.putExtra("wartosc", message);
                jak = "true";
                c.putExtra("jak", jak);
                startActivity(c);
            }
        });

        magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Pprodukty_kategoria.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                String message = "Magazyn";
                c.putExtra("wartosc", message);
                jak="true";
                c.putExtra("jak",jak);
                startActivity(c);
            }
        });

        koszty_potrawy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Koszt_potrawy.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });

        potrawy_wykonane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Wykonane_potrawy.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });

        lista_zakupow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Lista_zakupow.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });

        przychody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Magzyn.this, Przychod.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });
    }
}
