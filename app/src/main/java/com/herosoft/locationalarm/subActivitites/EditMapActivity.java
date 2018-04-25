package com.herosoft.locationalarm.subActivitites;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.herosoft.locationalarm.AlarmInfo;
import com.herosoft.locationalarm.R;

/**
 * Created by oguzhero on 12/10/2017.
 */

public class EditMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private SupportMapFragment mapFragment;
    private LatLng pass = null;
    private LocationManager locationManager;
    private GoogleMap myMap;
    private AlarmInfo info;
    private int which;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_map_selct);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Intent intent = getIntent();
        info = (AlarmInfo) intent.getSerializableExtra("listinfoobj");
        which = intent.getIntExtra("whichone",0);
        pass = new LatLng(info.getLatitude(),info.getLongitude());
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
        googleMap.addMarker(new MarkerOptions().position(pass).title(info.getAlarmClock()));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pass,15));
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                pass = latLng;
                googleMap.addMarker(new MarkerOptions().position(latLng).title(info.getAlarmClock()));
                info.setLongitude(latLng.longitude);
                info.setLatitude(latLng.latitude);
            }
        });
    }

    public void addClock(View v) {

        startActivity(new Intent(this, EditAlarmDatesActivity.class)
                .putExtra("LocationObj2", info)
                .putExtra("whicalarm",which));

    }

    @Override
    public void onLocationChanged(Location location) {

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
