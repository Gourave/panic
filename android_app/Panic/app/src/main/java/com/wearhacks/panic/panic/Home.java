package com.wearhacks.panic.panic;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;


public class Home extends ActionBarActivity implements LocationListener {

    String userLatitude;
    String userLongitude;
    Button mPanicButton;
    AudioRecording audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        audio = new AudioRecording();
        mPanicButton = (Button)findViewById(R.id.bPanic);

        mPanicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                audio.onRecord(true);

                Thread stopRecording = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           Thread.sleep(15000);
                           audio.onRecord(false);
                       }
                       catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
                });
                audio.onRecord(false);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent mOpenSettings = new Intent(this, Settings.class);
            startActivity(mOpenSettings);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

        userLatitude = Double.toString(location.getLatitude());
        userLongitude = Double.toString(location.getLongitude());

        // Below is for testing
        Toast.makeText(Home.this, userLatitude, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }
    @Override
    public void onProviderEnabled(String provider) { }
    @Override
    public void onProviderDisabled(String provider) { }

}
