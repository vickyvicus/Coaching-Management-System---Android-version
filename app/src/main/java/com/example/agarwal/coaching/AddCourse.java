package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by AGARWAL on 6/27/2016.
 */
public class AddCourse extends DrawerActivity {

    EditText cource_name,months,fee;
    EditText days;
    Button submit,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcourse);

        cource_name=(EditText)findViewById(R.id.edtcoursename);
        months=(EditText)findViewById(R.id.edtcourdurmon);
        days=(EditText) findViewById(R.id.edtcourdurday);
        fee=(EditText)findViewById(R.id.edtcourfees);

        submit=(Button)findViewById(R.id.btnaddcur);
        back=(Button)findViewById(R.id.btnaddcurback);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,mon,day,feetemp;

                name=cource_name.getText().toString();
                mon=months.getText().toString();
                day=days.getText().toString();
                feetemp=fee.getText().toString();

                if(name==null || name.equals(""))
                {
                    Toast.makeText(AddCourse.this,"Enter a Name",Toast.LENGTH_LONG).show();
                }
                else if(mon==null || mon.equals(""))
                {
                    Toast.makeText(AddCourse.this,"Enter a Valid Month",Toast.LENGTH_LONG).show();
                }
                else if(day==null || day.equals(""))
                {
                    Toast.makeText(AddCourse.this,"Enter a Valid day",Toast.LENGTH_LONG).show();
                }
                else if(feetemp==null || feetemp.equals(""))
                {
                    Toast.makeText(AddCourse.this,"Enter a Valid fee",Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(day)>30)
                {
                    Toast.makeText(AddCourse.this,"Enter a Valid Days",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Helper.addCouirse(AddCourse.this,name,Integer.parseInt(mon),Integer.parseInt(day),Integer.parseInt(feetemp));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(AddCourse.this,AdminWindow.class);
                startActivity(intent);*/
                finish();
            }
        });
    }
}
