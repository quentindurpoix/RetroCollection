package com.durpoix.quentin.retrocollection;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;



public class Preferences extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

