package com.serviceproject.gryffgryff.askforhelp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.klinker.android.send_message.Message;
import com.klinker.android.send_message.Settings;
import com.klinker.android.send_message.Transaction;

import java.util.Arrays;


public class ChooseContactsActivity extends AppCompatActivity {

    Button firstCG;
    Button secondCG;
    Button thirdCG;
    Button fourthCG;
    Button back;
    Bundle whatToText;
    Bundle whoToText = new Bundle();
    Context context;

    //LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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
        context = ChooseContactsActivity.this;
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

    public void addNumbersToBundle() {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            whoToText.putStringArray("first_group", new String[] {sharedPreferences.getString("first_group_number", "")});
            whoToText.putStringArray("second_group", new String[] {sharedPreferences.getString("second_group_number", "")});
            whoToText.putStringArray("third_group", new String[] {sharedPreferences.getString("third_group_number", "")});
            whoToText.putStringArray("fourth_group", new String[] {sharedPreferences.getString("fourth_group_number", "")});
        } catch (Exception e) {
            whoToText.putStringArray("first_group", new String[] {""});
            whoToText.putStringArray("second_group", new String[] {""});
            whoToText.putStringArray("third_group", new String[] {""});
            whoToText.putStringArray("fourth_group", new String[] {""});
        }
    }

    public void setClickListener() {
        firstCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendText("first_group");
                //textPerson("first_group");
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

    public void sendText(String text) {
        Log.e("sendText", "sendText was called");
        Settings sendSettings = new Settings();
        sendSettings.setMmsc("https://mmsc.mobile.att.net");
        sendSettings.setProxy("proxy.mobile.att.net");
        sendSettings.setPort("80");
        sendSettings.setUseSystemSending(true);
        Log.e("sendText", Boolean.toString(sendSettings.getGroup()));
        String textBody = (whatToText.getString(whatToText.getString("last_button_pressed")));

        Transaction transaction = new Transaction(ChooseContactsActivity.this, sendSettings);

        //Message message = new Message(textBody, whoToText.getStringArray(text));
        Message message = new Message(textBody, new String[] {"4128777232", "4128779326"});
        Log.e("sendText", Arrays.toString(message.getAddresses()));
        transaction.sendNewMessage(message, Transaction.NO_THREAD_ID);
        Log.e("sendText", "text should have been sent");
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

            for (int i = 0; i < lengthOfArray; i++) {
                address = numbers[i];

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(address, scAddress, textBody, sentIntent, deliveryIntent);

                Toast.makeText(ChooseContactsActivity.this, whatToText.getString(whatToText.getString("last_button_pressed")) + " was sent to ", Toast.LENGTH_LONG).show();
            }
    }
}
