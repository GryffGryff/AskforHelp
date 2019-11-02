package com.serviceproject.gryffgryff.askforhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button firstRequest;
    Button secondRequest;
    Button thirdRequest;
    Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVariables();
        setClickListener();
    }

    public void setVariables() {
        firstRequest = (Button) findViewById(R.id.firstRequest);
        secondRequest = (Button) findViewById(R.id.secondRequest);
        thirdRequest = (Button) findViewById(R.id.thirdRequest);
        settings = (Button) findViewById(R.id.settings);
    }

    public void setClickListener() {
        firstRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        secondRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        thirdRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
