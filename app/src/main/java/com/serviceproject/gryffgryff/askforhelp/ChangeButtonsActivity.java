package com.serviceproject.gryffgryff.askforhelp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class ChangeButtonsActivity extends AppCompatActivity {


    Button deleteFirstButton;
    Button deleteSecondButton;
    Button deleteThirdButton;
    Button deleteFourthButton;
    Button saveNewButton;

    EditText setNewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_buttons);

        setVariables();
    }

    public void setVariables() {
        deleteFirstButton = (Button) findViewById(R.id.deleteFirstButton);
        deleteSecondButton = (Button) findViewById(R.id.deleteSecondButton);
        deleteThirdButton = (Button) findViewById(R.id.deleteThirdButton);
        deleteFourthButton = (Button) findViewById(R.id.deleteFourthButton);
        saveNewButton = (Button) findViewById(R.id.saveNewButton);
        setNewButton = (EditText) findViewById(R.id.setNewButton);
    }

    public void setClickListener() {

    }
}
