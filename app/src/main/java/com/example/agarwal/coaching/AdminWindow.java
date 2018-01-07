package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by AGARWAL on 6/28/2016.
 */
public class AdminWindow extends DrawerActivity {

    Button newcource,newbatch,newuser,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminwindow);

        newcource=(Button)findViewById(R.id.btnadminaddcour);
        newbatch=(Button)findViewById(R.id.btnadminaddbat);
        newuser=(Button)findViewById(R.id.btnadminadduser);
        back=(Button)findViewById(R.id.btnadminaback);

        newcource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AdminWindow.this,AddCourse.class);
                startActivity(intent);
                finish();
            }
        });

        newbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminWindow.this,AddBatch.class);
                startActivity(intent);
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminWindow.this,AddUser.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(AdminWindow.this,MainWindow.class);
                startActivity(intent);*/
                finish();
            }
        });
    }
}
