package com.example.michal.siema;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.michal.siema.R.layout;


public class Sala_3 extends ActionBarActivity {

//menu zablokuj ustawienie , odblokuj ustawienie

    String[] tablica = new String[32];
    double[] tab = new double[32];
    int[] licz = new int[31];
    int zm;
    int zm1=15;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Sala3";

    static ResultSet rs;
    static Statement st;
    Connection connection = null;

    private static final int CAMERA_PIC_REQUEST = 1111;

    TextView img1 = null,img2 = null,img3 = null,img4 = null,img5 = null,img6 = null,img7 = null,img8 = null,img9 = null;
    TextView img10 = null,img11 = null,img12 = null,img13 = null,img14 = null,img15 = null;
    AbsoluteLayout aLayout;
    boolean stan=false;

    public static final int PIERWSZY_ELEMENT = 1;
    public static final int DRUGI_ELEMENT = 2;
    public static final int TRZECI_ELEMENT = 3;
    public static final int CZWARTY_ELEMENT = 4;
    public static final int PIATY_ELEMENT = 5;
    public static final int SZUSTY_ELEMENT = 6;
    public static final int SIUDMY_ELEMENT = 7;
    public static final int OSMY_ELEMENT = 8;
    public static final int DZIEWIATY_ELEMENT = 9;
    public static final int DZIESIATY_ELEMENT = 10;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void ToDataBase()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    SAMPLE_TABLE_NAME +
                    " (Id INT ,Sala3 DOUBLE,Zdjecie VARCHAR,Stan INT);");

        }
        catch (Exception e){}

    }

    private void ResetSqlLigt()
    {
        ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("DELETE FROM Sala3");

            for (int i = 0; i <= 29; i = i + 0) {
                sampleDB.execSQL("INSERT INTO Sala3 ('Id') VALUES ('"+i+"')");
                i++;
            }
            sampleDB.close();
        }catch (Exception e){showToast("Blad w update");}
    }

    private void ResetMySql()
    {
        connect();

        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
            String sql = "DELETE FROM Sala3";


            try {
                st.executeUpdate(sql);
            } catch (SQLException e1) {
                // e1.printStackTrace();
            }
        }

        for (int i = 0; i <= 29; i = i + 0) {
            String sql = "INSERT INTO Sala3 " + "VALUES ('"+i+"',0)";
            i++;

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

    private void writeToDataBase()
    {
        ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            for (int i = 0; i <= 29; i = i + 0) {
                sampleDB.execSQL("UPDATE Sala3 SET Sala3=('" + tab[i] + "'),Zdjecie=('" + tablica[30] + "') WHERE Id=('" + i + "') ");
                //sampleDB.execSQL("INSERT INTO Stol ('Id') VALUES ('"+i+"')");

                i++;
            }
            sampleDB.close();
        }catch (Exception e){showToast("Blad w update");}
    }

    private void readFromDataBase()
    {
        try{
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            for (int i = 0; i <= 29; i = i + 0) {
                Cursor c=sampleDB.rawQuery("SELECT * FROM Sala3 WHERE Id='"+i+"'",null);
                if(c.moveToFirst())
                {
                    tab[i]= Double.parseDouble(c.getString(1));
                    tablica[30] = String.valueOf(c.getString(2));
                    zm1 = Integer.parseInt(c.getString(3));
                }
                i++;
            }

            sampleDB.close();
            stan = true;
        }catch (Exception a){}


    }
    //zapis danych do bazy danych

    public void connectToDataBase() {

        connect();

        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }

            for (int i = 0; i <= 29; i = i + 0) {
                String sql = "UPDATE Sala3 SET Sala3=(" + tab[i] + ") WHERE ID=(" + i + ")";
                i++;

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

    //tworzenie połączenia z bazą danych
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
            showToast("brak połączenia z internetem");
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

            for(int i =0; i<=29;i=i+0) {
                String sql = "SELECT * FROM Sala3 WHERE ID=("+i+")";


                try {
                    rs=st.executeQuery(sql);
                } catch (SQLException e1) {
                    //  e1.printStackTrace();
                }
                try{
                    while (rs.next())
                    {
                        tab[i]=rs.getDouble("Sala3");

                    }
                } catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                i++;
            }
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                showToast("brak połączenia z internetem");}

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_sala_1);
        aLayout = (AbsoluteLayout) findViewById(R.id.absLayout);

        //tworzenie kontaktu z stolikami
        img1 = (TextView) findViewById(R.id.textView1);
        img2 = (TextView) findViewById(R.id.textView2);
        img3 = (TextView) findViewById(R.id.textView3);
        img4 = (TextView) findViewById(R.id.textView4);
        img5 = (TextView) findViewById(R.id.textView5);
        img6 = (TextView) findViewById(R.id.textView6);
        img7 = (TextView) findViewById(R.id.textView7);
        img8 = (TextView) findViewById(R.id.textView8);
        img9 = (TextView) findViewById(R.id.textView9);
        img10 = (TextView) findViewById(R.id.textView10);
        img11 = (TextView) findViewById(R.id.textView11);
        img12 = (TextView) findViewById(R.id.textView12);
        img13 = (TextView) findViewById(R.id.textView13);
        img14 = (TextView) findViewById(R.id.textView14);
        img15 = (TextView) findViewById(R.id.textView15);


        //odczyt z bazy danych i z pliku
        try {
            readFromDataBase();
            try {
                Bitmap thumbnail = (BitmapFactory.decodeFile(tablica[30]));
                // Log.w("path of image from gallery......******************.........", picturePath+"");
                img1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                img15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            } catch (Exception e) {
            }

            if (tab[0] != 0 || tab[1] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[0], (int) tab[1]);
                img1.setLayoutParams(lp);
            }

            if (tab[2] != 0 & tab[3] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[2], (int) tab[3]);
                img2.setLayoutParams(lp);

                if(zm1<2){img2.setVisibility(View.INVISIBLE);}}

            if (tab[4] != 0 & tab[5] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[4], (int) tab[5]);
                img3.setLayoutParams(lp);
                if(zm1<3){img3.setVisibility(View.INVISIBLE);}}

            if (tab[6] != 0 & tab[7] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[6], (int) tab[7]);
                img4.setLayoutParams(lp);
                if(zm1<4){img4.setVisibility(View.INVISIBLE);}}
            if (tab[8] != 0 || tab[9] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[8], (int) tab[9]);
                img5.setLayoutParams(lp);
                if(zm1<5){img5.setVisibility(View.INVISIBLE);}}

            if (tab[10] != 0 & tab[11] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[10], (int) tab[11]);
                img6.setLayoutParams(lp);
                if(zm1<6) {img6.setVisibility(View.INVISIBLE);}}

            if (tab[12] != 0 & tab[13] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[12], (int) tab[13]);
                img7.setLayoutParams(lp);
                if(zm1<7) {img7.setVisibility(View.INVISIBLE);}}

            if (tab[14] != 0 & tab[15] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[14], (int) tab[15]);
                img8.setLayoutParams(lp);
                if(zm1<8) {img8.setVisibility(View.INVISIBLE);}}
            if (tab[16] != 0 || tab[17] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[16], (int) tab[17]);
                img9.setLayoutParams(lp);
                if(zm1<9) {img9.setVisibility(View.INVISIBLE);}}

            if (tab[18] != 0 & tab[19] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[18], (int) tab[19]);
                img10.setLayoutParams(lp);
                if(zm1<10) {img10.setVisibility(View.INVISIBLE);}}

            if (tab[20] != 0 & tab[21] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[20], (int) tab[21]);
                img11.setLayoutParams(lp);
                if(zm1<11) {img11.setVisibility(View.INVISIBLE);}}

            if (tab[22] != 0 & tab[23] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[22], (int) tab[23]);
                img12.setLayoutParams(lp);
                if(zm1<12) {img12.setVisibility(View.INVISIBLE);}}
            if (tab[24] != 0 & tab[25] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[24], (int) tab[25]);
                img13.setLayoutParams(lp);
                if(zm1<13) {img13.setVisibility(View.INVISIBLE);}}
            if (tab[26] != 0 & tab[27] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[26], (int) tab[27]);
                img14.setLayoutParams(lp);
                if(zm1<14){img14.setVisibility(View.INVISIBLE);}}
            if (tab[28] != 0 & tab[29] != 0) {

                AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) tab[28], (int) tab[29]);
                img15.setLayoutParams(lp);
                if(zm1<15){img15.setVisibility(View.INVISIBLE);}}
        }catch (Exception e){}



        img1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_1";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[0] = 0;
                            return false;
                        }
                        if (stan==false) {
                            zm = 1;
                            licz[0]++;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_2";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[1] = 0;
                            return false;
                        }
                        if (stan==false) {
                            zm=2;
                            licz[1]++;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_3";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[2] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[2]++;
                            zm = 3;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_4";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[3] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[3]++;
                            zm = 4;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_5";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[4] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[4]++;
                            zm = 5;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img6.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {


                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_6";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[5] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[5]++;
                            zm = 6;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img7.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_7";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[6] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[6]++;
                            zm = 7;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img8.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_8";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[7] = 0;
                            return false;

                        }
                        if (stan==false) {
                            licz[7]++;
                            zm = 8;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img9.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_9";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[8] = 0;
                            return false;

                        }
                        if (stan==false) {
                            licz[8]++;
                            zm = 9;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img10.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_10";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[9] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[9]++;
                            zm = 10;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img11.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_11";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[10] = 0;
                            return false;

                        }
                        if (stan==false) {
                            licz[10]++;
                            zm = 11;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img12.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_12";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[11] = 0;
                            return false;

                        } if (stan==false) {
                            licz[11]++;
                            zm = 12;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img13.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_13";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[12] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[12]++;
                            zm = 13;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img14.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_14";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[13] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[0]++;
                            zm = 14;
                            return false;
                        }

                    }
                    default:
                        return false;
                }
            }
        });
        img15.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent evt) {

                switch (evt.getAction()) {
                    case MotionEvent.ACTION_DOWN: {


                        if (stan==true) {
                            Intent i = new Intent(Sala_3.this, Karta.class);
                            String userMassage = "Sala_3 / Stolik_15";
                            i.putExtra("Sala", userMassage);
                            startActivity(i);
                            licz[14] = 0;
                            return false;
                        }
                        if (stan==false) {
                            licz[14]++;
                            zm = 15;
                            return false;
                        }



                    }
                    default:
                        return false;
                }
            }
        });



        aLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                Log.i("touch", "" + event);

                if(stan==false) {

                    switch (zm) {
                        case 1: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img1.getWidth() / 2, (int) event.getY() - img1.getHeight() / 2);
                            img1.setLayoutParams(lp);

                            tab[0] = (double) (event.getX() - img1.getWidth() / 2);
                            tab[1] = (double) (event.getY() - img1.getHeight() / 2);

                            // writeToFile();

                        }
                        break;

                        case 2: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img2.getWidth() / 2, (int) event.getY() - img2.getHeight() / 2);
                            img2.setLayoutParams(lp);

                            tab[2] = (double) (event.getX() - img2.getWidth() / 2);
                            tab[3] = (double) (event.getY() - img2.getHeight() / 2);



                            // writeToFile();
                        }
                        break;
                        case 3: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img3.getWidth() / 2, (int) event.getY() - img3.getHeight() / 2);
                            img3.setLayoutParams(lp);

                            tab[4] = (double) (event.getX() - img3.getWidth() / 2);
                            tab[5] = (double) (event.getY() - img3.getHeight() / 2);


                            //writeToFile();
                        }
                        break;
                        case 4: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img4.getWidth() / 2, (int) event.getY() - img4.getHeight() / 2);
                            img4.setLayoutParams(lp);

                            tab[6] = (double) (event.getX() - img4.getWidth() / 2);
                            tab[7] = (double) (event.getY() - img4.getHeight() / 2);


                            // writeToFile();
                        }
                        break;
                        case 5: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img5.getWidth() / 2, (int) event.getY() - img5.getHeight() / 2);
                            img5.setLayoutParams(lp);

                            tab[8] = (double) (event.getX() - img5.getWidth() / 2);
                            tab[9] = (double) (event.getY() - img5.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 6: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img6.getWidth() / 2, (int) event.getY() - img6.getHeight() / 2);
                            img6.setLayoutParams(lp);

                            tab[10] = (double) (event.getX() - img6.getWidth() / 2);
                            tab[11] = (double) (event.getY() - img6.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 7: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img7.getWidth() / 2, (int) event.getY() - img7.getHeight() / 2);
                            img7.setLayoutParams(lp);

                            tab[12] = (double) (event.getX() - img7.getWidth() / 2);
                            tab[13] = (double) (event.getY() - img7.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 8: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img8.getWidth() / 2, (int) event.getY() - img8.getHeight() / 2);
                            img8.setLayoutParams(lp);

                            tab[14] = (double) (event.getX() - img8.getWidth() / 2);
                            tab[15] = (double) (event.getY() - img8.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 9: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img9.getWidth() / 2, (int) event.getY() - img9.getHeight() / 2);
                            img9.setLayoutParams(lp);

                            tab[16] = (double) (event.getX() - img9.getWidth() / 2);
                            tab[17] = (double) (event.getY() - img9.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 10: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img10.getWidth() / 2, (int) event.getY() - img10.getHeight() / 2);
                            img10.setLayoutParams(lp);

                            tab[18] = (double) (event.getX() - img10.getWidth() / 2);
                            tab[19] = (double) (event.getY() - img10.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 11: {

                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img11.getWidth() / 2, (int) event.getY() - img11.getHeight() / 2);
                            img11.setLayoutParams(lp);

                            tab[20] = (double) (event.getX() - img11.getWidth() / 2);
                            tab[21] = (double) (event.getY() - img11.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 12: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img12.getWidth() / 2, (int) event.getY() - img12.getHeight() / 2);
                            img12.setLayoutParams(lp);

                            tab[22] = (double) (event.getX() - img12.getWidth() / 2);
                            tab[23] = (double) (event.getY() - img12.getHeight() / 2);

                            // writeToFile();


                        }
                        break;
                        case 13: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img13.getWidth() / 2, (int) event.getY() - img13.getHeight() / 2);
                            img13.setLayoutParams(lp);

                            tab[24] = (double) (event.getX() - img13.getWidth() / 2);
                            tab[25] = (double) (event.getY() - img13.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 14: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img14.getWidth() / 2, (int) event.getY() - img14.getHeight() / 2);
                            img14.setLayoutParams(lp);

                            tab[26] = (double) (event.getX() - img14.getWidth() / 2);
                            tab[27] = (double) (event.getY() - img14.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        case 15: {
                            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT, (int) event.getX() - img15.getWidth() / 2, (int) event.getY() - img15.getHeight() / 2);
                            img15.setLayoutParams(lp);

                            tab[28] = (double) (event.getX() - img15.getWidth() / 2);
                            tab[29] = (double) (event.getY() - img15.getHeight() / 2);

                            // writeToFile();
                        }
                        break;
                        default: {
                        }
                        break;
                    }
                }
                return true;
            }
        });}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            //2
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            img1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            //3
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            //4
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image3.jpg");
            try {
                tablica[30]=String.valueOf(file);
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                //5
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }}



        if (requestCode == 2) {

            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            tablica[30] = picturePath;
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            // Log.w("path of image from gallery......******************.........", picturePath+"");
            img1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
            img15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));


        }
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        menu.add(0, PIERWSZY_ELEMENT, 0, "Zrób Zdjęcie");
        menu.add(1, DRUGI_ELEMENT, 0, "Dodaj zdjęcie");
        menu.add(2, TRZECI_ELEMENT, 0, "Zablokuj stoliki");
        menu.add(3, CZWARTY_ELEMENT, 0, "Odblokuj stoliki");
        menu.add(5, PIATY_ELEMENT, 0, "Dodaj stolik");
        menu.add(4, SZUSTY_ELEMENT, 0, "Usuń stolik");
        menu.add(6, SIUDMY_ELEMENT, 0, "Zapis");
        menu.add(7, OSMY_ELEMENT, 0, "export");
        menu.add(8, DZIEWIATY_ELEMENT, 0, "import");
        menu.add(9, DZIESIATY_ELEMENT, 0, "Reset stolikow");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()) {

            case PIERWSZY_ELEMENT:
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);
                break;
            case DRUGI_ELEMENT:
                intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                break;
            case TRZECI_ELEMENT:
                stan=true;
                break;
            case CZWARTY_ELEMENT:
                stan=false;
                break;
            case PIATY_ELEMENT:
                if(zm1>=2&&zm1<=15)
                {


                    switch (zm1)
                    {
                        case 2:img2.setVisibility(View.VISIBLE);break;
                        case 3:img3.setVisibility(View.VISIBLE);break;
                        case 4:img4.setVisibility(View.VISIBLE);break;
                        case 5:img5.setVisibility(View.VISIBLE);break;
                        case 6:img6.setVisibility(View.VISIBLE);break;
                        case 7:img7.setVisibility(View.VISIBLE);break;
                        case 8:img8.setVisibility(View.VISIBLE);break;
                        case 9:img9.setVisibility(View.VISIBLE);break;
                        case 10:img10.setVisibility(View.VISIBLE);break;
                        case 11:img11.setVisibility(View.VISIBLE);break;
                        case 12:img12.setVisibility(View.VISIBLE);break;
                        case 13:img13.setVisibility(View.VISIBLE);break;
                        case 14:img14.setVisibility(View.VISIBLE);break;
                        case 15:img15.setVisibility(View.VISIBLE);break;
                        default:{}
                    }
                    zm1=zm1+1;
                    showToast(String.valueOf(zm1));
                }
                break;
            case SZUSTY_ELEMENT:
                if(zm1>2||zm1<15)
                {
                    switch (zm1)
                    {
                        case 2:img2.setVisibility(View.INVISIBLE);break;
                        case 3:img3.setVisibility(View.INVISIBLE);break;
                        case 4:img4.setVisibility(View.INVISIBLE);break;
                        case 5:img5.setVisibility(View.INVISIBLE);break;
                        case 6:img6.setVisibility(View.INVISIBLE);break;
                        case 7:img7.setVisibility(View.INVISIBLE);break;
                        case 8:img8.setVisibility(View.INVISIBLE);break;
                        case 9:img9.setVisibility(View.INVISIBLE);break;
                        case 10:img10.setVisibility(View.INVISIBLE);break;
                        case 11:img11.setVisibility(View.INVISIBLE);break;
                        case 12:img12.setVisibility(View.INVISIBLE);break;
                        case 13:img13.setVisibility(View.INVISIBLE);break;
                        case 14:img14.setVisibility(View.INVISIBLE);break;
                        case 15:img15.setVisibility(View.INVISIBLE);break;
                        default:{}
                    }
                    zm1=zm1-1;

                }


                break;
            case SIUDMY_ELEMENT:

                writeToDataBase();
                stan=true;
                break;
            case OSMY_ELEMENT:

                connectToDataBase();

                break;
            case DZIEWIATY_ELEMENT:
                wczytywanie();
                writeToDataBase();
                Intent i = new Intent(Sala_3.this,MainActivity.class);
                startActivity(i);
                break;
            case DZIESIATY_ELEMENT:
                ResetMySql();
                ResetSqlLigt();
                break;
            default:


        }
        return true;
    }


}
