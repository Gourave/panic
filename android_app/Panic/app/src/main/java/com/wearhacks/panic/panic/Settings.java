package com.wearhacks.panic.panic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


public class Settings extends ActionBarActivity {

    EditText mAge;
    EditText mEmergency;

    int age;
    String emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        age = 0;
        emergency ="";
    }

    @Override
    public void onStart() {
        super.onStart();

        age = Integer.parseInt(mAge.getText().toString());
        emergency = mEmergency.getText().toString();

        mAge.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                //tv.setText(txtMessage.getText().toString().length());
                if (checkInteger(mAge.getText().toString())) {
                    age = Integer.parseInt(mAge.getText().toString());
                }
                else
                    age = 0;

                emergency = mEmergency.getText().toString();

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
