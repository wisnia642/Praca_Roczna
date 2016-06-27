package com.example.michal.siema;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.michal.siema.R.layout.custom_spiner1;

public class Faktura extends ActionBarActivity {

    Date date;
    DateFormat dateFormat;

    String[] Klient = new String[20];
    String[] klient = new String[20];
    Double[] Suma = new Double[20];
    Double[] suma = new Double[20];
    String[] danie = new String[20];
    String[] ilosc = new String[20];
    String[] zdj = new String[20];
    String[] Danie = new String[20];
    String[] Ilosc = new String[20];
    public static final int PIERWSZY_ELEMENT = 1;

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

    List<String> listaStringow1 = new ArrayList<String>();
    List<String> listaStringow2 = new ArrayList<String>();


    private static final String url="jdbc:mysql://192.168.1.101:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    int x,w,c,q,v,y,d;
    double zm1,zm2,zm3,zm4;
    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";
    String zm;
    String data,nr_faktura;

    TextView Kwota;
    EditText Firma,Adres,Miejscowosc;
    Button ok,anuluj;

    Bundle applesData;
    String s,m,k,W;

    List<String> listaStringow = new ArrayList<String>();
    Spinner Stolik,klient1,firma1;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    //odczyt zamowienia
    public void dane_faktura() {
        for (int i = 0; i < q; i = i + 0) {
            danie[i] = null;
            ilosc[i] = null;
            zdj[i] = null;
            i++;
        }
        // a=q;
        q = 0;
        zm1 = 0.0;
        zm2 = 0.0;
        for (int i = 0; i < x; i = i + 0) {
            if (klient[w].equals(Klient[i])) {
                danie[q] = Danie[i];
                ilosc[q] = Ilosc[i];
                suma[q] = Suma[i];
                zm1 = zm2 + Suma[i];
                zm2 = zm1;
                q = q + 1;
            }
            i++;
        }
        Kwota.setText(String.valueOf(zm1));
    }
    //Faktura
    public void createPDF()
    {
        Document doc = new Document();


        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/droidText";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);


            File file = new File(dir, nr_faktura);
            FileOutputStream fOut = new FileOutputStream(file+".pdf");

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            //NR.faktury
            Paragraph p7 = new Paragraph("Faktura Nr:");
            Font paraFont7= new Font(Font.COURIER,15.1f, Color.GREEN);
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            p7.setFont(paraFont7);

            doc.add(p7);

            //Miejscowość i data
            Paragraph p8 = new Paragraph("Miejscowosc i data:");
            Font paraFont8= new Font(Font.COURIER,00.1f, Color.GREEN);
            p8.setAlignment(Paragraph.ALIGN_RIGHT);
            p8.setFont(paraFont8);

            doc.add(p8);

            //NR.faktury?
            Paragraph p24 = new Paragraph(nr_faktura);
            Font paraFont24= new Font(Font.COURIER,10.1f, Color.GREEN);
            p24.setAlignment(Paragraph.ALIGN_LEFT);
            p24.setFont(paraFont24);

            doc.add(p24);

            //Miejscowo\ść i data?
            Paragraph p25 = new Paragraph(Miejcowosc[c]+ " " + data);
            Font paraFont25= new Font(Font.COURIER,00.1f, Color.GREEN);
            p25.setAlignment(Paragraph.ALIGN_RIGHT);
            p25.setFont(paraFont25);

            doc.add(p25);

            //Nazwa
            Paragraph p29 = new Paragraph("FAKTURA VAT");
            Font paraFont29= new Font(Font.SYMBOL,50.7f, Color.GREEN);
            p29.setAlignment(Paragraph.ALIGN_CENTER);
            p29.setFont(paraFont29);

            doc.add(p29);

            //Termin Płatności
            Paragraph p9 = new Paragraph("Termin Platnosci:");
            Font paraFont9= new Font(Font.COURIER,35.1f, Color.GREEN);
            p9.setAlignment(Paragraph.ALIGN_LEFT);
            p9.setFont(paraFont9);

            doc.add(p9);

            //Data zakończenia wystawiania usługi
            Paragraph p11 = new Paragraph("Data zakonczenia wystawiania usługi:");
            Font paraFont11= new Font(Font.COURIER,1.1f, Color.GREEN);
            p11.setAlignment(Paragraph.ALIGN_CENTER);
            p11.setFont(paraFont11);

            doc.add(p11);

            //Forma płatności
            Paragraph p10 = new Paragraph("Forma Platnosci:");
            Font paraFont10= new Font(Font.COURIER,1.1f, Color.GREEN);
            p10.setAlignment(Paragraph.ALIGN_RIGHT);
            p10.setFont(paraFont10);

            doc.add(p10);

            //Termin Płatności?
            Paragraph p17 = new Paragraph(data);
            Font paraFont17= new Font(Font.COURIER,10.1f, Color.GREEN);
            p17.setAlignment(Paragraph.ALIGN_LEFT);
            p17.setFont(paraFont17);

            doc.add(p17);

            //Data zakończenia wystawiania usługi?
            Paragraph p18 = new Paragraph(data);
            Font paraFont18= new Font(Font.COURIER,1.1f, Color.GREEN);
            p18.setAlignment(Paragraph.ALIGN_CENTER);
            p18.setFont(paraFont18);

            doc.add(p18);

            //Forma płatności?
            Paragraph p19 = new Paragraph("Gotówka");
            Font paraFont19= new Font(Font.COURIER,1.1f, Color.GREEN);
            p19.setAlignment(Paragraph.ALIGN_RIGHT);
            p19.setFont(paraFont19);

            doc.add(p19);

            //Dane Firmy
            Paragraph p1 = new Paragraph(Nazwa_firmy1[d]);
            Font paraFont= new Font(Font.COURIER,40.1f, Color.GREEN);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont);

            doc.add(p1);

            Paragraph p2 = new Paragraph(Ulica1[d]+" "+Numer_domu1[d]);
            Font paraFont2= new Font(Font.COURIER);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont2);

            doc.add(p2);
            Paragraph p3 = new Paragraph(Kod_pocztowy1[d]+" "+Miejscowosc1[d]);
            Font paraFont3= new Font(Font.COURIER);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont3);

            doc.add(p3);

            //Dane klienta
            Paragraph p4 = new Paragraph(Nazwa_firmy[c]);
            Font paraFont4= new Font(Font.COURIER,-23.1f, Color.GREEN);
            p4.setAlignment(Paragraph.ALIGN_RIGHT);
            p4.setFont(paraFont4);


            doc.add(p4);

            Paragraph p5 = new Paragraph(Ulica[c]+" "+Numer_domu[c]);
            Font paraFont5= new Font(Font.COURIER);
            p5.setAlignment(Paragraph.ALIGN_RIGHT);
            p5.setFont(paraFont5);

            doc.add(p5);

            Paragraph p6 = new Paragraph(Kod_pocztowy[c]+" "+Miejcowosc[c]);
            Font paraFont6= new Font(Font.COURIER);
            p6.setAlignment(Paragraph.ALIGN_RIGHT);
            p6.setFont(paraFont6);

            doc.add(p6);


            //Nazwa towaru
            Paragraph p12 = new Paragraph("Lp.| Nazwa towaru | J.M | Ilosc | Cena Netto | Stawka Vat | Kwota Vat | Wartosc z Vat ");
            Font paraFont12= new Font(Font.COURIER,40.1f, Color.GREEN);
            p12.setAlignment(Paragraph.ALIGN_CENTER);
            p12.setFont(paraFont12);

            doc.add(p12);

            for(int i=0;i<q;i=i+0) {
                //Towar
                dane_faktura();
                zm3=(suma[i]*23)/100;
                zm4=suma[i]-zm3;
                Paragraph p13 = new Paragraph(""+i+1+". | "+danie[i]+". | SZT. |  "+ilosc[i]+". |   "+zm4+".    |    23%    |  "+zm3+".  |    "+suma[i]+".   ");
                Font paraFont13 = new Font(Font.COURIER, 20.1f, Color.GREEN);
                p13.setAlignment(Paragraph.ALIGN_CENTER);
                p13.setFont(paraFont13);

                doc.add(p13);
                i=i+1;
            }
            //Uwagi
            Paragraph p14 = new Paragraph("Uwagi: ");
            Font paraFont14 = new Font(Font.COURIER,60.1f, Color.GREEN);
            p14.setAlignment(Paragraph.ALIGN_LEFT);
            p14.setFont(paraFont14);

            doc.add(p14);

            //Stawka
            Paragraph p15 = new Paragraph("Stawka | Wartosc Netto | Kwota Vat | Wartosc z podatkiem Vat ");
            Font paraFont15 = new Font(Font.COURIER,-3.1f, Color.GREEN);
            p15.setAlignment(Paragraph.ALIGN_RIGHT);
            p15.setFont(paraFont15);

            doc.add(p15);

            //Stawka1
            zm3=(zm1*23)/100;
            zm4=zm1-zm3;
            Paragraph p16 = new Paragraph(  "23%   |    "+zm4+".    |   "+zm3+".    |    "+zm1+". ");
            Font paraFont16 = new Font(Font.COURIER,15.1f, Color.GREEN);
            p16.setAlignment(Paragraph.ALIGN_RIGHT);
            p16.setFont(paraFont16);

            doc.add(p16);

            //Fakturę wystawił
            Paragraph p27 = new Paragraph("Fakture wystawil:");
            Font paraFont27 = new Font(Font.COURIER,41.1f, Color.GREEN);
            p27.setAlignment(Paragraph.ALIGN_LEFT);
            p27.setFont(paraFont27);

            doc.add(p27);

            //Fakturę wystawił kto?
            Paragraph p28 = new Paragraph(Nazwa_firmy[c]);
            Font paraFont28 = new Font(Font.COURIER);
            p28.setAlignment(Paragraph.ALIGN_LEFT);
            p28.setFont(paraFont28);

            doc.add(p28);


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
        }

    }

    public void open()
    {
        File file = new File ("/sdcard/droidText/"+nr_faktura+".pdf");
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

    private void sqlLight()
    {
        ToDataBase();
        x=0;

        SQLiteDatabase sampleDB2 = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);


        try {
            Cursor c  = sampleDB2.rawQuery("SELECT * FROM ZAMOWIENIE WHERE Faktura='false'",null);

            while (c.moveToNext()) {
                zm = String.valueOf(c.getString(0));
                if(zm!=null){
                    Klient[x] = String.valueOf(c.getString(0));
                    Danie[x] = String.valueOf(c.getString(1));
                    Ilosc[x] = String.valueOf(c.getString(2));
                    Suma[x] = Double.valueOf(c.getDouble(6));
                    x++;}
            }
            sampleDB2.close();
        } catch (Exception e){
        }

    }

    private void readsqlLigt1()
    {
        v=0;
        y=0;

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
                    Nazwa_firmy[v] = String.valueOf(f.getString(1));
                    Ulica[v] = String.valueOf(f.getString(2));
                    Numer_domu[v] = String.valueOf(f.getString(3));
                    Miejcowosc[v] = String.valueOf(f.getString(4));
                    Kod_pocztowy[v] = String.valueOf(f.getString(5));
                    v++;
                }
            }

            sampleDB1.close();
        }
        catch (Exception e)
        {

        }

    }

    private void mysql() {
        x = 0;
        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String sql2 = ("SELECT * FROM ZAMOWIENIE WHERE Faktura='false'");

            try {
                rs = st.executeQuery(sql2);
            } catch (SQLException e1) {
                //  e1.printStackTrace();
            }
            try {
                PreparedStatement stmt = connection.prepareStatement(sql2);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    String zm = rs.getString("Klient");
                    if (zm != null) {
                        Klient[x] = rs.getString("Klient");
                        Danie[x] = rs.getString("Danie");
                        Ilosc[x] = rs.getString("Ilosc");
                        Suma[x] = rs.getDouble("Suma");
                        x++;
                    }

                }
            } catch (SQLException e1) {
            }
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                showToast("brak polaczenia z internetem");}

        }
    }

    private void ToDataBase()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Zamowienie (Klient VARCHAR,Danie VARCHAR,Ilosc VARCHAR,Dodatki VARCHAR," +
                    "Dodatkowe_Zyczenia VARCHAR,Zdjecie VARCHAR,Suma DOUBLE,Sposob_przygotowania VARCHAR,Skladniki VARCHAR,Stan VARCHAR,Faktura VARCHAR);");

        }
        catch (Exception e){}

    }

   private void wczytywanie() {
       v = 0;
       y = 0;
       connect();
       if (connection != null) {

           try {
               st = connection.createStatement();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }

           String sql = ("select * from dane_faktura where Wlasciciel='firma'");

           try {
               rs = st.executeQuery(sql);
           } catch (SQLException e1) {
               //  e1.printStackTrace();
           }
           try {
               PreparedStatement stmt = connection.prepareStatement(sql);
               rs = stmt.executeQuery();

               while (rs.next()) {
                   String zm = rs.getString("Dane");
                   if (zm != null) {
                       Nazwa_firmy[v] = rs.getString("Dane");
                       Ulica[v] = rs.getString("Dane1");
                       Numer_domu[v] = rs.getString("Dane2");
                       Miejcowosc[v] = rs.getString("Dane3");
                       Kod_pocztowy[v] = rs.getString("Dane4");
                       v++;
                   }

               }
           } catch (SQLException e1) {
           }


           String sql1 = ("select * from dane_faktura where Wlasciciel='klient'");

           try {
               rs = st.executeQuery(sql1);
           } catch (SQLException e1) {
               //  e1.printStackTrace();
           }
           try {
               PreparedStatement stmt = connection.prepareStatement(sql1);
               rs = stmt.executeQuery();

               while (rs.next()) {
                   String zm = rs.getString("Dane");
                   if (zm != null) {
                       Nazwa_firmy1[y] = rs.getString("Dane");
                       Ulica1[y] = rs.getString("Dane1");
                       Numer_domu1[y] = rs.getString("Dane2");
                       Miejscowosc1[y] = rs.getString("Dane3");
                       Kod_pocztowy1[y] = rs.getString("Dane4");
                       y++;
                   }

               }
           } catch (SQLException e1) {
           }

           try {
               if (connection != null)
                   connection.close();
           } catch (SQLException se) {
               showToast("brak polaczenia z internetem");
           }

       }

   }

    public void ZapisSqlLight()
    {
        ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("UPDATE ZAMOWIENIE SET Faktura=('true') WHERE Klient=('"+klient[w]+"')");
            sampleDB.close();

        }catch (Exception e)
        {showToast(e+"");}
    }

    public void ZapisMySql()
    {
        connect();

        if (connection != null) {
            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }


                String sql = "UPDATE ZAMOWIENIE SET Faktura=('true') WHERE Klient=('"+klient[w]+"')";

                try {
                    st.executeUpdate(sql);
                } catch (SQLException e1) {
                    // e1.printStackTrace();
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
        setContentView(R.layout.activity_faktura);

        Kwota = (TextView) findViewById(R.id.textView45);
        ok = (Button) findViewById(R.id.button60);
        anuluj = (Button) findViewById(R.id.button61);

        klient1 = (Spinner) findViewById(R.id.spinner11);
        firma1 = (Spinner) findViewById(R.id.spinner2);

        applesData = getIntent().getExtras();
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        W = applesData.getString("wszystko");

        sqlLight();
        if(Klient[0]==null)
        {
            mysql();
        }
      //  showToast(Klient[0]);

        //nie wiem co to robi ?
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

        readsqlLigt1();
        if(Nazwa_firmy[0]==null & Nazwa_firmy1[0]==null)
        {
            wczytywanie();
        }
        try {
            for (int j = 0; j < Nazwa_firmy.length; j++) {
                if (Nazwa_firmy[j] != null) {
                    listaStringow2.add(Nazwa_firmy[j]);
                }
            }

            for (int r = 0; r < Nazwa_firmy1.length; r++) {
                if (Nazwa_firmy1[r] != null) {
                    listaStringow1.add(Nazwa_firmy1[r]);
                }
            }

        }catch (Exception e){}

        Stolik = (Spinner) findViewById(R.id.spinner10);
        Stolik.setAdapter(new MyAdapter(this, R.layout.custom_spiner1, listaStringow));
        klient1.setAdapter(new MyAdapter1(this, R.layout.custom_spiner1, listaStringow2));
        firma1.setAdapter(new Adapter(this, R.layout.custom_spiner1, listaStringow1));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        if(!listaStringow.isEmpty()) {
                            //pobieranie daty do faktury
                            ZapisMySql();
                            ZapisSqlLight();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            data = sdf.format(new Date());
                            SimpleDateFormat sdf1 = new SimpleDateFormat("mmssyyyyMMdd");
                            nr_faktura = sdf1.format(new Date());
                            createPDF();
                            open();
                        }
                   else{showToast("Brak klientów do wystawienia faktury");}
                    }catch (Exception e){}
                }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Faktura.this, MainActivity.class);
                i.putExtra("sala_sprzedazy", s);
                i.putExtra("wszystko", W);
                i.putExtra("magazyn", m);
                i.putExtra("kuchnia", k);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            menu.add(0, PIERWSZY_ELEMENT, 0, "Dodaj Klienta");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case PIERWSZY_ELEMENT:
                Intent intent = new Intent(Faktura.this,Dane_do_faktury.class);
                intent.putExtra("sala_sprzedazy", s);
                intent.putExtra("wszystko", W);
                intent.putExtra("magazyn", m);
                intent.putExtra("kuchnia", k);
                intent.putExtra("faktura", "true");
                startActivity(intent);
                break;
            default:
        }
        return true;
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
            dane_faktura();
            Kwota.setText(String.valueOf(zm1));
            w=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner1, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text2);
            main_text.setText(klient[position]);
            return mySpinner;
        }}

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
