package com.example.michal.siema;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Ustawienia extends ActionBarActivity {

    Button faktura,konto;
    Bundle applesData;
    String Magazyn,Kuchnia,Sala_sprzedazy,Wszystko,uzytkownik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        faktura = (Button) findViewById(R.id.button58);
        konto = (Button) findViewById(R.id.button81);

        applesData = getIntent().getExtras();
        Sala_sprzedazy = applesData.getString("sala_sprzedazy");
        Magazyn = applesData.getString("magazyn");
        Kuchnia = applesData.getString("kuchnia");
        Wszystko = applesData.getString("wszystko");

        faktura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Ustawienia.this, Dane_do_faktury.class);
                a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                a.putExtra("wszystko", Wszystko);
                a.putExtra("magazyn", Magazyn);
                a.putExtra("kuchnia", Kuchnia);
                startActivity(a);
            }
        });

        konto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Ustawienia.this, Nowe_konto.class);
                a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                a.putExtra("wszystko", Wszystko);
                a.putExtra("magazyn", Magazyn);
                a.putExtra("kuchnia", Kuchnia);
                startActivity(a);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ustawienia, menu);
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
