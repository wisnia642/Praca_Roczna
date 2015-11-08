package com.example.michal.siema;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Glowne_menu extends ActionBarActivity {

    Button kuchnia,magazyn,sala_sprzedaży,wyloguj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowne_menu);

        kuchnia = (Button) findViewById(R.id.button56);
        magazyn = (Button) findViewById(R.id.button57);
        wyloguj = (Button) findViewById(R.id.button58);
        sala_sprzedaży = (Button) findViewById(R.id.button55);

        kuchnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Glowne_menu.this, Kuchnia.class);
                startActivity(a);
            }
        });

        magazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(Glowne_menu.this, Magzyn.class);
                startActivity(b);
            }
        });

        sala_sprzedaży.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Glowne_menu.this, MainActivity.class);
                startActivity(c);
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
