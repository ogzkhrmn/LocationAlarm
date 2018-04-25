package com.herosoft.locationalarm.sunservices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.herosoft.locationalarm.AlarmInfo;

/**
 * Created by oguzhero on 12/10/2017.
 */

public class AlarmNotifyService extends Service {
    LocationManager manager;

    @Override
    public void onCreate() {
        manager= (LocationManager) getSystemService(LOCATION_SERVICE);
        Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(startId!=1){
           new String("adasd");
        }
        return Service.START_STICKY;
    }
}
