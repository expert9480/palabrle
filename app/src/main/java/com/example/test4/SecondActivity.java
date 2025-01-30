//package com.example.test4;
//
//import android.graphics.Color;
//import android.content.res.AssetManager;
//import android.os.Bundle;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.GridLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class SecondActivity extends AppCompatActivity {
//
//    private String targetWord; // Target word from .txt file
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
//        targetWord = getRandomWordFromAssets(); // Load the target word from the assets file
//        wordleGrid = findViewById(R.id.wordle_grid);
//        keyboard = findViewById(R.id.keyboard);
//
//        // Initialize the word grid (6 rows, 5 columns)
//        for (int row = 0; row < 6; row++) {
//            for (int col = 0; col < 5; col++) {
//                TextView textView = new TextView(this);
//                textView.setText(" ");
//                textView.setTextSize(26);
//                textView.setWidth(100);
//                textView.setHeight(90);
//                textView.setGravity(Gravity.CENTER); // Center the text within the cell
//                textView.setBackgroundResource(android.R.drawable.edit_text);
//
//                // Create layout parameters for the TextView with margins
//                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//                params.setGravity(Gravity.CENTER);
//                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f); // Ensure cells equally fill the grid columns
//
//                // Set margins to add space on the left and right sides
//                int marginSize = 8; // Adjust this value as needed
//                params.setMargins(marginSize, 0, marginSize, 10);
//
//                // Set the layout parameters to the TextView
//                textView.setLayoutParams(params);
//
//                // Add the TextView to the grid
//                wordleGrid.addView(textView);
//                gridViews[row][col] = textView;
//            }
//        }
//
//
//
//
//
//
//        // Initialize the keyboard buttons (A-Z)
//// Initialize the keyboard buttons (A-Z)
//        keyboard.setColumnCount(10); // Adjust the number of columns for the keyboard layout
//        for (char c = 'A'; c <= 'Z'; c++) {
//            final char letter = c;
//            Button button = new Button(this);
//            button.setText(String.valueOf(letter));
//            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//            params.setGravity(Gravity.CENTER);
//            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
//            button.setLayoutParams(params);
//            keyboard.addView(button);
//            button.setOnClickListener(v -> onKeyboardButtonClick(String.valueOf(letter)));
//        }
//
//        // Add the 'Ñ' character separately after 'Z'
//        Button ñButton = new Button(this);
//        ñButton.setText("Ñ");
//        ñButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        GridLayout.LayoutParams paramsÑ = new GridLayout.LayoutParams();
//        paramsÑ.setGravity(Gravity.CENTER);
//        paramsÑ.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
//        ñButton.setLayoutParams(paramsÑ);
//        ñButton.setOnClickListener(v -> onKeyboardButtonClick("Ñ"));
//        keyboard.addView(ñButton);
//
//        // Add "Submit" and "Clear" buttons to the keyboard
//        Button submitButton = new Button(this);
//        submitButton.setText("✔");
//        submitButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        GridLayout.LayoutParams paramsSubmit = new GridLayout.LayoutParams();
//        paramsSubmit.setGravity(Gravity.CENTER);
//        paramsSubmit.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
//        submitButton.setLayoutParams(paramsSubmit);
//        submitButton.setOnClickListener(v -> onSubmitClick());
//        keyboard.addView(submitButton);
//
//        Button clearButton = new Button(this);
//        clearButton.setText("⌫");
//        clearButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        GridLayout.LayoutParams paramsClear = new GridLayout.LayoutParams();
//        paramsClear.setGravity(Gravity.CENTER);
//        paramsClear.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
//        clearButton.setLayoutParams(paramsClear);
//        clearButton.setOnClickListener(v -> onClearClick());
//        keyboard.addView(clearButton);
//    }
package com.example.test4;

import android.graphics.Color;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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
    private TextView[][] gridViews;
    private GridLayout wordleGrid;
    private GridLayout keyboard;
    private int letterCount; // Letter count passed from MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get the letter count from the intent
        letterCount = getIntent().getIntExtra("LETTER_COUNT", 5); // Default to 5 if not provided
        Log.d("SecondActivity", "Received letterCount: " + letterCount); // Debug log

        // Initialize the grid and keyboard
        wordleGrid = findViewById(R.id.wordle_grid);
        keyboard = findViewById(R.id.keyboard);

        // Dynamically set the grid column count based on the letter count
        wordleGrid.setColumnCount(letterCount);
        gridViews = new TextView[6][letterCount]; // 6 rows, dynamic columns based on letter count

        // Load the target word based on the selected letter count
        targetWord = getRandomWordFromAssets(letterCount);

        // Initialize the word grid (6 rows, dynamic columns)
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < letterCount; col++) {
                TextView textView = new TextView(this);
                textView.setText(" ");
                textView.setTextSize(26);
                textView.setWidth(100);
                textView.setHeight(90);
                textView.setGravity(Gravity.CENTER); // Center the text within the cell
                textView.setBackgroundResource(android.R.drawable.edit_text);

                // Create layout parameters for the TextView with margins
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setGravity(Gravity.CENTER);
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f); // Ensure cells equally fill the grid columns

                // Set margins to add space on the left and right sides
                int marginSize = 8; // Adjust this value as needed
                params.setMargins(marginSize, 0, marginSize, 10);

                // Set the layout parameters to the TextView
                textView.setLayoutParams(params);

                // Add the TextView to the grid
                wordleGrid.addView(textView);
                gridViews[row][col] = textView;
            }
        }



        // Initialize the keyboard buttons (A-Z, Ñ, Submit, Clear)
        initializeKeyboard();
    }

    private String getRandomWordFromAssets(int letterCount) {
        Random random = new Random();
        AssetManager assetManager = getAssets();
        System.out.println(letterCount);
        String fileName =  letterCount + ".txt"; // Dynamically generate the file name (e.g., 3.txt, 4.txt, etc.)
        System.out.println(fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("PalabrasSinAcentos/" + fileName)))) {
            String line;
            List<String> words = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toUpperCase());
            }
            if (!words.isEmpty()) {
                return words.get(random.nextInt(words.size()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read the file: " + fileName);
        }

        throw new RuntimeException("No words found in the file: " + fileName);
    }

    private void initializeKeyboard() {
        keyboard.setColumnCount(10); // Adjust the number of columns for the keyboard layout
        for (char c = 'A'; c <= 'Z'; c++) {
            final char letter = c;
            Button button = new Button(this);
            button.setText(String.valueOf(letter));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
            button.setLayoutParams(params);
            keyboard.addView(button);
            button.setOnClickListener(v -> onKeyboardButtonClick(String.valueOf(letter)));
        }

        // Add the 'Ñ' character separately after 'Z'
        Button ñButton = new Button(this);
        ñButton.setText("Ñ");
        ñButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        GridLayout.LayoutParams paramsÑ = new GridLayout.LayoutParams();
        paramsÑ.setGravity(Gravity.CENTER);
        paramsÑ.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
        ñButton.setLayoutParams(paramsÑ);
        ñButton.setOnClickListener(v -> onKeyboardButtonClick("Ñ"));
        keyboard.addView(ñButton);

        // Add "Submit" and "Clear" buttons to the keyboard
        Button submitButton = new Button(this);
        submitButton.setText("✔");
        submitButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        GridLayout.LayoutParams paramsSubmit = new GridLayout.LayoutParams();
        paramsSubmit.setGravity(Gravity.CENTER);
        paramsSubmit.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
        submitButton.setLayoutParams(paramsSubmit);
        submitButton.setOnClickListener(v -> onSubmitClick());
        keyboard.addView(submitButton);

        Button clearButton = new Button(this);
        clearButton.setText("⌫");
        clearButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        GridLayout.LayoutParams paramsClear = new GridLayout.LayoutParams();
        paramsClear.setGravity(Gravity.CENTER);
        paramsClear.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Ensure buttons equally fill the grid columns
        clearButton.setLayoutParams(paramsClear);
        clearButton.setOnClickListener(v -> onClearClick());
        keyboard.addView(clearButton);
    }

    private void onKeyboardButtonClick(String letter) {
        if (currentGuess.length() < letterCount) {
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
        if (currentGuess.length() == letterCount) {
            if (!isWordInList(currentGuess)) {
                Toast.makeText(this, "Word not in list", Toast.LENGTH_SHORT).show();
                return;
            }
            checkGuess();
            currentGuess = "";
            currentRow++;
        } else {
            Toast.makeText(this, "Guess must be " + letterCount + " letters!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isWordInList(String word) {
        AssetManager assetManager = getAssets();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("PalabrasSinAcentos/5.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().toUpperCase().equals(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void checkGuess() {
        String guess = currentGuess.toUpperCase();

        // Arrays to track the status of letters
        boolean[] usedInGuess = new boolean[letterCount]; // To mark letters in the guess that are already used
        int[] letterCountTarget = new int[26];  // To count occurrences of each letter in the target word
        int[] letterCountGuess = new int[26];   // To count occurrences of each letter in the guess

        // Count the occurrences of each letter in the target word
        for (int i = 0; i < letterCount; i++) {
            letterCountTarget[targetWord.charAt(i) - 'A']++;
        }

        // First pass: Check for correct letters in the correct positions (green)
        for (int i = 0; i < letterCount; i++) {
            String letter = String.valueOf(guess.charAt(i));

            // Check if the letter is correct and in the right position
            if (targetWord.charAt(i) == guess.charAt(i)) {
                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                updateKeyboardButtonColor(letter, Color.GREEN); // Update the button color to green
                usedInGuess[i] = true; // Mark this letter as used (green)
                letterCountTarget[letter.charAt(0) - 'A']--; // Decrease the target count for this letter
                gridViews[currentRow][i].setText(letter);
            }
        }

        // Second pass: Check for correct letters in the wrong positions (yellow)
        for (int i = 0; i < letterCount; i++) {
            String letter = String.valueOf(guess.charAt(i));

            // Only check yellow if the letter hasn't been used already (i.e., not already green)
            if (!usedInGuess[i]) {
                // Check if the letter is in the target word and not in the right position
                if (targetWord.contains(letter) && letterCountTarget[letter.charAt(0) - 'A'] > 0) {
                    gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    updateKeyboardButtonColor(letter, Color.YELLOW); // Update the button color to yellow
                    letterCountTarget[letter.charAt(0) - 'A']--; // Decrease the available count for this letter in target word
                    gridViews[currentRow][i].setText(letter);
                } else {
                    gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    updateKeyboardButtonColor(letter, Color.GRAY); // Update the button color to gray
                    gridViews[currentRow][i].setText(letter);
                }
            }
        }

        // Check if the guess is correct
        if (currentGuess.equals(targetWord)) {
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
        } else if (currentRow == 5) {
            Toast.makeText(this, "Game Over. The word was: " + targetWord, Toast.LENGTH_SHORT).show();
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

    private void updateGrid() {
        for (int i = 0; i < letterCount; i++) {
            gridViews[currentRow][i].setText("");
        }
        for (int i = 0; i < currentGuess.length(); i++) {
            gridViews[currentRow][i].setText(String.valueOf(currentGuess.charAt(i)));
        }
    }
}
