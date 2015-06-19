package com.galeras.udenar.udenarapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


public class Data extends ActionBarActivity {

    EditText startYear;
    EditText startMounth;
    EditText startDay;
    EditText finishYear;
    EditText finishMount;
    EditText finishDay;

    String key;
    String startDate;
    String finishDate;
    String reason = "Unknown";

    Context cntx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("Key");

        startYear = (EditText) findViewById(R.id.startYear);
        startMounth = (EditText) findViewById(R.id.startMounth);
        startDay = (EditText) findViewById(R.id.startDay);
        finishYear = (EditText) findViewById(R.id.finishYear);
        finishMount = (EditText) findViewById(R.id.finishMount);
        finishDay = (EditText) findViewById(R.id.finishDay);

    }

    public void callTickets(View view) {
        if (verifyDate()) {
            Intent i = new Intent(cntx, Tickets.class);
            Log.e("URL","key: "+key+"SDate: "+startDate+" fDate: "+finishDate);
            i.putExtra("Key", key);
            i.putExtra("startDate", startDate);
            i.putExtra("finishDate", finishDate);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(cntx, reason, Toast.LENGTH_LONG).show();
        }

    }

    private boolean verifyDate() {

        int sy = Integer.parseInt(startYear.getText().toString());
        int sm = Integer.parseInt(startMounth.getText().toString());
        int sd = Integer.parseInt(startDay.getText().toString());
        int fy = Integer.parseInt(finishYear.getText().toString());
        int fm = Integer.parseInt(finishMount.getText().toString());
        int fd = Integer.parseInt(finishDay.getText().toString());

        if (((sy > 1900) && (sm > 0 && sm < 13) && (sd > 0 && sd < 32))
                &&
                ((fy > 1900) && (fm > 0 && fm < 13) && (fd > 0 && fd < 32))
                ) {

            startDate=""+sy+"-"+sm+"-"+sd;
            finishDate=""+fy+"-"+fm+"-"+fd;

            return true;
        } else {
            reason = getString(R.string.err_date);
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data, menu);
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
