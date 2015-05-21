package com.example.michal.siema;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Karta extends ActionBarActivity {

    Button buton1,buton2,buton3,buton4,buton5,buton6,buton7,buton8,buton9,buton10,buton11,buton12,
            buton13,buton14,buton15,buton16;


    String zdjecie = null;
    String message3;
    Intent intent;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta);

        buton1=(Button) findViewById(R.id.makarony);
        buton2=(Button) findViewById(R.id.przystawki);
        buton3=(Button) findViewById(R.id.ryba);
        buton4=(Button) findViewById(R.id.salatki);
        buton5=(Button) findViewById(R.id.fast_food);
        buton6=(Button) findViewById(R.id.pizza);
        buton7=(Button) findViewById(R.id.zupy);
        buton8=(Button) findViewById(R.id.suszi);
        buton9=(Button) findViewById(R.id.wina);
        buton10=(Button) findViewById(R.id.piwo);
        buton11=(Button) findViewById(R.id.desery);
        buton12=(Button) findViewById(R.id.dodatki);
        buton13=(Button) findViewById(R.id.napoje_gazowane);
        buton14=(Button) findViewById(R.id.napoje_zimne);
        buton15=(Button) findViewById(R.id.napoje_gorace);
        buton16=(Button) findViewById(R.id.soki);

        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Makarony";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Przystawki";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Ryba";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Salatki";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Fast_Food";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Pizza";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Zupy";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Suszi";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Wina";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Piwo";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        }); buton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Desery";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Dodatki";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Napoje_Gazowane";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Napoje_Zimne";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Napoje_Gorace";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });
        buton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta.this,Lista.class);
                String userMassage = "Soki";
                i.putExtra("applesMessage", userMassage);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);
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

