package com.example.michal.siema;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.Locale;

public class Przychod extends ActionBarActivity {

    Button pokaz, oblicz, cancel;
    TextView wykonywane,przychody,wynik1,wynik2;
    EditText koszty,narzut,data1,data2;

    String[] Nazwa = new String[40];
    String[] Cena = new String[40];
    String[] Ilosc = new String[40];
    String[] Nazwa1 = new String[40];

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;
    boolean kosz=false;
    boolean narz=false;

    Bundle applesData;
    String s,m,k,w;

    String[] Kategorie = {"Zupy","Makarony","Przystawki","Ryba","Salatki","Fast_Food","Pizza",
            "Suszi","Wina","Piwo","Desery","Dodatki","Napoje_Gazowane","Napoje_Zimne","Napoje_Gorace","Soki"};


    Calendar myCalendar;
    private SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener date;

    String data,dat2,data11,data22,koszty2,procenty2;
    int x=0,y=0,z=0;
    Double Ilosc1=0.0,Ilosc2=0.0,cena1=0.0,cena2=0.0,cena3=0.0;
    Double koszty1=0.0;
    Double procenty1=0.0;
    Double cena4=0.0,wynik;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

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
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            showToast("brak polaczenia z internetem");
            return;
        }

    }

    private void readsqlLigt()
    {
        x=0;
        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from Wykonane where Data BETWEEN '" + data11 + "' AND '" + data22 + "'", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(2));
                if(zm!=null){
                    Nazwa1[x] = String.valueOf(c.getString(2));
                    Ilosc[x] = String.valueOf(c.getString(3));
                    x++;}
            }
            sampleDB.close();
        }
        catch (Exception e)
        {

        }

    }

    public void wczytywanie2() {
        x=0;
        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sql = ("select * from Wykonane where Data BETWEEN '" + data11 + "' AND '" + data22 + "'");

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
                    String  zm = rs.getString("Nazwa");
                    if(zm!=null){
                        Nazwa1[x] = rs.getString("Nazwa");
                        Ilosc[x] = rs.getString("Ilosc");
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

    }

    //wczytywanie danych z tablicy do bazy danych
    public void wczytywanie() {
z=0;
        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
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
                            Nazwa[z] = rs.getString("Nazwa");
                            Cena[z] = rs.getString("Cena");
                            z++;

                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                i++;
            }
        }
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException se) {
                    showToast("brak połączenia z internetem");
                }

            }

    private void readsqlLigtData() {
        z=0;
        for (int i = 0; i < 16; i = i + 0) {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            try {
                Cursor c = sampleDB.rawQuery("SELECT * FROM '" + Kategorie[i] + "'", null);

                while (c.moveToNext()) {
                    String zm = String.valueOf(c.getString(0));
                    if (zm != null) {
                        Nazwa[z] = String.valueOf(c.getString(0));
                        Cena[z] = String.valueOf(c.getString(5));
                        z++;
                    }
                }
                sampleDB.close();
            } catch (Exception e) {

            }
            i++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przychod);

        myCalendar = Calendar.getInstance();
        pokaz =  (Button) findViewById(R.id.button70);
        oblicz = (Button) findViewById(R.id.button71);
        cancel = (Button) findViewById(R.id.button72);
        data1 = (EditText) findViewById(R.id.editText22);
        data2 = (EditText) findViewById(R.id.editText23);
        wykonywane = (TextView) findViewById(R.id.textView123);
        przychody = (TextView) findViewById(R.id.textView125);
        koszty = (EditText) findViewById(R.id.editText24);
        narzut = (EditText) findViewById(R.id.editText25);
        wynik1 = (TextView) findViewById(R.id.textView128);
        wynik2 = (TextView) findViewById(R.id.textView129);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        w = applesData.getString("wszystko");

        try {
            readsqlLigtData();
            if (Nazwa[0] == null) {
                wczytywanie();
            }
        }catch (Exception e){}


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
                new DatePickerDialog(Przychod.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                y = 1;
            }
        });

        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Przychod.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                y=2;
            }
        });

        pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cena1 = 0.0;
                    cena2 = 0.0;
                    Ilosc1 = 0.0;
                    przychody.setText("");
                    wykonywane.setText("");
                    data11 = data1.getText().toString();
                    data22 = data2.getText().toString();

                       readsqlLigt();
                     if(Nazwa1[0]==null) {
                    wczytywanie2();
                       }
                    for (int i = 0; i < x; i = i + 0) {
                        Ilosc2 = Ilosc1 + Integer.parseInt(Ilosc[i]);
                        Ilosc1 = Ilosc2;

                        for (int j = 0; j < x; j = j + 0) {
                            if (Nazwa1[i].equals(Nazwa[j])) {
                                cena3 = Double.parseDouble(Cena[j]) * Integer.parseInt(Ilosc[i]);
                                cena1 = cena2 + cena3;
                                cena2 = cena1;
                            }
                            j++;
                        }
                        i++;
                    }

                    przychody.setText(String.valueOf(cena2));
                    wykonywane.setText(String.valueOf(Ilosc2));

                }catch (Exception e ){}
            }
        });


        oblicz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                    wynik=0.0;
                try{
                    wynik2.setText("0.0 Zł");

                    String value = narzut.getText().toString();
                    String value1 = koszty.getText().toString();

                    if (TextUtils.isEmpty(value) || TextUtils.isEmpty(value1)) {
                    }else{
                        koszty2 = koszty.getText().toString();
                        procenty2 = narzut.getText().toString();
                        cena4= Double.valueOf(cena1);
                        wynik =  cena4 - Double.parseDouble(koszty2) ;
                        koszty1= wynik*Double.parseDouble(procenty2);
                        procenty1= koszty1/ 100;
                        wynik = cena4-procenty1;
                    }

                    if (TextUtils.isEmpty(value) ) {
                        koszty2 = koszty.getText().toString();
                        cena4= Double.valueOf(cena1);
                        wynik = cena4 - Double.parseDouble(koszty2);
                    }else{
                        procenty2 = narzut.getText().toString();
                        cena4= Double.valueOf(cena1);
                        koszty1= cena4*Double.parseDouble(procenty2);
                        procenty1= koszty1/ 100;
                        wynik = cena4-procenty1;
                   }


                wynik1.setVisibility(View.VISIBLE);
                wynik2.setVisibility(View.VISIBLE);
                wynik2.setText(String.valueOf(wynik) + " Zł");
                    kosz=false;
                    narz=false;
            }catch (Exception e){showToast("blad w liczeniu");}
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Przychod.this,Magzyn.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", w);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });
    }
}
