package com.wearhacks.panic.panic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Settings extends ActionBarActivity {

    EditText mName;
    EditText mAge;
    EditText mEmergency;

    String name;
    int age;
    String emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mName = (EditText)findViewById(R.id.etName);
        mAge = (EditText)findViewById(R.id.etAge);
        mEmergency = (EditText)findViewById(R.id.etEmergencyContact);

        name = "";
        age = 0;
        emergency ="";
    }

    @Override
    public void onStart() {
        super.onStart();

        mAge.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if (checkInteger(mAge.getText().toString())) {
                    age = Integer.parseInt(mAge.getText().toString());
                }
                else
                    age = 0;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }

    protected boolean checkInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

}
