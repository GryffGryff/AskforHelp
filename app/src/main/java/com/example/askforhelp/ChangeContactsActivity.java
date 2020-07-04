package com.example.askforhelp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

    Button settings;

    EditText setNewGroup;

    List[] ids = new List[4];

    List<String> firstGroupIds = new ArrayList<>();
    List<String> secondGroupIds = new ArrayList<>();
    List<String> thirdGroupIds = new ArrayList<>();
    List<String> fourthGroupIds = new ArrayList<>();

    List[] numbers = new List[4];

    List<String> firstGroupNumbers = new ArrayList<>();
    List<String> secondGroupNumbers = new ArrayList<>();
    List<String> thirdGroupNumbers = new ArrayList<>();
    List<String> fourthGroupNumbers = new ArrayList<>();

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

    protected void onResume() {
        super.onResume();
        checkFirstContact();
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

        settings = findViewById(R.id.settingsButtonContacts);

        context = ChangeContactsActivity.this;

        try {
            sharedPreferences = context.getSharedPreferences("com.serviceproject.gryffgryff.askforhelp.PREFERENCES", Context.MODE_PRIVATE);
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
    }

    public void checkFirstContact() {
        if(sharedPreferences.getBoolean("contactsCheck", true)) {
            AlertDialog.Builder contactsDialogBuilder = new AlertDialog.Builder(context);
            contactsDialogBuilder.setMessage("This is where you set up groups of contacts to text. To name the group, type the name down below and press the save new group button. To add or remove contacts from a group, press on the button with that group's name on it. The small buttons on the right let you delete a group.");
            contactsDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //user read message
                }
            });
            AlertDialog contactsDialog = contactsDialogBuilder.create();
            contactsDialog.show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("contactsCheck", false);
            editor.apply();
        }
    }

    public void setGroupNamesAndIds() {
        try {
            firstGroup.setText(sharedPreferences.getString("first_group", ""));
            secondGroup.setText(sharedPreferences.getString("second_group", ""));
            thirdGroup.setText(sharedPreferences.getString("third_group", ""));
            fourthGroup.setText(sharedPreferences.getString("fourth_group", ""));

            firstGroupIds.addAll(sharedPreferences.getStringSet("first_group_ids", new HashSet<String>()));
            secondGroupIds.addAll(sharedPreferences.getStringSet("second_group_ids", new HashSet<String>()));
            thirdGroupIds.addAll(sharedPreferences.getStringSet("third_group_ids", new HashSet<String>()));
            fourthGroupIds.addAll(sharedPreferences.getStringSet("fourth_group_ids", new HashSet<String>()));

            firstGroupNumbers.addAll(sharedPreferences.getStringSet("first_group_number", new HashSet<String>()));
            secondGroupNumbers.addAll(sharedPreferences.getStringSet("second_group_number", new HashSet<String>()));
            thirdGroupNumbers.addAll(sharedPreferences.getStringSet("third_group_number", new HashSet<String>()));
            fourthGroupNumbers.addAll(sharedPreferences.getStringSet("fourth_group_number", new HashSet<String>()));
        } catch (Exception e) {
            firstGroup.setText("");
            secondGroup.setText("");
            thirdGroup.setText("");
            fourthGroup.setText("");

            firstGroupIds.clear();
            secondGroupIds.clear();
            thirdGroupIds.clear();
            fourthGroupIds.clear();

            firstGroupNumbers.clear();
            secondGroupNumbers.clear();
            thirdGroupNumbers.clear();
            fourthGroupNumbers.clear();
        }
    }

    public void addGroupsToArray() {
        groups[0] = firstGroup;
        groups[1] = secondGroup;
        groups[2] = thirdGroup;
        groups[3] = fourthGroup;

        ids[0] = firstGroupIds;
        ids[1] = secondGroupIds;
        ids[2] = thirdGroupIds;
        ids[3] = fourthGroupIds;

        numbers[0] = firstGroupNumbers;
        numbers[1] = secondGroupNumbers;
        numbers[2] = thirdGroupNumbers;
        numbers[3] = fourthGroupNumbers;
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
                deleteIds("first_group_ids");
            }
        });

        deleteSecondGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(1);
                deleteIds("second_group_ids");
            }
        });

        deleteThirdGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(2);
                deleteIds("third_group_ids");
            }
        });

        deleteFourthGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(3);
                deleteIds("fourth_group_ids");
            }
        });

        saveNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewGroup();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeContactsActivity.this, SettingsActivity.class);
                setNewInformation();
                ChangeContactsActivity.this.startActivity(intent);
            }
        });
    }

    public void bumpUpNames(int numGroups) {
        while (numGroups < 3) {
            groups[numGroups].setText(groups[numGroups+1].getText().toString());
            ids[numGroups].clear();
            ids[numGroups].addAll(ids[numGroups+1]);
            numbers[numGroups].clear();
            numbers[numGroups].addAll(numbers[numGroups+1]);
            numGroups++;
        }
        groups[3].setText("");
        ids[3].clear();
        numbers[3].clear();
        saveNewGroup.setEnabled(true);

    }

    public void deleteIds(String idGroup) {
        try{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(idGroup, new HashSet<String>());
            editor.apply();
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
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
                contactResults.clear();
                contactResults.addAll(MultiContactPicker.obtainResult(data));
                getResultsFromContactSelection();
            }
        }
    }


    public void getResultsFromContactSelection() {
        ListIterator listIterator = contactResults.listIterator();
        pNumbers.clear();
        contactIds.clear();
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
        setNewNumbers();
    }

    public void setNewNumbers() {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (whichGroup == 1) {
                editor.putStringSet("first_group_ids", contactIds);
                firstGroupIds.clear();
                firstGroupIds.addAll(contactIds);
                firstGroupNumbers.clear();
                firstGroupNumbers.addAll(pNumbers);
            } else if (whichGroup == 2) {
                editor.putStringSet("second_group_ids", contactIds);
                secondGroupIds.clear();
                secondGroupIds.addAll(contactIds);
                secondGroupNumbers.clear();
                secondGroupNumbers.addAll(pNumbers);
            } else if (whichGroup == 3) {
                editor.putStringSet("third_group_ids", contactIds);
                thirdGroupIds.clear();
                thirdGroupIds.addAll(contactIds);
                thirdGroupNumbers.clear();
                thirdGroupNumbers.addAll(pNumbers);
            } else if (whichGroup == 4) {
                editor.putStringSet("fourth_group_ids", contactIds);
                fourthGroupIds.clear();
                fourthGroupIds.addAll(contactIds);
                fourthGroupNumbers.clear();
                fourthGroupNumbers.addAll(pNumbers);
            } else {
                //number did not get added to any group
            }
            editor.apply();
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
    }

    public Set<String> createSetFromList(List<String> listToChange) {
        Set<String> set = new HashSet<>();
        ListIterator listIterator = listToChange.listIterator();
        while(listIterator.hasNext()) {
            set.add(listIterator.next().toString());
        }
        return set;
    }

    public void setNewInformation() {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first_group", firstGroup.getText().toString());
            editor.putString("second_group", secondGroup.getText().toString());
            editor.putString("third_group", thirdGroup.getText().toString());
            editor.putString("fourth_group", fourthGroup.getText().toString());

            editor.putStringSet("first_group_ids", createSetFromList(firstGroupIds));
            editor.putStringSet("second_group_ids", createSetFromList(secondGroupIds));
            editor.putStringSet("third_group_ids", createSetFromList(thirdGroupIds));
            editor.putStringSet("fourth_group_ids", createSetFromList(fourthGroupIds));

            editor.putStringSet("first_group_number", createSetFromList(firstGroupNumbers));
            editor.putStringSet("second_group_number", createSetFromList(secondGroupNumbers));
            editor.putStringSet("third_group_number", createSetFromList(thirdGroupNumbers));
            editor.putStringSet("fourth_group_number", createSetFromList(fourthGroupNumbers));

            editor.apply();
        } catch (Exception e) {
            //failed to edit shared preferences file
            Toast.makeText(context, "There was an error. Please close the app and restart it.", Toast.LENGTH_LONG).show();
        }
    }
}


