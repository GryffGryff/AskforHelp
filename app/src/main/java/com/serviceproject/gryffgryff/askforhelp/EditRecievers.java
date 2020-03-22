package com.serviceproject.gryffgryff.askforhelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditRecievers extends AppCompatActivity {

    static final int PICK_CONTACT = 1;
    static final int MAX_PICK_CONTACT = 10;

    int whichGroup;

    Button[] groups = new Button[4];
    Button firstGroup;
    Button secondGroup;
    Button thirdGroup;
    Button fourthGroup;

    Button deleteFirstGroup;
    Button deleteSecondGroup;
    Button deleteThirdGroup;
    Button deleteFourthGroup;
    Button saveNewGroup;

    Button home;

    EditText setNewGroup;

    String firstGroupNumber;
    String secondGroupNumber;
    String thirdGroupNumber;
    String fourthGroupNumber;

    SharedPreferences sharedPreferences;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recievers);

        setVariables();
        addGroupsToArray();
        setGroupNames();
        setPhoneNumbers();
        setClickListener();
    }

    public void setVariables() {
        firstGroup = (Button) findViewById(R.id.firstGroup);
        secondGroup = (Button) findViewById(R.id.secondGroup);
        thirdGroup = (Button) findViewById(R.id.thirdGroup);
        fourthGroup = (Button) findViewById(R.id.fourthGroup);

        deleteFirstGroup = (Button) findViewById(R.id.deleteFirstGroup);
        deleteSecondGroup = (Button) findViewById(R.id.deleteSecondGroup);
        deleteThirdGroup = (Button) findViewById(R.id.deleteThirdGroup);
        deleteFourthGroup = (Button) findViewById(R.id.deleteFourthGroup);

        saveNewGroup = (Button) findViewById(R.id.saveNewGroup);
        setNewGroup = (EditText) findViewById(R.id.setNewGroup);

        home = (Button) findViewById(R.id.homeButtonContacts);

        context = EditRecievers.this;
    }

    public void setGroupNames() {
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            firstGroup.setText(sharedPreferences.getString("first_group", ""));
            secondGroup.setText(sharedPreferences.getString("second_group", ""));
            thirdGroup.setText(sharedPreferences.getString("third_group", ""));
            fourthGroup.setText(sharedPreferences.getString("fourth_group", ""));
        } catch (Exception e) {
            firstGroup.setText("");
            secondGroup.setText("");
            thirdGroup.setText("");
            fourthGroup.setText("");
        }
    }

    public void setPhoneNumbers() {
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            firstGroupNumber = sharedPreferences.getString("first_group_number", "");
            secondGroupNumber = sharedPreferences.getString("second_group_number", "");
            thirdGroupNumber = sharedPreferences.getString("third_group_number", "");
            fourthGroupNumber = sharedPreferences.getString("fourth_group_number", "");
        } catch (Exception e) {
            firstGroupNumber = "";
            secondGroupNumber = "";
            thirdGroupNumber = "";
            fourthGroupNumber = "";
        }
    }

    public void addGroupsToArray() {
        groups[0] = firstGroup;
        groups[1] = secondGroup;
        groups[2] = thirdGroup;
        groups[3] = fourthGroup;
    }

    public void setClickListener() {
        firstGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickNewContacts(1);
            }
        });

        secondGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickNewContacts(2);
            }
        });

        thirdGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickNewContacts(3);
            }
        });

        fourthGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickNewContacts(4);
            }
        });

        deleteFirstGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(0);
            }
        });

        deleteSecondGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(1);
            }
        });

        deleteThirdGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(2);
            }
        });

        deleteFourthGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(3);
            }
        });

        saveNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewGroup();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditRecievers.this, ChooseRequestsActivity.class);
                setNewGroupNames();
                EditRecievers.this.startActivity(intent);
            }
        });
    }

    public void bumpUpNames(int numGroups) {
        while (numGroups < 3) {
            groups[numGroups].setText(groups[numGroups+1].getText().toString());
            numGroups++;
        }
        groups[3].setText("");
        saveNewGroup.setEnabled(true);
    }

    public void addNewGroup() {
        String groupName = setNewGroup.getText().toString();
        int group = 0;
        while (group <= 3) {
            if (groups[group].getText().toString() == "") {
                if(group == 3) {
                    saveNewGroup.setEnabled(false);
                }
                groups[group].setText(groupName);
                group = 100;
            }
            group++;
        }
        setNewGroup.setText("");
    }

    public void pickNewContacts(int group) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        Toast.makeText(EditRecievers.this, "pickNewContacts was called", Toast.LENGTH_SHORT).show();
        whichGroup = group;
        //intent.setDataAndType(Uri.parse("content://contacts"), ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        String number = null;

        String name = null;

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor cursor = managedQuery(contactData, null, null, null, null);
                    if (cursor.moveToFirst()) {

                        String id  = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                            phones.moveToFirst();
                            number= phones.getString(phones.getColumnIndex("data1"));
                            Log.e("OnActivityResult", "number is: " + number);
                            Toast.makeText(EditRecievers.this, "number is: " + number, Toast.LENGTH_SHORT).show();
                        }
                        name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        Log.e("OnActivtyResult", "name is: " + name);
                        Toast.makeText(EditRecievers.this, "name is: " + name, Toast.LENGTH_SHORT).show();

                    }
                }
                break;
        }

        number = number.replaceAll("[^0-9+]", "");
        Log.e("onActivityResult", number);
        setNewNumber(number);

    }

    public void setNewNumber(String number) {
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Log.e("setNewNumber", "setNewNumber was called");
            if (whichGroup == 1) {
                Log.e("setNewNumber", "first if statement returned true");
                editor.putString("first_group_number", number);
                Log.e("setNewNumber", number + "was added to group one.");
            } else if (whichGroup == 2) {
                editor.putString("second_group_number", number);
            } else if (whichGroup == 3) {
                editor.putString("third_group_number", number);
            } else if (whichGroup == 4) {
                editor.putString("fourth_group_number", number);
            } else {
                Toast.makeText(context, "number did not get added to any group", Toast.LENGTH_LONG).show();
            }
            editor.apply();
        } catch (Exception e) {
            Toast.makeText(context, "editing shared preferences file failed", Toast.LENGTH_LONG).show();
        }
    }

    public void setNewGroupNames() {
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first_group", firstGroup.getText().toString());
            editor.putString("second_group", secondGroup.getText().toString());
            editor.putString("third_group", thirdGroup.getText().toString());
            editor.putString("fourth_group", fourthGroup.getText().toString());
            editor.apply();
        } catch (Exception e) {
            Toast.makeText(context, "editing shared preferences file failed", Toast.LENGTH_LONG).show();
        }
    }
}

