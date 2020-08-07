package com.example.askforhelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class ApacheLicenseActivity extends AppCompatActivity {

    TextView apacheLicense;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apache_license);
        setVariables();
        setClickListener();
    }

    public void setVariables() {
        apacheLicense = findViewById(R.id.apacheLicense);
        apacheLicense.setText(getLicenseText());
        home = findViewById(R.id.licenseHome);
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
