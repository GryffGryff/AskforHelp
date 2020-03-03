package com.serviceproject.gryffgryff.askforhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences sharedPreferences;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recievers);

        setVariables();
        addGroupsToArray();
        setGroupNames();
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

            }
        });

        secondGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickNewContacts();
            }
        });

        thirdGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fourthGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void pickNewContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        Toast.makeText(EditRecievers.this, "pickNewContacts was called", Toast.LENGTH_SHORT).show();
        //intent.setDataAndType(Uri.parse("content://contacts"), ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        Log.e("onActivityResult", "onActivityResult was called. RequestCode = " + requestCode + ", resultCode = " + resultCode + ", RESULT_OK = " + RESULT_OK);
        if (requestCode == PICK_CONTACT) {
            Log.e("onActivityResult", "first if statement returned true");
            if (resultCode == RESULT_OK) {
                Log.e("onActivityResult", "second if statement returned true");
                /*Uri contactUri = resultIntent.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                int column =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                Toast.makeText(EditRecievers.this, "number is "+number, Toast.LENGTH_SHORT).show();*/
            }
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

