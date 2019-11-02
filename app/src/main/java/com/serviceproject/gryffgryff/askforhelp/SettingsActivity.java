package com.serviceproject.gryffgryff.askforhelp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity{

    MainActivity mainActivity;
    LinearLayout linearLayout;

    Button changeRequests;
    Button addGroups;

    EditText setButtonName;
    EditText setRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setVariables();
        setClickListener();
    }

    public void setVariables() {
        mainActivity = new MainActivity();
        linearLayout = (LinearLayout) findViewById(R.id.settingsLayout);
        changeRequests = (Button) findViewById(R.id.changeRequests);
        addGroups = (Button) findViewById(R.id.addGroups);
    }

    public void setClickListener() {
        changeRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditText("Enter button name", 1);
                addEditText("Enter request to text", 2);
            }
        });

        addGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void addEditText(String hint, Integer id) {
        final EditText editText = new EditText(this);
        editText.setId(id);
        editText.setHint(hint);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setPadding(20, 20, 20, 20);

        if(linearLayout != null) {
            linearLayout.addView(editText);
        }
    }
}
