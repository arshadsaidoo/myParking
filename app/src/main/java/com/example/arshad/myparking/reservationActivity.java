package com.example.arshad.myparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class reservationActivity extends AppCompatActivity {
    private CheckBox checkBoxA, checkBoxB, checkBoxC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        checkBoxA = (CheckBox) findViewById(R.id.checkBox);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox2);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox3);
        final Button res_ = (Button) findViewById(R.id.reserve);

        res_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //do a post then change activy

                Intent intent = new Intent(reservationActivity.this,mapActivity.class);
                startActivity(intent);
            }

        });
    }
    public void onCheckboxClicked(View view) {
        switch(view.getId()) {
            case R.id.checkBox:
                checkBoxB.setChecked(false);
                checkBoxC.setChecked(false);
                break;
            case R.id.checkBox2:
                checkBoxC.setChecked(false);
                checkBoxA.setChecked(false);
                break;
            case R.id.checkBox3:
                checkBoxA.setChecked(false);
                checkBoxB.setChecked(false);
                break;
        }
    }

}