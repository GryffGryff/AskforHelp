package com.example.askforhelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String selectedAppPackage = String.valueOf(intent.getExtras().get(Intent.EXTRA_CHOSEN_COMPONENT));
        Log.e("myReceiver", selectedAppPackage);
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("app_to_use", selectedAppPackage);
            editor.apply();
        } catch (Exception e) {
            //accessing shared preferences file failed
        }
    }
}
