package com.serviceproject.gryffgryff.askforhelp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseRequestsActivity extends AppCompatActivity {

    Button firstButton;
    Button secondButton;
    Button thirdButton;
    Button fourthButton;
    Button settings;

    Bundle requestsText = new Bundle();

    Integer requestCode = 3;

    String[] savedButtonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_requests);
        Toast.makeText(ChooseRequestsActivity.this, "onCreate()", Toast.LENGTH_LONG).show();
        setVariables();
        setNewButtonNames();
        addRequestsToBundle();
        getPermission();
        setClickListener();
    }

    protected void onResume() {
        super.onResume();
        Toast.makeText(ChooseRequestsActivity.this, "onResume()", Toast.LENGTH_LONG).show();
        setNewButtonNames();
        addRequestsToBundle();
    }

    public void setVariables() {
        firstButton = (Button) findViewById(R.id.firstRequest);
        secondButton = (Button) findViewById(R.id.secondRequest);
        thirdButton = (Button) findViewById(R.id.thirdRequest);
        fourthButton = (Button) findViewById(R.id.fourthRequest);
        settings = (Button) findViewById(R.id.settings);
        savedButtonNames = new String[4];
    }

    public void setNewButtonNames() {
        try {
            Context context = ChooseRequestsActivity.this;
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            firstButton.setText(sharedPreferences.getString("first_text", ""));
            secondButton.setText(sharedPreferences.getString("second_text", ""));
            thirdButton.setText(sharedPreferences.getString("third_text", ""));
            fourthButton.setText(sharedPreferences.getString("fourth_text", ""));
        } catch (Exception e) {
            firstButton.setText("");
            secondButton.setText("");
            thirdButton.setText("");
            fourthButton.setText("");
        }
    }

    public void addRequestsToBundle() {
        requestsText.putString("first_text", getButtonName(firstButton));
        requestsText.putString("second_text", getButtonName(secondButton));
        requestsText.putString("third_text", getButtonName(thirdButton));
        requestsText.putString("fourth_text", getButtonName(fourthButton));
        requestsText.putString("last_button_pressed", "none");
    }

    public void setClickListener() {
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent("first_text");
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent("second_text");
            }
        });
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent("third_text");
            }
        });
        fourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent("fourth_text");
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseRequestsActivity.this, SettingsActivity.class);
                ChooseRequestsActivity.this.startActivity(intent);
            }
        });
    }

    public String getButtonName(Button currButton) {
        return currButton.getText().toString();
    }

    public void startIntent(String text) {
        Intent intent = new Intent(ChooseRequestsActivity.this, ChooseContactsActivity.class);
        requestsText.remove("last_button_pressed");
        requestsText.putString("last_button_pressed", text);
        intent.putExtras(requestsText);
        ChooseRequestsActivity.this.startActivity(intent);
    }

    public void getPermission() {
        if (ContextCompat.checkSelfPermission(ChooseRequestsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChooseRequestsActivity.this, new String[] {Manifest.permission.SEND_SMS}, requestCode );
            onRequestPermissionsResult(requestCode, new String[] {Manifest.permission.SEND_SMS}, new int[] {PackageManager.PERMISSION_GRANTED});
        } else {
            //permission granted
            //Toast.makeText(ChooseRequestsActivity.this, "permission already granted", Toast.LENGTH_LONG).show();
        }
    }


}
