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
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Glowne_menu extends ActionBarActivity {

    Button kuchnia,magazyn,sala_sprzedaz,wyloguj,konto;
    Bundle applesData;
    String Magazyn,Kuchnia,Sala_sprzedazy,Wszystko,login;

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    String[] stan = new String[10];
    int x;

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

    public void UpdateSql()
    {

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("UPDATE logowanie SET Stan=('false') WHERE Uzytkownik='"+login+"'");
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


            String sql1 = "UPDATE logowanie SET Stan=('false') WHERE Uzytkownik='"+login+"'";

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

    private void readsqlLigt()
    {

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from logowanie WHERE Stan=('true')", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    login = String.valueOf(c.getString(1));
                    }
            }
            sampleDB.close();
        }
        catch (Exception e)
        {

        }

    }

    public void wczytywanie() {
        x=0;
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
        setContentView(R.layout.activity_glowne_menu);

        kuchnia = (Button) findViewById(R.id.button56);
        magazyn = (Button) findViewById(R.id.button57);
        sala_sprzedaz = (Button) findViewById(R.id.button55);
        wyloguj = (Button) findViewById(R.id.button78);
        konto = (Button) findViewById(R.id.button77);

        applesData = getIntent().getExtras();
        Sala_sprzedazy = applesData.getString("sala_sprzedazy");
        Magazyn = applesData.getString("magazyn");
        Kuchnia = applesData.getString("kuchnia");
        Wszystko = applesData.getString("wszystko");

        readsqlLigt();
        if(login==null)
        {
            wczytywanie();
        }

        kuchnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Kuchnia.equals("1") || Wszystko.equals("1")){
                    Intent a = new Intent(Glowne_menu.this, Kuchnia.class);
                   a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                    a.putExtra("wszystko", Wszystko);
                    a.putExtra("magazyn", Magazyn);
                    a.putExtra("kuchnia", Kuchnia);
                    startActivity(a);}
                else {
                    showToast("Brak uprawnień");
                }
            }
        });

        magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Magazyn.equals("1") || Wszystko.equals("1")){
                    Intent a = new Intent(Glowne_menu.this, Magzyn.class);
                    a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                    a.putExtra("wszystko", Wszystko);
                    a.putExtra("magazyn", Magazyn);
                    a.putExtra("kuchnia", Kuchnia);
                    startActivity(a);}
                else
                {
                    showToast("Brak uprawmień");
                }
            }
        });

        sala_sprzedaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Sala_sprzedazy.equals("1")|| Wszystko.equals("1")){
                    Intent a = new Intent(Glowne_menu.this, MainActivity.class);
                    a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                    a.putExtra("wszystko", Wszystko);
                    a.putExtra("magazyn", Magazyn);
                    a.putExtra("kuchnia", Kuchnia);
                    startActivity(a);}
                else
                {
                    showToast("Brak uprawnień");
                }
            }
        });


        konto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(Wszystko.equals("1")){
                Intent a = new Intent(Glowne_menu.this, Ustawienia.class);
                      a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                      a.putExtra("wszystko", Wszystko);
                      a.putExtra("magazyn", Magazyn);
                      a.putExtra("kuchnia", Kuchnia);
                startActivity(a);
                  }
              else
              {
                 showToast("Brak uprawnień");
               }
             }
        });

        wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSql();
                Intent c = new Intent(Glowne_menu.this, Logowanie.class);
                c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(c);
                finish();
            }
        });
    }
}
