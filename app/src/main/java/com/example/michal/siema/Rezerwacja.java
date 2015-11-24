package com.example.michal.siema;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
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
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Rezerwacja extends ActionBarActivity {

    String[] Sala = {"Sala_1","Sala_2","Sala_3","Sala_4","Sala_5"};
    String[] Stolik = {"Stolik_1","Stolik_2","Stolik_3","Stolik_4","Stolik_5","Stolik_6","Stolik_7","Stolik_8","Stolik_9",
            "Stolik_10","Stolik_11","Stolik_12","Stolik_13","Stolik_14","Stolik_15"};

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    int c,d,a,e,wartosc;
    Button ok,anuluj,spr;
    EditText Klient;
    Spinner sala;
    Spinner stolik;
    String data,czas,klient;
    String[] Sdata = new String[20];
    String[] Sczas = new String[20];
    String[] Sklient = new String[20];
    String[] Ssala = new String[20];
    String[] Ssztuki = new String[20];
    private PopupWindow mpopup;
    View popUpView;
    ListView lista;
    String Sczas1;
    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Rezerwacja";

    Bundle applesData;
    String s,m,k,w;

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    Connection connection = null;

    EditText txtDate,edt_time;
    Calendar myCalendar;
    private SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener date;


    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtDate.setText(sdf.format(myCalendar.getTime()));
        data=(sdf.format(myCalendar.getTime()));
    }

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
                    " (Stolik VARCHAR, Klient VARCHAR ,Czas VARCHAR,Czas1 VARCHAR,Data VARCHAR);");

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
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            showToast("brak polaczenia z internetem");
            return;
        }

    }

    private void funkcjonalnosci() {
        ToDataBase();

        if(wartosc==1)
        {
            try {
                SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

                 int x=0;
                Cursor c=sampleDB.rawQuery("SELECT * FROM Rezerwacja",null);
                while (c.moveToNext())
                {
                    Ssala[x] = String.valueOf(c.getString(0));
                    Sklient[x] = String.valueOf(c.getString(1));
                    Sczas[x] = String.valueOf(c.getString(2));
                    Sdata[x]= String.valueOf(c.getString(4));
                    Ssztuki[x]=String.valueOf(x);
                    x++;

                }
                sampleDB.close();
            }catch (Exception e){}
        }

        if(wartosc==3)
        {
            try {
                SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                sampleDB.execSQL("DELETE FROM Rezerwacja WHERE Klient=('" + Sklient[a] + "') AND Czas=('" + Sczas[a] + "')");
                sampleDB.close();
            }catch (Exception e){}

        }
        if(wartosc==2) {
            try {
                SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                sampleDB.execSQL("INSERT INTO Rezerwacja (Stolik,Klient,Czas,Czas1,Data) VALUES ('" + Sala[c] + " / " + Stolik[d] + "','" + klient + "','" + czas + "','" + Sczas1 + "','" + data + "')");
                sampleDB.close();

            } catch (Exception e) {
                showToast(e + "");
            }
        }
        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
            if(wartosc==1) {
                String sql = "SELECT * FROM Rezerwacja";

                try {
                    rs = st.executeQuery(sql);
                } catch (SQLException e1) {
                    //  e1.printStackTrace();
                }
                try {
                    int i = 0;
                    while (rs.next()) {

                        Ssala[i] = rs.getString("Stolik");
                        Sklient[i] = rs.getString("Klient");
                        Sczas[i] = rs.getString("Czas");
                        Sdata[i] = rs.getString("Data");
                        Ssztuki[i] = String.valueOf(i);
                        i++;
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();

                }
            }
            if(wartosc==2) {
                String sql = "INSERT INTO Rezerwacja" + " VALUES('" + Sala[c] + " / " + Stolik[d] + "','" + klient + "','" + czas + "','" + Sczas1 + "','" + data + "')";

                try {
                    st.executeUpdate(sql);
                } catch (SQLException e1) {
                    // e1.printStackTrace();
                }
            }
            if(wartosc==3)
            {
                String sql = "DELETE FROM Rezerwacja WHERE Klient=('" + Sklient[a] + "') AND Czas=('" + Sczas[a] + "')";

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
        }}

    public void lista()
    {
        lista = (ListView) popUpView.findViewById(R.id.listView2);
        customAdapter3 adapter = new customAdapter3( this,Ssztuki,Ssala,Sklient,Sczas,Sdata);
        lista.setAdapter(adapter);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezerwacja);

        sala = (Spinner) findViewById(R.id.spinner2);
        stolik = (Spinner) findViewById(R.id.spinner4);
        Klient = (EditText) findViewById(R.id.editText8);
        ok = (Button) findViewById(R.id.button60);
        spr = (Button) findViewById(R.id.button44);
        anuluj = (Button) findViewById(R.id.button61);
        stolik.setAdapter(new Rezerwacja.MyAdapter(this, R.layout.custom_spiner1, Stolik));
        sala.setAdapter(new Rezerwacja.Adapter(this, R.layout.custom_spiner1, Sala));

        myCalendar = Calendar.getInstance();
        edt_time=(EditText) findViewById(R.id.time1);
        txtDate = (EditText) findViewById(R.id.data1);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        w = applesData.getString("wszystko");

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

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Rezerwacja.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Rezerwacja.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour<10)
                        {
                            edt_time.setText("0"+ selectedHour + " : " + selectedMinute);
                            czas=String.valueOf("0"+ selectedHour + " : " + selectedMinute);
                            Sczas1 = String.valueOf(selectedHour+"."+ selectedMinute);
                        }
                        if(selectedMinute<10)
                        {
                            edt_time.setText(selectedHour + " : " + "0"+ selectedMinute);
                            czas=String.valueOf(selectedHour + " : " +"0"+ selectedMinute);
                            Sczas1 = String.valueOf(selectedHour +"."+ "0"+selectedMinute);
                        }
                        if(selectedHour<10&selectedMinute<10)
                        {
                            edt_time.setText("0"+selectedHour + " : " + "0"+ selectedMinute);
                            czas=String.valueOf("0"+selectedHour + " : " +"0"+ selectedMinute);
                            Sczas1 = String.valueOf(selectedHour +"."+"0"+ selectedMinute);
                        }
                        if(selectedHour>10&selectedMinute>10) {
                            edt_time.setText(selectedHour + " : " + selectedMinute);
                            czas = String.valueOf(selectedHour + " : " + selectedMinute);
                            Sczas1 = String.valueOf(selectedHour +"."+ selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time

                mTimePicker.setTitle("Time Selected");
                mTimePicker.show();
            }
        });

        spr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpView = getLayoutInflater().inflate(R.layout.spr_rezerwacji, null);
                // inflating popup layout
                mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //Creation of popup
                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
                mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

                wartosc = 1;
                funkcjonalnosci();
                lista();
               lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       e = 1;
                       a = i;
                   }
               });
                Button usun = (Button) popUpView.findViewById(R.id.button44);

                usun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (e == 1) {
                                wartosc = 3;
                                funkcjonalnosci();
                                finish();
                                startActivity(getIntent());
                            } else {
                                showToast("Wybierz rezerwacje do usuniecia");
                            }
                        } catch (Exception e) {
                        }
                    }
                });


                Button btnCancel = (Button) popUpView.findViewById(R.id.button43);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mpopup.dismiss();
                    }
                });



            }

        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    try {
    klient = Klient.getText().toString();
    wartosc = 2;
    if (data != null & czas != null) {
        funkcjonalnosci();
        finish();
        startActivity(getIntent());
    } else {
        showToast("Uzupełnij wszystkie dane");
    }
        }catch (Exception e){}
            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Rezerwacja.this,MainActivity.class);
                c.putExtra("sala_sprzedazy", s);
                c.putExtra("wszystko", w);
                c.putExtra("magazyn", m);
                c.putExtra("kuchnia", k);
                startActivity(c);
            }
        });

    }

    public class MyAdapter extends ArrayAdapter<String>
    {
        public MyAdapter(Context ctx, int txtViewResourceId, String[] objects)
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
            View mySpinner = inflater.inflate(R.layout.custom_spiner1, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text2);
            main_text.setText(Stolik[position]);
            return mySpinner;
        }}

    public class Adapter extends ArrayAdapter<String>
    {
        public Adapter(Context ctx, int txtViewResourceId, String[] objects)
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
            View mySpinner = inflater.inflate(R.layout.custom_spiner1, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text2);
            main_text.setText(Sala[position]);
            return mySpinner;
        }}
}
