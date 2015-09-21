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

    TextView nazwa;
    EditText stan_krytyczny,danie,ilosc;
    Spinner kategoria,przynaleznosc;
    ListView produkty;
    Button ok,cancel;
    String wartosc,Danie,Ilosc,Stan_krytyczny;
    String[] Wagi = {"Szt","Kg","dag","gram"};
    String[] listaStringow = {"Lodówka","Mroźnia","Magazyn","Brak Kategorii"};

    CustomAdapter5 adapter;

    String[] Brak_kategorii= new String[40];
    String[] Brak_kategorii_ilosc = new String[40];
    String[] Brak_kategorii_kategoria = new String[40];
    String[] Brak_kategorii_stankrytyczny = new String[40];
    String[] Lodowka= new String[40];
    String[] Lodowka_ilosc = new String[40];
    String[] Lodowka_kategoria = new String[40];
    String[] Lodowka_stankrytyczny = new String[40];
    String[] Mroznia= new String[40];
    String[] Mroznia_ilosc = new String[40];
    String[] Mroznia_kategoria = new String[40];
    String[] Mroznia_stankrytyczny = new String[40];
    String[] Magazyn= new String[40];
    String[] Magazyn_ilosc = new String[40];
    String[] Magazyn_kategoria = new String[40];
    String[] Magazyn_stankrytyczny = new String[40];

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    int A,B,C,D,p,o;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void readsqlLight() {

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            A=0;
            Cursor a  = sampleDB.rawQuery("SELECT * FROM Lodowka", null);

            while (a.moveToNext()) {
                String zm = String.valueOf(a.getString(0));
                if(zm!=null){
                    Lodowka[A] = String.valueOf(a.getString(0));
                    Lodowka_ilosc[A] = String.valueOf(a.getString(1));
                    Lodowka_kategoria[A] = String.valueOf(a.getString(2));
                    Lodowka_stankrytyczny[A] = String.valueOf(a.getString(3));
                    A++;}
            }

            D=0;
            Cursor c  = sampleDB.rawQuery("SELECT * FROM Brak_kategori",null);

            while (c.moveToNext()) {
                String zm = String.valueOf(c.getString(0));
                if(zm!=null){
                    Brak_kategorii[D] = String.valueOf(c.getString(0));
                    Brak_kategorii_ilosc[D] = String.valueOf(c.getString(1));
                    Brak_kategorii_kategoria[D] = String.valueOf(c.getString(2));
                    Brak_kategorii_stankrytyczny[D] = String.valueOf(c.getString(3));
                    D++;}
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
                    B++;}
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

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            //poprawić ma być insert bo tych składników jeszcze nie ma
            sampleDB.execSQL("INSERT INTO "+wartosc+" (Nazwa,Ilosc,Kategoria,Stan_krytyczny,Przynaleznosc) VALUES ('" + Danie + "','" + Ilosc + "','" + Wagi[o] + "','" + Stan_krytyczny + "','" + listaStringow[p] + "') ");

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
        danie = (EditText) findViewById(R.id.editText12);
        ilosc = (EditText) findViewById(R.id.editText13);
        stan_krytyczny = (EditText) findViewById(R.id.editText11);
        kategoria = (Spinner) findViewById(R.id.spinner5);
        przynaleznosc = (Spinner) findViewById(R.id.spinner6);
        produkty = (ListView) findViewById(R.id.listView4);
        ok = (Button) findViewById(R.id.button51);
        cancel = (Button) findViewById(R.id.button52);

        Bundle applesData = getIntent().getExtras();
        if (applesData == null) {
            return;
        }
        wartosc = applesData.getString("wartosc");

        readsqlLight();

        kategoria.setAdapter(new MyAdapter(this, R.layout.custom_spiner, Wagi));
        przynaleznosc.setAdapter(new MyAdapter1(this, R.layout.custom_spiner, listaStringow));

        if (wartosc.equals("Mroznia")) {
            adapter = new CustomAdapter5(this, Mroznia, Mroznia_ilosc, Mroznia_kategoria, Mroznia_stankrytyczny);
            produkty.setAdapter(adapter);
        }

        if (wartosc.equals("Magazyn")) {
            adapter = new CustomAdapter5(this, Magazyn, Magazyn_ilosc, Magazyn_kategoria, Magazyn_stankrytyczny);
            produkty.setAdapter(adapter);
        }

        if (wartosc.equals("Lodowka")) {
            adapter = new CustomAdapter5(this, Lodowka, Lodowka_ilosc, Lodowka_kategoria, Lodowka_stankrytyczny);
            produkty.setAdapter(adapter);
        }
        if (wartosc.equals("Brak_kategorii")) {
            adapter = new CustomAdapter5(this, Brak_kategorii, Brak_kategorii_ilosc, Brak_kategorii_kategoria, Brak_kategorii_stankrytyczny);
            produkty.setAdapter(adapter);
        }

        produkty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (wartosc.equals("Mroznia")) {
                    nazwa.setText("MROŹNIA");
                    danie.setText(Mroznia[i]);
                    ilosc.setText(Mroznia_ilosc[i]);
                    przynaleznosc.setSelection(1);
                    stan_krytyczny.setText(Mroznia_stankrytyczny[i]);
                }
                if (wartosc.equals("Magazyn")) {
                    nazwa.setText("MAGAZYN");
                    danie.setText(Magazyn[i]);
                    ilosc.setText(Magazyn_ilosc[i]);
                    przynaleznosc.setSelection(2);
                    stan_krytyczny.setText(Magazyn_stankrytyczny[i]);
                }
                if (wartosc.equals("Lodowka")) {
                    nazwa.setText("LODÓWKA");
                    danie.setText(Lodowka[i]);
                    ilosc.setText(Lodowka_ilosc[i]);
                    przynaleznosc.setSelection(0);
                    stan_krytyczny.setText(Lodowka_stankrytyczny[i]);
                }
                if (wartosc.equals("Brak_kategorii")) {
                    nazwa.setText("BRAK KATEGORII");
                    danie.setText(Brak_kategorii[i]);
                    ilosc.setText(Brak_kategorii_ilosc[i]);
                    przynaleznosc.setSelection(3);
                    stan_krytyczny.setText(Brak_kategorii_stankrytyczny[i]);
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               saveSqlLight();
                finish();
                startActivity(getIntent());

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Pprodukty_kategoria.this, Kuchnia.class);
                startActivity(c);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pprodukty_kategoria, menu);
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
