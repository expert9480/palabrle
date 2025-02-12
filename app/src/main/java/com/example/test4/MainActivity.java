package com.example.test4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import androidx.appcompat.app.AppCompatActivity;

//public class MainActivity extends AppCompatActivity {
//
//    private NumberPicker numberPicker;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize the NumberPicker
//        numberPicker = findViewById(R.id.numberPicker);
//        numberPicker.setMinValue(3); // Minimum letter count
//        numberPicker.setMaxValue(15); // Maximum letter count
//        numberPicker.setValue(5); // Default value
//
//        Button buttonNavigate = findViewById(R.id.buttonNavigate);
//
//        buttonNavigate.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            //when clicked, sends letter count to second activity and makes the screen second screen
//            public void onClick(View v) {
//                // Get the selected letter count from the NumberPicker
//                int letterCount = numberPicker.getValue();
//
//                // Create an intent to navigate to the SecondActivity
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("LETTER_COUNT", letterCount); // Pass the letter count to SecondActivity
//                startActivity(intent); // Start the new activity
//            }
//        });
//    }
//}

public class MainActivity extends AppCompatActivity {

    private NumberPicker numberPicker;
    private NumberPicker numberPickerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the NumberPickers
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(3); // Minimum letter count
        numberPicker.setMaxValue(15); // Maximum letter count
        numberPicker.setValue(5); // Default value

        numberPickerTime = findViewById(R.id.numberPickerTime);
        numberPickerTime.setMinValue(0); // Minimum time (in minutes)
        numberPickerTime.setMaxValue(60); // Maximum time (in minutes)
        numberPickerTime.setValue(0); // Default time value (5 minutes)

        Button buttonNavigate = findViewById(R.id.buttonNavigate);

        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected values from the NumberPickers
                int letterCount = numberPicker.getValue();
                int timeLimit = numberPickerTime.getValue();

                // Create an intent to navigate to the SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("LETTER_COUNT", letterCount); // Pass the letter count to SecondActivity
                intent.putExtra("TIME_LIMIT", timeLimit); // Pass the time limit to SecondActivity
                startActivity(intent); // Start the new activity
            }
        });
    }
}

