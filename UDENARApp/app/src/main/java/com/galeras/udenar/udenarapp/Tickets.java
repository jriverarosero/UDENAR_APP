package com.galeras.udenar.udenarapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Tickets extends ActionBarActivity {

    String key;
    String startDate;
    String finishDate;


    TextView name;
    TextView lista;
    String nom = " ";
    String ver = "";
    int ver2 = 0;

    HttpClient httpclient;
    HttpGet httpGet;
    ProgressDialog dialog;
    Context cntx = this;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView lv;


    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("Key");
        startDate = bundle.getString("startDate");
        finishDate = bundle.getString("finishDate");

        httpclient = new DefaultHttpClient();

        lv = (ListView) findViewById(R.id.lvt);


        name = (TextView) findViewById(R.id.t_user_name);
        lista = (TextView) findViewById(R.id.list_tickets);


        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.on_load_tickets));
        dialog.setCancelable(false);

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setTitle(getString(R.string.other_usr_AD));
        alertDialog.setMessage(getString(R.string.other_usr));
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.runFinalization();
                System.exit(0);
            }
        });

        Log.e("URL", Conection.TICKETS_URL + key + "/" + startDate + "/" + finishDate);
        httpGet = new HttpGet(Conection.TICKETS_URL + key + "/" + startDate + "/" + finishDate);
        new MyTicketsTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tickets, menu);
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
            Intent i = new Intent(cntx, About.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public class MyTicketsTask extends AsyncTask<String, Float, ListView> {

        protected void onPreExecute() {
            dialog.show();
        }

        protected ListView doInBackground(String... urls) {
            try {
                HttpResponse response = httpclient.execute(httpGet);
                list = new ArrayList<String>();

                String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                JSONObject jsonMainNode = new JSONObject(jsonResult);
                JSONArray jsonArray = jsonMainNode.getJSONArray("desprendibles");

                ver = jsonMainNode.optString("nombre");
                nom = jsonMainNode.optString("nombre") + "   " + jsonMainNode.optString("apellido");

                ver2 = jsonArray.length();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject ch = jsonArray.getJSONObject(i);
                    String cad = "\n" + ch.optString("fecha");
                    String cad1 = "\n" + ch.optString("seccion");
                    String cad2 = ch.optString("banco");
                    String cad3 = "\n" + "Concepto:";
                    String cad4 = ch.optString("concepto");
                    String cad5 = ch.optString("nombre");
                    String cad6 = ch.optString("tipo");

                    //String totCad = "" + cad + " " + cad1 + " " + cad2 + " " + cad3 + " " + cad4 + " " + cad5 + " " + cad6 + " ";
                    String totCad = "";
                    if (cad6.compareTo("D") == 0) {
                        totCad = "" + cad + " " + cad1 + " " + cad2 + " " + cad3 + " " + cad4 + " " + "\nDescuento\t" + cad5;
                    } else if (cad6.compareTo("B") == 0) {
                        totCad = "" + cad + " " + cad1 + " " + cad2 + " " + cad3 + " " + cad4 + " " + "\nPago\t" + cad5;
                    }

                    list.add(totCad);
                }


            } catch (Exception e) {
                Log.v("err", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(Float... valores) {
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null)
                    answer.append(rLine);
            } catch (IOException e) {
            }

            return answer;
        }

        protected void onPostExecute(ListView bytes) {

            try {

                adapter = new ArrayAdapter<String>(cntx, android.R.layout.simple_list_item_1, list);
                lv.setAdapter(adapter);

            } catch (Exception e) {

            } finally {

                dialog.dismiss();

                if (ver.compareTo("NULL") == 0 || ver.compareTo("") == 0) {
                    name.setText(getString(R.string.other_usr));
                    alertDialog.show();
                } else {
                    name.setText(nom);
                }
                if (ver2 <= 0) {
                    lista.setText(getString(R.string.list_tickets_empty));
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(cntx, MainMenu.class);
        i.putExtra("Key", key);
        startActivity(i);
        finish();
    }

}
