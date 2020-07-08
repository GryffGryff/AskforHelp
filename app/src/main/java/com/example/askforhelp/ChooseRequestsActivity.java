package com.example.askforhelp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ChooseRequestsActivity extends AppCompatActivity {

    Button firstButton;
    Button secondButton;
    Button thirdButton;
    Button fourthButton;
    Button settings;

    Bundle requestsText = new Bundle();

    Integer permissionsRequestCode = 345;

    String[] savedButtonNames;

    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_requests);
        setVariables();
        setNewButtonNames();
        addRequestsToBundle();
        getAllPermissions();
        setClickListener();
    }

    protected void onResume() {
        super.onResume();
        setNewButtonNames();
        addRequestsToBundle();
        checkFirstTime();
    }

    public void checkFirstTime() {
        if (sharedPreferences.getBoolean("requestsCheck", true)) {
            AlertDialog.Builder requestsDialogBuilder = new AlertDialog.Builder(context);
            requestsDialogBuilder.setMessage("This is the first run. YAY! Go to settings to set up your app");
            requestsDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //user read message
                }
            });
            AlertDialog requestsDialog = requestsDialogBuilder.create();
            requestsDialog.show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("requestsCheck", false);
            editor.apply();
        }
    }

    public void setVariables() {
        firstButton = findViewById(R.id.firstRequest);
        secondButton = findViewById(R.id.secondRequest);
        thirdButton = findViewById(R.id.thirdRequest);
        fourthButton = findViewById(R.id.fourthRequest);
        settings = findViewById(R.id.settings);
        savedButtonNames = new String[4];
        context = ChooseRequestsActivity.this;
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
    }

    public void setNewButtonNames() {
        try {
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

    public void getAllPermissions() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChooseRequestsActivity.this, new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, permissionsRequestCode);
            onRequestPermissionsResult(permissionsRequestCode, new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, new int[] {PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED});
        } else {
            //permission granted
        }
    }

}