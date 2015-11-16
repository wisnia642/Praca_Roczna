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

    Button kuchnia,magazyn,sala_sprzedaz,wyloguj,faktura,konto;
    Bundle applesData;
    String Magazyn,Kuchnia,Sala_sprzedazy,Wszystko,uzytkownik;



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
        faktura = (Button) findViewById(R.id.button58);
        sala_sprzedaz = (Button) findViewById(R.id.button55);
        wyloguj = (Button) findViewById(R.id.button78);
        konto = (Button) findViewById(R.id.button77);

        applesData = getIntent().getExtras();
        Sala_sprzedazy = applesData.getString("sala_sprzedazy");
        Magazyn = applesData.getString("magazyn");
        Kuchnia = applesData.getString("kuchnia");
        Wszystko = applesData.getString("wszystko");
        uzytkownik = applesData.getString("uzytkownik");


        kuchnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Kuchnia.equals("1") || Wszystko.equals("1")){
                    Intent a = new Intent(Glowne_menu.this, Kuchnia.class);
                   a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                    a.putExtra("wszystko", Wszystko);
                    a.putExtra("magazyn", Magazyn);
                    a.putExtra("kuchnia", Kuchnia);
                    a.putExtra("uzytkownik", uzytkownik);
                    startActivity(a);}
                else {
                    showToast("Brak uprawnień");
                }
            }
        });

        magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Magazyn.equals("1") || Wszystko.equals("1")){
                    Intent a = new Intent(Glowne_menu.this, Magzyn.class);
                    a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                    a.putExtra("wszystko", Wszystko);
                    a.putExtra("magazyn", Magazyn);
                    a.putExtra("kuchnia", Kuchnia);
                    a.putExtra("uzytkownik", uzytkownik);
                    startActivity(a);}
                else
                {
                    showToast("Brak uprawmień");
                }
            }
        });

        sala_sprzedaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Sala_sprzedazy.equals("1")|| Wszystko.equals("1")){
                    Intent a = new Intent(Glowne_menu.this, MainActivity.class);
                    a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                    a.putExtra("wszystko", Wszystko);
                    a.putExtra("magazyn", Magazyn);
                    a.putExtra("kuchnia", Kuchnia);
                    a.putExtra("uzytkownik", uzytkownik);
                    startActivity(a);}
                else
                {
                    showToast("Brak uprawnień");
                }
            }
        });

        faktura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Wszystko.equals("1")) {
                    Intent a = new Intent(Glowne_menu.this, Dane_do_faktury.class);
                    a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                    a.putExtra("wszystko", Wszystko);
                    a.putExtra("magazyn", Magazyn);
                    a.putExtra("kuchnia", Kuchnia);
                    startActivity(a);
                } else

                {
                    showToast("Brak uprawnień");
                }
            }

        });

        konto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(Wszystko.equals("1")){
                Intent a = new Intent(Glowne_menu.this, Nowe_konto.class);
                a.putExtra("sala_sprzedazy", Sala_sprzedazy);
                a.putExtra("wszystko", Wszystko);
                a.putExtra("magazyn", Magazyn);
                a.putExtra("kuchnia", Kuchnia);
                startActivity(a);}
              else
              {
                 showToast("Brak uprawnień");
               }
             }
        });

        wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Glowne_menu.this, Logowanie.class);
                startActivity(c);
            }
        });
    }
}
