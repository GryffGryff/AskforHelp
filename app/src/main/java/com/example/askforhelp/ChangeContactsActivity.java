package com.example.askforhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;
import com.wafflecopter.multicontactpicker.RxContacts.PhoneNumber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class ChangeContactsActivity extends AppCompatActivity {

    static final int CONTACT_PICKER_REQUEST = 3;

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

    List<String> firstGroupIds = new ArrayList<>();
    List<String> secondGroupIds = new ArrayList<>();
    List<String> thirdGroupIds = new ArrayList<>();
    List<String> fourthGroupIds = new ArrayList<>();

    List<ContactResult> contactResults = new ArrayList<>();
    Set<String> pNumbers = new HashSet<>();
    Set<String> contactIds = new HashSet<>();

    SharedPreferences sharedPreferences;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_contacts);

        setVariables();
        addGroupsToArray();
        setGroupNamesAndIds();
        setClickListener();
    }

    public void setVariables() {
        firstGroup = findViewById(R.id.firstGroup);
        secondGroup = findViewById(R.id.secondGroup);
        thirdGroup = findViewById(R.id.thirdGroup);
        fourthGroup = findViewById(R.id.fourthGroup);

        deleteFirstGroup = findViewById(R.id.deleteFirstGroup);
        deleteSecondGroup = findViewById(R.id.deleteSecondGroup);
        deleteThirdGroup = findViewById(R.id.deleteThirdGroup);
        deleteFourthGroup = findViewById(R.id.deleteFourthGroup);

        saveNewGroup = findViewById(R.id.saveNewGroup);
        setNewGroup = findViewById(R.id.setNewGroup);

        home = findViewById(R.id.homeButtonContacts);

        context = ChangeContactsActivity.this;
    }

    public void setGroupNamesAndIds() {
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            firstGroup.setText(sharedPreferences.getString("first_group", ""));
            secondGroup.setText(sharedPreferences.getString("second_group", ""));
            thirdGroup.setText(sharedPreferences.getString("third_group", ""));
            fourthGroup.setText(sharedPreferences.getString("fourth_group", ""));

            firstGroupIds.addAll(sharedPreferences.getStringSet("first_group_ids", new HashSet<String>()));
            secondGroupIds.addAll(sharedPreferences.getStringSet("second_group_ids", new HashSet<String>()));
            thirdGroupIds.addAll(sharedPreferences.getStringSet("third_group_ids", new HashSet<String>()));
            fourthGroupIds.addAll(sharedPreferences.getStringSet("fourth_group_ids", new HashSet<String>()));

        } catch (Exception e) {
            firstGroup.setText("");
            secondGroup.setText("");
            thirdGroup.setText("");
            fourthGroup.setText("");

            firstGroupIds.clear();
            secondGroupIds.clear();
            thirdGroupIds.clear();
            fourthGroupIds.clear();
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
                Intent intent = new Intent(ChangeContactsActivity.this, ChooseRequestsActivity.class);
                setNewGroupNames();
                ChangeContactsActivity.this.startActivity(intent);
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
        whichGroup = group;
        if(group == 1) {
            String[] sIds = makeIdArray(firstGroupIds.toArray());
            new MultiContactPicker.Builder(ChangeContactsActivity.this).limitToColumn(LimitColumn.PHONE).setSelectedContacts(sIds).showPickerForResult(CONTACT_PICKER_REQUEST);
        } else if (group == 2) {
            String[] sIds = makeIdArray(secondGroupIds.toArray());
            new MultiContactPicker.Builder(ChangeContactsActivity.this).limitToColumn(LimitColumn.PHONE).setSelectedContacts(sIds).showPickerForResult(CONTACT_PICKER_REQUEST);
        } else if (group == 3) {
            String[] sIds = makeIdArray(thirdGroupIds.toArray());
            new MultiContactPicker.Builder(ChangeContactsActivity.this).limitToColumn(LimitColumn.PHONE).setSelectedContacts(sIds).showPickerForResult(CONTACT_PICKER_REQUEST);
        } else if (group == 4) {
            String[] sIds = makeIdArray(fourthGroupIds.toArray());
            new MultiContactPicker.Builder(ChangeContactsActivity.this).limitToColumn(LimitColumn.PHONE).setSelectedContacts(sIds).showPickerForResult(CONTACT_PICKER_REQUEST);
        }
    }

    public String[] makeIdArray(Object[] ids) {
        int len = ids.length;
        String[] sIds = new String[len];
        for(int i = 0; i < len; i++) {
            sIds[i] = (String) ids[i];
        }
        return sIds;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_PICKER_REQUEST) {
            if(resultCode == RESULT_OK) {
                contactResults.addAll(MultiContactPicker.obtainResult(data));
                printOutResults();
            }
        }
    }


    public void printOutResults() {
        ListIterator listIterator = contactResults.listIterator();
        while (listIterator.hasNext()) {
           ContactResult contactResult =  (ContactResult) listIterator.next();
           contactIds.add(contactResult.getContactID());
           List<PhoneNumber> phoneNumbers = new ArrayList<>();
           phoneNumbers.addAll(contactResult.getPhoneNumbers());
           ListIterator numbers = phoneNumbers.listIterator();
           String mobile = "";
           while (numbers.hasNext()) {
               PhoneNumber p = (PhoneNumber) numbers.next();
               if(p.getTypeLabel().equalsIgnoreCase("mobile")) {
                   mobile = p.getNumber();
               }
           }
           pNumbers.add(mobile);
        }
        setNewNumber();
    }



    public void setNewNumber() {
        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (whichGroup == 1) {
                editor.putStringSet("first_group_number", pNumbers);
                editor.putStringSet("first_group_ids", contactIds);
            } else if (whichGroup == 2) {
                editor.putStringSet("second_group_number", pNumbers);
                editor.putStringSet("second_group_ids", contactIds);
            } else if (whichGroup == 3) {
                editor.putStringSet("third_group_number", pNumbers);
                editor.putStringSet("third_group_ids", contactIds);
            } else if (whichGroup == 4) {
                editor.putStringSet("fourth_group_number", pNumbers);
                editor.putStringSet("fourth_group_ids", contactIds);
            } else {
                //number did not get added to any group
            }
            editor.apply();
        } catch (Exception e) {
            //editing shared preferences file failed
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
            //editing shared preferences file failed
        }
    }
}

