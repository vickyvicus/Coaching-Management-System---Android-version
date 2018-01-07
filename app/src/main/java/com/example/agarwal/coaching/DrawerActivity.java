package com.example.agarwal.coaching;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by AGARWAL on 7/14/2016.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    TextView user,email;
    public static String suser="Guest",semail="Guest@gmail.com";
    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setTitle("Activity Title");

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        //headerView.findViewById(R.id.navigation_header_text);

        user=(TextView)headerView.findViewById(R.id.navheaderuser);
        email=(TextView)headerView.findViewById(R.id.navheaderemail);

        user.setText(suser);
        email.setText(semail);

        setTitle("Hello "+suser);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.aboutus) {
            Intent intent=new Intent(DrawerActivity.this,AboutUs.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.writeus) {
            Mail.msg="write your message Here";

            Mail.address="vickyvicus@gmail.com";

            Intent intent=new Intent(DrawerActivity.this,Mail.class);
            startActivity(intent);
        }

        else if(id==R.id.home)
        {
            Intent intent=new Intent(DrawerActivity.this,MainWindow.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Session session = new Session(DrawerActivity.this);
            session.setLogin(false);
            Intent intent=new Intent(DrawerActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setNavWindow(String suser,String semail)
    {
        this.suser=suser;
        this.semail=semail;
        setTitle("Hello "+suser);
        user.setText(suser);
        email.setText(semail);
    }
}

