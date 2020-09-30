package com.windrider.askforhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class InfoActivity extends AppCompatActivity {
    Button goToLicense;
    TextView link;
    TextView infoAboutLicense;
    Button back;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setVariables();
        darkMode();
        setOnClickListeners();
    }

    public void setVariables() {
        goToLicense = findViewById(R.id.buttonToLicense);
        link = findViewById(R.id.link);
        infoAboutLicense = findViewById(R.id.aboutLicense);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        back = findViewById(R.id.infoBackButton);
        try {
            sharedPreferences = InfoActivity.this.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(InfoActivity.this, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
    }

    public void darkMode() {
        if(sharedPreferences.getBoolean("dark_mode_on", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public void setOnClickListeners() {
        goToLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLicense = new Intent(InfoActivity.this, ApacheLicenseActivity.class);
                InfoActivity.this.startActivity(goToLicense);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(InfoActivity.this, ChooseRequestsActivity.class);
                InfoActivity.this.startActivity(goHome);
            }
        });
    }
}

//add code to attribute creator of delete button: Delete by Landan Lloyd from the Noun Project
