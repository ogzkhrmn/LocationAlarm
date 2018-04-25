package com.herosoft.locationalarm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.ReflectedParcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by oguzhero on 12/8/2017.
 */

public class AlarmInfo extends Object implements Serializable {

    private String alarmClock;
    private List<String> alarmDays;
    private double latitude;
    private double longitude;
    private String alarmUid;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAlarmClock() {
        return alarmClock;
    }

    public void setAlarmClock(String alarmClock) {
        this.alarmClock = alarmClock;
    }

    public List<String> getAlarmDays() {
        return alarmDays;
    }

    public void setAlarmDays(List<String> alarmDays) {
        this.alarmDays = alarmDays;
    }

    public String getAlarmUid() {
        return alarmUid;
    }

    public void setAlarmUid(String alarmUid) {
        this.alarmUid = alarmUid;
    }
}
