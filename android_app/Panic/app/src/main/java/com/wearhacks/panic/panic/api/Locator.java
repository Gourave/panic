package com.wearhacks.panic.panic.api;

/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.wearhacks.panic.panic.Home;

public class Locator implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener,
        android.location.LocationListener {
    Activity activity;
    double lat = 0;
    double lng = 0;
    int ENABLED = 1;
    int DISABLED = 2;
    boolean status;
    private static Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = getClass().getSimpleName();
    private LocationRequest mLocationRequest;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setSmallestDisplacement(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    public Locator(Activity activity) {
        this.activity = activity;
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        Log.i(TAG, "Location Request recieved");
    }

    public boolean getCurrentLocation() {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        Home.currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            status = true;
            Intent i = new Intent();
            i.setAction("com.stampitgo.location_update");
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
            Bundle extras = new Bundle();
            extras.putDouble("lat", lat);
            extras.putDouble("lng", lng);
            i.putExtras(extras);

//            LocalBroadcastManager.getInstance(
//                    AppController.getInstance().getApplicationContext())
//                    .sendBroadcast(i);

        } else {
            status = false;
        }
        return status;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        createLocationRequest();
    }

    @Override
    public void onConnected(Bundle arg0) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {

        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + arg0.getErrorCode());

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location arg0) {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        Home.currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(activity, "Location Enabled", Toast.LENGTH_SHORT).show();
        getCurrentLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}