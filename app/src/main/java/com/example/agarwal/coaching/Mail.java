package com.example.agarwal.coaching;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AGARWAL on 7/3/2016.
 */
public class Mail extends AppCompatActivity {

    public static String address;
    public static String msg;

   /* Mail()
    {
        super();
    }

    Mail(String add,String message)
    {
        address=add;
        msg=message;
    }*/

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        String add[]={address};
        send(add,msg);
    }

    public void send(String address[], String msg) {

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        //intent.putExtra(Intent.EXTRA_CC,{"vicky.agarwal@gmail.com"});
        //intent.putExtra(Intent.EXTRA_BCC,{"vickyvicus@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coaching Management Registration");
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Email"));
        finish();
    }
}
