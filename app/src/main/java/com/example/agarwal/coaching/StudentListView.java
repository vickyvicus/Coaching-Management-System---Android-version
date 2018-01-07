package com.example.agarwal.coaching;

/**
 * Created by AGARWAL on 7/5/2016.
 */
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import java.util.ArrayList;

public class StudentListView extends DrawerActivity {
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

    TextView course,batch;
    int batch_id,course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstudent);

        course=(TextView)findViewById(R.id.txtviewstudcour);
        batch=(TextView)findViewById(R.id.txtviewstudbat);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String scourse = bundle.getString("course");
        String sbatch= bundle.getString("batch");
        course_id=bundle.getInt("course_id");
        batch_id=bundle.getInt("batch_id");

        course.append(": "+scourse);
        batch.append(": "+sbatch);

        Log.e("batch_id",batch_id+"");

        StudentCustomList adapter = new
                StudentCustomList(StudentListView.this,name,image,phone,emails,id,comments,course_id,batch_id);
        Helper.getStudentRecord(StudentListView.this,true,name,image,emails,phone,id,comments,batch_id,course_id,adapter);

        list=(ListView)findViewById(R.id.listviewstud);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(StudentListView.this, "You Clicked at " + name.get(position), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
