package com.wearhacks.panic.panic;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.scanner.ScanActivity;
import com.wearhacks.panic.panic.api.PaniacPackageAudio;
import com.wearhacks.panic.panic.api.PanicPackage;
import com.wearhacks.panic.panic.api.PanicPackageService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class Home extends ActionBarActivity implements LocationListener {

    private String userLatitude;
    private String userLongitude;

    private Button mPanicButton;
    private Button mChangeMyo;

    private AudioRecording audio;

    private RelativeLayout mLayoutHome;

    private RestAdapter restAdapter;
    private PanicPackageService service;
    private PanicPackage pkg;

    private String name;
    private int heartbeat;
    private double latitude;
    private double longitude;
    private int temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = "";
        heartbeat = 0;
        latitude = 0;
        longitude = 0;
        temperature = 0;
        
        audio = new AudioRecording(getApplicationContext());
        mPanicButton = (Button)findViewById(R.id.bPanic);
        mChangeMyo = (Button)findViewById(R.id.bChangeMyo);
        mLayoutHome = (RelativeLayout)findViewById(R.id.container_home);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://panicapp.herokuapp.com")
                .build();

        service = restAdapter.create(PanicPackageService.class);

        pkg = new PanicPackage();

        Hub hub = Hub.getInstance();
        if (!hub.init(this)) {
            System.out.println("Could not initialize the Hub.");
            finish();
            return;
        }

        Intent intent = new Intent(Home.this, ScanActivity.class);
        Home.this.startActivity(intent);

        mPanicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                audio.onRecord(true);

                /*Thread stopRecording = new Thread(new Runnable() {
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
                });*/

                // stopRecording.start();
                //audio.onRecord(false);


                pkg.name = name;
                pkg.heartbeat = heartbeat;
                pkg.latitude = latitude;
                pkg.longitude = longitude;
                pkg.temperature = temperature;
                pkg.filename = audio.getFileName();

                service.submitPackage(pkg, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Log.d("HTTP: ", "Success!");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("HTTP: ", "Failed: " + error.getMessage());
                    }
                });

                PaniacPackageAudio pkgAudio = new PaniacPackageAudio();
                pkgAudio.audio = audio.getAudioFile();

                // BROKEN. FIX A$AP
                service.upload(pkgAudio, "Paniac_Audio_File", new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Log.d("HTTP: ", "Success!");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("HTTP: ", "Failed: " + error.getMessage());
                    }
                });

                audio.onRecord(false);

            }
        });

        mChangeMyo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                try {
                    Hub.getInstance().removeListener(mListener);

                    Intent intent = new Intent(Home.this, ScanActivity.class);
                    Home.this.startActivity(intent);

                    Hub.getInstance().addListener(mListener);
                    Hub.getInstance().setLockingPolicy(Hub.LockingPolicy.NONE);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Hub.getInstance().setLockingPolicy(Hub.LockingPolicy.NONE);
        Hub.getInstance().addListener(mListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Hub.getInstance().removeListener(mListener);
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

    private DeviceListener mListener = new AbstractDeviceListener() {
        @Override
        public void onConnect(Myo myo, long timestamp) {
            for (int i = 1; i <= 2; i++) {
                myo.vibrate(Myo.VibrationType.SHORT);
            }
        }

        @Override
        public void onDisconnect(Myo myo, long timestamp) {
            myo.vibrate(Myo.VibrationType.LONG);
        }

        @Override
        public void onPose(Myo myo, long timestamp, Pose pose) {

            if (pose != pose.REST) {
                Toast.makeText(Home.this, "Pose: " + pose, Toast.LENGTH_SHORT).show();
                myo.vibrate(Myo.VibrationType.SHORT);
                System.out.println("Pose: " + pose);
            }

        }
    };

}
