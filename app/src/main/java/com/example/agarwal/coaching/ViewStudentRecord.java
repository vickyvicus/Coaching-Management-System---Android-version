package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by AGARWAL on 7/5/2016.
 */
public class ViewStudentRecord extends DrawerActivity {

    Button submit,back;
    Spinner course,batch;
    ArrayList<String> courselist,batchlist;
    ArrayAdapter<String> courseadpt,batchadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstudentrecord);

        submit=(Button)findViewById(R.id.btnviewstudsubmit);
        back=(Button)findViewById(R.id.btnviewstudback);
        course=(Spinner) findViewById(R.id.spnviewstudcource);
        batch=(Spinner) findViewById(R.id.spnviewstudbatch);

        courselist=new ArrayList<String>();
        courselist.add("Select Course");
        courseadpt=new ArrayAdapter<String>(ViewStudentRecord.this,android.R.layout.simple_list_item_1,courselist);
        course.setAdapter(courseadpt);
        Helper.fillCourses(ViewStudentRecord.this,courselist,courseadpt);

        batchlist=new ArrayList<String>();
        batchlist.add("Select Batch");
        batchadpt=new ArrayAdapter<String>(ViewStudentRecord.this,android.R.layout.simple_list_item_1,batchlist);
        batch.setAdapter(batchadpt);

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    batchlist=new ArrayList<String>();
                    batchlist.add("Select Batch");
                    batchadpt=new ArrayAdapter<String>(ViewStudentRecord.this,android.R.layout.simple_list_item_1,batchlist);
                    batch.setAdapter(batchadpt);

                    String itemselected = (String) course.getItemAtPosition(position);
                    int cur_id = Integer.parseInt(itemselected.substring(0, 4));
                    Helper.fillBatch(ViewStudentRecord.this,batchlist,batchadpt,cur_id);
                }
                else
                {
                    batchlist=new ArrayList<String>();
                    batchlist.add("Select Batch");
                    batchadpt=new ArrayAdapter<String>(ViewStudentRecord.this,android.R.layout.simple_list_item_1,batchlist);
                    batch.setAdapter(batchadpt);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(batch.getSelectedItemPosition()==0)
                {
                    Toast.makeText(ViewStudentRecord.this,"Please select batch and course",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent=new Intent(ViewStudentRecord.this,StudentListView.class);

                Bundle bundle = new Bundle();

                String scourse=((String)course.getSelectedItem()).substring(4).trim();
                String sbatch=((String)batch.getSelectedItem()).substring(6).trim();

                String itemselected = (String)course.getSelectedItem();
                int course_id = Integer.parseInt(itemselected.substring(0, 4));

                itemselected = (String)batch.getSelectedItem();
                int batch_id = Integer.parseInt(itemselected.substring(0, 5));

                //Add your data to bundle
                bundle.putInt("course_id",course_id);
                bundle.putInt("batch_id",batch_id);
                bundle.putString("course", scourse);
                bundle.putString("batch",sbatch);

                //Add the bundle to the intent
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
