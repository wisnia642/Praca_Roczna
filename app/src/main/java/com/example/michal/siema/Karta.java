package com.example.michal.siema;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Karta extends ActionBarActivity {

    Button buton1, buton2, buton3, buton4, buton5, buton6, buton7, buton8, buton9, buton10, buton11, buton12,
            buton13, buton14, buton15, buton16;

    Bundle applesData;
    String posilek, userMassage;
    String[] zdjecie = new String[17];
    int wiadomosc = 0;
    int stan = 0;
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

            sampleDB.execSQL("INSERT INTO Karta ('Nazwa','Zdjecie','Stan') VALUES ('" + userMassage + "','" + zdjecie[stan] + "','" + stan + "')");

            sampleDB.close();
        } catch (Exception e) {
            showToast("Blad w update");
        }
    }


    private void readsqlLight() {
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

    public void menu()
    {
        switch (wiadomosc){
            case 1: Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);break;

            case 2:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);break;

            case 3:
                buton1.setBackgroundResource(R.drawable.brak);
                zdjecie[stan]= "brak";
                savesqlLight(); break;

            case 4:
                zdjecie[stan]="";
                savesqlLight(); break;
            default:
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta);

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
            readsqlLight();
            try {
                if(zdjecie[0].equals("brak")) {buton1.setBackgroundResource(R.drawable.brak);}
                else {
                     thumbnail = (BitmapFactory.decodeFile(zdjecie[0]));
                    buton1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                }

                if(zdjecie[1].equals("brak")){buton2.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[1]));
                buton2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[2].equals("brak")){buton3.setBackgroundResource(R.drawable.brak);}
                else{
                thumbnail = (BitmapFactory.decodeFile(zdjecie[2]));
                buton3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[3].equals("brak")){buton4.setBackgroundResource(R.drawable.brak);}
                else{
                thumbnail = (BitmapFactory.decodeFile(zdjecie[3]));
                buton4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[4].equals("brak")){buton5.setBackgroundResource(R.drawable.brak);}
                else{
                thumbnail = (BitmapFactory.decodeFile(zdjecie[4]));
                buton5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[5].equals("brak")){buton6.setBackgroundResource(R.drawable.brak);}
                else{
                thumbnail = (BitmapFactory.decodeFile(zdjecie[5]));
                buton6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[6].equals("brak")){buton7.setBackgroundResource(R.drawable.brak);}
                else{
                thumbnail = (BitmapFactory.decodeFile(zdjecie[6]));
                buton7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[7]=="brak"){buton8.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[7]));
                buton8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[8]=="brak"){buton9.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[8]));
                buton9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[9]=="brak"){buton10.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[9]));
                buton10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[10]=="brak"){buton11.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[10]));
                buton11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[11]=="brak"){buton12.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[11]));
                buton12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[12]=="brak"){buton13.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[12]));
                buton13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[13]=="brak"){buton14.setBackgroundResource(R.drawable.brak);}
                else{
                thumbnail = (BitmapFactory.decodeFile(zdjecie[13]));
                buton14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[14]=="brak"){buton15.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[14]));
                buton15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}

                if(zdjecie[15]=="brak"){buton16.setBackgroundResource(R.drawable.brak);}
                else {
                thumbnail = (BitmapFactory.decodeFile(zdjecie[15]));
                buton16.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));}


            } catch (Exception e) {
            }

            buton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Makarony";
                    stan = 0;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Przystawki";
                    stan = 1;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Ryba";
                    stan=2;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Salatki";
                    stan=3;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Fast_Food";
                    stan=4;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Pizza";
                    stan=5;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Zupy";
                    stan=6;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Suszi";
                    stan=7;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Wina";
                    stan=8;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Piwo";
                    stan=9;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Desery";
                    stan=10;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Dodatki";
                    stan=11;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Napoje_Gazowane";
                    stan=12;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Napoje_Zimne";
                    stan=13;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Napoje_Gorace";
                    stan=14;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });
            buton16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Karta.this, Lista.class);
                    userMassage = "Soki";
                    stan = 15;
                    i.putExtra("applesMessage", userMassage);
                    i.putExtra("Sala", posilek);
                    startActivity(i);
                }
            });

            buton1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    userMassage = "Makarony";
                    stan = 0;
                    menu();
                    return true;
                }
            });
        buton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    userMassage = "Przystawki";
                    stan = 1;
                   menu();
                return true;
            }
        });

        buton3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    userMassage = "Ryba";
                    stan = 2;
               menu();
                return true;
            }
        });

        buton4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Salatki";
                stan=3;
                menu();
                return true;
            }
        });

        buton5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Fast_Food";
                stan=4;
                menu();
                return true;
            }
        });

        buton6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Pizza";
                stan=5;
                menu();
                return true;
            }
        });

        buton7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Zupy";
                stan=6;
                menu();
                return true;
            }
        });

        buton8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Suszi";
                stan=7;
                menu();
                return true;
            }
        });

        buton9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Wina";
                stan=8;
                menu();
                return true;
            }
        });

        buton10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Piwo";
                stan=9;
                menu();
                return true;
            }
        });

        buton11.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Desery";
                stan=10;
                menu();
                return true;
            }
        });

        buton12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Dodatki";
                stan=11;
                menu();
                return true;
            }
        });

        buton13.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Napoje_Gazowane";
                stan=12;
                menu();
                return true;
            }
        });

        buton14.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Napoje_Zimne";
                stan=13;
                menu();
                return true;
            }
        });
        buton15.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Napoje_Gorace";
                stan=14;
                menu();
                return true;
            }
        });
        buton16.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                userMassage = "Soki";
                stan=15;
                menu();
                return true;
            }
        });

        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CAMERA_PIC_REQUEST) {
                //2
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                switch (stan) {
                    case 0:
                        buton1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 1:
                        buton2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 2:
                        buton3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 3:
                        buton4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 4:
                        buton5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 5:
                        buton6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 6:
                        buton7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 7:
                        buton8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 8:
                        buton9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 9:
                        buton10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 10:
                        buton11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 11:
                        buton12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 12:
                        buton13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 13:
                        buton14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 14:
                        buton15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 16:
                        buton16.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    default:
                        break;
                }
                //3
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                //4
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + userMassage+".jpg");
                try {
                    zdjecie[stan] = String.valueOf(file);
                    savesqlLight();
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    //5
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                zdjecie[stan] = picturePath;
                savesqlLight();
                Bitmap thumbnail = (BitmapFactory.decodeFile(zdjecie[stan]));
                // Log.w("path of image from gallery......******************.........", picturePath+"");
                switch (stan) {
                    case 0:
                        buton1.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 1:
                        buton2.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 2:
                        buton3.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 3:
                        buton4.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 4:
                        buton5.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 5:
                        buton6.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 6:
                        buton7.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 7:
                        buton8.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 8:
                        buton9.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 9:
                        buton10.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 10:
                        buton11.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 11:
                        buton12.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 12:
                        buton13.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 13:
                        buton14.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 14:
                        buton15.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                        break;
                    case 16:
                    buton16.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(thumbnail, 95, 28, true)));
                    break;
                    default:
                        break;
                }

            }}

            @Override
            public boolean onCreateOptionsMenu (Menu menu){
                menu.add(0, PIERWSZY_ELEMENT, 0, "Zrob Zdjecie");
                menu.add(1, DRUGI_ELEMENT, 0, "Dodaj zdjêcie");
                menu.add(2, TRZECI_ELEMENT, 0, "Brak Zdjecia");
                menu.add(3, CZWARTY_ELEMENT, 0, "Reset Zdjecia");
                return true;
            }

            @Override
            public boolean onOptionsItemSelected (MenuItem item){

                switch (item.getItemId()) {

                    case PIERWSZY_ELEMENT:
                           wiadomosc=1;
                            showToast("aby dodac zdjecie przytrzymaj dana kategorie");
                        break;
                    case DRUGI_ELEMENT:
                        wiadomosc=2;
                        showToast("aby dodac zdjecie przytrzymaj dana kategorie");
                        break;
                    case TRZECI_ELEMENT:
                        wiadomosc=3;
                        showToast("aby dodac zdjecie przytrzymaj dana kategorie");
                        break;
                    case CZWARTY_ELEMENT:
                        wiadomosc=4;
                        showToast("aby dodac zdjecie przytrzymaj dana kategorie");
                        break;
                    default:


                }
                return true;
            }

        }

