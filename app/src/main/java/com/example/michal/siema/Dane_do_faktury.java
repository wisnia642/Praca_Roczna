package com.example.michal.siema;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

import static com.example.michal.siema.R.layout.custom_spiner1;

public class Dane_do_faktury extends ActionBarActivity {

    Button ok,anuluj;
    EditText nazwa_firmy,ulica,numer_domu,miejscowosc,kod_pocztowy;
    EditText nazwa_firmy1,ulica1,numer_domu1,miejscowosc1,kod_pocztowy1;
    String[] Nazwa_firmy = new String[15];
    String[] Ulica = new String[15];
    String[] Numer_domu = new String[15];
    String[] Miejcowosc = new String[15];
    String[] Kod_pocztowy = new String[15];
    String[] Nazwa_firmy1 = new String[15];
    String[] Ulica1 = new String[15];
    String[] Numer_domu1 = new String[15];
    String[] Miejscowosc1 = new String[15];
    String[] Kod_pocztowy1 = new String[15];
    String[] wartosc = new String[15];
    Spinner klient_s,firma_s;

    Bundle applesData;
    String s,m,k,W,faktura;
    int x,y,d,c;
    int g=0;
    int h=0;
    boolean stan=false;
    boolean stan1=false;

    List<String> listaStringow = new ArrayList<String>();
    List<String> listaStringow1 = new ArrayList<String>();


    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

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
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);;

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS dane_faktura (Id VARCHAR,Dane VARCHAR,Dane1 VARCHAR,Dane2 VARCHAR,Dane3 VARCHAR," +
                    "Dane4 VARCHAR,Wlasciciel VARCHAR);");
        }
        catch (Exception e){}

    }

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

    public void connectToDataBase() {


        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

                sampleDB.execSQL("UPDATE dane_faktura SET Dane=('" + wartosc[5] + "'),Dane1=('" + wartosc[6] + "'),Dane2=('" + wartosc[7] + "')," +
                        "Dane3=('" + wartosc[8] + "'),Dane4=('" +wartosc[9] + "') WHERE Id='"+c+"' AND Wlasciciel='klient'");
                sampleDB.close();
            }

        catch (Exception e){showToast("Blad w update");}

        connect();

        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }

                String sql = "UPDATE dane_faktura SET Dane=('" + wartosc[5] + "'),Dane1=('" + wartosc[6] + "'),Dane2=('" + wartosc[7] + "')," +
                        "Dane3=('" + wartosc[8] + "'),Dane4=('" +wartosc[9] + "') WHERE Id='"+c+"' AND Wlasciciel='klient'";


                try {
                    st.executeUpdate(sql);
                } catch (SQLException e1) {
                    // e1.printStackTrace();
                }
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                showToast("brak połączenia z internetem");
            }
        }

    public void connectToDataBase1() {
        if (W.equals("1")) {

            try {
                SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

                sampleDB.execSQL("UPDATE dane_faktura SET Dane=('" + wartosc[0] + "'),Dane1=('" + wartosc[1] + "'),Dane2=('" + wartosc[2] + "')," +
                        "Dane3=('" + wartosc[3] + "'),Dane4=('" + wartosc[4] + "') WHERE Id='" + d + "' AND Wlasciciel='firma'");
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

                String sql = "UPDATE dane_faktura SET Dane=('" + wartosc[0] + "'),Dane1=('" + wartosc[1] + "'),Dane2=('" + wartosc[2] + "')," +
                        "Dane3=('" + wartosc[3] + "'),Dane4=('" + wartosc[4] + "') WHERE Id='" + d + "' AND Wlasciciel='firma'";


                try {
                    st.executeUpdate(sql);
                } catch (SQLException e1) {
                    // e1.printStackTrace();
                }
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
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO dane_faktura (Id,Dane,Dane1,Dane2,Dane3,Dane4,Wlasciciel) VALUES ('"+x+"','"+wartosc[5]+"','"+wartosc[6]+"','"+wartosc[7]+"'," +
                    "'"+wartosc[8]+"','"+wartosc[9]+"','klient') ");
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


            String sql1 = "INSERT INTO dane_faktura (Id,Dane,Dane1,Dane2,Dane3,Dane4,Wlasciciel) VALUES" +
                    " (?,?,?,?,?,?,?) ";

            try {
                ps = connection.prepareStatement(sql1);
                ps.setString(1, String.valueOf(x));
                ps.setString(2, String.valueOf(wartosc[5]));
                ps.setString(3, String.valueOf(wartosc[6]));
                ps.setString(4, String.valueOf(wartosc[7]));
                ps.setString(5, String.valueOf(wartosc[8]));
                ps.setString(6, String.valueOf(wartosc[9]));
                ps.setString(7, String.valueOf("klient"));
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

    public void UpdateSql() {
        if (W.equals("1")) {
            ToDataBase();

            try {
                SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                //poprawić ma być insert bo tych składników jeszcze nie ma
                sampleDB.execSQL("INSERT INTO dane_faktura (Id,Dane,Dane1,Dane2,Dane3,Dane4,Wlasciciel) VALUES ('" + y + "','" + wartosc[0] + "','" + wartosc[1] + "','" + wartosc[2] + "'," +
                        "'" + wartosc[3] + "','" + wartosc[4] + "','firma')");
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

                String sql = "INSERT INTO dane_faktura (Id,Dane,Dane1,Dane2,Dane3,Dane4,Wlasciciel) VALUES" +
                        " (?,?,?,?,?,?,?)";

                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(y));
                    ps.setString(2, String.valueOf(wartosc[0]));
                    ps.setString(3, String.valueOf(wartosc[1]));
                    ps.setString(4, String.valueOf(wartosc[2]));
                    ps.setString(5, String.valueOf(wartosc[3]));
                    ps.setString(6, String.valueOf(wartosc[4]));
                    ps.setString(7, String.valueOf("firma"));
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
    }

    private void readsqlLigt()
    {x=0;
        y=0;
        ToDataBase();

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from dane_faktura where Wlasciciel='klient'", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    Nazwa_firmy1[y] = String.valueOf(c.getString(1));
                    Ulica1[y] = String.valueOf(c.getString(2));
                    Numer_domu1[y] = String.valueOf(c.getString(3));
                    Miejscowosc1[y] = String.valueOf(c.getString(4));
                    Kod_pocztowy1[y] = String.valueOf(c.getString(5));
                    y++;
                }
            }
            sampleDB.close();
        }
        catch (Exception e)
        {

        }

        SQLiteDatabase sampleDB1 = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor f = sampleDB1.rawQuery("select * from dane_faktura where Wlasciciel='firma'", null);

            while (f.moveToNext()) {
                String zm1 = String.valueOf(f.getString(1));
                if (zm1 != null) {
                    Nazwa_firmy[x] = String.valueOf(f.getString(1));
                    Ulica[x] = String.valueOf(f.getString(2));
                    Numer_domu[x] = String.valueOf(f.getString(3));
                    Miejcowosc[x] = String.valueOf(f.getString(4));
                    Kod_pocztowy[x] = String.valueOf(f.getString(5));
                    x++;
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
        y=0;
        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sql = ("select * from dane_faktura where Wlasciciel='firma'");

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
                    String  zm = rs.getString("Dane");
                    if(zm!=null){
                        Nazwa_firmy[x] = rs.getString("Dane");
                        Ulica[x] = rs.getString("Dane1");
                        Numer_domu[x] = rs.getString("Dane2");
                        Miejcowosc[x] = rs.getString("Dane3");
                        Kod_pocztowy[x] = rs.getString("Dane4");
                        x++;}

                } }catch (SQLException e1){}


            String sql1 = ("select * from dane_faktura where Wlasciciel='klient'");

            try {
                rs=st.executeQuery(sql1);
            } catch (SQLException e1) {
                //  e1.printStackTrace();
            }
            try{
                PreparedStatement stmt = connection.prepareStatement(sql1);
                rs = stmt.executeQuery();

                while (rs.next())
                {
                    String  zm = rs.getString("Dane");
                    if(zm!=null){
                        Nazwa_firmy1[y] = rs.getString("Dane");
                        Ulica1[y] = rs.getString("Dane1");
                        Numer_domu1[y] = rs.getString("Dane2");
                        Miejscowosc1[y] = rs.getString("Dane3");
                        Kod_pocztowy1[y] = rs.getString("Dane4");
                        y++;}

                } }catch (SQLException e1){}

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
        setContentView(R.layout.activity_dane_do_faktury);

        ok = (Button) findViewById(R.id.button80);
        anuluj = (Button) findViewById(R.id.button79);
        nazwa_firmy = (EditText) findViewById(R.id.editText28);
        nazwa_firmy1 = (EditText) findViewById(R.id.editText29);
        ulica = (EditText) findViewById(R.id.editText30);
        ulica1 = (EditText) findViewById(R.id.editText31);
        numer_domu = (EditText) findViewById(R.id.editText32);
        numer_domu1 = (EditText) findViewById(R.id.editText33);
        miejscowosc = (EditText) findViewById(R.id.editText34);
        miejscowosc1 = (EditText) findViewById(R.id.editText35);
        kod_pocztowy = (EditText) findViewById(R.id.editText36);
        kod_pocztowy1 = (EditText) findViewById(R.id.editText37);
        klient_s = (Spinner) findViewById(R.id.spinner8);
        firma_s = (Spinner) findViewById(R.id.spinner9);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");
        faktura = applesData.getString("faktura");

        if(W.equals("1")) {
            nazwa_firmy.setVisibility(View.VISIBLE);
            ulica.setVisibility(View.VISIBLE);
            numer_domu.setVisibility(View.VISIBLE);
            miejscowosc.setVisibility(View.VISIBLE);
            kod_pocztowy.setVisibility(View.VISIBLE);
        }

        readsqlLigt();
        if(Nazwa_firmy[0]==null & Nazwa_firmy1[0]==null)
        {
            wczytywanie();
        }
        try {
            for (int j = 0; j < Nazwa_firmy.length; j++) {
                if (Nazwa_firmy[j] != null) {
                    listaStringow.add(Nazwa_firmy[j]);
                }
            }

            for (int r = 0; r < Nazwa_firmy1.length; r++) {
                if (Nazwa_firmy1[r] != null) {
                    listaStringow1.add(Nazwa_firmy1[r]);
                }
            }

            klient_s.setAdapter(new MyAdapter(this, R.layout.custom_spiner, listaStringow));

            firma_s.setAdapter(new Adapter(this, R.layout.custom_spiner, listaStringow1));

        }catch (Exception e){}

        klient_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stan = true;
                if (stan == true & g == 2) {
                    nazwa_firmy.setText(Nazwa_firmy[d]);
                    ulica.setText(Ulica[d]);
                    numer_domu.setText(Numer_domu[d]);
                    miejscowosc.setText(Miejcowosc[d]);
                    kod_pocztowy.setText(Kod_pocztowy[d]);
                }
                g=2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

       firma_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stan1=true;
                if(stan1==true & h==1) {
                    nazwa_firmy1.setText(Nazwa_firmy1[c]);
                    ulica1.setText(Ulica1[c]);
                    numer_domu1.setText(Numer_domu1[c]);
                    miejscowosc1.setText(Miejscowosc1[c]);
                    kod_pocztowy1.setText(Kod_pocztowy1[c]);
                }
                h=1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (faktura.equals("false")) {
                        Intent i = new Intent(Dane_do_faktury.this, Ustawienia.class);
                        i.putExtra("sala_sprzedazy", s);
                        i.putExtra("wszystko", W);
                        i.putExtra("magazyn", m);
                        i.putExtra("kuchnia", k);
                        startActivity(i);
                    }
                    if (faktura.equals("true")) {
                        Intent w = new Intent(Dane_do_faktury.this, Faktura.class);
                        w.putExtra("sala_sprzedazy", s);
                        w.putExtra("wszystko", W);
                        w.putExtra("magazyn", m);
                        w.putExtra("kuchnia", k);
                        startActivity(w);
                    }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wartosc[0] = nazwa_firmy.getText().toString();
                wartosc[5] = nazwa_firmy1.getText().toString();
                wartosc[1] = ulica.getText().toString();
                wartosc[6] = ulica1.getText().toString();
                wartosc[2] = numer_domu.getText().toString();
                wartosc[7] = numer_domu1.getText().toString();
                wartosc[3] = miejscowosc.getText().toString();
                wartosc[8] = miejscowosc1.getText().toString();
                wartosc[4] = kod_pocztowy.getText().toString();
                wartosc[9] = kod_pocztowy1.getText().toString();

                if (stan == true) {
                    connectToDataBase1();
                }
                else
                {
                    UpdateSql();
                }

                if(stan1 == true)
                {
                    connectToDataBase();
                }
                else
                {
                    UpdateSql1();
                }
                showToast("Dane zostały dodane");
                finish();
                startActivity(getIntent());
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
            d=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(custom_spiner1, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text2);
            main_text.setText(Nazwa_firmy[position]);
            return mySpinner;
        }}

    public class Adapter extends ArrayAdapter<String>
    {
        public Adapter(Context ctx, int txtViewResourceId, List<String> objects)
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
            c=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(custom_spiner1, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text2);
            main_text.setText(Nazwa_firmy1[position]);
            return mySpinner;
        }}
}


