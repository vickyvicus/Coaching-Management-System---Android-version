package com.example.agarwal.coaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login,reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent=new Intent(MainActivity.this,MainWindow.class);
        startActivity(intent);
        finish();*/

        username=(EditText)findViewById(R.id.edtmainuser);
        password=(EditText)findViewById(R.id.edtmainpass);
        login=(Button)findViewById(R.id.btnmainlogin);
        reset=(Button)findViewById(R.id.btnmainreset);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(MainActivity.this,MainWindow.class);
                startActivity(intent);
                finish();*/

                Helper.validateUser(MainActivity.this,username.getText().toString(),password.getText().toString());
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
                password.setText("");
            }
        });

    }
}
