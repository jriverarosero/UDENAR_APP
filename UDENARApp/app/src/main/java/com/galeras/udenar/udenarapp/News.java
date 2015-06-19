package com.galeras.udenar.udenarapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;


public class News extends ActionBarActivity {

    static final String DATA_TITLE = "T";
    static final String DATA_LINK = "L";
    static LinkedList<HashMap<String, String>> data;

    ListView lvn;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lvn = (ListView) findViewById(R.id.lstData);

        lvn.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int position,
                                    long id) {
                /**
                 * Obtenemos el elemento sobre el que se presion—
                 */
                HashMap<String, String> entry = data.get(position);

                /**
                 * Preparamos el intent ACTION_VIEW y luego iniciamos la actividad (navegador en este caso)
                 */
                Intent browserAction = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(entry.get(DATA_LINK)));
                startActivity(browserAction);
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.on_load_news));
        dialog.setCancelable(false);

        loadNews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
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
            Intent i = new Intent(this, About.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private final Handler progressHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                data = (LinkedList<HashMap<String, String>>) msg.obj;
                setData(data);
            }
            dialog.dismiss();
        }
    };

    public void loadNews() {
        ListView lvn = (ListView) findViewById(R.id.lstData);
        /**
         * Si el ListView ya contiene datos (es diferente de null) vamos
         * a mostrar un di‡logo preguntando al usuario si est‡ seguro de
         * realizar la carga de nuevo
         */
        if (lvn.getAdapter() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(News.this);
            builder.setMessage("ya ha cargado datos, ÀEst‡ seguro de hacerlo de nuevo?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            loadData();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create()
                    .show();

            /**
             * Si el ListView no contiene datos (es null) cargamos con loadData()
             */
        } else {
            loadData();
        }
    }


    private void setData(LinkedList<HashMap<String, String>> data) {
        SimpleAdapter sAdapter = new SimpleAdapter(getApplicationContext(), data,
                android.R.layout.two_line_list_item,
                new String[]{DATA_TITLE, DATA_LINK},
                new int[]{android.R.id.text1, android.R.id.text2});
        ListView lv = (ListView) findViewById(R.id.lstData);
        lv.setAdapter(sAdapter);
    }

    /**
     * Funci—n auxiliar que inicia la carga de datos, muestra al usuario un di‡logo de que
     * se est‡n cargando los datos y levanta un thread para lograr la carga.
     */
    private void loadData() {
        dialog = ProgressDialog.show(
                News.this,
                "",
                getString(R.string.on_load_news),
                true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                XMLParser parser = new XMLParser(Conection.RSS_URL);
                Message msg = progressHandler.obtainMessage();
                msg.obj = parser.parse();
                progressHandler.sendMessage(msg);
            }
        }).start();
    }
}
