package com.galeras.udenar.udenarapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;


public class MainMenu extends ActionBarActivity {

    String key;
    Context cntx = this;

    HttpClient httpclient;
    HttpGet httpGet;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("Key");

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public void callProfile(View view) {
        Intent i = new Intent(cntx, Profile.class);
        i.putExtra("Key", key);
        startActivity(i);
    }

    public void callNews(View view) {
        Intent i = new Intent(cntx, News.class);
        startActivity(i);
    }

    public void callSubjects(View view) {
        Intent i = new Intent(cntx, Subjects.class);
        i.putExtra("Key", key);
        startActivity(i);
        finish();
    }

    public void callTickets(View view) {
        Intent i = new Intent(cntx, Data.class);
        i.putExtra("Key", key);
        startActivity(i);
        finish();
    }

    public void callGrades(View view) {
        Intent i = new Intent(cntx, Grades.class);
        i.putExtra("Key", key);
        startActivity(i);
        finish();
    }

    public void callMap(View view) {
        Intent i = new Intent(cntx, MapsActivity.class);
        i.putExtra("Key", key);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
        else if (id == R.id.action_logout){
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {


        httpclient = new DefaultHttpClient();

        dialog = new ProgressDialog(cntx);
        dialog.setMessage(getString(R.string.logout));
        dialog.setCancelable(false);

        httpGet = new HttpGet(Conection.LOGOUT_URL + key);

        new MyLogoutTask().execute();
    }

    public class MyLogoutTask extends AsyncTask<String, Float, ListView> {

        protected void onPreExecute() {
            dialog.show();
        }

        protected ListView doInBackground(String... urls) {
            try {

                HttpResponse response = httpclient.execute(httpGet);

            } catch (Exception e) {
                Log.v("err", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(Float... valores) {
        }

        protected void onPostExecute(ListView bytes) {

            dialog.dismiss();
            System.runFinalization();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        logout();
    }
}
