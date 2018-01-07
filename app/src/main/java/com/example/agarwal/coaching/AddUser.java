package com.example.agarwal.coaching;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by AGARWAL on 6/29/2016.
 */
public class AddUser extends DrawerActivity {

    EditText user,pass,email;
    Button submit,back;
    Spinner usertype;
    //Mail m=new Mail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);

        user=(EditText)findViewById(R.id.edtadusername);
        email=(EditText)findViewById(R.id.edtaduseremail);
        pass=(EditText)findViewById(R.id.edtaduserpass);

        submit=(Button)findViewById(R.id.btnadusersub);
        back=(Button)findViewById(R.id.btnaduserback);

        usertype=(Spinner)findViewById(R.id.spnadusertype);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().length() > 0 && pass.getText().toString().trim().length() > 0 && user.getText().toString().trim().length() > 0)
                {

                }
                else if((!email.getText().toString().contains("@")))
                {
                    Snackbar.make(v, "Please correct email", Snackbar.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                Helper.adduser(AddUser.this,user.getText().toString(),email.getText().toString(),pass.getText().toString());

                /*if(b) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddUser.this);
                    builder.setTitle("Alert Dialog");
                    builder.setMessage("A Mail Will be Send To The Mail Address");
                    String msg = createMail();
                    String address[] = {email.getText().toString()};
                    send(address, msg);
                    //builder.setIcon(R.drawable.dialog);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AddUser.this, "User Successfully Added", Toast.LENGTH_LONG).show();
                        }
                    });

                    builder.show();
                }*/
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(AddUser.this,AdminWindow.class);
                startActivity(intent);*/
                finish();
            }
        });
    }

    /*public String  createMail() {

        String msg="congratulations your account has been successfully created.Please note the below imformation for" +
                "any future references.\n\n"+"Your Username:"+user.getText().toString()+"\n Your Password:"+pass.getText().toString()+"\n " +
                "Your Email:"+email.getText().toString()+"\n\nThank You...\nHave a Nice Day";

        return msg;
    }

    public void send(String address[], String msg) {

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        //intent.putExtra(Intent.EXTRA_CC,cc);
        //intent.putExtra(Intent.EXTRA_BCC,bcc);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coaching Management Registration");
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Email"));
    }*/
}
