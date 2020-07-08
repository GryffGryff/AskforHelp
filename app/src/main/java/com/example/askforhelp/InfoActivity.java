package com.example.askforhelp;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    Button goToLicense;
    TextView link;
    TextView infoAboutLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setVariables();
    }

    public void setVariables() {
        goToLicense = findViewById(R.id.buttonToLicense);
        link = findViewById(R.id.link);
        infoAboutLicense = findViewById(R.id.aboutLicense);
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

//add code to attribute creator of delete button: Delete by Landan Lloyd from the Noun Project
