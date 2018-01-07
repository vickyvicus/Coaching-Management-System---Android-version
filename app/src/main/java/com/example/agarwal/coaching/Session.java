package com.example.agarwal.coaching;

/**
 * Created by AGARWAL on 7/13/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Session
{
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public Session(Context context)
    {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public boolean setLogin(boolean status)
    {
        spEditor = sp.edit();
        spEditor.putBoolean("is_logged_in", status);
        spEditor.commit();
        return true;
    }
    public boolean getLoggedIn()
    {
        return sp.getBoolean("is_logged_in", false);
    }

    public void setUser(String user,String email)
    {
        spEditor = sp.edit();
        spEditor.putString("user",user);
        spEditor.putString("email",email);
        spEditor.commit();
    }

    public  Map<String, String> getUser()
    {
        Map<java.lang.String, java.lang.String> params = new HashMap<java.lang.String, java.lang.String>();

        params.put("user",sp.getString("user","Guest"));
        params.put("email",sp.getString("email","guest@gmail.com"));
        return params;
    }
}