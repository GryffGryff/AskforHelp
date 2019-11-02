package com.serviceproject.gryffgryff.askforhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button firstButton;
    Button secondButton;
    Button thirdButton;
    Button fourthButton;
    Button settings;

    String firstRequest;
    String secondRequest;
    String thirdRequest;
    String fourthRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVariables();
        setClickListener();
    }

    public void setVariables() {
        firstButton = (Button) findViewById(R.id.firstRequest);
        secondButton = (Button) findViewById(R.id.secondRequest);
        thirdButton = (Button) findViewById(R.id.thirdRequest);
        fourthButton = (Button) findViewById(R.id.fourthRequest);
        settings = (Button) findViewById(R.id.settings);
    }

    public void setClickListener() {
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fourthButton.setOnClickListener(new View.OnClickListener() {
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

    public String getFirstRequest() {
        return this.firstRequest;
    }

    public void setFirstRequest(String request) {
        this.firstRequest = request;
    }

    public String getSecondRequest() {
        return this.secondRequest;
    }

    public void setSecondRequest(String request) {
        this.secondRequest = request;
    }

    public String getThirdRequest() {
        return this.thirdRequest;
    }

    public void setThirdRequest(String request) {
        this.thirdRequest = request;
    }

    public String getFourthRequest() {
        return this.fourthRequest;
    }

    public void setFourthRequest(String request) {
        this.fourthRequest = request;
    }

    public void setButtonName(Button button, String buttonName) {
        button.setText(buttonName);
    }
}
