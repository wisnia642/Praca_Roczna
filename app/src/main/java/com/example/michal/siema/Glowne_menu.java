package com.example.michal.siema;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Glowne_menu extends ActionBarActivity {

    Button kuchnia,magazyn,sala_sprzedaży,wyloguj,faktura,konto;
    Bundle applesData;
    String Magazyn,Kuchnia,Sala_sprzedazy,Wszystko;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowne_menu);

        kuchnia = (Button) findViewById(R.id.button56);
        magazyn = (Button) findViewById(R.id.button57);
        wyloguj = (Button) findViewById(R.id.button58);
        sala_sprzedaży = (Button) findViewById(R.id.button55);
        faktura = (Button) findViewById(R.id.button78);
        konto = (Button) findViewById(R.id.button78);

        applesData = getIntent().getExtras();
        Magazyn = applesData.getString("magazyn");
        Kuchnia = applesData.getString("kuchnia");
        Sala_sprzedazy = applesData.getString("sala_sprzedazy");
        Wszystko = applesData.getString("wszystko");

        kuchnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Kuchnia=="1" || Wszystko=="1"){
                Intent a = new Intent(Glowne_menu.this, Kuchnia.class);
                    a.putExtra("Wszystko", Wszystko);
                startActivity(a);}
                else {
                    showToast("Brak uprawnień");
                }
            }
        });

        magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Magazyn=="1" || Wszystko=="1"){
                Intent b = new Intent(Glowne_menu.this, Magzyn.class);
                    b.putExtra("Wszystko", Wszystko);
                startActivity(b);}
                else
                {
                    showToast("Brak uprawmień");
                }
            }
        });

        sala_sprzedaży.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Sala_sprzedazy=="1" || Wszystko=="1"){
                Intent c = new Intent(Glowne_menu.this, MainActivity.class);
                    c.putExtra("Wszystko", Wszystko);
                startActivity(c);}
                else
                {
                    showToast("Brak uprawnień");
                }
            }
        });

        wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Wszystko=="1")
                {}
                else
                {
                    showToast("Brak uprawnień");
                }
            }
        });

        konto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Wszystko=="1"){
                Intent c = new Intent(Glowne_menu.this, Nowe_konto.class);
                startActivity(c);}
                else
                {
                    showToast("Brak uprawnień");
                }
            }
        });

        faktura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Glowne_menu.this, Logowanie.class);
                startActivity(c);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_glowne_menu, menu);
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
