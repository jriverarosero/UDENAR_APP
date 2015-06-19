package com.galeras.udenar.udenarapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        LatLng ing = new LatLng(1.232716, -77.293606);
        LatLng audit = new LatLng(1.231481, -77.292610);
        LatLng cafeV = new LatLng(1.233315, -77.292997);
        LatLng cafeN = new LatLng(1.231861, -77.292735);
        LatLng mate = new LatLng(1.232321, -77.292626);
        LatLng ocara = new LatLng(1.233203, -77.292735);
        LatLng medicina = new LatLng(1.232442, -77.292008);
        LatLng fuchi = new LatLng(1.232053, -77.292590);
        LatLng face = new LatLng(1.232107, -77.292257);
        LatLng artes = new LatLng(1.230412, -77.293054);
        LatLng biblio = new LatLng(1.231636, -77.292234);
        LatLng admin = new LatLng(1.231278, -77.292348);
        LatLng labesp = new LatLng(1.233622, -77.293644);
        LatLng vete = new LatLng(1.231052, -77.294262);
        LatLng derecho = new LatLng(1.231163, -77.292035);
        LatLng sico = new LatLng(1.231386, -77.291948);
        LatLng parc1 = new LatLng(1.233401, -77.293560);
        LatLng parc2 = new LatLng(1.231192, -77.292619);
        LatLng parc3 = new LatLng(1.231361, -77.291839);
        LatLng coli = new LatLng(1.230531, -77.293923);


        //MarkerOptions ingenieria = new MarkerOptions().position(ing).title("Fac.  Ingeniería").snippet("Bloque 6: Facultad de ingeniería");
        MarkerOptions ingenieria = new MarkerOptions().position(ing).title(getString(R.string.ing));
        MarkerOptions auditorio = new MarkerOptions().position(audit).title(getString(R.string.audit));
        MarkerOptions cafeteria1 = new MarkerOptions().position(cafeV).title(getString(R.string.cafeV));
        MarkerOptions cafeteria2 = new MarkerOptions().position(cafeN).title(getString(R.string.cafeN));
        MarkerOptions matematicas = new MarkerOptions().position(mate).title(getString(R.string.mate));
        MarkerOptions ocarab = new MarkerOptions().position(ocara).title(getString(R.string.ocara));
        MarkerOptions medicinab = new MarkerOptions().position(medicina).title(getString(R.string.medicina));
        MarkerOptions facea = new MarkerOptions().position(face).title(getString(R.string.face));
        MarkerOptions artesb = new MarkerOptions().position(artes).title(getString(R.string.artes));
        MarkerOptions biblioteca = new MarkerOptions().position(biblio).title(getString(R.string.biblio));
        MarkerOptions administrativo = new MarkerOptions().position(admin).title(getString(R.string.admin));
        MarkerOptions laboesp = new MarkerOptions().position(labesp).title(getString(R.string.labesp));
        MarkerOptions veterinaria = new MarkerOptions().position(vete).title(getString(R.string.vete));
        MarkerOptions derechob = new MarkerOptions().position(derecho).title(getString(R.string.derecho));
        MarkerOptions sicocs = new MarkerOptions().position(sico).title(getString(R.string.sico));
        MarkerOptions park1 = new MarkerOptions().position(parc1).title(getString(R.string.parc1));
        MarkerOptions park2 = new MarkerOptions().position(parc2).title(getString(R.string.parc2));
        MarkerOptions park3 = new MarkerOptions().position(parc3).title(getString(R.string.parc3));
        MarkerOptions coliceo = new MarkerOptions().position(coli).title(getString(R.string.coli));
        MarkerOptions pzFuchi = new MarkerOptions().position(fuchi).title(getString(R.string.fuchi));



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mate, 18));

        mMap.addMarker(pzFuchi);
        mMap.addMarker(ingenieria);
        mMap.addMarker(auditorio);
        mMap.addMarker(cafeteria1);
        mMap.addMarker(cafeteria2);
        mMap.addMarker(matematicas);
        mMap.addMarker(ocarab);
        mMap.addMarker(medicinab);
        mMap.addMarker(facea);
        mMap.addMarker(artesb);
        mMap.addMarker(biblioteca);
        mMap.addMarker(administrativo);
        mMap.addMarker(laboesp);
        mMap.addMarker(veterinaria);
        mMap.addMarker(derechob);
        mMap.addMarker(sicocs);
        mMap.addMarker(park1);
        mMap.addMarker(park2);
        mMap.addMarker(park3);
        mMap.addMarker(coliceo);


    }
}
