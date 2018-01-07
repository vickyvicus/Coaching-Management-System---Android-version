package com.example.agarwal.coaching;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by AGARWAL on 7/7/2016.
 */
public class FeeDeposit extends DrawerActivity {

    EditText todeposit;
    TextView date,receiptno,contact,fee,deposited,due,email;
    Button submit,back;
    Spinner course,batch,student;
    ArrayList<String> courselist,batchlist,studentlist;
    ArrayAdapter<String> courseadpt,batchadpt,studentadpt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feesdeposit);

        todeposit=(EditText)findViewById(R.id.edtfeedeptodep);
        date=(TextView)findViewById(R.id.viewfeedepdate);
        receiptno=(TextView)findViewById(R.id.viewfeedeprecno);
        contact=(TextView)findViewById(R.id.viewfeedepcontno);
        fee=(TextView)findViewById(R.id.viewfeedepfeeamt);
        deposited=(TextView)findViewById(R.id.viewfeedeposited);
        due=(TextView)findViewById(R.id.viewfeedepdue);
        email=(TextView)findViewById(R.id.viewfeedepemail);

        course=(Spinner)findViewById(R.id.spnfeedepcourse);
        batch=(Spinner)findViewById(R.id.spnfeedepbatch);
        student=(Spinner)findViewById(R.id.spnfeedepstudname);

        submit=(Button)findViewById(R.id.btnfeedepsub);
        back=(Button)findViewById(R.id.btnfeedepback);

        Calendar calender;
        int day,month,yr;
        String strdate;
        calender =Calendar.getInstance();
        day=calender.get(calender.DAY_OF_MONTH);
        month=calender.get(calender.MONTH);
        yr=calender.get(calender.YEAR);

        strdate=day+"/"+(month+1)+"/"+yr;
        date.setText(strdate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Helper.fillReceipt(FeeDeposit.this,receiptno);

        courselist=new ArrayList<String>();
        courselist.add("Select Course");
        courseadpt=new ArrayAdapter<String>(FeeDeposit.this,android.R.layout.simple_list_item_1,courselist);
        course.setAdapter(courseadpt);
        Helper.fillCourses(FeeDeposit.this,courselist,courseadpt);

        batchlist=new ArrayList<String>();
        batchlist.add("Select Batch");
        batchadpt=new ArrayAdapter<String>(FeeDeposit.this,android.R.layout.simple_list_item_1,batchlist);
        batch.setAdapter(batchadpt);

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    batchlist=new ArrayList<String>();
                    batchlist.add("Select Batch");
                    batchadpt=new ArrayAdapter<String>(FeeDeposit.this,android.R.layout.simple_list_item_1,batchlist);
                    batch.setAdapter(batchadpt);

                    String itemselected = (String) course.getItemAtPosition(position);
                    int cur_id = Integer.parseInt(itemselected.substring(0, 4));
                    Helper.fillBatch(FeeDeposit.this,batchlist,batchadpt,cur_id);
                }
                else
                {
                    batchlist=new ArrayList<String>();
                    batchlist.add("Select Course");
                    batchadpt=new ArrayAdapter<String>(FeeDeposit.this,android.R.layout.simple_list_item_1,batchlist);
                    batch.setAdapter(batchadpt);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        studentlist=new ArrayList<String>();
        studentlist.add("Add Batch");
        studentadpt=new ArrayAdapter<String>(FeeDeposit.this,android.R.layout.simple_list_item_1,batchlist);
        student.setAdapter(batchadpt);

        batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    studentlist=new ArrayList<String>();
                    studentlist.add("Select Students");
                    studentadpt=new ArrayAdapter<String>(FeeDeposit.this,android.R.layout.simple_list_item_1,studentlist);
                    student.setAdapter(studentadpt);

                    String itemselected = (String) batch.getItemAtPosition(position);
                    int bat_id = Integer.parseInt(itemselected.substring(0, 5));
                    Helper.fillStudent(FeeDeposit.this,studentlist,studentadpt,bat_id);
                }
                else
                {
                    studentlist=new ArrayList<String>();
                    studentlist.add("Select Batch");
                    studentadpt=new ArrayAdapter<String>(FeeDeposit.this,android.R.layout.simple_list_item_1,studentlist);
                    student.setAdapter(studentadpt);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0) {
                    String itemselected = (String) student.getItemAtPosition(position);
                    Log.e("Student", itemselected);
                    int student_id = Integer.parseInt(itemselected.substring(0, 7));

                    Helper.fillStudInfo(FeeDeposit.this, student_id, contact, email);

                    itemselected = (String)batch.getSelectedItem();
                    int batch_id = Integer.parseInt(itemselected.substring(0, 5));

                    itemselected = (String)course.getSelectedItem();
                    int course_id = Integer.parseInt(itemselected.substring(0, 4));

                    Helper.fetchFeeRecord(FeeDeposit.this,student_id,batch_id,course_id,fee,due,deposited);
                }
                else
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todeposit.getText().toString().trim().equals(""))
                {
                    Toast.makeText(FeeDeposit.this,"Enter Fee To Be Deposited",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(batch.getSelectedItemPosition()==0)
                {
                    Toast.makeText(FeeDeposit.this,"Please select batch and course",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(student.getSelectedItemPosition()==0)
                {
                    Toast.makeText(FeeDeposit.this,"Please select batch and course and students",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(Integer.parseInt(todeposit.getText().toString())>(Integer.parseInt(due.getText().toString())))
                {
                    Toast.makeText(FeeDeposit.this,"You Cann't Deposit More Than Due",Toast.LENGTH_LONG).show();
                }

                String itemselected = (String)student.getSelectedItem();
                int student_id = Integer.parseInt(itemselected.substring(0, 7));

                itemselected = (String)batch.getSelectedItem();
                int batch_id = Integer.parseInt(itemselected.substring(0, 5));

                int paid=Integer.parseInt(todeposit.getText().toString());
                int fees=Integer.parseInt(fee.getText().toString());
                //int todep=Integer.parseInt(todeposit.getText().toString());
                int dues=Integer.parseInt(due.getText().toString())-paid;

                Helper.addFeeRecord(FeeDeposit.this,student_id,batch_id,paid,fees,dues,email.getText().toString());
            }
        });
    }
}
