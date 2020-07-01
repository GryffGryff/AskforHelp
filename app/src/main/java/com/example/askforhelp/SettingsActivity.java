package com.example.askforhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button changeButton;
    Button editReceivers;
    Button aboutPage;
    Button resetTutorial;

    Switch location;

    SharedPreferences sharedPreferences;
    Context context;

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setVariables();
        checkFirstOpen();
        setClickListener();
    }

    protected void onResume() {
        super.onResume();
        checkFirstOpen();
    }

    public void checkFirstOpen() {
        if(sharedPreferences.getBoolean("settingsCheck", true)) {
            Toast.makeText(context, "Here you can set up your messages and contacts for the app. You can also turn location on and off. If it's on, a link to your location will be sent with each message.", Toast.LENGTH_LONG).show();
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

        location = findViewById(R.id.locationOnOff);

        backButton = findViewById(R.id.backButtonTwo);

        context = SettingsActivity.this;
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
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
                setLocation();
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
                //add toast saying: tutorial has been reset -- go to home page to start
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

    public void setLocation() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(location.isChecked()) {
            editor.putBoolean("locationOn", true);
            editor.apply();
        } else {
            editor.putBoolean("locationOn", false);
            editor.apply();
        }
    }
}

