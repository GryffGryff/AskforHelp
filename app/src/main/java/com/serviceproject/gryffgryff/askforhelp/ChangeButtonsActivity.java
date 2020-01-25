package com.serviceproject.gryffgryff.askforhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeButtonsActivity extends AppCompatActivity {

    TextView[] buttons = new TextView[4];
    TextView firstButton;
    TextView secondButton;
    TextView thirdButton;
    TextView fourthButton;

    Button deleteFirstButton;
    Button deleteSecondButton;
    Button deleteThirdButton;
    Button deleteFourthButton;
    Button saveNewButton;

    Button homeButton;

    Bundle buttonNames;

    EditText setNewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_buttons);

        setVariables();
        addButtonsToArray();
        setButtonNames();
        setClickListener();
    }

    public void addButtonsToArray() {
        buttons[0] = firstButton;
        buttons[1] = secondButton;
        buttons[2] = thirdButton;
        buttons[3] = fourthButton;
    }

    public void setVariables() {
        firstButton = (TextView) findViewById(R.id.firstButton);
        secondButton = (TextView) findViewById(R.id.secondButton);
        thirdButton = (TextView) findViewById(R.id.thirdButton);
        fourthButton = (TextView) findViewById(R.id.fourthButton);

        deleteFirstButton = (Button) findViewById(R.id.deleteFirstButton);
        deleteSecondButton = (Button) findViewById(R.id.deleteSecondButton);
        deleteThirdButton = (Button) findViewById(R.id.deleteThirdButton);
        deleteFourthButton = (Button) findViewById(R.id.deleteFourthButton);

        saveNewButton = (Button) findViewById(R.id.saveNewButton);
        setNewButton = (EditText) findViewById(R.id.setNewButton);

        homeButton = (Button) findViewById(R.id.homeButtonSettings);

        buttonNames = getIntent().getExtras();
    }

    public void setButtonNames() {
        firstButton.setText(buttonNames.getString("first_text"));
        secondButton.setText(buttonNames.getString("second_text"));
        thirdButton.setText(buttonNames.getString("third_text"));
        fourthButton.setText(buttonNames.getString("fourth_text"));
    }

    public void setClickListener() {
        deleteFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(0);
            }
        });

        deleteSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(1);
            }
        });

        deleteThirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(2);
            }
        });

        deleteFourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bumpUpNames(3);
            }
        });

        saveNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewButton();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeButtonsActivity.this, ChooseRequestsActivity.class);
                setNewButtonNames();
                intent.putExtras(buttonNames);
                ChangeButtonsActivity.this.startActivity(intent);
            }
        });
    }

    public void bumpUpNames(int numButtons) {
        while (numButtons < 3) {
            buttons[numButtons].setText(buttons[numButtons+1].getText().toString());
            numButtons++;
        }
        buttons[3].setText("");
        saveNewButton.setEnabled(true);
    }

    public void addNewButton() {
        String buttonName = setNewButton.getText().toString();
        int button = 0;
        while (button <= 3) {
            if (buttons[button].getText().toString() == "") {
                if(button == 3) {
                    saveNewButton.setEnabled(false);
                }
                buttons[button].setText(buttonName);
                button = 100;
            }
            button++;
        }
        setNewButton.setText("");
    }

    public void setNewButtonNames() {
        buttonNames.clear();
        buttonNames.putString("first_text", firstButton.getText().toString());
        buttonNames.putString("second_text", secondButton.getText().toString());
        buttonNames.putString("third_text", thirdButton.getText().toString());
        buttonNames.putString("fourth_text", fourthButton.getText().toString());
    }
}
