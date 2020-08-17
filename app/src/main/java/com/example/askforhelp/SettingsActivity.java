package com.example.askforhelp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

public class SettingsActivity extends AppCompatActivity {

    Button changeButton;
    Button editReceivers;
    Button aboutPage;
    Button resetTutorial;
    Button defaultAppChooser;
    Button resetAppChoice;

    Switch location;
    Switch darkOnOff;

    SharedPreferences sharedPreferences;
    Context context;

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setVariables();
        darkMode();
        checkFirstOpen();
        setClickListener();
    }

    protected void onResume() {
        super.onResume();
        checkFirstOpen();
    }

    public void checkFirstOpen() {
        if(sharedPreferences.getBoolean("settingsCheck", true)) {
            AlertDialog.Builder settingsDialogBuilder = new AlertDialog.Builder(context);
            settingsDialogBuilder.setMessage("Here you can set up your messages and contacts. You can also turn location on and off. If location is on, a link to your location will be sent with each message.");
            settingsDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //user read message
                }
            });
            AlertDialog settingsDialog = settingsDialogBuilder.create();
            settingsDialog.show();
            //Toast.makeText(context, "Here you can set up your messages and contacts for the app. You can also turn location on and off. If it's on, a link to your location will be sent with each message.", Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("settingsCheck", false);
            editor.apply();
        }
    }

    public void setVariables() {
        changeButton = findViewById(R.id.changeButtons);
        editReceivers = findViewById(R.id.editContacts);
        aboutPage = findViewById(R.id.toAboutPage);
        resetTutorial = findViewById(R.id.resetTutorial);
        defaultAppChooser = findViewById(R.id.default_app_chooser);
        resetAppChoice = findViewById(R.id.resetAppChoice);

        location = findViewById(R.id.locationOnOff);
        darkOnOff = findViewById(R.id.darkOnOff);

        backButton = findViewById(R.id.backButtonTwo);

        context = SettingsActivity.this;
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
        if(sharedPreferences.getBoolean("locationOn", false) && (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) + ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            location.setChecked(true);
        } else {
            location.setChecked(false);
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean("locationOn", false);
            sharedPreferencesEditor.apply();
        }
    }

    public void darkMode() {
        if(sharedPreferences.getBoolean("dark_mode_on", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            darkOnOff.setChecked(true);
        }
    }

    public void setClickListener() {
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ChangeButtonsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        editReceivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ChangeContactsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    location.setChecked(false);
                    AlertDialog.Builder locationDialogBuilder = new AlertDialog.Builder(context);
                    locationDialogBuilder.setMessage("You cannot turn on location services right now because we do not have access to your phone's location. Would you like to give us access?");
                    locationDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(locationIntent);
                        }
                    });
                    locationDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog locationDialog = locationDialogBuilder.create();
                    locationDialog.show();
                }
                setLocation();
            }
        });

        darkOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                darkModeSettings();
            }
        });

        aboutPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, InfoActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        resetTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("requestsCheck", true);
                editor.putBoolean("firstSendCheck", true);
                editor.putBoolean("buttonsCheck", true);
                editor.putBoolean("contactsCheck", true);
                editor.putBoolean("settingsCheck", true);
                editor.apply();
                Intent intent = new Intent(SettingsActivity.this, ChooseRequestsActivity.class);
                SettingsActivity.this.startActivity(intent);
                //add toast saying: tutorial has been reset -- go to home page to start
            }
        });

        resetAppChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("app_package", "");
                editor.apply();
                AlertDialog.Builder appDialogBuilder = new AlertDialog.Builder(context);
                appDialogBuilder.setMessage("We cleared your preferences for which app to send the text with. Next time you send a text, please select which app to use when the prompt comes up.");
                appDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog appDialog = appDialogBuilder.create();
                appDialog.show();
            }
        });

        defaultAppChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSMSAppChooser();


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ChooseRequestsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
    }

    public void darkModeSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(darkOnOff.isChecked()) {
            editor.putBoolean("dark_mode_on", true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if(!darkOnOff.isChecked()){
            editor.putBoolean("dark_mode_on", false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        editor.apply();
        recreate();
    }

    public void openSMSAppChooser() {
        //Toast.makeText(context, "openSMSAppChooser was called", Toast.LENGTH_SHORT).show();
        String appPackage = sharedPreferences.getString("app_package", "");
        if (appPackage.isEmpty()) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "hello");
            sendIntent.setData(Uri.parse("smsto:4128777232, 4128777338"));

            Intent receiver = new Intent(context, MyReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT);

            String title = "Pick texting app to use";
            Intent chooser = Intent.createChooser(sendIntent, title, pendingIntent.getIntentSender());
            context.startActivity(chooser);
        } else {
            //https://stackoverflow.com/questions/19081654/send-text-to-specific-contact-programmatically-whatsapp
            Uri uri = Uri.parse("smsto:4128777232, 4128777338");
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "hello");
            sendIntent.setPackage(appPackage);
            startActivity(sendIntent);
        }


            /*
            Intent sendIntent = new Intent();
            sendIntent.setComponent(new ComponentName("com.google.android.apps.messaging", "com.google.android.apps.messaging.ui.conversation.LaunchConversationActivity"));
            sendIntent.setData(Uri.parse("smsto:4128777232, 4128777338"));
            sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
            context.startActivity(sendIntent); */
/*
        String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        //sendIntent.setDataAndType(Uri.parse("smsto:4128777232,4128777338"), "text/plain");
        //sendIntent.setType("text/plain");
        boolean found = false;
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(sendIntent, 0);
        Log.e("listSize", Integer.toString(list.size()));
        if(!list.isEmpty()) {
            for(ResolveInfo info:list) {
                Log.e("packagenames", info.activityInfo.packageName);
                if(info.activityInfo.packageName.toLowerCase().contains(defaultSmsPackageName) || info.activityInfo.name.toLowerCase().contains(defaultSmsPackageName)) {
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "hello");
                    sendIntent.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
        }
        if(found) {
            Log.e("packageName", sendIntent.getPackage());
        }
        sendIntent.setData(Uri.parse("smsto:4128777232, 4128777338"));
        startActivity(Intent.createChooser(sendIntent, "Select"));
 */
    }

    public void setLocation() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(location.isChecked()) {
            editor.putBoolean("locationOn", true);
            editor.apply();
        } else if(!location.isChecked()){
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder locationOffBuilder = new AlertDialog.Builder(context);
                locationOffBuilder.setMessage("Turning off location here will stop the app from sending a link to your location but will not rescind the app's permission to access it. If you don't want the app to be able to access your location anymore, go to your phone's settings app and change the permissions there.");
                locationOffBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog locationOffDialog = locationOffBuilder.create();
                locationOffDialog.show();
            }
            editor.putBoolean("locationOn", false);
            editor.apply();
        }
    }
}

