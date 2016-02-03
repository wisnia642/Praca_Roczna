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
import java.util.ArrayList;
import java.util.List;

public class Nowe_konto extends ActionBarActivity {

    EditText Login,Haslo,Powtorz_haslo;
    Spinner edycja;
    CheckBox Magazyn,Kuchnia,Sala_sprzedazy,Wszystko;
    Button ok,annuluj;
    Bundle applesData;
    String s,m,k,w;
    List<String> listaStringow = new ArrayList<String>();

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
    boolean[] czy_puste = new boolean[5];
    int x,z,q,i,p;
    boolean stan=false;
    boolean stan1 =false;
    boolean stan2 =false;
    boolean stan3 =false;
    boolean stan4 =false;
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


    private void Hash()
    {
        try {
            String input = Haslo.getText().toString();
            hash1 = "%032x440472108104"+String.valueOf(input.hashCode());
        }
        catch (Exception e){}
    }


    public void UpdateSql()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO logowanie (Id,Uzytkownik,Haslo,Sala_sprzedazy,Magazyn,Kuchnia,Wszystko) VALUES ('"+String.valueOf(p)+"','"+login1+"','"+hash1+"'," +
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


            String sql1 = "INSERT INTO logowanie (Id,Uzytkownik,Haslo,Sala_sprzedazy,Magazyn,Kuchnia,Wszystko) VALUES" +
                    " (?,?,?,?,?,?,?)";

            try {
                ps = connection.prepareStatement(sql1);
                ps.setString(1, String.valueOf(p));
                ps.setString(2, login1);
                ps.setString(3, hash1);
                ps.setString(4, wartosc[0]);
                ps.setString(5, wartosc[1]);
                ps.setString(6, wartosc[2]);
                ps.setString(7, wartosc[3]);
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

    private void writeToDataBase()
    {

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

                sampleDB.execSQL("DELETE FROM logowanie WHERE Id=('" + q + "') ");

            sampleDB.close();
        }catch (Exception e){showToast("Blad w update");}

        connect();

        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }

                String sql ="DELETE FROM logowanie WHERE Id=('" + q + "') ";

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


    private void readsqlLigt()
    {x=0;

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

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        w = applesData.getString("wszystko");

        czy_puste[0]=false;
        czy_puste[1]=false;
        czy_puste[2]=false;
        czy_puste[3]=false;

        readsqlLigt();
        if(uzytkonkik[0]==null)
        {
            wczytywanie();
        }

        for(int j=0;j<uzytkonkik.length;j++)
        {
            if(uzytkonkik[j]!=null)
            {
                listaStringow.add(uzytkonkik[j]);
            }
        }
          edycja.setAdapter(new MyAdapter1(this, R.layout.custom_spiner, listaStringow));

        edycja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stan == true) {
                    stan = false;
                    stan1 = true;
                    Login.setText(uzytkonkik[position]);
                    Haslo.setText(haslo[position]);
                    Powtorz_haslo.setText(haslo[position]);
                    if (sala_sprzedazy[q] == "1") ;
                    {
                        Sala_sprzedazy.setChecked(true);
                    }
                    if (magazyn[q] == "1") ;
                    {
                        Magazyn.setChecked(true);
                    }
                    if (kuchnia[q] == "1") ;
                    {
                        Kuchnia.setChecked(true);
                    }
                    if (wszystko[q] == "1") ;
                    {
                        Wszystko.setChecked(true);
                        Kuchnia.setChecked(true);
                        Magazyn.setChecked(true);
                        Sala_sprzedazy.setChecked(true);

                    }

                }
                stan = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        annuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Nowe_konto.this, Ustawienia.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", w);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stan2=true;
            }
        });

        Haslo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stan3=true;
            }
        });
        Powtorz_haslo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stan4=true;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login1 = Login.getText().toString();
                haslo1 = Haslo.getText().toString();
                powtorz_haslo1 = Powtorz_haslo.getText().toString();
                try {
                    if (stan2==true&stan3==true&stan4==true) {
                        if (haslo1.equals(powtorz_haslo1)) {
                            if (stan1 == true) {
                                if (haslo[q].equals(hash1)) {
                                    p=x-1;
                                    if(czy_puste[0]!=true&czy_puste[1]!=true&czy_puste[2]!=true&czy_puste[3]!=true)
                                    {
                                        wartosc[0]=sala_sprzedazy[i];
                                        wartosc[1] =magazyn[i];
                                        wartosc[2] =kuchnia[i];
                                        wartosc[3]=wszystko[i];
                                    }
                                    writeToDataBase();
                                    UpdateSql();
                                    Intent c = new Intent(Nowe_konto.this, Glowne_menu.class);
                                    c.putExtra("sala_sprzedazy", s);
                                    c.putExtra("wszystko", w);
                                    c.putExtra("magazyn", m);
                                    c.putExtra("kuchnia", k);
                                    startActivity(c);
                                } else {
                                    p=x-1;
                                    Hash();
                                    if(czy_puste[0]!=true&czy_puste[1]!=true&czy_puste[2]!=true&czy_puste[3]!=true)
                                    {
                                        wartosc[0]=sala_sprzedazy[i];
                                        wartosc[1] =magazyn[i];
                                        wartosc[2] =kuchnia[i];
                                        wartosc[3]=wszystko[i];
                                    }
                                    writeToDataBase();
                                    UpdateSql();
                                    Intent c = new Intent(Nowe_konto.this, Glowne_menu.class);
                                    c.putExtra("sala_sprzedazy", s);
                                    c.putExtra("wszystko", w);
                                    c.putExtra("magazyn", m);
                                    c.putExtra("kuchnia", k);
                                    startActivity(c);
                                }
                            } else {
                                p=x;
                                Hash();
                                if(czy_puste[0]!=true&czy_puste[1]!=true&czy_puste[2]!=true&czy_puste[3]!=true)
                                {
                                    wartosc[0]=sala_sprzedazy[i];
                                    wartosc[1] =magazyn[i];
                                    wartosc[2] =kuchnia[i];
                                    wartosc[3]=wszystko[i];
                                }
                                UpdateSql();
                                Intent c = new Intent(Nowe_konto.this, Glowne_menu.class);
                                c.putExtra("sala_sprzedazy", s);
                                c.putExtra("wszystko", w);
                                c.putExtra("magazyn", m);
                                c.putExtra("kuchnia", k);
                                startActivity(c);
                            }
                        } else {
                            showToast("Hasła nie są identyczne");
                        }

                    } else {
                        showToast("Uzupełnij wszystkie pola");
                    }
                }catch (Exception e){}

            }
        });

        Sala_sprzedazy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                czy_puste[0]=true;
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
                czy_puste[1]=true;
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
                czy_puste[2]=true;
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
                czy_puste[3]=true;
                if (((CheckBox) view).isChecked()) {
                    Sala_sprzedazy.setChecked(true);
                    Wszystko.setChecked(true);
                    Magazyn.setChecked(true);
                    Kuchnia.setChecked(true);
                    wartosc[3]="1";
                    wartosc[2]="1";
                    wartosc[1]="1";
                    wartosc[0]="1";
                } else {
                    Sala_sprzedazy.setChecked(false);
                    Wszystko.setChecked(false);
                    Magazyn.setChecked(false);
                    Kuchnia.setChecked(false);
                    wartosc[3]="0";
                    wartosc[2]="0";
                    wartosc[1]="0";
                    wartosc[0]="0";
                }
            }
        });

    }

    public class MyAdapter1 extends ArrayAdapter<String>
    {
        public MyAdapter1(Context ctx, int txtViewResourceId, List<String> objects)
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
