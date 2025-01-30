//package com.example.test4;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button buttonNavigate = findViewById(R.id.buttonNavigate);
//
//        buttonNavigate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Create an intent to navigate to the SecondActivity
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent); // Start the new activity
//            }
//        });
//    }
//}

package com.example.test4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the NumberPicker
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(3); // Minimum letter count
        numberPicker.setMaxValue(15); // Maximum letter count
        numberPicker.setValue(5); // Default value

        Button buttonNavigate = findViewById(R.id.buttonNavigate);

        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected letter count from the NumberPicker
                int letterCount = numberPicker.getValue();

                // Create an intent to navigate to the SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("LETTER_COUNT", letterCount); // Pass the letter count to SecondActivity
                startActivity(intent); // Start the new activity
            }
        });
    }
}
