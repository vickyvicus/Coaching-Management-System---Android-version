package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by AGARWAL on 7/6/2016.
 */
public class ViewQueryRecord extends DrawerActivity{

    Button submit,back;
    Spinner course;
    ArrayList<String> courselist;
    ArrayAdapter<String> courseadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewqueryrecord);

        submit=(Button)findViewById(R.id.btnviewquerysubmit);
        back=(Button)findViewById(R.id.btnviewqueryback);
        course=(Spinner) findViewById(R.id.spnviewquerycource);
       // batch=(Spinner) findViewById(R.id.spnviewstudbatch);

        courselist=new ArrayList<String>();
        courselist.add("Select Course");
        courseadpt=new ArrayAdapter<String>(ViewQueryRecord.this,android.R.layout.simple_list_item_1,courselist);
        course.setAdapter(courseadpt);
        Helper.fillCourses(ViewQueryRecord.this,courselist,courseadpt);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(course.getSelectedItemPosition()==0)
                {
                    Toast.makeText(ViewQueryRecord.this,"Please select course",Toast.LENGTH_LONG).show();
                    return;
                }


                Intent intent=new Intent(ViewQueryRecord.this,QueryListView.class);

                Bundle bundle = new Bundle();

                String scourse=((String)course.getSelectedItem()).substring(4).trim();
                //Add your data to bundle
                bundle.putString("course", scourse);

                String itemselected = (String)course.getSelectedItem();
                int course_id = Integer.parseInt(itemselected.substring(0, 4));
                bundle.putInt("course_id",course_id);

                //Add the bundle to the intent
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
