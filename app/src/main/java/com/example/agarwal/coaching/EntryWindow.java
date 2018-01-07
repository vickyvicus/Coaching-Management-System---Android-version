package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by AGARWAL on 6/28/2016.
 */
public class EntryWindow extends DrawerActivity {

    Button studententry,queryentry,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrywindow);

        studententry = (Button) findViewById(R.id.btnstudentry);
        queryentry = (Button) findViewById(R.id.btnqueryentry);
        back = (Button) findViewById(R.id.btnentryback);

        studententry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EntryWindow.this,AddStudentEntry.class);
                startActivity(intent);
            }
        });

        queryentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EntryWindow.this,AddStudentQuery.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(EntryWindow.this,MainWindow.class);
                startActivity(intent);*/
                finish();

            }
        });
    }
}
