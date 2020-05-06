package com.example.askforhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChooseContactsActivity extends AppCompatActivity {

    Button firstCG;
    Button secondCG;
    Button thirdCG;
    Button fourthCG;
    Button back;
    Bundle whatToText;
    Bundle whoToText = new Bundle();
    Context context;
    LocationManager locationManager;
    LocationProvider locationProvider;
    boolean gpsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contacts);
        setVariables();
        checkGPSEnabled();
        setNewGroupNames();
        addNumbersToBundle();
        setClickListener();
    }

    public void setVariables() {
        firstCG = findViewById(R.id.firstContactGroup);
        secondCG = findViewById(R.id.secondContactGroup);
        thirdCG = findViewById(R.id.thirdContactGroup);
        fourthCG = findViewById(R.id.fourthContactGroup);
        back = findViewById(R.id.backButtonOne);
        whatToText = getIntent().getExtras();
        context = ChooseContactsActivity.this;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
    }

    public void checkGPSEnabled() {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!gpsEnabled) {
            enableLocationSettings();
        }
    }

    public void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public String getGPS() {
        try {
            Location gpsCoordinates = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Toast.makeText(context, gpsCoordinates.getLatitude() + ", " + gpsCoordinates.getLongitude(), Toast.LENGTH_LONG).show();
            String location = gpsCoordinates.getLatitude() + "%2C" + gpsCoordinates.getLongitude();
            return location;
        } catch (SecurityException se) {
            //permission not given
            return "";
        }
    }

    public void setNewGroupNames() {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            firstCG.setText(sharedPreferences.getString("first_group", ""));
            secondCG.setText(sharedPreferences.getString("second_group", ""));
            thirdCG.setText(sharedPreferences.getString("third_group", ""));
            fourthCG.setText(sharedPreferences.getString("fourth_group", ""));
        } catch (Exception e) {
            firstCG.setText("");
            secondCG.setText("");
            thirdCG.setText("");
            fourthCG.setText("");
        }
    }
/*
    public void addNumbersToBundle() {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            whoToText.putStringArray("first_group", (String[]) sharedPreferences.getStringSet("first_group_number", new HashSet<String>()).toArray());
            whoToText.putStringArray("second_group", (String[]) sharedPreferences.getStringSet("second_group_number", new HashSet<String>()).toArray());
            whoToText.putStringArray("third_group", (String[]) sharedPreferences.getStringSet("third_group_number", new HashSet<String>()).toArray());
            whoToText.putStringArray("fourth_group", (String[]) sharedPreferences.getStringSet("fourth_group_number", new HashSet<String>()).toArray());
        } catch (Exception e) {
            whoToText.putStringArray("first_group", new String[] {""});
            whoToText.putStringArray("second_group", new String[] {""});
            whoToText.putStringArray("third_group", new String[] {""});
            whoToText.putStringArray("fourth_group", new String[] {""});
        }
    }

 */
    public void addNumbersToBundle() {
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            whoToText.putString("first_group", createPhoneGroups(sharedPreferences.getStringSet("first_group_number", new HashSet<String>())));
            whoToText.putString("second_group", createPhoneGroups(sharedPreferences.getStringSet("second_group_number", new HashSet<String>())));
            whoToText.putString("third_group", createPhoneGroups(sharedPreferences.getStringSet("third_group_number", new HashSet<String>())));
            whoToText.putString("fourth_group", createPhoneGroups(sharedPreferences.getStringSet("fourth_group_number", new HashSet<String>())));
        } catch (Exception e) {
            whoToText.putString("first_group", new String());
            whoToText.putString("second_group", new String());
            whoToText.putString("third_group", new String());
            whoToText.putString("fourth_group", new String());
        }
    }

    public String createPhoneGroups(Set<String> numberGroup) {
        Iterator<String> iterator = numberGroup.iterator();
        String phoneGroups = "";
        String separator = "; ";
        int i = 0;
        if(Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            separator = ", ";
        }
        while (iterator.hasNext()) {
            phoneGroups = phoneGroups.concat(iterator.next() + separator);
        }
        return phoneGroups;
    }

    public void setClickListener() {
        firstCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendText("first_group");
                //textPerson("first_group");
                textPeople("first_group");
            }
        });

        secondCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //textPerson("second_group");
                textPeople("second_group");
            }
        });

        thirdCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //textPerson("third_group");
                textPeople("third_group");
            }
        });

        fourthCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //textPerson("fourth_group");
                textPeople("fourth_group");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseContactsActivity.this, ChooseRequestsActivity.class);
                ChooseContactsActivity.this.startActivity(intent);
            }
        });
    }
/*
    public void textPerson(String text) {

        //error handling?
        String address;
        String textBody = (whatToText.getString(whatToText.getString("last_button_pressed")));
        String scAddress = null;
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;

        String[] numbers = whoToText.getStringArray(text);
        int lengthOfArray = numbers.length;

        for (int i = 0; i < lengthOfArray; i++) {
            address = numbers[i];

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(address, scAddress, textBody, sentIntent, deliveryIntent);

            Toast.makeText(ChooseContactsActivity.this, whatToText.getString(whatToText.getString("last_button_pressed")) + " was sent to ", Toast.LENGTH_LONG).show();
        }

    }
    */

    public void textPeople(String text) {
        String textBody = (whatToText.getString(whatToText.getString("last_button_pressed")));
        String numbers = whoToText.getString(text);
        String mapLink = "https://maps.app.goo.gl/?link=https://www.google.com/maps/place/" + getGPS();
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("vnd.android-dir/mms-sms");
            sendIntent.putExtra("sms_body", textBody + " " + mapLink);
            sendIntent.putExtra("address", numbers);
            startActivity(sendIntent);
        } catch (Exception e) {
            //sending text failed
        }
    }
}

