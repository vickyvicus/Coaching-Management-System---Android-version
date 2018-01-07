package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by AGARWAL on 6/28/2016.
 */
public class ViewWindow extends DrawerActivity {

    Button viewstudent,viewquery,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewwindow);

        viewstudent = (Button) findViewById(R.id.btnviewstud);
        viewquery = (Button) findViewById(R.id.btnviewquery);
        back = (Button) findViewById(R.id.btnviewback);

        viewstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewWindow.this,ViewStudentRecord.class);
                startActivity(intent);
            }
        });

        viewquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewWindow.this,ViewQueryRecord.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(ViewWindow.this,MainWindow.class);
                startActivity(intent);*/
                finish();

            }
        });
    }
}
