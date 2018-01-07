package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AGARWAL on 6/28/2016.
 */
public class MainWindow extends DrawerActivity {

    Button admin,newentry,viewentry,fees,logout;
    //TextView user,email;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwindow);

        admin=(Button)findViewById(R.id.btnadmin);
        newentry=(Button)findViewById(R.id.btnnewent);
        viewentry=(Button)findViewById(R.id.btnviewrec);
        fees=(Button)findViewById(R.id.btnfees);
        logout=(Button)findViewById(R.id.btnlogout);

        //user=(TextView)findViewById(R.id.navheaderuser);
        //email=(TextView)findViewById(R.id.navheaderemail);

        session = new Session(MainWindow.this);


        if (!session.getLoggedIn())
        {
            session.setLogin(false);
            Intent intent = new Intent(MainWindow.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
       else {
            Map<String,String> params=new HashMap<String, String>();
            params=session.getUser();

            Log.e("user",params.get("user"));
            Log.e("email",params.get("email"));


            setNavWindow(params.get("user"),params.get("email"));

        }

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainWindow.this,AdminWindow.class);
                startActivity(intent);
            }
        });

        newentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainWindow.this,EntryWindow.class);
                startActivity(intent);
            }
        });

        viewentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainWindow.this,ViewWindow.class);
                startActivity(intent);
            }
        });

        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainWindow.this,FeeDeposit.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLogin(false);
                Intent intent=new Intent(MainWindow.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
