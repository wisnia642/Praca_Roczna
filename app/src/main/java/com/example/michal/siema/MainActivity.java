package com.example.michal.siema;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
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


public class MainActivity extends ActionBarActivity {

    String posilek, userMassage;
    int stan = 0;
    String[] zdjecie = new String[17];
    String[] Klient = new String[20];
    String[] klient = new String[20];
    String[] danie = new String[20];
    String[] ilosc = new String[20];
    String[] zdj = new String[20];
    String[] Danie = new String[20];
    String[] Ilosc = new String[20];
    String[] Zdjecie = new String[20];
    Double[] Suma = new Double[20];
    String zm=null;
    Double zm1;
    Double zm2;
    int Numer,Numer1,wartosc;
    int x,w,c,a,q,z;

    List<String> listaStringow = new ArrayList<String>();
    Spinner Stolik;

    private PopupWindow mpopup;
    customAdapter1 adapter1;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    Bitmap thumbnail;
    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    ListView lista,lista1;
    TextView Txt,Txt1;

    Button menu1,menu2,menu3,menu4,menu5,menu6,menu7,menu8,menu9,menu10,menu11,menu12,menu13,menu14,menu15,menu16;
    Button rabat,napiwek,anulacja,odswierz,przerwa,wyjdz,karta,gotowka,faktura;
    Button dodaj,odejmnij,usun;


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
                    " (Nazwa VARCHAR ,Zdjecie VARCHAR,Stan INT);");

        } catch (Exception e) {
        }

    }

    private void funkcjonalnosci()
    {
            try {
                SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                if(wartosc==1) {
                    sampleDB.execSQL("UPDATE Zamowienie SET Ilosc=('" + ilosc[Numer1] + "') WHERE Danie=('" + danie[Numer1] + "') AND Klient=('" + klient[Numer] + "') ");
                }
                if(wartosc==2) {
                    sampleDB.execSQL("DELETE FROM Zamowienie WHERE Danie=('" + danie[Numer1] + "') AND Klient=('" + klient[Numer] + "')");
                }
                if(wartosc==3)
                {
                    sampleDB.execSQL("INSERT INTO Zamowienie (Suma) VALUES ('"+zm2+"') WHERE Klient= ('"+klient[w]+"')" );
                }
                sampleDB.close();

            }catch (Exception e)
            {showToast(e+"");}

        connect();
        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
            if(wartosc==2){
            String sql = "DELETE FROM Zamowienie WHERE Danie=('"+danie[Numer1]+"') AND Klient=('"+klient[Numer]+"')";
            try {
                st.executeUpdate(sql);
            } catch (SQLException e1) {
                // e1.printStackTrace();
            }}
        }

        if(wartosc==1){
            String sql = "UPDATE Zamowienie SET Ilosc=('" + ilosc[Numer1] + "') WHERE Danie=('" + danie[Numer1] + "') AND Klient=('" + klient[Numer] + "') ";

            try {
                st.executeUpdate(sql);
            } catch (SQLException e1) {
                // e1.printStackTrace();
            }}
        if(wartosc==3)
        {
            String sql = "INSERT INTO Zamowienie (Suma) VALUES (?) WHERE Klient= ('"+klient[w]+"')";
            try{
                ps = connection.prepareStatement(sql);
                ps.setString(7, String.valueOf(zm2));
                ps.executeUpdate();
                connection.commit();

            } catch (SQLException e) {

            }
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            showToast("brak połączenia z internetem");
        }
        }

    private void readsqlLight() {
        ToDataBase();
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            for (int i = 0; i <= 17; i = i + 0) {
                Cursor c = sampleDB.rawQuery("SELECT * FROM Karta WHERE Stan='" + i + "'", null);
                if (c.moveToFirst()) {
                    zdjecie[i] = String.valueOf(c.getString(1));

                }
                i++;
            }

            Cursor c  = sampleDB.rawQuery("SELECT * FROM ZAMOWIENIE",null);

           while (c.moveToNext()) {
               zm = String.valueOf(c.getString(1));
               if(zm!=null){
                Klient[x] = String.valueOf(c.getString(0));
                   Danie[x] = String.valueOf(c.getString(1));
                   Ilosc[x] = String.valueOf(c.getString(2));
                   Zdjecie[x] = String.valueOf(c.getString(5));
                   Suma[x] = Double.valueOf(c.getDouble(6));
                x++;}
            }
            sampleDB.close();
        } catch (Exception a) {
        }
        customAdapter2 adapter2=new customAdapter2(this, klient);
        lista1.setAdapter(adapter2);
    }
    //TODO MAmy tutaj problem ziom :) - juz nie !!!
    //anulacja calego zamowienia
    public void anulacja_sqlLight_SQL()
    {
        try{
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("DELETE FROM Zamowienie WHERE Klient=('" + Klient[Numer] + "')");

            sampleDB.close();

        }catch (Exception a){}

        connect();

        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
            String sql = "DELETE FROM Zamowienie WHERE Klient=('" + Klient[Numer] + "')";


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
        showToast("Anulacja rachunku zakończona" + Klient[Numer]);
    }
    //odczyt zamowienia
    public void SqlLight()
    {
        for(int i=0;i<q;i=i+0)
        {
            danie[i]=null;
            ilosc[i]=null;
            zdj[i]=null;
            i++;
        }
       // a=q;
        q=0;
        zm1=0.0;
        zm2=0.0;
        for(int i = 0; i < x; i=i+0){
            if(klient[Numer].equals(Klient[i])){
                danie[q]=Danie[i];
                ilosc[q]=Ilosc[i];
                zdj[q]=Zdjecie[i];
                zm1=zm2+Suma[i];
                zm2=zm1;
                q=q+1;
            }
            i++;
        }

        adapter1=new customAdapter1(this, danie,ilosc,zdj,q);
        lista.setAdapter(adapter1);
        Txt.setText("Nazwa: " + klient[Numer]);
        Txt1.setText("Suma: " + String.valueOf(zm1));

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

    //wczytywanie danych z tablicy do bazy danych
    public void wczytywanie() {

        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


                String sql = "SELECT * FROM ZAMOWIENIE";

                try {
                    rs=st.executeQuery(sql);
                } catch (SQLException e1) {
                    //  e1.printStackTrace();
                }
                try{
                    int i=0;
                    while (rs.next())
                    {
                        Klient[i] = rs.getString(0);
                        Danie[i] = rs.getString(1);
                        Ilosc[i] = rs.getString(2);
                        Suma[i] = rs.getDouble(6);
                        i++;
                    }
                } catch (SQLException e1)
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


    private void zdjecie1() {
        //wyswietlanie zdjec na poczatku programu

        if (zdjecie[0].equals("brak")) {
            menu1.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[0]));
            menu1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private void zdjecie2() {
        if (zdjecie[1].equals("brak")) {
            menu2.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[1]));
            menu2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private void zdjecie3() {
        if (zdjecie[2].equals("brak")) {
            menu3.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[2]));
            menu3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private void zdjecie4() {
        if (zdjecie[3].equals("brak")) {
            menu4.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[3]));
            menu4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private void zdjecie5() {
        if (zdjecie[4].equals("brak")) {
            menu5.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[4]));
            menu5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private void zdjecie6() {
        if(zdjecie[5].equals("brak")){
            menu6.setBackgroundResource(R.drawable.brak);
        } else{
            thumbnail = (BitmapFactory.decodeFile(zdjecie[5]));
            menu6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

    }
    private  void zdjecie7()
    {
        if(zdjecie[6].equals("brak")){
            menu7.setBackgroundResource(R.drawable.brak);}
    else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[6]));
            menu7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie8() {
        if (zdjecie[7].equals("brak")) {
            menu8.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[7]));
            menu8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie9() {
        if (zdjecie[8].equals("brak")) {
            menu9.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[8]));
            menu9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie10() {
        if (zdjecie[9].equals("brak")) {
            menu10.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[9]));
            menu10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie11(){
        if(zdjecie[10].equals("brak")) {menu11.setBackgroundResource(R.drawable.brak);}
        else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[10]));
            menu11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}
    }
    private  void zdjecie12() {
        if (zdjecie[11].equals("brak")) {
            menu12.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[11]));
            menu12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie13() {
        if (zdjecie[12].equals("brak")) {
            menu13.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[12]));
            menu13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie14() {
        if (zdjecie[13].equals("brak")) {
            menu14.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[13]));
            menu14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie15() {
        if (zdjecie[14].equals("brak")) {
            menu15.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[14]));
            menu15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }
    private  void zdjecie16(){
        if(zdjecie[15].equals("brak")) {menu16.setBackgroundResource(R.drawable.brak);}
        else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[15]));
            menu16.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button Sala1 = (Button) findViewById(R.id.button6);
       Button Sala2 = (Button) findViewById(R.id.button7);
       Button Sala3 = (Button) findViewById(R.id.button8);
       Button Sala4 = (Button) findViewById(R.id.button9);
       Button Sala5 = (Button) findViewById(R.id.button10);

        Txt = (TextView) findViewById(R.id.textView);
        Txt1 = (TextView) findViewById(R.id.textView2);

        menu1 = (Button) findViewById(R.id.button19);
        menu2 = (Button) findViewById(R.id.button18);
        menu3 = (Button) findViewById(R.id.button27);
        menu4 = (Button) findViewById(R.id.button20);
        menu5 = (Button) findViewById(R.id.button30);
        menu6 = (Button) findViewById(R.id.button31);
        menu7 = (Button) findViewById(R.id.button33);
        menu8 = (Button) findViewById(R.id.button32);
        menu9 = (Button) findViewById(R.id.button21);
        menu10 = (Button) findViewById(R.id.button22);
        menu11 = (Button) findViewById(R.id.button28);
        menu12 = (Button) findViewById(R.id.button23);
        menu13 = (Button) findViewById(R.id.button24);
        menu14 = (Button) findViewById(R.id.button25);
        menu15 = (Button) findViewById(R.id.button29);
        menu16 = (Button) findViewById(R.id.button26);

        lista = (ListView) findViewById(R.id.lista);
        lista1 = (ListView) findViewById(R.id.lista2);

        wyjdz = (Button) findViewById(R.id.button11);
        przerwa = (Button) findViewById(R.id.button12);
        napiwek = (Button) findViewById(R.id.button13);
        rabat = (Button) findViewById(R.id.button14);
        anulacja = (Button) findViewById(R.id.button15);
        odswierz = (Button) findViewById(R.id.button16);
        karta = (Button) findViewById(R.id.Button17);
        gotowka = (Button) findViewById(R.id.button36);
        faktura = (Button) findViewById(R.id.button37);

        dodaj = (Button) findViewById(R.id.button2);
        usun = (Button) findViewById(R.id.button3);
        odejmnij = (Button) findViewById(R.id.button4);

        //odczyt z bazy danych i z pliku
        try{readsqlLight();}catch (Exception e){}
        try{wczytywanie();} catch (Exception e){}
        try{zdjecie1();}catch (Exception e){}
        try{zdjecie2();}catch (Exception e){}
        try{zdjecie3();}catch (Exception e){}
        try{zdjecie4();}catch (Exception e){}
        try{zdjecie5();}catch (Exception e){}
        try{zdjecie6();}catch (Exception e){}
        try{zdjecie7();}catch (Exception e){}
        try{zdjecie8();}catch (Exception e){}
        try{zdjecie9();}catch (Exception e){}
        try{zdjecie10();}catch (Exception e){}
        try{zdjecie11();}catch (Exception e){}
        try{zdjecie12();}catch (Exception e){}
        try{zdjecie13();}catch (Exception e){}
        try{zdjecie14();}catch (Exception e){}
        try{zdjecie15();}catch (Exception e){}
        try{zdjecie16();}catch (Exception e){}

        for (int i=0; i < x; i = i+ 0) {
            for (int j = 0; j < x; j = j+ 0) {
                if(j==0)
                {
                    j=j+i;
                }

                if (Klient[j].equals(Klient[i])) {
                    w = w + 1;
                }
                j = j + 1;
            }
            if (w == 1) {
                klient[c] = Klient[i];
                listaStringow.add(klient[c]);
                c=c+1;

            }
            w = 0;
            i=i+1;
        }
       c=0;


        customAdapter2 adapter2=new customAdapter2(this, klient);
        lista1.setAdapter(adapter2);

        rabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popUpView = getLayoutInflater().inflate(R.layout.popup, null);
                // inflating popup layout
                mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //Creation of popup
                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
                mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

                final TextView textV = (TextView) popUpView.findViewById(R.id.textView33);
                textV.setText("Rabat w %");

                Button btnOk = (Button) popUpView.findViewById(R.id.button60);
                final EditText editT = (EditText) popUpView.findViewById(R.id.editText5);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            double zm3 = Double.parseDouble(editT.getText().toString());
                            zm2 = zm1 - ((zm1 * zm3) / 100);
                            zm2 *= 100; // zaokraglanie
                            zm2 = Double.valueOf(Math.round(zm2));
                            zm2 /= 100;
                            Txt1.setText("Suma + rabat: " + String.valueOf(zm2));
                            mpopup.dismiss();
                        } catch (Exception e) {
                        }
                    }
                });

                Button btnCancel = (Button) popUpView.findViewById(R.id.button61);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mpopup.dismiss();
                    }
                });

                wartosc = 3;
                funkcjonalnosci();

            }
        });

        odswierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);

            }
        });

        anulacja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anulacja_sqlLight_SQL();
            }


        });

        karta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Brak czytnika kart płatniczych");
            }
        });

        gotowka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Gotowka1.class);
                i.putExtra("applesMessage", zm2);
                startActivity(i);

            }

        });

        faktura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Faktura.class);
                startActivity(i);
            }
        });


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    z = Integer.parseInt(ilosc[Numer1]);
                    z++;
                    ilosc[Numer1] = String.valueOf(z);
                    wartosc = 1;
                    showToast("wybież danie do zwiększenia ilości");
                }catch (Exception e){}
            }
        });

        odejmnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    z = Integer.parseInt(ilosc[Numer1]);
                    z--;
                    ilosc[Numer1] = String.valueOf(z);
                    wartosc = 1;
                    showToast("wybież danie do zmniejszenia ilości");
                }catch (Exception e) {}
            }
        });

        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wartosc=2;
                showToast("Wybież danie do usunięcia");
            }
        });

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Numer = position;
                    // lista.setAdapter(null);
                    SqlLight();
                } catch (Exception e) {
                }

            }

        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   Numer1=position;

                if(wartosc==1||wartosc==2) {
                    funkcjonalnosci();
                }
            }

        });

        napiwek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popUpView = getLayoutInflater().inflate(R.layout.popup, null);
                // inflating popup layout
                mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //Creation of popup
                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
                mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

                final TextView textV = (TextView) popUpView.findViewById(R.id.textView33);
                textV.setText("Napiwek w zł");

                Button btnOk = (Button)popUpView.findViewById(R.id.button60);
                final EditText editT = (EditText) popUpView.findViewById(R.id.editText5);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            zm2 = Double.valueOf(editT.getText().toString());
                            zm2 = zm1 + zm2;
                            Txt1.setText("Suma + napiwek: " + String.valueOf(zm2));
                            mpopup.dismiss();
                        }catch (Exception e){}
                    }
                });

                Button btnCancel = (Button)popUpView.findViewById(R.id.button61);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mpopup.dismiss();
                    }
                });

                wartosc=3;
                funkcjonalnosci();
            }
        });

        Sala1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Sala_1.class);
                startActivity(i);
            }
        });
        Sala2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Sala_2.class);
                startActivity(i);
            }
        });
        Sala4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Sala_3.class);
                startActivity(i);
            }
        });
        Sala3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Sala_4.class);
                startActivity(i);
            }
        });
        Sala5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Sala_5.class);
                startActivity(i);
            }
        });

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Makarony";
                stan = 0;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Przystawki";
                stan=3;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Ryba";
                stan=8;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Salatki";
                stan=9;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Fast_Food";
                stan=11;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Pizza";
                stan=12;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Zupy";
                stan=13;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Suszi";
                stan = 15;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Wina";
                stan=2;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });menu10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Piwo";
                stan=10;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Desery";
                stan=14;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Dodatki";
                stan=4;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Napoje_Gazowane";
                stan=5;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Napoje_Zimne";
                stan=7;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Napoje_Gorace";
                stan=6;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);

            }
        });
        menu16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Lista.class);
                userMassage = "Soki";
                stan = 1;
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
