package com.example.michal.siema;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Wykonane_potrawy extends ActionBarActivity {

    EditText nazwa,datatxt,czastxt,ilosc,czas_wykonania;
    Spinner kto_wykonal;
    ListView lista;
    Button ok,cancel,usun,utworz;

    Bundle applesData;
    String s,m,k,W;
    List<String> listaStringow = new ArrayList<String>();

    String[] Data = new String[40];
    String[] Czas = new String[40];
    String[] Nazwa = new String[40];
    String[] Ilosc = new String[40];
    String[] Czas_wykonania = new String[40];
    String[] Kto_wykonal = new String[40];
    String[] uzytkownik = new String[10];

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    Calendar myCalendar;
    private SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener date;

    int x,wartosc,o,w,c;
    String data,czas;

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

        datatxt.setText(sdf.format(myCalendar.getTime()));
        data=(sdf.format(myCalendar.getTime()));
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

    public void wczytywanie() {

        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sql = "SELECT * FROM wykonane";

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
                    Data[x] = rs.getString("Data");
                    Czas[x] = rs.getString("Czas");
                    Nazwa[x] = rs.getString("Nazwa");
                    Ilosc[x] = rs.getString("Ilosc");
                    Czas_wykonania[x] = rs.getString("Czas_wykonania");
                    Kto_wykonal[x] = rs.getString("Kto_wykonal");
                    x++;

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

    private void readsqlLight() {

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try {
            Cursor c  = sampleDB.rawQuery("SELECT * FROM wykonane",null);

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

    public void saveSqlLight()
    {

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO Wykonane (Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal) VALUES ('" + datatxt.getText().toString() + "','" + czastxt.getText().toString() + "','" + nazwa.getText().toString() + "'," +
                    "'" + ilosc.getText().toString() + "','" + czas_wykonania.getText().toString() + "','" + uzytkownik[o] + "') ");

            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }

    }

    public void deletefromSQLlight()
    {

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("DELETE FROM Wykonane WHERE Czas_wykonania = ('" + czas_wykonania.getText().toString() + "') ");
            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }
    }

    private void funkcjonalnosci() {

        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
            if(wartosc==3){

                String sql = "INSERT INTO Wykonane (Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal) VALUES (?,?,?,?,?,?)";
                try{
                    ps = connection.prepareStatement(sql);
                    ps.setString(1,datatxt.getText().toString());
                    ps.setString(2,czastxt.getText().toString());
                    ps.setString(3,nazwa.getText().toString());
                    ps.setString(4,ilosc.getText().toString());
                    ps.setString(5,czas_wykonania.getText().toString());
                    ps.setString(6, uzytkownik[w]);
                  //  ps.executeUpdate();
                    connection.commit();

                } catch (SQLException e) {

                }
                finally {
                    try {
                        ps.close();

                    } catch (SQLException e) {

                    }

                }
            }
            if(wartosc==2)
            {
                String sql = "DELETE FROM Wykonane WHERE Czas_wykonania = ('" + czas_wykonania.getText().toString() + "')";

                try {
                    st.executeUpdate(sql);
                } catch (SQLException e1) {
                    // e1.printStackTrace();
                }
            }
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                showToast("brak polaczenia z internetem");}

        }}

    private void funkcjonalnosci1() {

        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }


            String sql = "UPDATE Wykonane SET (Data,Czas,Nazwa,Ilosc,Czas_wykonania,Kto_wykonal) = (?,?,?,?,?,?) WHERE Czas_wykonania=(" + Czas_wykonania[c] + ")";
            try{
                ps = connection.prepareStatement(sql);
                ps.setString(1,datatxt.getText().toString());
                ps.setString(2,czastxt.getText().toString());
                ps.setString(3,nazwa.getText().toString());
                ps.setString(4,ilosc.getText().toString());
                ps.setString(5,czas_wykonania.getText().toString());
                ps.setString(6, uzytkownik[o]);
                connection.commit();

            } catch (SQLException e) {

            }
            finally {
                try {
                    ps.close();

                } catch (SQLException e) {

                }

            }

            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                showToast("brak polaczenia z internetem");}
        }

    }

    private void readsqlLigt1()
    {x=0;
        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

        try
        {
            Cursor c = sampleDB.rawQuery("select * from logowanie", null);

            while (c.moveToNext()) {
                String  zm = String.valueOf(c.getString(1));
                if(zm!=null){
                    uzytkownik[x] = String.valueOf(c.getString(1));
                    x++;}
            }
            sampleDB.close();
        }
        catch (Exception e)
        {

        }

    }

    public void wczytywanie1() {
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
                        uzytkownik[x] = rs.getString("Uzytkownik");
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
        setContentView(R.layout.activity_wykonane_potrawy);

        datatxt = (EditText) findViewById(R.id.editText11);
        czastxt = (EditText) findViewById(R.id.editText12);
        ilosc = (EditText) findViewById(R.id.editText17);
        kto_wykonal = (Spinner) findViewById(R.id.spinner5);
        ok = (Button) findViewById(R.id.button51);
        cancel = (Button) findViewById(R.id.button52);
        usun = (Button) findViewById(R.id.button53);
        utworz = (Button) findViewById(R.id.button54);
        nazwa = (EditText) findViewById(R.id.editText16);
        czas_wykonania = (EditText) findViewById(R.id.editText13);
        lista = (ListView) findViewById(R.id.listView4);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");

        try {
            readsqlLight();
            if (Czas[0] == null) {
                wczytywanie();
            }
        }catch (Exception e){}

        try {
            readsqlLigt1();
            if (uzytkownik[0] == null) {
                wczytywanie1();
            }
        }catch (Exception e){}

        for(int i=0;i<uzytkownik.length;i=i+0)
        {
            if(uzytkownik[i]!=null)
            {
                listaStringow.add(uzytkownik[i]);
            }
            i++;
        }

       kto_wykonal.setAdapter(new MyAdapter1(this, R.layout.custom_spiner, listaStringow));

        CustomAdapter7  adapter1 = new CustomAdapter7(this,Data, Czas, Nazwa,Ilosc,Czas_wykonania,Kto_wykonal);
        lista.setAdapter(adapter1);

        myCalendar = Calendar.getInstance();

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

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                datatxt.setText(Data[i]);
                czastxt.setText(Czas[i]);
                nazwa.setText(Nazwa[i]);
                ilosc.setText(Ilosc[i]);
                czas_wykonania.setText(Czas_wykonania[i]);
                for(int j=0; j<uzytkownik.length;j=j+0)
                {
                    if(Kto_wykonal[i].equals(uzytkownik[j]))
                    {
                        w=j;
                    }
                    j++;
                }
                kto_wykonal.setSelection(w);
                c=i;
            }
        });

        datatxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Wykonane_potrawy.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        czastxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Wykonane_potrawy.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            czastxt.setText(selectedHour + ":" + selectedMinute+":00");
                            czas = String.valueOf(selectedHour + ":" + selectedMinute+":00");

                    }
                }, hour, minute, true);//Yes 24 hour time

                mTimePicker.setTitle("Time Selected");
                mTimePicker.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Wykonane_potrawy.this, Magzyn.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", W);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });

        utworz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datatxt.getText().toString()!=null) {
                saveSqlLight();
                wartosc=3;
                funkcjonalnosci();
                finish();
                startActivity(getIntent());
                }else {
                    showToast("Uzupełnij wszystkie pola");
                }
            }
        });
        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datatxt.getText().toString()!=null) {
                    deletefromSQLlight();
                    wartosc = 2;
                    funkcjonalnosci();
                    finish();
                    startActivity(getIntent());
                }else {
                    showToast("Uzupełnij wszystkie pola");
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datatxt.getText().toString()!=null) {
                    deletefromSQLlight();
                    saveSqlLight();
                     funkcjonalnosci1();
                    finish();
                    startActivity(getIntent());
                }else {
                    showToast("Uzupełnij wszystkie pola");
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
            o=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text1);
            main_text.setText(uzytkownik[position]);
            return mySpinner;
        }}
}
