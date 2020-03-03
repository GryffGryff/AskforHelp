package com.serviceproject.gryffgryff.askforhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        changeButton = (Button) findViewById(R.id.changeButtons);
        editReceivers = (Button) findViewById(R.id.editContacts);

        backButton = (Button) findViewById(R.id.backButtonTwo);
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
                Intent intent = new Intent(SettingsActivity.this, EditRecievers.class);
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
