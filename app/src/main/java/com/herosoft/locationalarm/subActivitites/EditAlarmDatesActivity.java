package com.herosoft.locationalarm.subActivitites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.herosoft.locationalarm.AlarmInfo;
import com.herosoft.locationalarm.MainActivity;
import com.herosoft.locationalarm.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by oguzhero on 12/10/2017.
 */

public class EditAlarmDatesActivity extends Activity {
    private LatLng pass;
    private AlarmInfo info;
    private int which;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.days_lay);
        info = (AlarmInfo)getIntent().getExtras().getSerializable("LocationObj2");
        which = getIntent().getIntExtra("whicalarm",0);
        setExistData(info);
        Button btn = findViewById(R.id.button4);
        btn.setText("Update");
    }

    public void setExistData(AlarmInfo info){
        EditText etext= findViewById(R.id.editText);
        etext.setText(info.getAlarmClock());
        if(info.getAlarmDays().size() == 7){
            CheckBox box = findViewById(R.id.all);
            box.setChecked(true);
            CheckBox box1 = findViewById(R.id.friday);
            CheckBox box2 = findViewById(R.id.monday);
            CheckBox box3 = findViewById(R.id.sunday);
            CheckBox box4 = findViewById(R.id.tuesday);
            CheckBox box5 = findViewById(R.id.thursday);
            CheckBox box6 = findViewById(R.id.saturday);
            CheckBox box7 = findViewById(R.id.wednesday);
            box1.setEnabled(false);
            box2.setEnabled(false);
            box3.setEnabled(false);
            box4.setEnabled(false);
            box5.setEnabled(false);
            box6.setEnabled(false);
            box7.setEnabled(false);
            box1.setChecked(false);
            box2.setChecked(false);
            box3.setChecked(false);
            box4.setChecked(false);
            box5.setChecked(false);
            box6.setChecked(false);
            box7.setChecked(false);
        }else{
            CheckBox box1 = findViewById(R.id.friday);
            CheckBox box2 = findViewById(R.id.monday);
            CheckBox box3 = findViewById(R.id.sunday);
            CheckBox box4 = findViewById(R.id.tuesday);
            CheckBox box5 = findViewById(R.id.thursday);
            CheckBox box6 = findViewById(R.id.saturday);
            CheckBox box7 = findViewById(R.id.wednesday);
            HashMap<String,CheckBox> map = new HashMap<>();
            map.put("Monday ",box2);
            map.put("Friday ",box1);
            map.put("Sunday ",box3);
            map.put("Tuesday ",box4);
            map.put("Wednesday ",box7);
            map.put("Thursday ",box5);
            map.put("Saturday ",box6);
            for(String day:info.getAlarmDays()){
                CheckBox box = map.get(day);
                box.setChecked(true);
            }
        }
    }

    public void selectAll(View v){
        CheckBox box1 = findViewById(R.id.friday);
        CheckBox box2 = findViewById(R.id.monday);
        CheckBox box3 = findViewById(R.id.sunday);
        CheckBox box4 = findViewById(R.id.tuesday);
        CheckBox box5 = findViewById(R.id.thursday);
        CheckBox box6 = findViewById(R.id.saturday);
        CheckBox box7 = findViewById(R.id.wednesday);
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            box1.setEnabled(false);
            box2.setEnabled(false);
            box3.setEnabled(false);
            box4.setEnabled(false);
            box5.setEnabled(false);
            box6.setEnabled(false);
            box7.setEnabled(false);
            box1.setChecked(false);
            box2.setChecked(false);
            box3.setChecked(false);
            box4.setChecked(false);
            box5.setChecked(false);
            box6.setChecked(false);
            box7.setChecked(false);
        }else{
            box1.setEnabled(true);
            box2.setEnabled(true);
            box3.setEnabled(true);
            box4.setEnabled(true);
            box5.setEnabled(true);
            box6.setEnabled(true);
            box7.setEnabled(true);
        }
    }

    public void writeToFile(View v){
        String filename = "testFilemost.srl";
        CheckBox all = findViewById(R.id.selectall);
        ArrayList<String> days = new ArrayList<String>();
        if(all.isChecked()){
            days.add("Monday");
            days.add("Tuesday");
            days.add("Wednesday");
            days.add("Thursday");
            days.add("Friday");
            days.add("Saturday");
            days.add("Sunday");
        }else{
            List<CheckBox> allbox = new ArrayList<>();
            CheckBox box1 = findViewById(R.id.friday);
            CheckBox box2 = findViewById(R.id.monday);
            CheckBox box3 = findViewById(R.id.sunday);
            CheckBox box4 = findViewById(R.id.tuesday);
            CheckBox box5 = findViewById(R.id.thursday);
            CheckBox box6 = findViewById(R.id.saturday);
            CheckBox box7 = findViewById(R.id.wednesday);
            allbox.add(box2);
            allbox.add(box4);
            allbox.add(box7);
            allbox.add(box5);
            allbox.add(box1);
            allbox.add(box6);
            allbox.add(box3);
            for(CheckBox boxes:allbox){
                if(boxes.isChecked()){
                    days.add(boxes.getText().toString() + " ");
                }
            }

        }
        info.setAlarmDays(days);
        EditText etext= findViewById(R.id.editText);
        info.setAlarmClock(etext.getText().toString());
        ObjectOutput out = null;
        try {
            MainActivity.listInfo.remove(which);
            MainActivity.listInfo.add(info);
            out = new ObjectOutputStream(new FileOutputStream(new File(new File(getFilesDir(),"")+File.separator+filename)));
            out.writeObject(MainActivity.listInfo);
            out.close();
            Toast.makeText(this,"Alarm Added",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EditAlarmDatesActivity.this, MainActivity.class);
            i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
