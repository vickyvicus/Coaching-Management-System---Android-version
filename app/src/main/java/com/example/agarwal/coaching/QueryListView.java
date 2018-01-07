package com.example.agarwal.coaching;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by AGARWAL on 7/6/2016.
 */
public class QueryListView extends DrawerActivity {
    ListView list;
    /*String[] web = {
            "Google Plus",
            "Twitter",
            "Windows"
    } ;
    Integer[] imageId = {
            R.drawable.background,
            R.drawable.cutebackground,
            R.drawable.iphonebackround

    };

    String emails[]={
            "vicky.agarwal4us@gmail.com",
            "rockingbuddy229@gmail.com",
            "vickyvicus@gmail.com"
    };

    String phone[]={
            "1234567890","9829790640","9799123456"
    };*/

    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> image=new ArrayList<String>();
    ArrayList<String> emails=new ArrayList<String>();
    ArrayList<String> phone=new ArrayList<String>();
    ArrayList<String> id=new ArrayList<String>();
    ArrayList<String> comments=new ArrayList<String>();

    TextView course;
    int course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewquery);

        course=(TextView)findViewById(R.id.txtviewquerycour);

        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String scourse = bundle.getString("course");
        course_id=bundle.getInt("course_id");
        Log.e("course_id",course_id+"");

        course.append(": "+scourse);

        StudentCustomList adapter = new
                StudentCustomList(QueryListView.this,name, image,phone,emails,id,comments,course_id,0);
        Helper.getStudentRecord(QueryListView.this,false,name,image,emails,phone,id,comments,0,course_id,adapter);

        list=(ListView)findViewById(R.id.listviewquery);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(QueryListView.this, "You Clicked at " + name.get(position), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
