package com.smartattendance;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class att3 extends AppCompatActivity {

    ListView listView;
    listAdapter2 adapter;
    ArrayAdapter<String> adapterSpinner;
    ArrayList<String> adate;
    ArrayList<String> ahour;
    ArrayList<String> names;
    ArrayList<String> registers;
    Activity thisActivity = this;
    Spinner spinner;
    public static String time,period;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance3);

        time = getIntent().getStringExtra("DATE");
        period = getIntent().getStringExtra("PERIOD");

        Log.d("Attendance", time + " -- " + period);
        listView = (ListView) findViewById(R.id.attendanceListViwe);
        adate = new ArrayList<>();
        ahour = new ArrayList<>();
        names = new ArrayList<>();
        registers = new ArrayList<>();

        Button btn = (Button)findViewById(R.id.loadButton);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadListView(v);
            }
        });

        Button btnx = (Button)findViewById(R.id.buttonSaveAttendance);
        assert btnx != null;
        btnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Saving",Toast.LENGTH_LONG).show();
                adapter.saveAll();
            }
        });

        spinner = (Spinner) findViewById(R.id.attendanceSpinner);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AppBase.divisions);
        assert spinner != null;
        spinner.setAdapter(adapterSpinner);

    }

    public void loadListView(View view) {
        names.clear();
        registers.clear();
        adate.clear();
        ahour.clear();

        String qu = "SELECT * FROM STUDENT WHERE cl = '" + spinner.getSelectedItem().toString() + "' " +
                "ORDER BY REGNO";
        String MY_QUERY = "SELECT * FROM STUDENT a INNER JOIN ATTENDANCE b ON a.regno=b.register WHERE a.cl = '" + spinner.getSelectedItem().toString() + "' " ;


        Cursor cursor = AppBase.handler.execQuery(MY_QUERY);
        if(cursor==null||cursor.getCount()==0)
        {

        }else
        {
            int ctr = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                adate.add(cursor.getString(3));
                ahour.add(cursor.getString(4));
                names.add(cursor.getString(0) + " (" + cursor.getInt(2) + ')');
                registers.add(cursor.getString(2))
                ;
                cursor.moveToNext();
                ctr++;
            }
            if(ctr==0)
            {
                Toast.makeText(getBaseContext(),"No Students Found",Toast.LENGTH_LONG).show();
            }
            Log.d("Attendance", "Got " + ctr + " students");
        }


        adapter = new listAdapter2(thisActivity,adate,ahour,names,registers);
        listView.setAdapter(adapter);
    }
}
