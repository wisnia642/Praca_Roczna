package com.example.michal.siema;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Message;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Lista_zakupow extends ActionBarActivity {

    ListView lista;
    EditText produkty1,stan_krytyczny,ilosc,koszt;
    Button usun,popraw,email,cancel,dodaj,sms;
    Spinner kto_wykonal,kategoria;

    String[] produkty = {"Magazyn","Lodowka","Brak_kategori","Mroznia"};
    String[] Wagi = {"Szt","Kg","Dag","Gram"};

    private static final String SAMPLE_DB_NAME = "Restalracja";
    private static final String SAMPLE_TABLE_NAME = "Karta";

    private static final String url="jdbc:mysql://192.168.1.100:3306/restalracja1234";
    private static final String user="michal";
    private static final String pass="kaseta12";

    static ResultSet rs;
    static Statement st;
    PreparedStatement ps;
    FileInputStream fis = null;
    Connection connection = null;

    String[] Nazwa_produktu = new String[50];
    String[] Kategoria = new String[50];
    Double[] Stan_krytyczny =new Double[50];
    Double[] Ilosc =new Double[50];
    Double[] Cena = new Double[50];
    String[] Przynaleznosc =new String[50];
    String[] Nazwa_produktu1 = new String[30];
    String[] Kategoria1 = new String[30];
    Double[] Stan_krytyczny1 =new Double[30];
    Double[] Ilosc1 =new Double[30];
    Double[] Cena1 = new Double[30];
    String[] Przynaleznosc1 =new String[30];
    String zakupy,zakupy1;

    String phoneNumber = "0790510703";
    String smsBody = "Lista Zakupów stary nie zapomnij :)";



    int y,x,o,v,w,n,b,p;
    Double wynik;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    //tworzenie polaczenia z baza danych
    public void connect()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            return;
        }


        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            showToast("brak polaczenia z internetem");
            return;
        }

    }

    public void readfromMySql(){
        connect();
        if (connection != null) {

            try {
                st = connection.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

    for (int i=0;i<4;i=i+0){
        String sql = "SELECT * FROM "+produkty[i]+"";

        try {
            rs = st.executeQuery(sql);
        } catch (SQLException e1) {
            //  e1.printStackTrace();
        }
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String zm = rs.getString("Nazwa");
                if (zm != null) {
                    Nazwa_produktu[y] = rs.getString("Nazwa");
                    Ilosc[y] = rs.getDouble("Ilosc");
                    Kategoria[y] = rs.getString("Kategoria");
                    Stan_krytyczny[y] = rs.getDouble("Stan_krytyczny");
                    Cena[y] = rs.getDouble("Cena_detaliczna");
                    Przynaleznosc[y] = rs.getString("Przynaleznosc");
                    y++;
                }
            }
            } catch (SQLException e1){}

                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException se) {
                    showToast("brak połączenia z internetem");
                }
                i++;

        }}}

    public void readsqlLight()
    {

        for (int i=0;i<4;i=i+0) {
        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        try {
        Cursor c = sampleDB.rawQuery("SELECT * FROM  "+produkty[i]+"", null);

        while (c.moveToNext()) {
        String zm = String.valueOf(c.getString(0));
        if (zm != null) {
        Nazwa_produktu[y] = String.valueOf(c.getString(0));
        Ilosc[y] = c.getDouble(1);
        Kategoria[y] = String.valueOf(c.getString(2));
        Stan_krytyczny[y] = c.getDouble(3);
            Cena[y] = c.getDouble(6);
            Przynaleznosc[y] = String.valueOf(c.getString(4));
        y++;
        }
        }
        sampleDB.close();
        } catch (Exception e) {
        }
        i++;
        }
        }

    protected void sendEmail() {
        String recipients = "wisnia642@gmail.com";
        String subject = "Lista Zakupów";
      //  String body ="Siema nie zapomniałeś o czymś :)";

        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));

        // prompts email clients only
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, recipients);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, zakupy);
        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose an email client from..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Lista_zakupow.this, "No email client installed.",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void sendSmsByManager() {
        try {

            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,
                    null,
                    smsBody,
                    null,
                    null);
            Toast.makeText(getApplicationContext(), "Your sms has successfully sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {

            Toast.makeText(getApplicationContext(),"Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsBySIntent() {
        // add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // add the message at the sms_body extra field
        smsSIntent.putExtra("sms_body", zakupy);
        try{
            startActivity(smsSIntent);
        } catch (Exception ex) {

            Toast.makeText(Lista_zakupow.this, "Your sms has failed...",

                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsByVIntent() {
        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        // prompts only sms-mms clients
        smsVIntent.setType("vnd.android-dir/mms-sms");
        // extra fields for number and message respectively
        smsVIntent.putExtra("address", phoneNumber);
        smsVIntent.putExtra("sms_body", smsBody);
        try{
            startActivity(smsVIntent);
        } catch (Exception ex) {
            Toast.makeText(Lista_zakupow.this, "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

    }

    public void lista()
    {
        CustomAdapter8 adapter1 = new CustomAdapter8(this, Nazwa_produktu1, Kategoria1, Ilosc1, Stan_krytyczny1, Cena1, Przynaleznosc1);
        lista.setAdapter(adapter1);
    }


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zakupow);

        produkty1 = (EditText) findViewById(R.id.editText11);
        kategoria = (Spinner) findViewById(R.id.spinner7);
        stan_krytyczny = (EditText) findViewById(R.id.editText17);
        kto_wykonal = (Spinner) findViewById(R.id.spinner5);
        usun = (Button) findViewById(R.id.button51);
        popraw = (Button) findViewById(R.id.button52);
        email = (Button) findViewById(R.id.button53);
        cancel = (Button) findViewById(R.id.button54);
        ilosc = (EditText) findViewById(R.id.editText16);
        koszt = (EditText) findViewById(R.id.editText13);
        lista = (ListView) findViewById(R.id.listView4);
        dodaj = (Button) findViewById(R.id.button68);
        sms = (Button) findViewById(R.id.button69);

       readsqlLight();
    if(Nazwa_produktu[0]==null) {
        readfromMySql();
    }

    kto_wykonal.setAdapter(new MyAdapter1(this, R.layout.custom_spiner, produkty));
    kategoria.setAdapter(new MyAdapter(this, R.layout.custom_spiner, Wagi));

    for(int i=0;i<y;i++)
    {
        if(Stan_krytyczny[i]>Ilosc[i]) {

            Nazwa_produktu1[x] = Nazwa_produktu[i];
            Ilosc1[x] = Ilosc[i];
            Kategoria1[x] = Kategoria[i];
            Stan_krytyczny1[x] = Stan_krytyczny[i];
            Cena1[x] = Cena[i];
            Przynaleznosc1[x] = Przynaleznosc[i];
            wynik = Stan_krytyczny[i] - Ilosc[i];
            x++;
        }
        }

        CustomAdapter8 adapter1 = new CustomAdapter8(this, Nazwa_produktu1, Kategoria1, Ilosc1, Stan_krytyczny1, Cena1, Przynaleznosc1);
        lista.setAdapter(adapter1);



    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            produkty1.setText(Nazwa_produktu1[i]);
            for (int j = 0; j <4; j = j + 0) {
                if (Wagi[j].equals(Kategoria1[i])) {
                    kategoria.setSelection(j);
                }
                j++;
            }
            ilosc.setText(String.valueOf(Ilosc1[i]));
            stan_krytyczny.setText(String.valueOf(Stan_krytyczny1[i]));
            koszt.setText(String.valueOf(Cena1[i]));
            for (int j = 0; j <4; j = j + 0) {
                if (produkty[j].equals(Przynaleznosc1[i])) {
                    kto_wykonal.setSelection(j);
                }
                j++;
            }

            b = i;
        }
    });

    dodaj.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Nazwa_produktu1[x] = produkty1.getText().toString();
                Kategoria1[x] = produkty[p];
                Ilosc1[x] = Double.valueOf(ilosc.getText().toString());
                Stan_krytyczny1[x] = Double.valueOf(stan_krytyczny.getText().toString());
                Cena1[x] = Double.valueOf(koszt.getText().toString());
                Przynaleznosc1[x] = produkty[o];
                lista();
            }catch (Exception e){showToast(""+e);}
        }
    });

    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Lista_zakupow.this,Magzyn.class);
            startActivity(i);
        }
    });

    usun.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Nazwa_produktu1[b] = null;
                Kategoria1[b] = null;
                Ilosc1[b] = null;
                Stan_krytyczny1[b] = null;
                Cena1[b] = null;
                Przynaleznosc1[b] = null;
                lista();
            }catch (Exception e){}

        }
    });

    popraw.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Nazwa_produktu1[b] = produkty1.getText().toString();
                Kategoria1[b] = Wagi[p];
                Ilosc1[b] = Double.valueOf(ilosc.getText().toString());
                Stan_krytyczny1[b] = Double.valueOf(stan_krytyczny.getText().toString());
                Cena1[b] = Double.valueOf(koszt.getText().toString());
                Przynaleznosc1[b] = produkty[o];
                lista();
            }catch (Exception e){}
        }
    });

    sms.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            zakupy="Produkt, Kategoria, Ilość, Stan krytyczny, Cena";
            for(int i = 0;i<x;i=i+0)
            {
                zakupy1=zakupy+"\n"+Nazwa_produktu1[i]+"  ,         "+ Kategoria1[i]+"  ,         "+Ilosc1[i]+"  ,         "+Stan_krytyczny1[i]+"  ,         "+Cena1[i];
                zakupy=zakupy1;
                i++;
            }
          // sendSmsByManager();
            sendSmsBySIntent();
           // sendSmsByVIntent();
        }
    });

    email.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            zakupy="Produkt, Kategoria, Ilość, Stan krytyczny, Cena";
            for(int i = 0;i<x;i=i+0)
            {
                zakupy1=zakupy+"\n"+Nazwa_produktu1[i]+"  ,         "+ Kategoria1[i]+"  ,         "+Ilosc1[i]+"  ,         "+Stan_krytyczny1[i]+"  ,         "+Cena1[i];
                zakupy=zakupy1;
                i++;
            }
            sendEmail();
        }
    });

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_zakupow, menu);
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
            o=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text1);
            main_text.setText(produkty[position]);
            return mySpinner;
        }}

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
            p=position;
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spiner, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.text1);
            main_text.setText(Wagi[position]);
            return mySpinner;
        }}
}
