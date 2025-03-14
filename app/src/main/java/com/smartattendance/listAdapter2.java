package com.smartattendance;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class listAdapter2 extends BaseAdapter{
    ArrayList<String> nameList;
    ArrayList<String> registers;
    ArrayList<String> adate;
    ArrayList<String> ahour;
    Activity activity;

    ArrayList<Boolean> attendanceList;
    public listAdapter2(Activity activity,ArrayList<String> adate,ArrayList<String> ahour,ArrayList<String> nameList, ArrayList<String> reg) {
        this.nameList = nameList;
        this.activity = activity;
        this.adate = adate;
        this.ahour = ahour;
        this.registers = reg;
        attendanceList = new ArrayList<>();
        for(int i=0; i<nameList.size(); i++)
        {
            attendanceList.add(new Boolean(true));
        }
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.list_ele, null);
        }
        final int pos = position;
        TextView textView = (TextView) v.findViewById(R.id.attendanceName);
        textView.setText(nameList.get(position));
        final CheckBox checkBox = (CheckBox)v.findViewById(R.id.attMarker);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceList.set(pos,checkBox.isChecked());
                Log.d("Attendance", nameList.get(position).toString() + " is absent " + attendanceList.get(position));
            }
        });
        return v;
    }

    public void saveAll()
    {
        for(int i=0; i<nameList.size(); i++)
        {
            int sts = 1;
            if(attendanceList.get(i))
                sts = 1;
            else sts = 0;
            String qu = "INSERT INTO ATTENDANCE VALUES('" +attendanceActivity.time + "',"+
                    "" + attendanceActivity.period + ","+
                    "'" + registers.get(i) + "',"+
                    "" + sts + ");";
            AppBase.handler.execAction(qu);
            activity.finish();
        }
    }
}
