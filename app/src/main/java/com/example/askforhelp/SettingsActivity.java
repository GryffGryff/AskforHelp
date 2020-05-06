package com.example.askforhelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button changeButton;
    Button editReceivers;

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setVariables();
        setClickListener();
    }

    public void setVariables() {
        changeButton = findViewById(R.id.changeButtons);
        editReceivers = findViewById(R.id.editContacts);

        backButton = findViewById(R.id.backButtonTwo);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ChooseRequestsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
    }
}

