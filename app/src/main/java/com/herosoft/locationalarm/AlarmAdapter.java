package com.herosoft.locationalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by oguzhero on 12/8/2017.
 */

public class AlarmAdapter extends ArrayAdapter{

    private final Context context;
    private List<AlarmInfo> values;

    public AlarmAdapter(Context context, int textViewResourceId, List<AlarmInfo> values) {
        super(context, R.layout.activity_main, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.content_main, null);
        TextView textView = v.findViewById(R.id.alarmtext);
        TextView textView2 = v.findViewById(R.id.daytext);
        String days = "";
        for(String s:values.get(position).getAlarmDays()){
            days = days + " " + s ;
        }
        textView.setText(values.get(position).getAlarmClock());
        textView2.setText(days);
        return v;
    }

}
