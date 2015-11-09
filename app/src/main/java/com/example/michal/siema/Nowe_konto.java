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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Nowe_konto extends ActionBarActivity {

    EditText Login,Haslo,Powtorz_haslo;
    Spinner edycja;
    CheckBox Magazyn,Kuchnia,Sala_sprzedazy,Wszystko;
    Button ok,annuluj;

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
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
    String[] wartosc = new String[5];
    int x,z,q;
    String hash1,login1,haslo1,powtorz_haslo1;

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
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS logowanie (Uzytkownik VARCHAR,Haslo VARCHAR,Sala_sprzedazy VARCHAR," +
                    "Magazyn VARCHAR,Kuchnia VARCHAR,Wszystko VARCHAR);");
        }
        catch (Exception e){}

    }

    private void Hash()
    {
        try {
            String plaintext = Haslo.getText().toString();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(plaintext));
            hash1="%032x"+ new BigInteger(1, md5.digest());

        }
        catch (Exception e){}
    }


    public void UpdateSql()
    {
        ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO logowanie (Uzytkownik,Haslo,Sala_sprzedazy,Magazyn,Kuchnia,Wszystko) VALUES ('"+login1+"','"+haslo1+"'," +
                    "'"+wartosc[0]+"','"+wartosc[1]+"','"+wartosc[2]+"','"+wartosc[3]+"') ");
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


            String sql1 = "INSERT INTO logowanie (Uzytkownik,Haslo,Sala_sprzedazy,Magazyn,Kuchnia,Wszystko) VALUES" +
                    " (?,?,?,?,?,?)";

            try {
                ps = connection.prepareStatement(sql1);
                ps.setString(1, login1);
                ps.setString(2, haslo1);
                ps.setString(3, wartosc[0]);
                ps.setString(4, wartosc[1]);
                ps.setString(5, wartosc[2]);
                ps.setString(6, wartosc[3]);
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

    private void readsqlLigt()
    {x=0;
        ToDataBase();

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from logowanie", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(0));
                if(zm!=null){
                    uzytkonkik[x] = String.valueOf(c.getString(0));
                    haslo[x] = String.valueOf(c.getString(1));
                    sala_sprzedazy[x] = String.valueOf(c.getString(2));
                    magazyn[x] = String.valueOf(c.getString(3));
                    kuchnia[x] = String.valueOf(c.getString(4));
                    wszystko[x] = String.valueOf(c.getString(5));
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
        setContentView(R.layout.activity_nowe_konto);

        Login = (EditText) findViewById(R.id.editTextN);
        Haslo = (EditText) findViewById(R.id.editTextS);
        Powtorz_haslo = (EditText) findViewById(R.id.editText10);
        edycja = (Spinner) findViewById(R.id.spinerdod);
        Sala_sprzedazy = (CheckBox) findViewById(R.id.checkBox4);
        Magazyn = (CheckBox) findViewById(R.id.checkBox3);
        Kuchnia = (CheckBox) findViewById(R.id.checkBox);
        Wszystko = (CheckBox) findViewById(R.id.checkBox2);
        ok = (Button) findViewById(R.id.button75);
        annuluj = (Button) findViewById(R.id.button76);

        readsqlLigt();
        if(uzytkonkik[0]==null)
        {
            wczytywanie();
        }

        edycja.setAdapter(new MyAdapter1(this, R.layout.custom_spiner, uzytkonkik));

        edycja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Login.setText(uzytkonkik[i]);
                Haslo.setText(haslo[i]);
                Powtorz_haslo.setText(haslo[i]);
                if(sala_sprzedazy[i]=="1");
                {
                    Sala_sprzedazy.setChecked(true);
                }
                if(magazyn[i]=="1");
                {
                    Magazyn.setChecked(true);
                }
                if(kuchnia[i]=="1");
                {
                    Kuchnia.setChecked(true);
                }
                if(wszystko[i]=="1");
                {
                    Wszystko.setChecked(true);
                    Kuchnia.setChecked(true);
                    Magazyn.setChecked(true);
                    Sala_sprzedazy.setChecked(true);

                }

            }
        });

        annuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Nowe_konto.this,Glowne_menu.class);
                startActivity(i);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login1= Login.getText().toString();
                haslo1=Haslo.getText().toString();
                powtorz_haslo1=Powtorz_haslo.getText().toString();
                if(login1!=null & haslo1!=null & powtorz_haslo1!=null)
                {
                    if(haslo1==powtorz_haslo1)
                    {
                        UpdateSql();
                    }
                    else
                    {
                        showToast("Hasła nie są identyczne");
                    }

                }
                else
                {
                    showToast("Uzupełnij wszystkie pola");
                }
            }
        });

        Sala_sprzedazy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    Sala_sprzedazy.setChecked(true);
                    wartosc[0]="1";
                }
                else
                {
                    wartosc[0]="0";
                }
            }
        });
        Magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    Magazyn.setChecked(true);
                    wartosc[1] = "1";
                } else {
                    wartosc[1] = "0";
                }
            }
        });

        Kuchnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                Kuchnia.setChecked(true);
                    wartosc[2] = "1";
                } else {
                    wartosc[2] = "0";
                }
            }
        });

        Wszystko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    Sala_sprzedazy.setChecked(true);
                    Wszystko.setChecked(true);
                    Magazyn.setChecked(true);
                    Kuchnia.setChecked(true);
                    wartosc[3]="1";
                } else {
                    Sala_sprzedazy.setChecked(false);
                    Wszystko.setChecked(false);
                    Magazyn.setChecked(false);
                    Kuchnia.setChecked(false);
                    wartosc[3]="0";
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nowe_konto, menu);
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
    public class MyAdapter1 extends ArrayAdapter<String>
    {
        public MyAdapter1(Context ctx, int txtViewResourceId, String[] objects)
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
            q=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text1);
            main_text.setText(uzytkonkik[position]);
            return mySpinner;
        }}
}