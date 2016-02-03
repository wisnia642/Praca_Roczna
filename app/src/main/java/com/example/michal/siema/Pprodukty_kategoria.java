package com.example.michal.siema;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Pprodukty_kategoria extends ActionBarActivity {

    TextView nazwa,przyn;
    EditText stan_krytyczny,danie,ilosc,ilosc_detaliczna,cena_detaliczna;
    Spinner kategoria,przynaleznosc;
    ListView produkty;
    Button ok,cancel,usun,utworz,popraw;
    String wartosc,Danie,Ilosc,Stan_krytyczny,wagi,cena_d,ilosc_d,jak,naz,ilo;
    String[] Wagi = {"Szt","Kg","Dag","Gram","Litry","Mililitry"};
    String[] listaStringow = {"Lodówka","Mroźnia","Magazyn","Brak Kategorii"};

    CustomAdapter6 adapter1;

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    Bundle applesData;
    String s,m,k,w;

    String[] Brak_kategorii= new String[20];
    String[] Brak_kategorii_ilosc = new String[20];
    String[] Brak_kategorii_kategoria = new String[20];
    String[] Brak_kategorii_stankrytyczny = new String[20];
    String[] Brak_kategorii_cena_detaliczna = new String[20];
    String[] Brak_kategorii_ilosc_detaliczna = new String[20];
    String[] Lodowka= new String[20];
    String[] Lodowka_ilosc = new String[20];
    String[] Lodowka_kategoria = new String[20];
    String[] Lodowka_stankrytyczny = new String[20];
    String[] Lodówka_cena_detaliczna = new String[20];
    String[] Lodówka_ilosc_detaliczna = new String[20];
    String[] Mroznia= new String[20];
    String[] Mroznia_ilosc = new String[20];
    String[] Mroznia_kategoria = new String[20];
    String[] Mroznia_stankrytyczny = new String[20];
    String[] Mroznia_cena_detaliczna = new String[20];
    String[] Mroznia_ilosc_detaliczna = new String[20];
    String[] Magazyn= new String[20];
    String[] Magazyn_ilosc = new String[20];
    String[] Magazyn_kategoria = new String[20];
    String[] Magazyn_stankrytyczny = new String[20];
    String[] Magazyn_cena_detaliczna = new String[20];
    String[] Magazyn_ilosc_detaliczna = new String[20];

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    int A,B,C,D,p,o;
    boolean klikniecie=false,klikniecie1=false;
    String Nazwa_dania;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void ToDataBase()
    {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Wykonane (Data VARCHAR,Czas VARCHAR,Nazwa VARCHAR," +
                    "Ilosc VARCHAR,Czas_wykonania VARCHAR,Kto_wykonal VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Lodowka (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR,Stan_krytyczny VARCHAR," +
                    "Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Mroznia (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR," +
                    "Stan_krytyczny VARCHAR,Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Magazyn (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR," +
                    "Stan_krytyczny VARCHAR,Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Brak_kategori (Nazwa VARCHAR,Ilosc VARCHAR,Kategoria VARCHAR," +
                    "Stan_krytyczny VARCHAR,Przynaleznosc VARCHAR,Komunikat VARCHAR,Cena_detaliczna VARCHAR,Ilosc_detaliczna VARCHAR);");

        }
        catch (Exception e){}

    }

        private void readsqlLight1() {
            ToDataBase();
            try {
                SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

                A=0;
                Cursor a  = sampleDB.rawQuery("SELECT * FROM Lodowka",null);

                while (a.moveToNext()) {
                    String zm = String.valueOf(a.getString(0));
                    if(zm!=null){
                        Lodowka[A] = String.valueOf(a.getString(0));
                        Lodowka_ilosc[A] = String.valueOf(a.getString(1));
                        Lodowka_kategoria[A] = String.valueOf(a.getString(2));
                        Lodowka_stankrytyczny[A] = String.valueOf(a.getString(3));
                        Lodówka_cena_detaliczna[A] = String.valueOf(a.getString(6));
                        Lodówka_ilosc_detaliczna[A] = String.valueOf(a.getString(7));
                        A++;}
                }

                C=0;
                Cursor b  = sampleDB.rawQuery("SELECT * FROM Magazyn",null);

                while (b.moveToNext()) {
                    String zm = String.valueOf(b.getString(0));
                    if(zm!=null){
                        Magazyn[C] = String.valueOf(b.getString(0));
                        Magazyn_ilosc[C] = String.valueOf(b.getString(1));
                        Magazyn_kategoria[C] = String.valueOf(b.getString(2));
                        Magazyn_stankrytyczny[C] = String.valueOf(b.getString(3));
                        Magazyn_cena_detaliczna[C] = String.valueOf(b.getString(6));
                        Magazyn_ilosc_detaliczna[C] = String.valueOf(b.getString(7));
                        C++;}
                }

                B=0;
                Cursor d  = sampleDB.rawQuery("SELECT * FROM Mroznia",null);

                while (d.moveToNext()) {
                    String zm = String.valueOf(d.getString(0));
                    if(zm!=null){
                        Mroznia[B] = String.valueOf(d.getString(0));
                        Mroznia_ilosc[B] = String.valueOf(d.getString(1));
                        Mroznia_kategoria[B] = String.valueOf(d.getString(2));
                        Mroznia_stankrytyczny[B] = String.valueOf(d.getString(3));
                        Mroznia_cena_detaliczna[B] = String.valueOf(d.getString(6));
                        Mroznia_ilosc_detaliczna[B] = String.valueOf(d.getString(7));
                        B++;
                    }
                }

                int D=0;
                Cursor e  = sampleDB.rawQuery("SELECT * FROM Brak_kategori",null);

                while (e.moveToNext()) {
                    String zm = String.valueOf(e.getString(0));
                    if(zm!=null){
                        Brak_kategorii[D] = String.valueOf(e.getString(0));
                        Brak_kategorii_ilosc[D] = String.valueOf(e.getString(1));
                        Brak_kategorii_kategoria[D] = String.valueOf(e.getString(2));
                        Brak_kategorii_stankrytyczny[D] = String.valueOf(e.getString(3));
                        Brak_kategorii_cena_detaliczna[D] = String.valueOf(e.getString(6));
                        Brak_kategorii_ilosc_detaliczna[D] = String.valueOf(e.getString(7));
                        D++;}
                }

                sampleDB.close();
            } catch (Exception a) {
            }

        }

    public void saveSqlLight()
    {
        Danie=danie.getText().toString();
        Ilosc=ilosc.getText().toString();
        Stan_krytyczny=stan_krytyczny.getText().toString();
        cena_d=cena_detaliczna.getText().toString();
        ilosc_d =ilosc_detaliczna.getText().toString();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO "+wartosc+" (Nazwa,Ilosc,Kategoria,Stan_krytyczny,Przynaleznosc,Cena_detaliczna,Ilosc_detaliczna) VALUES ('" + Danie + "','" + Ilosc + "','" + Wagi[o] + "','" + Stan_krytyczny + "','" + listaStringow[p] + "','" + cena_d+ "','" + ilosc_d + "') ");

            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }

    }

    public void deletefromSQLlight()
    {
        Danie=danie.getText().toString();
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("DELETE FROM " + wartosc + " WHERE Nazwa = ('" + Nazwa_dania + "') ");
            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pprodukty_kategoria);

        nazwa = (TextView) findViewById(R.id.textView77);
        przyn = (TextView) findViewById(R.id.textView100);
        danie = (EditText) findViewById(R.id.editText11);
        ilosc = (EditText) findViewById(R.id.editText12);
        stan_krytyczny = (EditText) findViewById(R.id.editText17);
        kategoria = (Spinner) findViewById(R.id.spinner5);
        przynaleznosc = (Spinner) findViewById(R.id.spinner6);
        produkty = (ListView) findViewById(R.id.listView4);
        ok = (Button) findViewById(R.id.button51);
        cancel = (Button) findViewById(R.id.button52);
        usun = (Button) findViewById(R.id.button53);
        utworz = (Button) findViewById(R.id.button54);
        popraw = (Button) findViewById(R.id.button67);
        ilosc_detaliczna = (EditText) findViewById(R.id.editText16);
        cena_detaliczna = (EditText) findViewById(R.id.editText13);

        Bundle applesData = getIntent().getExtras();
        if (applesData == null) {
            return;
        }

        wartosc = applesData.getString("wartosc");
        s = applesData.getString("sala_sprzedazy");
        m = applesData.getString("magazyn");
        k = applesData.getString("kuchnia");
        w = applesData.getString("wszystko");
        if (applesData != null) {
            jak = applesData.getString("jak");
        }

        readsqlLight1();

        kategoria.setAdapter(new MyAdapter(this, R.layout.custom_spiner, Wagi));
        przynaleznosc.setAdapter(new MyAdapter1(this, R.layout.custom_spiner, listaStringow));

        if (wartosc.equals("Mroznia")) {
            nazwa.setText("MROŹNIA");
            przynaleznosc.setSelection(1);
            adapter1 = new CustomAdapter6(this, Mroznia, Mroznia_ilosc, Mroznia_kategoria, Mroznia_stankrytyczny,Mroznia_cena_detaliczna,Mroznia_ilosc_detaliczna);
            produkty.setAdapter(adapter1);
        }

        if (wartosc.equals("Magazyn")) {
            nazwa.setText("MAGAZYN");
            przynaleznosc.setSelection(2);
            adapter1 = new CustomAdapter6(this, Magazyn, Magazyn_ilosc, Magazyn_kategoria, Magazyn_stankrytyczny,Magazyn_cena_detaliczna,Magazyn_ilosc_detaliczna);
            produkty.setAdapter(adapter1);
        }

        if (wartosc.equals("Lodowka")) {
            nazwa.setText("LODÓWKA");
            przynaleznosc.setSelection(0);
            adapter1 = new CustomAdapter6(this, Lodowka, Lodowka_ilosc, Lodowka_kategoria, Lodowka_stankrytyczny,Lodówka_cena_detaliczna,Lodówka_ilosc_detaliczna);
            produkty.setAdapter(adapter1);
        }
        if (wartosc.equals("Brak_kategori")) {
            nazwa.setText("BRAK KATEGORI");
            przynaleznosc.setSelection(3);
            adapter1 = new CustomAdapter6(this, Brak_kategorii, Brak_kategorii_ilosc, Brak_kategorii_kategoria, Brak_kategorii_stankrytyczny,
                    Brak_kategorii_cena_detaliczna,Brak_kategorii_ilosc_detaliczna);
            produkty.setAdapter(adapter1);
        }

        produkty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    klikniecie = true;

                    if (wartosc.equals("Mroznia")) {
                        wagi = Mroznia_kategoria[i];
                        danie.setText(Mroznia[i]);
                        Nazwa_dania=Mroznia[i];
                        ilosc.setText(Mroznia_ilosc[i]);
                        stan_krytyczny.setText(Mroznia_stankrytyczny[i]);
                        cena_detaliczna.setText(Mroznia_cena_detaliczna[i]);
                        ilosc_detaliczna.setText(Mroznia_ilosc_detaliczna[i]);
                        przyn.setVisibility(View.VISIBLE);
                        przynaleznosc.setVisibility(View.VISIBLE);

                    }
                    if (wartosc.equals("Magazyn")) {
                        wagi = Magazyn_kategoria[i];
                        danie.setText(Magazyn[i]);
                        Nazwa_dania=Magazyn[i];
                        ilosc.setText(Magazyn_ilosc[i]);
                        stan_krytyczny.setText(Magazyn_stankrytyczny[i]);
                        cena_detaliczna.setText(Magazyn_cena_detaliczna[i]);
                        ilosc_detaliczna.setText(Magazyn_ilosc_detaliczna[i]);
                        przyn.setVisibility(View.VISIBLE);
                        przynaleznosc.setVisibility(View.VISIBLE);
                    }
                    if (wartosc.equals("Lodowka")) {
                        wagi = Lodowka_kategoria[i];
                        danie.setText(Lodowka[i]);
                        Nazwa_dania=Lodowka[i];
                        ilosc.setText(Lodowka_ilosc[i]);
                        stan_krytyczny.setText(Lodowka_stankrytyczny[i]);
                        cena_detaliczna.setText(Lodówka_cena_detaliczna[i]);
                        ilosc_detaliczna.setText(Lodówka_ilosc_detaliczna[i]);
                        przyn.setVisibility(View.VISIBLE);
                        przynaleznosc.setVisibility(View.VISIBLE);
                    }
                    if (wartosc.equals("Brak_kategori")) {
                        wagi = Brak_kategorii_kategoria[i];
                        danie.setText(Brak_kategorii[i]);
                        Nazwa_dania=Brak_kategorii[i];
                        ilosc.setText(Brak_kategorii_ilosc[i]);
                        stan_krytyczny.setText(Brak_kategorii_stankrytyczny[i]);
                        cena_detaliczna.setText(Brak_kategorii_cena_detaliczna[i]);
                        ilosc_detaliczna.setText(Brak_kategorii_ilosc_detaliczna[i]);
                        przyn.setVisibility(View.VISIBLE);
                        przynaleznosc.setVisibility(View.VISIBLE);
                    }

                    if (wagi.equals("Szt")) {
                        kategoria.setSelection(0);
                    }
                    if (wagi.equals("Dag")) {
                        kategoria.setSelection(2);
                    }
                    if (wagi.equals("Gram")) {
                        kategoria.setSelection(3);
                    }
                    if (wagi.equals("Kg")) {
                        kategoria.setSelection(1);
                    }
                    if (wagi.equals("Litry")) {
                        kategoria.setSelection(4);
                    }
                    if (wagi.equals("Mililitry")) {
                        kategoria.setSelection(5);
                    }
                }catch (Exception e){}

            }
        });

        popraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (klikniecie == true & danie.getText().toString() != null & ilosc.getText().toString() != null) {
                    deletefromSQLlight();
                    saveSqlLight();
                    finish();
                    startActivity(getIntent());
                } else {
                    showToast("wybierz składanik do poprawy");
                }
                klikniecie = false;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(klikniecie == true&danie.getText().toString()!=null&ilosc.getText().toString()!=null) {
                if (klikniecie == true) {
                    deletefromSQLlight();
                    if (p == 0) {
                        wartosc = "Lodowka";

                    }
                    if (p == 1) {
                        wartosc = "Mroznia";

                    }
                    if (p == 2) {
                        wartosc = "Magazyn";

                    }
                    if (p == 3) {
                        wartosc = "Brak_kategori";

                    }
                }

                    saveSqlLight();
                    finish();
                    startActivity(getIntent());
                }
                else{showToast("wybierz danie do przeniesienia");}
                klikniecie = false;
            }
        });

        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (klikniecie == true&danie.getText().toString()!=""&ilosc.getText().toString()!="") {
                    deletefromSQLlight();
                    finish();
                    startActivity(getIntent());
                }
                else{showToast("wybierz danie do usunięcia");}
                klikniecie = false;
            }
        });
        danie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                klikniecie1=true;
            }
        });

        utworz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naz =danie.getText().toString();
                ilo =ilosc.getText().toString();
                if(klikniecie1==true & naz!=null & ilo!=null) {
                    saveSqlLight();
                    finish();
                    startActivity(getIntent());
                }
                else{showToast("uspełnij nazwę dania i jego składniki");}
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jak.equals("true")) {
                    Intent c = new Intent(Pprodukty_kategoria.this, Magzyn.class);
                    c.putExtra("sala_sprzedazy", s);
                    c.putExtra("wszystko", w);
                    c.putExtra("magazyn", m);
                    c.putExtra("kuchnia", k);
                    startActivity(c);
                }
                if(jak.equals("false")) {
                    Intent c = new Intent(Pprodukty_kategoria.this, Kuchnia.class);
                    c.putExtra("sala_sprzedazy", s);
                    c.putExtra("wszystko", w);
                    c.putExtra("magazyn", m);
                    c.putExtra("kuchnia", k);
                    startActivity(c);

                }
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
            o=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text1);
            main_text.setText(Wagi[position]);
            return mySpinner;
        }}
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
            p=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text1);
            main_text.setText(listaStringow[position]);
            return mySpinner;
        }}
}
