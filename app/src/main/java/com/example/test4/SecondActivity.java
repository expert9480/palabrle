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

    //initializing variables
    private String targetWord;
    private String currentGuess = "";
    private int currentRow = 0;
    private TextView[][] gridViews;
    private GridLayout wordleGrid;
    private GridLayout keyboard;
    private int letterCount;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //setting variables
        letterCount = getIntent().getIntExtra("LETTER_COUNT", 5);
        Log.d("SecondActivity", "Received letterCount: " + letterCount);

        wordleGrid = findViewById(R.id.wordle_grid);
        keyboard = findViewById(R.id.keyboard);

        wordleGrid.setColumnCount(letterCount);
        wordleGrid.setRowCount(6); // Ensure the grid has 6 rows

        gridViews = new TextView[6][letterCount];

        targetWord = getRandomWordFromAssets(letterCount);

        //draws the grid of boxes for guesses
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < letterCount; col++) {
                TextView textView = new TextView(this);
                textView.setText(" ");
                textView.setTextSize(26);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundResource(android.R.drawable.edit_text);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row, 1f); // Set the row weight to 1
                params.columnSpec = GridLayout.spec(col, 1f); // Set the column weight to 1
                params.setMargins(8, 8, 8, 8); // Set margins

                textView.setLayoutParams(params);

                wordleGrid.addView(textView);
                gridViews[row][col] = textView;
            }
        }

        initializeKeyboard();
    }

    //Generates a random word with the number of letters given
    private String getRandomWordFromAssets(int letterCount) {
        Random random = new Random();
        AssetManager assetManager = getAssets();
        fileName =  letterCount + ".txt";
        Log.d("SecondActivity", "Attempting to read file: " + fileName); // Debug log

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
            Log.e("SecondActivity", "Failed to read the file: " + fileName, e); // Error log
            throw new RuntimeException("Failed to read the file: " + fileName);
        }

        Log.e("SecondActivity", "No words found in the file: " + fileName); // Error log
        throw new RuntimeException("No words found in the file: " + fileName);
    }

    //creating the keyboard because making 50 buttons is too much work .-.
    private void initializeKeyboard() {
        keyboard.setColumnCount(10);

        //a-z alphabet keys
        for (char c = 'A'; c <= 'Z'; c++) {
            final char letter = c;
            Button button = new Button(this);
            button.setText(String.valueOf(letter));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            button.setLayoutParams(params);
            keyboard.addView(button);
            button.setOnClickListener(v -> onKeyboardButtonClick(String.valueOf(letter)));
        }

        //ñ button
        Button ñButton = new Button(this);
        ñButton.setText("Ñ");
        ñButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        GridLayout.LayoutParams paramsÑ = new GridLayout.LayoutParams();
        paramsÑ.setGravity(Gravity.CENTER);
        paramsÑ.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        ñButton.setLayoutParams(paramsÑ);
        ñButton.setOnClickListener(v -> onKeyboardButtonClick("Ñ"));
        keyboard.addView(ñButton);

        //submit button
        Button submitButton = new Button(this);
        submitButton.setText("✔");
        submitButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        GridLayout.LayoutParams paramsSubmit = new GridLayout.LayoutParams();
        paramsSubmit.setGravity(Gravity.CENTER);
        paramsSubmit.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        submitButton.setLayoutParams(paramsSubmit);
        submitButton.setOnClickListener(v -> onSubmitClick());
        keyboard.addView(submitButton);

        //delete button
        Button clearButton = new Button(this);
        clearButton.setText("⌫");
        clearButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        GridLayout.LayoutParams paramsClear = new GridLayout.LayoutParams();
        paramsClear.setGravity(Gravity.CENTER);
        paramsClear.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        clearButton.setLayoutParams(paramsClear);
        clearButton.setOnClickListener(v -> onClearClick());
        keyboard.addView(clearButton);
    }

    //makes keys type
    private void onKeyboardButtonClick(String letter) {
        if (currentGuess.length() < letterCount) {
            currentGuess += letter;
            updateGrid();
        }
    }

    //makes clear key clear
    private void onClearClick() {
        if (currentGuess.length() > 0) {
            currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
            updateGrid();
        }
    }

    //makes submit button check the guess
    private void onSubmitClick() {
        if (currentGuess.length() == letterCount) {
            if (!isWordInList(currentGuess)) {
                Toast.makeText(this, "La Palabra no esta en la lista!", Toast.LENGTH_SHORT).show();
                return;
            }
            checkGuess();
            currentGuess = "";
            currentRow++;
        } else {
            Toast.makeText(this, "Palabra necesita " + letterCount + " letras!", Toast.LENGTH_SHORT).show();
        }
    }

    //Checks to see if the input word is actually is in the list and verifying if the word is a real word
    private boolean isWordInList(String word) {
        AssetManager assetManager = getAssets();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("PalabrasSinAcentos/" + fileName)))) {
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

    //checks the guess to see if guess is correct and updates keyboard and board colors
    //for some reason cant use same color system .-.
    private void checkGuess() {
        String guess = currentGuess.toUpperCase();

        boolean[] usedInGuess = new boolean[letterCount]; // To mark letters in the guess that are already used
        int[] letterCountTarget = new int[26];  // To count occurrences of each letter in the target word
        int[] yellowMarkedCount = new int[26];  // To track how many letters have been marked yellow

        for (int i = 0; i < letterCount; i++) {
            letterCountTarget[targetWord.charAt(i) - 'A']++;
        }

        // First pass: mark correct positions (green)
        for (int i = 0; i < letterCount; i++) {
            String guessLetter = String.valueOf(guess.charAt(i));

            if (targetWord.charAt(i) == guess.charAt(i)) {
                gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                updateKeyboardButtonColor(guessLetter, Color.GREEN);
                usedInGuess[i] = true;
                letterCountTarget[guessLetter.charAt(0) - 'A']--;
                gridViews[currentRow][i].setText(guessLetter);
            }
        }

        // Second pass: mark incorrect positions (yellow) and incorrect letters (gray)
        for (int i = 0; i < letterCount; i++) {
            String guessLetter = String.valueOf(guess.charAt(i));
            int letterIndex = guessLetter.charAt(0) - 'A';

            if (!usedInGuess[i]) {
                if (letterCountTarget[letterIndex] > 0 && yellowMarkedCount[letterIndex] < letterCountTarget[letterIndex]) {
                    gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    updateKeyboardButtonColor(guessLetter, Color.YELLOW);
                    yellowMarkedCount[letterIndex]++;
                    usedInGuess[i] = true; // Mark this letter as used
                } else {
                    gridViews[currentRow][i].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    updateKeyboardButtonColor(guessLetter, Color.GRAY);
                }
                gridViews[currentRow][i].setText(guessLetter);
            }
        }

        if (currentGuess.equals(targetWord)) {
            Toast.makeText(this, "Ganas!", Toast.LENGTH_SHORT).show();
        } else if (currentRow == 5) {
            Toast.makeText(this, "Perdiste. La palabra era: " + targetWord, Toast.LENGTH_SHORT).show();
        }
    }

    //helps update colors of stuff
    private void updateKeyboardButtonColor(String letter, int color) {
        // Loop through all the buttons on the keyboard
        for (int i = 0; i < keyboard.getChildCount(); i++) {
            View view = keyboard.getChildAt(i);

            // Check if the button's text matches the letter we want to color
            if (view instanceof Button) {
                Button button = (Button) view;
                if (button.getText().toString().equals(letter)) {
                    button.setBackgroundColor(color);
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
