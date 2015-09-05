package com.example.michal.siema;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Rezerwacja extends ActionBarActivity {

    String[] Sala = {"Sala_1","Sala_2","Sala_3","Sala_4","Sala_5"};
    String[] Stolik = {"Stolik_1","Stolik_2","Stolik_3","Stolik_4","Stolik_5","Stolik_6","Stolik_7","Stolik_8","Stolik_9",
            "Stolik_10","Stolik_11","Stolik_12","Stolik_13","Stolik_14","Stolik_15"};

    String data,czas,klient;

    EditText Czas,Data,Klient;
    Spinner sala,stolik;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Rezerwacja";

    static Statement st;
    PreparedStatement ps;
    Connection connection = null;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void ToDataBase() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    SAMPLE_TABLE_NAME +
                    " (Klient VARCHAR ,Czas VARCHAR,Data VARCHAR);");

        } catch (Exception e) {
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

    private void funkcjonalnosci() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("INSERT INTO Rezerwacja (Klient,Czas,Data) VALUES ('" + klient + "','" + czas + "','" + data + "')");
            sampleDB.close();

        } catch (Exception e) {
            showToast(e + "");
        }

        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }

            String sql = "INSERT INTO Zamowienie (KLient,Czas,Data) VALUES (?,?,?)";
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(0, String.valueOf(klient));
                ps.setString(1, String.valueOf(czas));
                ps.setString(2, String.valueOf(data));
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezerwacja);

       // Sala = (Spinner) findViewById(R.id.spinner2);
        //Stolik = (Spinner) findViewById(

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rezerwacja, menu);
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
