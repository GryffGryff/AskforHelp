package com.example.askforhelp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    Button goToLicense;
    TextView link;
    TextView infoAboutLicense;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setVariables();
        setOnClickListeners();
    }

    public void setVariables() {
        goToLicense = findViewById(R.id.buttonToLicense);
        link = findViewById(R.id.link);
        infoAboutLicense = findViewById(R.id.aboutLicense);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        back = findViewById(R.id.infoBackButton);
    }

    public void setOnClickListeners() {
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
