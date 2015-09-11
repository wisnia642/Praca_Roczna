package com.example.michal.siema;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    int x,w,c,q;
    double zm1,zm2,zm3,zm4;
    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";
    String zm;
    String data,nr_faktura,firma,adres,miejscowosc,kwota;

    TextView Kwota;
    EditText Firma,Adres,Miejscowosc;
    Button ok,anuluj;

    List<String> listaStringow = new ArrayList<String>();
    Spinner Stolik;

    private void readsqlLight() {

        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);


            try {
                Cursor c  = sampleDB.rawQuery("SELECT * FROM ZAMOWIENIE",null);

                while (c.moveToNext()) {
                    zm = String.valueOf(c.getString(1));
                    if(zm!=null){
                        Klient[x] = String.valueOf(c.getString(0));
                        Danie[x] = String.valueOf(c.getString(1));
                        Ilosc[x] = String.valueOf(c.getString(2));
                        Suma[x] = Double.valueOf(c.getDouble(6));
                        x++;}
                }
                sampleDB.close();
            } catch (Exception e){
            }


    }

    //odczyt zamowienia
    public void SqlLight() {
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
            Paragraph p25 = new Paragraph("Gorzów Wielkopolski  " + data);
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
            Paragraph p1 = new Paragraph("Przykladowa Firma-Restauracja");
            Font paraFont= new Font(Font.COURIER,40.1f, Color.GREEN);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont);

            doc.add(p1);

            Paragraph p2 = new Paragraph("ul.Przykladowa 6/4");
            Font paraFont2= new Font(Font.COURIER);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont2);

            doc.add(p2);
            Paragraph p3 = new Paragraph("66-400 Gorzów Wielkopolski");
            Font paraFont3= new Font(Font.COURIER);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont3);

            doc.add(p3);

            //Dane klienta
            Paragraph p4 = new Paragraph(firma);
            Font paraFont4= new Font(Font.COURIER,-23.1f, Color.GREEN);
            p4.setAlignment(Paragraph.ALIGN_RIGHT);
            p4.setFont(paraFont4);


            doc.add(p4);

            Paragraph p5 = new Paragraph(adres);
            Font paraFont5= new Font(Font.COURIER);
            p5.setAlignment(Paragraph.ALIGN_RIGHT);
            p5.setFont(paraFont5);

            doc.add(p5);

            Paragraph p6 = new Paragraph(miejscowosc);
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
                SqlLight();
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
            Paragraph p27 = new Paragraph("Fakture wystawił:");
            Font paraFont27 = new Font(Font.COURIER,41.1f, Color.GREEN);
            p27.setAlignment(Paragraph.ALIGN_LEFT);
            p27.setFont(paraFont27);

            doc.add(p27);

            //Fakturę wystawił kto?
            Paragraph p28 = new Paragraph("     XYZ      ");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktura);

        Firma = (EditText) findViewById(R.id.editText7);
        Adres = (EditText) findViewById(R.id.editText8);
        Miejscowosc = (EditText) findViewById(R.id.editText9);
        Kwota = (TextView) findViewById(R.id.textView45);
        ok = (Button) findViewById(R.id.button60);
        anuluj = (Button) findViewById(R.id.button61);
        SqlLight();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firma = Firma.getText().toString();
                adres = Adres.getText().toString();
                miejscowosc = Miejscowosc.getText().toString();

                    try {
                        //pobieranie daty do faktury
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        data = sdf.format(new Date());
                        SimpleDateFormat sdf1 = new SimpleDateFormat("mmssyyyyMMdd");
                        nr_faktura = sdf1.format(new Date());
                        createPDF();
                        Intent i = new Intent(Faktura.this,MainActivity.class);
                        startActivity(i);
                    }catch (Exception e){}
                }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Faktura.this,MainActivity.class);
                startActivity(i);
            }
        });



        try{readsqlLight();}catch (Exception e){}

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

        Stolik = (Spinner) findViewById(R.id.spinner2);
        Stolik.setAdapter(new Faktura.MyAdapter(this, R.layout.custom_spiner1, listaStringow));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faktura, menu);
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
            SqlLight();
            Kwota.setText(String.valueOf(zm1));
            w=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner1, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text2);
            main_text.setText(klient[position]);
            return mySpinner;
        }}
}
