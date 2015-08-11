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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
    Double zm1 =null;
    int Numer;
    int x,w,c;

    customAdapter1 adapter1;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    Bitmap thumbnail;
    static ResultSet rs;
    static Statement st;
    Connection connection = null;

    ListView lista,lista1;
    TextView Txt,Txt1;

    Button menu1,menu2,menu3,menu4,menu5,menu6,menu7,menu8,menu9,menu10,menu11,menu12,menu13,menu14,menu15,menu16;
    Button rabat,napiwek,anulacja,odswierz,przerwa,wyjdz,kasa;

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
    //TODO MAmy tutaj problem ziom :) - ju¿ nie !!!
    //anulacja ca³ego zamówienia
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
            showToast("brak po³¹czenia z internetem");
        }
        showToast(Klient[Numer]);
    }
    //odczyt zamowienia
    public void SqlLight()
    {

        int q=0; zm1=null;
        for(int i = 0; i < x; i=i+0){
            if(klient[Numer].equals(Klient[i])){
                danie[q]=Danie[i];
                ilosc[q]=Ilosc[i];
                zdj[q]=Zdjecie[i];
              //  zm1=zm1+Suma[i]; //Suma ma byæ double i tyle :) Dobranoc
                q=q+1;
            }
            i++;
        }

        adapter1=new customAdapter1(this, danie,ilosc,zdj,q);
        lista.setAdapter(adapter1);
        Txt.setText("Nazwa: "+ Klient[Numer]);
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
            connection = DriverManager.getConnection("jdbc:mysql://54.217.215.74/sql481900", "sql481900", "qF9!gX2*");
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
        kasa = (Button) findViewById(R.id.button17);


        //odczyt z bazy danych i z pliku
        try{readsqlLight();}catch (Exception e){}
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
                zm1 = (zm1 * 20) / 100;
                Txt1.setText("Suma: " + String.valueOf(zm1));
            }
        });

        odswierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);

            }
        });

        anulacja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anulacja_sqlLight_SQL();
            }
        });

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Numer=position;
               // lista.setAdapter(null);
                SqlLight();

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
