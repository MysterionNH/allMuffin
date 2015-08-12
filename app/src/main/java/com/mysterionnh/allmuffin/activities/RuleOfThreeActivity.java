package com.mysterionnh.allmuffin.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.helper.Errors;

/**
 * Weird calculator for percentage and rule of three stuff
 */
public class RuleOfThreeActivity extends BaseActivity {

    private final Context _context = this;

    // Count of TextViews in Layout, is important!
    private final int INPUT_FIELD_COUNT = 6;

    // Initializes the arrays used here
    private final String[] texts = new String[INPUT_FIELD_COUNT];
    private final boolean[] empty = new boolean[INPUT_FIELD_COUNT];
    private final View.OnClickListener btnListener;
    private EditText[] eTexts;

    {
        btnListener = new View.OnClickListener() {

            public void onClick(View v) {
                // Magic Stuff, don't touch
                getTexts();
                areInputsEmpty();
                calculate();
                updateFields(texts, eTexts);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_rule_of_three);
        setBGColorAccordingToSettings(_context);

        // Sets up the button listener for the submit button
        findViewById(R.id.submitBtn).setOnClickListener(btnListener);
    }

    private void updateFields(String[] texts, TextView[] eTexts) {

        for (int i = 0; i < texts.length; i++) {
            eTexts[i].setText(texts[i]);
        }
    }

    private void getTexts() {
        // Get all TextFields in Activity, put them into an array
        // Assign to global variable for later use @see updateFields()
        eTexts = new EditText[]{(EditText) findViewById(R.id.currentPercent),
                (EditText) findViewById(R.id.currentValue),
                (EditText) findViewById(R.id.valueOne),
                (EditText) findViewById(R.id.valueHundred),
                (EditText) findViewById(R.id.questionedPercent),
                (EditText) findViewById(R.id.questionedValue)};

        // Extract the text out of the TextFields
        for (int i = 0; i < INPUT_FIELD_COUNT; i++) {
            texts[i] = eTexts[i].getText().toString();
        }
    }

    // Looks up if the inputs are empty or not usable
    private void areInputsEmpty() {
        for (int i = 0; i < INPUT_FIELD_COUNT; i++) {
            empty[i] = texts[i].isEmpty();

            // Okay, there is text in it, but is it a double?
            if (!empty[i]) {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    Double.parseDouble(texts[i]);
                } catch (Exception e) {
                    // Nope it isn't, so it's empty for our uses
                    empty[i] = true;
                }
            }
        }
        // Okay, done so far, but to avoid a dividing by 0, one last check
        if (!empty[0]) {
            empty[0] = (Double.parseDouble(texts[0]) == 0.0);
        }
    }

    // Actually calculates stuff, if possible
    private void calculate() {
        if (!empty[0] && !empty[1]) {
            // Set value(s)
            texts[2] = String.valueOf(Double.parseDouble(texts[1]) / Double.parseDouble(texts[0]));
            texts[3] = String.valueOf(100 * (Double.parseDouble(texts[1]) / Double.parseDouble(texts[0])));
            lastLine();
        } else if (empty[2] && empty[3]) {
            // Nothing to calculate, stupid user
            Errors.errorPopup(_context, getResources().getString(R.string.title_wrong_input),
                    getResources().getString(R.string.message_wrong_input),
                    getResources().getString(R.string.button_wrong_input));
        } else if (!empty[2]) {
            // In this case the first two TextViews are useless, hence I "empty" them
            texts[0] = "---";
            texts[1] = "---";

            // Set value(s)
            texts[3] = String.valueOf(100 * Double.parseDouble(texts[2]));
            lastLine();
        } else {
            // In this case the first two TextViews are useless, hence I "empty" them
            texts[0] = "---";
            texts[1] = "---";

            // Set value(s)
            texts[2] = String.valueOf(Double.parseDouble(texts[3]) / 100);
            lastLine();
        }
    }

    // Sets the content of the last line in the Activity, if requested hence the if 'n stuff
    private void lastLine() {
        if (!empty[4]) {
            texts[5] = String.valueOf(Double.parseDouble(texts[2]) * Double.parseDouble(texts[4]));
        } else if (!empty[5]) {
            texts[4] = String.valueOf(Double.parseDouble(texts[5]) / Double.parseDouble(texts[2]));
        }
    }
}