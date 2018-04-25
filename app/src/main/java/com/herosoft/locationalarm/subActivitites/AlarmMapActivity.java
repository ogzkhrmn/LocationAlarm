package com.herosoft.locationalarm.subActivitites;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.herosoft.locationalarm.R;

public class AlarmMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    SupportMapFragment mapFragment;
    private LatLng pass = null;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_map_selct);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        myMap = googleMap;
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60, 10, this);
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

                @Override
                public boolean onMyLocationButtonClick() {
                    Location location = myMap.getMyLocation();
                    pass = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pass, 15);
                    myMap.animateCamera(cameraUpdate);
                    return true;
                }
            });
        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                pass = latLng;
                googleMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        final Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (location != null)
                {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
                    myMap.animateCamera(cameraUpdate);
                    pass = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }
        });
    }

    public void addClock(View v) {

            startActivity(new Intent(this, SetAlarmDatesActivity.class)
                    .putExtra("LocationObj",pass));

    }

    @Override
    public void onLocationChanged(Location location) {
        if(pass == null){
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            myMap.animateCamera(cameraUpdate);
            locationManager.removeUpdates(this);
            pass = latLng;
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
