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
import android.widget.Toast;


public class Home extends ActionBarActivity implements LocationListener {

    String locationCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        startAudioRecorder();

    }

    @Override
    public void onPause() {
        super.onPause();

        //put code in here to release use of audio recorder and mic
        releaseAudioRecorder();
    }

    @Override
    public void onResume() {
        super.onResume();

        // put code in here to resume the use of the audio recorder
        startAudioRecorder();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // put in code to release use of audio recorder again
        releaseAudioRecorder();
    }


    private void startAudioRecorder() {
        // Write code here to start the use of the audio recorder

    }

    private void releaseAudioRecorder() {
        // Write code here to release use of the audio recorder


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
            Intent mOpenSettings = new Intent(this, Settings.class);
            startActivity(mOpenSettings);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationCurrent = location.getLatitude() + "," + location.getLongitude();

        Toast.makeText(Home.this, locationCurrent, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }
    @Override
    public void onProviderEnabled(String provider) { }
    @Override
    public void onProviderDisabled(String provider) { }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }


}
