package com.example.michal.siema;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Gotowka1 extends ActionBarActivity {

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Zamowienie";

    static ResultSet rs;
    static Statement st;

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    String[] Klient = new String[20];
    String[] klient = new String[20];
    Double[] Suma = new Double[20];
    String zm,currentDateandTime;
    int wartosc = 0;
    double kasa, wydane;

    Button ok, anuluj;
    TextView reszta, kasa_do_zaplacenia, spiner;
    EditText kasa_otrzymana;

    int x, w, c, a, z;
    Bundle applesData;
    String s,m,k,W,u;

    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    List<String> listaStringow = new ArrayList<String>();
    Spinner Stolik;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    //tworzenie polaczenia z baza danych
    public void connect() {
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

    private void ToDataBase() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Historia (Data VARCHAR,Klient VARCHAR,Suma VARCHAR,Kto_obsluzyl VARCHAR);");

        } catch (Exception e) {
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

            if (wartosc == 1) {
                String sql = "SELECT * FROM ZAMOWIENIE";

                try {
                    rs = st.executeQuery(sql);
                } catch (SQLException e1) {
                    //  e1.printStackTrace();
                }
                try {
                    int i = 0;
                    while (rs.next()) {
                        Klient[i] = rs.getString(0);
                        Suma[i] = rs.getDouble(6);
                        i++;
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (wartosc == 2) {
                String sql = "DELETE FROM Zamowienie WHERE Klient=('" + klient[w] + "')";
                try {
                    st.executeUpdate(sql);
                } catch (SQLException e1) {
                    // e1.printStackTrace();
                }
                wartosc=3;
            }
        }
        if (wartosc == 3) {
            String sql = "INSERT INTO Historia (Data,Klient,Suma,Kto_obsluzyl) VALUES (?,?,?,?) ";
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(0, String.valueOf(currentDateandTime));
                ps.setString(1, String.valueOf(klient[w]));
                ps.setString(2, String.valueOf(Suma[w]));
                ps.setString(3, String.valueOf(u));
                ps.executeUpdate();
                connection.commit();

            } catch (SQLException e) {

            }
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            showToast("brak polaczenia z internetem");
        }

    }

    private void readsqlLight() {

            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            if(wartosc==1){
                try {
            Cursor c  = sampleDB.rawQuery("SELECT * FROM ZAMOWIENIE",null);

            while (c.moveToNext()) {
                zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    Klient[x] = String.valueOf(c.getString(0));
                    Suma[x] = Double.valueOf(c.getDouble(6));
                    x++;}
            }
            sampleDB.close();
        } catch (Exception e){
            }
            }

            if(wartosc==2)
            {
                sampleDB.execSQL("DELETE FROM Zamowienie WHERE Klient=('" + klient[w] + "')");
                wartosc=3;
            }
             if(wartosc==3)
             {
                 ToDataBase();

                 sampleDB.execSQL("INSERT INTO Historia (Data,Klient,Suma,Kto_obsluzyl) VALUES ('" + currentDateandTime + "','" + klient[w] + "','" + Suma[w] + "','"+u+"')");
             }
    }

    public void spinner()
    {
        Stolik.setAdapter(new Gotowka1.MyAdapter(this, R.layout.custom_spiner1, listaStringow));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gotowka1);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");
        u = applesData.getString("uzytkownik");

        wartosc=1;
        readsqlLight();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
         currentDateandTime = sdf.format(new Date());

        for (int i = 0; i < x; i = i + 0) {
            for (int j = 0; j < x; j = j + 0) {
                if (j == 0) {
                    j = j + i;
                }

                if (Klient[j].equals(Klient[i])) {
                    w = w + 1;
                }
                j = j + 1;
            }
            if (w == 1) {
                klient[c] = Klient[i];
                listaStringow.add(klient[c]);
                c = c + 1;

            }
            w = 0;
            i = i + 1;
        }
        c = 0;

        Stolik = (Spinner) findViewById(R.id.spinner3);
        Stolik.setAdapter(new Gotowka1.MyAdapter(this, R.layout.custom_spiner1, listaStringow));
        kasa_do_zaplacenia = (TextView) findViewById(R.id.textView36);
        reszta = (TextView) findViewById(R.id.textView38);
        kasa_otrzymana = (EditText) findViewById(R.id.editText6);
        ok = (Button) findViewById(R.id.button60);
        anuluj = (Button) findViewById(R.id.button61);
        kasa_do_zaplacenia.setText(String.valueOf(Suma[w]));

        Stolik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kasa_do_zaplacenia.setText(String.valueOf(Suma[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (Suma[w] >= 0) {
                        try {
                            kasa = Double.parseDouble(kasa_otrzymana.getText().toString());
                            wydane = Suma[w]-kasa;
                            wydane=wydane*(-1);
                            wydane *= 100; // zaokraglanie
                            wydane = Double.valueOf(Math.round(wydane));
                            wydane /= 100;
                            reszta.setText(String.valueOf(wydane));
                            showToast("Rachunek został zapłacony");
                            wartosc = 2;
                            wczytywanie();
                            readsqlLight();
                            spinner();

                        }catch (Exception e){}
                    }

                else {
                    showToast("Wybierz Klient / Stolik");
                }}
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Gotowka1.this,MainActivity.class);
                i.putExtra("sala_sprzedazy", s);
                i.putExtra("wszystko", W);
                i.putExtra("magazyn", m);
                i.putExtra("kuchnia", k);
                startActivity(i);
            }
        });

    }

    public class MyAdapter extends ArrayAdapter<String>
    {
        public MyAdapter(Context ctx, int txtViewResourceId, List<String> objects)
        {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt)
        {
            return getCustomView(position, cnvtView, prnt);
        }
        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt)
        {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent)
        {
            w=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner1, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text2);
            main_text.setText(klient[position]);
            return mySpinner;
        }}
}
