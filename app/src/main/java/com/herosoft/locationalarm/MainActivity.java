package com.herosoft.locationalarm;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.herosoft.locationalarm.subActivitites.AlarmMapActivity;
import com.herosoft.locationalarm.subActivitites.EditAlarmDatesActivity;
import com.herosoft.locationalarm.subActivitites.EditMapActivity;
import com.herosoft.locationalarm.sunservices.AlarmNotifyService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager = null;
    ListView listview;
    AlarmAdapter adapter = null;
    public static List<AlarmInfo> listInfo = new ArrayList<AlarmInfo>();
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ObjectInputStream input = null;
        String filename = "testFilemost.srl";
        try (ObjectInputStream objectInputStream = input = new ObjectInputStream(new FileInputStream(new File(getFilesDir(),"")+File.separator+filename))) {
            listInfo = (List<AlarmInfo>) input.readObject();
        }catch (FileNotFoundException e){
            ObjectOutput out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream(new File(new File(getFilesDir(),"")+File.separator+filename)));
                out.writeObject(listInfo);
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        adapter = new AlarmAdapter(this, R.layout.content_main ,listInfo);
        listview = findViewById(R.id.simpleListView);
        listview.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        }else{
            showGPSDisabledAlertToUser();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToList();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("What do you want to do ?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listInfo.remove(position);
                                ObjectOutput out = null;
                                String filename = "testFilemost.srl";
                                try {
                                    out = new ObjectOutputStream(new FileOutputStream(new File(new File(getFilesDir(),"")+File.separator+filename)));
                                    out.writeObject(MainActivity.listInfo);
                                    out.close();
                                    listview.invalidateViews();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }

                        })
                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, EditMapActivity.class);
                                intent.putExtra("listinfoobj",listInfo.get(position));
                                intent.putExtra("whichone",position);
                                startActivity(intent);
                            }
                        })
                        .show();


                return true;
            }
        });

        Intent intent = new Intent(this, AlarmNotifyService.class);
        startService(intent);
    }


    @Override
    protected void onResume(){
        super.onResume();
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        }else{
            showGPSDisabledAlertToUser();
        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        System.exit(1);
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    System.exit(1);
                }


            }
            default :
                System.exit(1);


                // other 'case' lines to check for other
                // permissions this app might request
        }
    }

    public void addItemToList(){
       startActivity(new Intent(this, AlarmMapActivity.class));
    }
}
