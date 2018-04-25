package com.herosoft.locationalarm.sunservices;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.herosoft.locationalarm.AlarmInfo;

public class AlarmTask extends AsyncTask<Object, Integer, Long> implements LocationListener {
    private Location location;
    @Override
    protected Long doInBackground(Object... objects) {
        LocationManager lm = (LocationManager) objects[0];
        Looper.prepare();
        if (!(ActivityCompat.checkSelfPermission((Context) objects[2], Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission((Context) objects[2], Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60, 10, this);
        }
        if(location != null){
            AlarmInfo info = (AlarmInfo) objects[1];
            double dist = Math.abs(location.getLatitude() - info.getLatitude()) + Math.abs(location.getLongitude() - info.getLongitude());
            if(dist > 10.0){
                Toast.makeText((Context) objects[2],"Location found",Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
