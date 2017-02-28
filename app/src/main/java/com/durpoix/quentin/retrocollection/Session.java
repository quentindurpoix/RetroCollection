package com.durpoix.quentin.retrocollection;

/**
 * Created by Coco on 28/02/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Session {

    private SharedPreferences prefs;
    private String username;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getusename() {
        username = prefs.getString("username","");
        return username;
    }



}