package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by AGARWAL on 6/27/2016.
 */
public class AddBatch extends DrawerActivity {

    Spinner coursename,date,mon,years,h,m;
    Button submit,back;
    ArrayList<String> al1=new<String>  ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbatch);


        coursename=(Spinner)findViewById(R.id.spncourse);
        date=(Spinner)findViewById(R.id.spnadbat30);
        mon=(Spinner)findViewById(R.id.spnadbat12);
        years=(Spinner)findViewById(R.id.spnadbatyear);
        h=(Spinner)findViewById(R.id.spnadbat24);
        m=(Spinner)findViewById(R.id.spnadbat60);

        submit=(Button)findViewById(R.id.btnaddbatsub);
        back=(Button)findViewById(R.id.btnaddbatback);

        ArrayList<String> al=new<String> ArrayList();

        Calendar calender=Calendar.getInstance();
        int yr=calender.get(calender.YEAR);

        al1.add("Choose Course");
        final ArrayAdapter adapter1=new ArrayAdapter(AddBatch.this,android.R.layout.simple_list_item_1,al1);
        coursename.setAdapter(adapter1);
        Helper.fillCourses(AddBatch.this,al1,adapter1);

        for(int i=0;i<5;i++)
        {
            al.add(""+(yr+i));
        }

        coursename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapter=new ArrayAdapter(AddBatch.this,android.R.layout.simple_list_item_1,al);
        years.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(date.getSelectedItemPosition()==0)
                {
                    Toast.makeText(AddBatch.this,"Please Select Date",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(mon.getSelectedItemPosition()==0)
                {
                    Toast.makeText(AddBatch.this,"Please Select month",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(h.getSelectedItemPosition()==0) {
                    Toast.makeText(AddBatch.this, "Please Select hours", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(m.getSelectedItemPosition()==0) {
                    Toast.makeText(AddBatch.this, "Please Select minutes", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(coursename.getSelectedItemPosition()==0)
                {
                    Toast.makeText(AddBatch.this, "Please Select a Course", Toast.LENGTH_LONG).show();
                    return;

                }

                int day=Integer.parseInt((String) date.getSelectedItem());
                int month=Integer.parseInt((String) mon.getSelectedItem());
                int year=Integer.parseInt((String) years.getSelectedItem());
                int minute=Integer.parseInt((String) m.getSelectedItem());
                int hour=Integer.parseInt((String) h.getSelectedItem());

                java.sql.Date currentd;
                currentd=new java.sql.Date(System.currentTimeMillis());
                try
                {
                    Calendar rightnow;
                    rightnow=Calendar.getInstance();
                    int dd=rightnow.get(Calendar.DATE);
                    int mm=rightnow.get(Calendar.MONTH);
                    int yy=rightnow.get(Calendar.YEAR);

                    currentd=currentd.valueOf(year+"-"+month+"-"+day);
                    java.sql.Date startdate=new java.sql.Date(System.currentTimeMillis());
                    startdate=startdate.valueOf(yy+"-"+mm+"-"+dd);

                    System.out.println("i was here");

                    boolean b=startdate.after(currentd);

                    if(b)
                    {
                        Toast.makeText(AddBatch.this,"YOu Cannot start batch on this date",Toast.LENGTH_LONG).show();
                        return;
                    }

                    int cur_id=Integer.parseInt(((String)coursename.getSelectedItem()).substring(0,4));

                    java.sql.Time currentt=new java.sql.Time(System.currentTimeMillis());
                    currentt=currentt.valueOf(hour+":"+minute+":00");

                    Helper.addBatch(AddBatch.this,cur_id,currentd,currentt);

                }
                catch(Exception ex)
                {
                    System.out.println("Exception in Checkin Date");
                }

                //Toast.makeText(AddBatch.this,""+day+" "+month+" "+year+" "+hour+" "+minute,Toast.LENGTH_LONG).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(AddBatch.this,AdminWindow.class);
                startActivity(intent);*/
                finish();
            }
        });
    }
}
