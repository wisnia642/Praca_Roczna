package com.example.michal.siema;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Karta_dodawanie extends ActionBarActivity {

    Button buton1, buton2, buton3, buton4, buton5, buton6, buton7, buton8, buton9, buton10, buton11, buton12,
            buton13, buton14, buton15, buton16;

    Bundle applesData;
    String posilek, userMassage;
    String[] zdjecie = new String[17];

    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    Bitmap thumbnail;

    public static final int PIERWSZY_ELEMENT = 1;
    public static final int DRUGI_ELEMENT = 2;
    public static final int TRZECI_ELEMENT = 3;
    public static final int CZWARTY_ELEMENT = 4;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void ToDataBase() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    SAMPLE_TABLE_NAME +
                    " (Nazwa VARCHAR ,Zdjecie VARCHAR,Stan INT);");

        } catch (Exception e) {
        }

    }

    private void savesqlLight() {
        ToDataBase();

        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            sampleDB.execSQL("DELETE FROM Karta WHERE Nazwa=('" + userMassage + "')");

            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }
    }

    private void readsqlLight() {
        ToDataBase();
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            for (int i = 0; i <= 17; i = i + 0) {
                Cursor c = sampleDB.rawQuery("SELECT * FROM Karta WHERE Stan='" + i + "'", null);
                if (c.moveToFirst()) {
                    zdjecie[i] = String.valueOf(c.getString(1));

                }
                i++;
            }
            sampleDB.close();
        } catch (Exception a) {
        }
    }

    private void zdjecie1() {
        //wyswietlanie zdjec na poczatku programu

        if (zdjecie[0].equals("brak")) {
            buton1.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[0]));
            buton1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie2() {
        if (zdjecie[1].equals("brak")) {
            buton2.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[1]));
            buton2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie3() {
        if (zdjecie[2].equals("brak")) {
            buton3.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[2]));
            buton3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie4() {
        if (zdjecie[3].equals("brak")) {
            buton4.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[3]));
            buton4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie5() {
        if (zdjecie[4].equals("brak")) {
            buton5.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[4]));
            buton5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie6() {
        if (zdjecie[5].equals("brak")) {
            buton6.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[5]));
            buton6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }

    }

    private void zdjecie7() {
        if (zdjecie[6].equals("brak")) {
            buton7.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[6]));
            buton7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie8() {
        if (zdjecie[7].equals("brak")) {
            buton8.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[7]));
            buton8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie9() {
        if (zdjecie[8].equals("brak")) {
            buton9.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[8]));
            buton9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie10() {
        if (zdjecie[9].equals("brak")) {
            buton10.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[9]));
            buton10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie11() {
        if (zdjecie[10].equals("brak")) {
            buton11.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[10]));
            buton11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie12() {
        if (zdjecie[11].equals("brak")) {
            buton12.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[11]));
            buton12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie13() {
        if (zdjecie[12].equals("brak")) {
            buton13.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[12]));
            buton13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie14() {
        if (zdjecie[13].equals("brak")) {
            buton14.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[13]));
            buton14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie15() {
        if (zdjecie[14].equals("brak")) {
            buton15.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[14]));
            buton15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }
    }

    private void zdjecie16() {
        if (zdjecie[15].equals("brak")) {
            buton16.setBackgroundResource(R.drawable.brak);
        } else {
            thumbnail = (BitmapFactory.decodeFile(zdjecie[15]));
            buton16.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta_dodawanie);

        applesData = getIntent().getExtras();

        posilek = applesData.getString("Sala");

        buton1 = (Button) findViewById(R.id.makarony);
        buton2 = (Button) findViewById(R.id.przystawki);
        buton3 = (Button) findViewById(R.id.ryba);
        buton4 = (Button) findViewById(R.id.salatki);
        buton5 = (Button) findViewById(R.id.fast_food);
        buton6 = (Button) findViewById(R.id.pizza);
        buton7 = (Button) findViewById(R.id.zupy);
        buton8 = (Button) findViewById(R.id.suszi);
        buton9 = (Button) findViewById(R.id.wina);
        buton10 = (Button) findViewById(R.id.piwo);
        buton11 = (Button) findViewById(R.id.desery);
        buton12 = (Button) findViewById(R.id.dodatki);
        buton13 = (Button) findViewById(R.id.napoje_gazowane);
        buton14 = (Button) findViewById(R.id.napoje_zimne);
        buton15 = (Button) findViewById(R.id.napoje_gorace);
        buton16 = (Button) findViewById(R.id.soki);

        //odczyt z bazy danych i z pliku
        try {
            readsqlLight();
        } catch (Exception e) {
        }
        try {
            zdjecie1();
        } catch (Exception e) {
        }
        try {
            zdjecie2();
        } catch (Exception e) {
        }
        try {
            zdjecie3();
        } catch (Exception e) {
        }
        try {
            zdjecie4();
        } catch (Exception e) {
        }
        try {
            zdjecie5();
        } catch (Exception e) {
        }
        try {
            zdjecie6();
        } catch (Exception e) {
        }
        try {
            zdjecie7();
        } catch (Exception e) {
        }
        try {
            zdjecie8();
        } catch (Exception e) {
        }
        try {
            zdjecie9();
        } catch (Exception e) {
        }
        try {
            zdjecie10();
        } catch (Exception e) {
        }
        try {
            zdjecie11();
        } catch (Exception e) {
        }
        try {
            zdjecie12();
        } catch (Exception e) {
        }
        try {
            zdjecie13();
        } catch (Exception e) {
        }
        try {
            zdjecie14();
        } catch (Exception e) {
        }
        try {
            zdjecie15();
        } catch (Exception e) {
        }
        try {
            zdjecie16();
        } catch (Exception e) {
        }


        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Makarony";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Przystawki";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Ryba";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Salatki";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Fast_Food";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Pizza";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Zupy";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Suszi";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Wina";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Piwo";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Desery";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Dodatki";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Napoje_Gazowane";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Napoje_Zimne";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Napoje_Gorace";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });
        buton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Karta_dodawanie.this, Lista_dodawanie.class);
                userMassage = "Soki";
                i.putExtra("applesMessage", userMassage);
                i.putExtra("Sala", posilek);
                startActivity(i);
            }
        });


        }
}