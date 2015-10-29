package com.example.michal.siema;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    String[] Kategorie = {"Zupy","Makarony","Przystawki","Ryba","Salatki","Fast_Food","Pizza",
            "Suszi","Wina","Piwo","Desery","Dodatki","Napoje_Gazownane","Napoje_Zimne","Napoje_Gorace","Soki"};


    Calendar myCalendar;
    private SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener date;

    String data,dat2,data11,data22;
    int x=0,y=0,z=0,Ilosc1=0,Ilosc2=0,cena1=0,cena2=0;
    boolean wartosc = false;
    Double koszty1=0.0,procenty1=0.0,wynik=0.0;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
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
        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select  Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal from Wykonane where Data BETWEEN '" + data11 + "' AND '" + data22 + "'", null);

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
                            Nazwa[x] = rs.getString("Nazwa");
                            Cena[x] = rs.getString("Cena");
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
        for (int i = 0; i < 16; i = i + 0) {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            try {
                Cursor c = sampleDB.rawQuery("SELECT * FROM " + Kategorie[i] + "", null);

                while (c.moveToNext()) {
                    String zm = String.valueOf(c.getString(0));
                    if (zm != null) {
                        Nazwa[x] = String.valueOf(c.getString(0));
                        Cena[x] = String.valueOf(c.getString(4));
                        z++;
                    }
                }
                sampleDB.close();
            } catch (Exception e) {
                showToast("dupa");
            }
            i++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przychod);

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

        readsqlLigtData();
        if(Nazwa[0]!=null) {
            wczytywanie();
        }


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
                y = 2;
            }
        });

        pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readsqlLigt();
                if(Nazwa1[0]!=null) {
                    wczytywanie2();
                }

                for(int i=0;i<x;i=i+0)
                {
                    Ilosc2=Ilosc1+Integer.parseInt(Ilosc[i]);
                    Ilosc1= Ilosc2;

                    for(int j=0;i<z;j=j+0) {
                        if (Nazwa1[i].equals(Nazwa[j])) {
                                cena2 = cena1 + Integer.parseInt(Cena[j])*Integer.parseInt(Ilosc[i]);
                                 cena1 = cena2;
                        }
                        j++;
                    }
                    i++;
                }

                przychody.setText(String.valueOf(cena2));
                wykonywane.setText(String.valueOf(Ilosc2));
                data11 = data1.getText().toString();
                data22 = data2.getText().toString();
            }
        });

        oblicz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(narzut.getText().toString()!=null)
                {
                    koszty1= Double.parseDouble(koszty.getText().toString());
                    procenty1 = Double.parseDouble(narzut.getText().toString());
                    wynik=((koszty1+cena2)*procenty1)/100;

                }
                else
                {
                    koszty1= Double.parseDouble(koszty.getText().toString());
                    wynik = koszty1+cena2;

                }

                wynik1.setVisibility(View.VISIBLE);
                wynik2.setVisibility(View.VISIBLE);
                wynik2.setText(String.valueOf(wynik));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Przychod.this,Magzyn.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_przychod, menu);
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
