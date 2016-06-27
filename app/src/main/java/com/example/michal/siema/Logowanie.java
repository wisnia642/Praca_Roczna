package com.example.michal.siema;

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
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Logowanie extends ActionBarActivity {

    EditText users,password;
    Button ok,anuluj;


    private static final String url="jdbc:mysql://192.168.0.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    String[] uzytkonkik = new String[15];
    String[] haslo = new String[15];
    String[] kuchnia = new String[15];
    String[] magazyn = new String[15];
    String[] sala_sprzedazy = new String[15];
    String[] wszystko = new String[15];

    int x,z,d;
    String hash1,u,stan,login;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

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

    private void ToDataBase()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS logowanie (Id VARCHAR,Uzytkownik VARCHAR,Haslo VARCHAR,Sala_sprzedazy VARCHAR," +
                    "Magazyn VARCHAR,Kuchnia VARCHAR,Wszystko VARCHAR,Stan VARCHAR);");
        }
        catch (Exception e){}

    }

    private void Hash()
    {
        try {
            String input = password.getText().toString();
            hash1 = "%032x440472108104"+String.valueOf(input.hashCode());

        }
        catch (Exception e){}
    }


    public void UpdateSql()
    {
        ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO logowanie (Id,Uzytkownik,Haslo,Sala_sprzedazy,Magazyn,Kuchnia,Wszystko) VALUES ('0','admin','%032x44047210810492668751','1','1','1','1') ");
            sampleDB.close();
        } catch (Exception e) {

        }


        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }


            String sql1 = "INSERT INTO logowanie (Id,Uzytkownik,Haslo,Sala_sprzedazy,Magazyn,Kuchnia,Wszystko) VALUES" +
                    " (?,?,?,?,?,?,?)";

            try {
                ps = connection.prepareStatement(sql1);
                ps.setString(1, "0");
                ps.setString(2, "admin");
                ps.setString(3, "%032x44047210810492668751");
                ps.setString(4, "1");
                ps.setString(5, "1");
                ps.setString(6, "1");
                ps.setString(7, "1");
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

    public void UpdateSql1()
    {
        ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("UPDATE logowanie SET Stan=('true') WHERE Uzytkownik='"+login+"'");
            sampleDB.close();
        } catch (Exception e) {

        }


        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }


            String sql1 = "UPDATE logowanie SET Stan=('true') WHERE Uzytkownik='"+login+"'";

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
    {x=0;
        ToDataBase();

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from logowanie", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    uzytkonkik[x] = String.valueOf(c.getString(1));
                    haslo[x] = String.valueOf(c.getString(2));
                    sala_sprzedazy[x] = String.valueOf(c.getString(3));
                    magazyn[x] = String.valueOf(c.getString(4));
                    kuchnia[x] = String.valueOf(c.getString(5));
                    wszystko[x] = String.valueOf(c.getString(6));
                    x++;}
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
                        uzytkonkik[x] = rs.getString("Uzytkownik");
                        haslo[x] = rs.getString("Haslo");
                        sala_sprzedazy[x] = rs.getString("Sala_sprzedazy");
                        magazyn[x] = rs.getString("Magazyn");
                        kuchnia[x] = rs.getString("Kuchnia");
                        wszystko[x] = rs.getString("Wszystko");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);

        users = (EditText) findViewById(R.id.editText27);
        password = (EditText) findViewById(R.id.editText26);
        ok = (Button) findViewById(R.id.button74);
        anuluj = (Button) findViewById(R.id.button73);

        readsqlLigt();
        if(uzytkonkik[0]==null)
        {
            wczytywanie();
            z=1;
        }
        if(uzytkonkik[0]==null & z==1)
        {
            UpdateSql();
            readsqlLigt();
            z=0;
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        d=0;
                        Hash();
                        login = users.getText().toString();
                        for (int i = 0; i < uzytkonkik.length; i = i + 0) {
                            if (login.equals(uzytkonkik[i])) {
                                if (hash1.equals(haslo[i])) {
                                    UpdateSql1();
                                    Intent c = new Intent(Logowanie.this, Glowne_menu.class);
                                    c.putExtra("sala_sprzedazy", sala_sprzedazy[i]);
                                    c.putExtra("wszystko", wszystko[i]);
                                    c.putExtra("magazyn", magazyn[i]);
                                    c.putExtra("kuchnia", kuchnia[i]);
                                    c.putExtra("uzytkownik", u);
                                    startActivity(c);
                                    d=1;
                                }
                            }
                            i++;
                        }

                        if (TextUtils.isEmpty(users.getText().toString()) & TextUtils.isEmpty(password.getText().toString())) {

                            showToast("Uzupełnij wszystkie pola");
                        }
                        else if(d==0)
                        {
                            showToast("Błędny login lub hasło");
                        }
                    }catch (Exception e){}
            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users.setText("");
                password.setText("");
            }
        });

    }
}
