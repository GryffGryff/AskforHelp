package com.serviceproject.gryffgryff.askforhelp;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
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

    //int requestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contacts);

        setVariables();
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

    public void addNumbersToBundle() {
        whoToText.putStringArray("first_group", new String[] {"4128777232", "4128777338"});
        whoToText.putStringArray("second_group", new String[] {"4128779326"});
        whoToText.putStringArray("third_group", new String[] {""});
        whoToText.putStringArray("fourth_group", new String[] {""});
    }

    public void setClickListener() {
        firstCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startText("first_group");
                textPerson("first_group");
            }
        });

        secondCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startText("second_group");
                textPerson("second_group");
            }
        });

        thirdCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startText("third_group");
                textPerson("third_group");
            }
        });

        fourthCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startText("fourth_group");
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

    public void startText(String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("smsto:"));
            intent.setType("vnd.android-dir/mms-sms");
            intent.putExtra("sms_body", whatToText.getString(whatToText.getString("last_button_pressed")));
            //intent.putExtra("address", new String("4128779326"));
            intent.putExtra("address", new String(whoToText.getString(text)));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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

            Toast.makeText(ChooseContactsActivity.this, "Text sent", Toast.LENGTH_LONG).show();
        }
    }
}
