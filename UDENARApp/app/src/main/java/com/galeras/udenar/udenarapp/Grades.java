package com.galeras.udenar.udenarapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Grades extends ActionBarActivity {

    String key;
    TextView name_2;
    TextView level;

    String nom = "";
    String car = " ";
    String cod = " ";

    HttpClient httpclient;
    HttpGet httpGet;
    HttpGet httpGet2;
    ProgressDialog dialog;
    Context cntx = this;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView lvs;

    String ver = "";
    ImageView picStd;

    AlertDialog alertDialog;

    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("Key");

        httpGet = new HttpGet(Conection.GRADES_URL + key);
        httpGet2 = new HttpGet(Conection.CODE_URL + key);
        httpclient = new DefaultHttpClient();

        lvs = (ListView) findViewById(R.id.lvs_gra);

        name_2 = (TextView) findViewById(R.id.std_name_gra);
        level = (TextView) findViewById(R.id.std_level_gra);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.on_load_subjects));
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

        picStd = (ImageView) findViewById(R.id.img_st_gra);

        new MyCodeTask().execute();
        new MyGradesTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grades, menu);
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

    public class MyGradesTask extends AsyncTask<String, Float, ListView> {

        protected void onPreExecute() {
        }

        protected ListView doInBackground(String... urls) {
            try {

                HttpResponse response = httpclient.execute(httpGet);
                list = new ArrayList<String>();
                String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

                JSONObject jsonMainNode = new JSONObject(jsonResult);

                ver = jsonMainNode.optString("nombre");
                nom = jsonMainNode.optString("nombre") + " " + jsonMainNode.optString("apellido");
                car = jsonMainNode.optString("programa") + " " + jsonMainNode.optString("semestre");

                JSONArray materias = jsonMainNode.getJSONArray("grades");

                for (int i = 0; i < materias.length(); i++) {

                    JSONObject ch = materias.getJSONObject(i);
                    String cad = ch.optString("Materia");
                    String cad1 = ch.optString("Nota");
                    String cad2 = ch.optString("tipo_nota");
                    String totCad = "" + cad + " " + cad1 + " " + cad2 + "";
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
                lvs.setAdapter(adapter);

            } catch (Exception e) {

            } finally {
                dialog.dismiss();
                if (ver.compareTo("NULL") == 0 || ver.compareTo("") == 0) {
                    name_2.setText(getString(R.string.other_usr));
                    level.setText(" ");
                    alertDialog.show();
                } else {
                    name_2.setText(nom);
                    level.setText(car);
                }
            }

        }
    }

    public class MyCodeTask extends AsyncTask<String, Float, ListView> {

        protected void onPreExecute() {
            dialog.show();
        }

        protected ListView doInBackground(String... urls) {
            try {

                HttpResponse response = httpclient.execute(httpGet2);
                list = new ArrayList<String>();
                String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

                JSONObject jsonMainNode = new JSONObject(jsonResult);

                cod = jsonMainNode.optString("code");

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

            new LoadResources().execute();

        }
    }

    public Bitmap downloadFile(String imageHttpAddress) {

        Bitmap loadedImage;
        URL imageUrl = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl
                    .openConnection();
            conn.connect();
            loadedImage = redimensionarImagenMaximo(
                    BitmapFactory.decodeStream(conn.getInputStream()), 150, 200);
            return loadedImage;

        } catch (Exception e) {
            loadedImage = drawableToBitmap(R.drawable.user157_);
            return loadedImage;
        }
    }

    public Bitmap drawableToBitmap(int id) {
        // Usamos el método decode Resource de BitmapFactory para la conversión
        return BitmapFactory.decodeResource(cntx.getResources(), id);
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {
        // Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    public class LoadResources extends AsyncTask<String, Float, ListView> {

        protected void onPreExecute() {
            try {
                // load.show();
                // load.setContentView(R.layout.progress_dialog);
            } catch (Exception e) {
            }
        }

        protected ListView doInBackground(String... urls) {
            try {
                img = downloadFile(Conection.IMG_URL + cod + ".JPG");

            } catch (Exception e) {

            }
            return null;
        }

        protected void onProgressUpdate(Float... valores) {
        }

        protected void onPostExecute(ListView bytes) {
            try {

                picStd.setImageBitmap(img);
                new MyGradesTask().execute();

            } catch (Exception e) {

                picStd.setImageResource(R.drawable.user157_);
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
