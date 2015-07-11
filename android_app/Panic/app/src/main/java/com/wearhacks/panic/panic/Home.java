package com.wearhacks.panic.panic;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Vibrator;
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
import com.wearhacks.panic.panic.api.HttpMultipartUploader;
import com.wearhacks.panic.panic.api.Locator;
import com.wearhacks.panic.panic.api.PanicPackage;
import com.wearhacks.panic.panic.api.PanicPackageService;
import com.wearhacks.panic.panic.smssending.SmsServices;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Home extends ActionBarActivity {

    public static Location currentLocation; //Yeah, we went there. #YOLO

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

    private Vibrator v;

    private SmsServices message;

    private String panicMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Locator locator = new Locator(this);
        locator.getCurrentLocation();

        name = "";
        heartbeat = 0;
        latitude = 0;
        longitude = 0;
        temperature = 0;

        audio = new AudioRecording(getApplicationContext());
        audio.setOnAudioRecordingCompleteListener(new AudioRecording.OnAudioRecordingCompleteListener() {
            @Override
            public void recordingComplete() {
                //Upload the audio file after recording is complete
                HttpMultipartUploader uploader = new HttpMultipartUploader();
                uploader.execute(audio.getAudioFile());
            }
        });

        mPanicButton = (Button)findViewById(R.id.bPanic);
        mChangeMyo = (Button)findViewById(R.id.bChangeMyo);
        mLayoutHome = (RelativeLayout)findViewById(R.id.container_home);

        message = new SmsServices();
        panicMessage = "I just pressed the panic button and need help RIGHT NOW. Please visit ";

        mPanicButton.setEnabled(true);

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
                panicCommand();
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

            if (pose == pose.FIST) {
                //Toast.makeText(Home.this, "Pose: " + pose, Toast.LENGTH_SHORT).show();
                myo.vibrate(Myo.VibrationType.SHORT);
                v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

                // Vibrate for 500 milliseconds
                v.vibrate(500);

                panicCommand();
            }

        }
    };

    private void panicCommand() {

        if (mPanicButton.isEnabled()) {
            mPanicButton.setEnabled( false );

            audio.onRecord(true);

            pkg.name = name;
            pkg.heartbeat = heartbeat;
            pkg.latitude = 43.6597;     //Just as a backup for the demo.
            pkg.longitude = 79.3889;
            pkg.temperature = temperature;
            pkg.filename = audio.getFileName();

            if (currentLocation != null) {
                pkg.latitude = currentLocation.getLatitude();
                pkg.longitude = currentLocation.getLongitude();
            }

            service.submitPackage(pkg, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Log.d("HTTP: ", "Success!");

                    message.sendTextMessage("1234567890", panicMessage + " Please visit http://panicapp.herokuapp.com");
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("HTTP: ", "Failed: " + error.getMessage());
                }
            });
        }

        mPanicButton.setEnabled( true );
    }

}
