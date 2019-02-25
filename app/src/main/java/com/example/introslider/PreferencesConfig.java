package com.example.introslider;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesConfig {


    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferencesConfig(Context context) {
        this.context = context;
        getPreference();

    }

    private void getPreference() {

        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_key), Context.MODE_PRIVATE);
    }

    public void writePreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.preference_status), "INIT_OK");
        editor.apply();
    }

    public boolean checkPreference() {
        boolean status = false;
        if (sharedPreferences.getString(context.getString(R.string.preference_status), "null").equals("null")) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public void clearPreference() {
        sharedPreferences.edit().clear().apply();
    }
}
