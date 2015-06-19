package com.galeras.udenar.udenarapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Login extends ActionBarActivity {

    EditText userName;
    EditText userPass;

    HttpClient httpclient;
    HttpGet httpGet;
    ProgressDialog dialog;

    String rspLogin = "NULL";
    Context cntx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userName = (EditText) findViewById(R.id.ed_user_name);
        userPass = (EditText) findViewById(R.id.ed_user_pass);

        httpclient = new DefaultHttpClient();

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.on_load));
        dialog.setCancelable(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void login(View view) {

        if (userName.getText().toString().compareTo("") == 0) {
            Toast.makeText(this, R.string.err_login_name, Toast.LENGTH_LONG).show();
        } else if (userPass.getText().toString().compareTo("") == 0) {
            Toast.makeText(this, R.string.err_login_pass, Toast.LENGTH_LONG).show();
        } else {
            httpGet = new HttpGet(Conection.LOGIN_URL + userName.getText() + "/" + userPass.getText());
            new MyLoginTask().execute();

        }
    }

    public class MyLoginTask extends AsyncTask<String, Float, ListView> {

        protected void onPreExecute() {
            dialog.show();
        }

        protected ListView doInBackground(String... urls) {
            try {

                HttpResponse response = httpclient.execute(httpGet);
                String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                JSONObject jsonMainNode = new JSONObject(jsonResult);
                rspLogin = jsonMainNode.optString("Token");

            } catch (Exception e) {
            }
            return null;
        }

        protected void onProgressUpdate(Float... valores) {
        }

        protected void onPostExecute(ListView bytes) {

            try {
                if (rspLogin.compareTo("NULL") == 0) {
                    Toast.makeText(cntx, R.string.err_login, Toast.LENGTH_LONG).show();
                } else if (rspLogin.compareTo("NULL") != 0) {
                    Intent i = new Intent(cntx, MainMenu.class);
                    i.putExtra("Key",rspLogin);
                    startActivity(i);
                    finish();
                }

            } catch (Exception e) {

            } finally {
                dialog.dismiss();
            }

        }
    }

    private StringBuilder inputStreamToString(InputStream rspLogin) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(rspLogin));

        try {
            while ((rLine = rd.readLine()) != null)
                answer.append(rLine);
        } catch (IOException e) {
        }

        return answer;
    }
}
