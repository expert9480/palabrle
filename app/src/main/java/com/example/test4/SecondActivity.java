//package com.example.test4;
//
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.GridLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class SecondActivity extends AppCompatActivity {
//
//    private String targetWord = "HELLO"; // Target word for this example.
//    private String currentGuess = "";
//    private int currentRow = 0;
//    private TextView[][] gridViews = new TextView[6][5]; // 6 rows, 5 columns
//    private GridLayout wordleGrid;
//    private GridLayout keyboard;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
//
//        wordleGrid = findViewById(R.id.wordle_grid);
//        keyboard = findViewById(R.id.keyboard);
//
//        // Initialize the word grid (6 rows, 5 columns)
//        for (int row = 0; row < 6; row++) {
//            for (int col = 0; col < 5; col++) {
//                TextView textView = new TextView(this);
//                textView.setText("");
//                textView.setTextSize(24);
//                textView.setWidth(100);
//                textView.setHeight(100);
//                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
//                textView.setBackgroundResource(android.R.drawable.edit_text);
//                wordleGrid.addView(textView);
//                gridViews[row][col] = textView;
//            }
//        }
//
//        // Initialize the keyboard buttons (A-Z)
//        // Initialize the keyboard buttons (A-Z)
//        for (char c = 'A'; c <= 'Z'; c++) {
//            final char letter = c;
//            Button button = new Button(this);
//            button.setText(String.valueOf(letter));
//            button.setOnClickListener(v -> onKeyboardButtonClick(String.valueOf(letter)));
//            keyboard.addView(button);
//        }
//
//        // Add the 'Ñ' character separately after 'Z'
//        Button ñButton = new Button(this);
//        ñButton.setText("Ñ");
//        ñButton.setOnClickListener(v -> onKeyboardButtonClick("Ñ"));
//        keyboard.addView(ñButton);
//
//        // Add "Submit" and "Clear" buttons to the keyboard
//        Button submitButton = new Button(this);
//        submitButton.setText("Submit");
//        submitButton.setOnClickListener(v -> onSubmitClick());
//        keyboard.addView(submitButton);
//
//        Button clearButton = new Button(this);
//        clearButton.setText("Clear");
//        clearButton.setOnClickListener(v -> onClearClick());
//        keyboard.addView(clearButton);
//    }
//
//    private void onKeyboardButtonClick(String letter) {
//        if (currentGuess.length() < 5) {
//            currentGuess += letter;
//            updateGrid();
//        }
//    }
//
//    private void onClearClick() {
//        if (currentGuess.length() > 0) {
//            currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
//            updateGrid();
//        }
//    }
//
//    private void onSubmitClick() {
//        if (currentGuess.length() == 5) {
//            checkGuess();
//            currentGuess = "";
//            currentRow++;
//        } else {
//            Toast.makeText(this, "Guess must be 5 letters!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
//    private void checkGuess() {
//        String guess = currentGuess.toUpperCase();
//
//        // Check each letter in the guess
//        for (int i = 0; i < 5; i++) {
//            String letter = String.valueOf(guess.charAt(i));
//
//            // Check if the letter is correct (in the right position)
//            if (targetWord.charAt(i) == guess.charAt(i)) {
//                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
//                updateKeyboardButtonColor(letter, Color.GREEN); // Update the button color to green
//            }
//            // Check if the letter is in the word but in the wrong position
//            else if (targetWord.contains(letter)) {
//                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
//                updateKeyboardButtonColor(letter, Color.YELLOW); // Update the button color to yellow
//            }
//            // Letter is not in the word at all
//            else {
//                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//                updateKeyboardButtonColor(letter, Color.GRAY); // Update the button color to gray
//            }
//
//            gridViews[currentRow][i].setText(letter);
//        }
//
//        if (currentGuess.equals(targetWord)) {
//            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
//        } else if (currentRow == 5) {
//            Toast.makeText(this, "Game Over. The word was: " + targetWord, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void updateKeyboardButtonColor(String letter, int color) {
//        // Loop through all the buttons on the keyboard
//        for (int i = 0; i < keyboard.getChildCount(); i++) {
//            View view = keyboard.getChildAt(i);
//
//            // Check if the button's text matches the letter we want to color
//            if (view instanceof Button) {
//                Button button = (Button) view;
//                if (button.getText().toString().equals(letter)) {
//                    button.setBackgroundColor(color); // Set the background color of the button
//                }
//            }
//        }
//    }
//
//
////    private void checkGuess() {
////        // Here, you can implement the logic to check if the guess is correct
////        String guess = currentGuess.toUpperCase();
////        for (int i = 0; i < 5; i++) {
////            String letter = String.valueOf(guess.charAt(i));
////            if (targetWord.charAt(i) == guess.charAt(i)) {
////                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
////            } else if (targetWord.contains(letter)) {
////                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
////            } else {
////                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
////            }
////            gridViews[currentRow][i].setText(letter);
////        }
////
////        if (currentGuess.equals(targetWord)) {
////            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
////        } else if (currentRow == 5) {
////            Toast.makeText(this, "Game Over. The word was: " + targetWord, Toast.LENGTH_SHORT).show();
////        }
////    }
//
//    private void updateGrid() {
//        for (int i = 0; i < 5; i++) {
//            gridViews[currentRow][i].setText("");
//        }
//        for (int i = 0; i < currentGuess.length(); i++) {
//            gridViews[currentRow][i].setText(String.valueOf(currentGuess.charAt(i)));
//        }
//    }
//}
//
//
//


package com.example.test4;

import android.graphics.Color;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    private String targetWord; // Target word from .txt file
    private String currentGuess = "";
    private int currentRow = 0;
    private TextView[][] gridViews = new TextView[6][5]; // 6 rows, 5 columns
    private GridLayout wordleGrid;
    private GridLayout keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        targetWord = getRandomWordFromAssets(); // Load the target word from the assets file
        wordleGrid = findViewById(R.id.wordle_grid);
        keyboard = findViewById(R.id.keyboard);

        // Initialize the word grid (6 rows, 5 columns)
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                TextView textView = new TextView(this);
                textView.setText("");
                textView.setTextSize(24);
                textView.setWidth(100);
                textView.setHeight(100);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                textView.setBackgroundResource(android.R.drawable.edit_text);
                wordleGrid.addView(textView);
                gridViews[row][col] = textView;
            }
        }

        // Initialize the keyboard buttons (A-Z)
        for (char c = 'A'; c <= 'Z'; c++) {
            final char letter = c;
            Button button = new Button(this);
            button.setText(String.valueOf(letter));
            button.setOnClickListener(v -> onKeyboardButtonClick(String.valueOf(letter)));
            keyboard.addView(button);
        }

        // Add the 'Ñ' character separately after 'Z'
        Button ñButton = new Button(this);
        ñButton.setText("Ñ");
        ñButton.setOnClickListener(v -> onKeyboardButtonClick("Ñ"));
        keyboard.addView(ñButton);

        // Add "Submit" and "Clear" buttons to the keyboard
        Button submitButton = new Button(this);
        submitButton.setText("✔");
        submitButton.setOnClickListener(v -> onSubmitClick());
        keyboard.addView(submitButton);

        Button clearButton = new Button(this);
        clearButton.setText("⌫");
        clearButton.setOnClickListener(v -> onClearClick());
        keyboard.addView(clearButton);
    }

    private String getRandomWordFromAssets() {
        List<String> words = new ArrayList<>();
        AssetManager assetManager = getAssets();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("PalabrasSinAcentos/5.txt")))) {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("app/build/generated/res/assets/Palabras/5.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read the file: Palabras/5.txt");
        }

        if (!words.isEmpty()) {
            Random random = new Random();
            return words.get(random.nextInt(words.size()));
        }

        throw new RuntimeException("No words found in the file: Palabras/5.txt");
    }

    private void onKeyboardButtonClick(String letter) {
        if (currentGuess.length() < 5) {
            currentGuess += letter;
            updateGrid();
        }
    }

    private void onClearClick() {
        if (currentGuess.length() > 0) {
            currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
            updateGrid();
        }
    }

    private void onSubmitClick() {
        if (currentGuess.length() == 5) {
            checkGuess();
            currentGuess = "";
            currentRow++;
        } else {
            Toast.makeText(this, "Guess must be 5 letters!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateKeyboardButtonColor(String letter, int color) {
        // Loop through all the buttons on the keyboard
        for (int i = 0; i < keyboard.getChildCount(); i++) {
            View view = keyboard.getChildAt(i);

            // Check if the button's text matches the letter we want to color
            if (view instanceof Button) {
                Button button = (Button) view;
                if (button.getText().toString().equals(letter)) {
                    button.setBackgroundColor(color); // Set the background color of the button
                }
            }
        }
    }

    private void checkGuess() {
        String guess = currentGuess.toUpperCase();

        // Check each letter in the guess
        for (int i = 0; i < 5; i++) {
            String letter = String.valueOf(guess.charAt(i));

            // Check if the letter is correct (in the right position)
            if (targetWord.charAt(i) == guess.charAt(i)) {
                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                updateKeyboardButtonColor(letter, Color.GREEN); // Update the button color to green
            }
            // Check if the letter is in the word but in the wrong position
            else if (targetWord.contains(letter)) {
                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                updateKeyboardButtonColor(letter, Color.YELLOW); // Update the button color to yellow
            }
            // Letter is not in the word at all
            else {
                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                updateKeyboardButtonColor(letter, Color.GRAY); // Update the button color to gray
            }

            gridViews[currentRow][i].setText(letter);
        }

        if (currentGuess.equals(targetWord)) {
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
        } else if (currentRow == 5) {
            Toast.makeText(this, "Game Over. The word was: " + targetWord, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGrid() {
        for (int i = 0; i < 5; i++) {
            gridViews[currentRow][i].setText("");
        }
        for (int i = 0; i < currentGuess.length(); i++) {
            gridViews[currentRow][i].setText(String.valueOf(currentGuess.charAt(i)));
        }
    }
}
