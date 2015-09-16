package edu.virginia.cs.httpscs4720.barmate;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.jar.Manifest;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void displayIngredient(View view) {

        EditText nameEditText = (EditText)findViewById(R.id.editText);
        TextView returnDisplay = (TextView)findViewById(R.id.returnText);

        returnDisplay.setText(nameEditText.getText());
        TextView gpsLat = (TextView)findViewById(R.id.gpsLocationLatitude);
        TextView gpsLong = (TextView)findViewById(R.id.gpsLocationLongitude);


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;

         Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            gpsLat.setText("Your latitude is " + lastKnownLocation.getLatitude());
            gpsLong.setText("Your longitude is " + lastKnownLocation.getLongitude());




    }
}