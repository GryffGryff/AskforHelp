package com.serviceproject.gryffgryff.askforhelp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseContactsActivity extends AppCompatActivity {

    Button firstCG;
    Button secondCG;
    Button thirdCG;
    Button fourthCG;
    Button back;
    Bundle whatToText;
    Bundle whoToText = new Bundle();
/*
    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    LocationProvider locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contacts);
        setVariables();
        setNewGroupNames();
        addNumbersToBundle();
        setClickListener();
    }

    public void setVariables() {
        firstCG = (Button) findViewById(R.id.firstContactGroup);
        secondCG = (Button) findViewById(R.id.secondContactGroup);
        thirdCG = (Button) findViewById(R.id.thirdContactGroup);
        fourthCG = (Button) findViewById(R.id.fourthContactGroup);
        back = (Button) findViewById(R.id.backButtonOne);
        whatToText = getIntent().getExtras();
    }

    public void setNewGroupNames() {
        try {
            Context context = ChooseContactsActivity.this;
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

    public void addNumbersToBundle() {
        whoToText.putStringArray("first_group", new String[] {"4128777232", "4128777338"});
        whoToText.putStringArray("second_group", new String[] {""});
        whoToText.putStringArray("third_group", new String[] {""});
        whoToText.putStringArray("fourth_group", new String[] {"4128779326"});
    }

    public void setClickListener() {
        firstCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textPerson("first_group");
            }
        });

        secondCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textPerson("second_group");
            }
        });

        thirdCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textPerson("third_group");
            }
        });

        fourthCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textPerson("fourth_group");
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

    public void textPerson(String text) {
        //error handling?
        String address;
        String textBody = (whatToText.getString(whatToText.getString("last_button_pressed")));
        String scAddress = null;
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;

        String[] numbers = whoToText.getStringArray(text);
        int lengthOfArray = numbers.length;

        for (int i = 0; i<lengthOfArray; i++) {
            address = numbers[i];

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(address, scAddress, textBody, sentIntent, deliveryIntent);

            Toast.makeText(ChooseContactsActivity.this, whatToText.getString(whatToText.getString("last_button_pressed")) + " was sent to ", Toast.LENGTH_LONG).show();
        }
    }
}
