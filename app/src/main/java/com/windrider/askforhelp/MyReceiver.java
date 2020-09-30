package com.windrider.askforhelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String selectedAppInfo = String.valueOf(intent.getExtras().get(Intent.EXTRA_CHOSEN_COMPONENT));
        String appPackage;
        int startIndex;
        int endIndex;
        startIndex = selectedAppInfo.indexOf('{') + 1;
        endIndex = selectedAppInfo.indexOf('/', startIndex);
        appPackage = selectedAppInfo.substring(startIndex, endIndex);
        Log.e("myReceiver", selectedAppInfo);
        Log.e("myReceiver", appPackage);
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("app_package", appPackage);
            editor.apply();
        } catch (Exception e) {
            //accessing shared preferences file failed
        }
    }
}
