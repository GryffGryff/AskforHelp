package com.windrider.askforhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.IOException;
import java.io.InputStream;

public class ApacheLicenseActivity extends AppCompatActivity {

    TextView apacheLicense;
    Button home;
    SharedPreferences sharedPreferences;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apache_license);
        setVariables();
        darkMode();
        setClickListener();
    }

    public void setVariables() {
        apacheLicense = findViewById(R.id.apacheLicense);
        apacheLicense.setText(getLicenseText());
        home = findViewById(R.id.licenseHome);
        context = ApacheLicenseActivity.this;
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
    }

    public void darkMode() {
        if(sharedPreferences.getBoolean("dark_mode_on", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public void setClickListener() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(ApacheLicenseActivity.this, ChooseRequestsActivity.class);
                ApacheLicenseActivity.this.startActivity(home);
            }
        });
    }

    public String getLicenseText() {
        String text = "";
        try {
            InputStream inputStream = getAssets().open("LICENSE-2.0.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
        } catch (IOException e) {
            //something happened with text file
        }
        return text;
    }
}
